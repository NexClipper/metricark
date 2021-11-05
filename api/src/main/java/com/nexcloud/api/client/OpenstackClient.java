package com.nexcloud.api.client;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.StringJoiner;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class OpenstackClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(OpenstackClient.class);
    private static final JsonNodeFactory NODE_FACTORY = JsonNodeFactory.instance;
    private static final Integer RETRY_CNT = 5;
    private static final String AUTH_TOKEN_ENDPOINT = "/identity/v3/auth/tokens";
    private static final String AUTH_TOKEN_REQUEST_HEADER_NAME = "X-Auth-Token";
    private static final String AUTH_TOKEN_RESPONSE_HEADER_NAME = "X-Subject-Token";
    private final RestTemplate restTemplate = new RestTemplate();
    private final Map<String, String> tokenCache = new ConcurrentHashMap<>();

    @Value("${openstack.endpoint}")
    private String ENDPOINT;
    @Value("${openstack.username}")
    private String USERNAME;
    @Value("${openstack.password}")
    private String PASSWORD;
    @Value("${openstack.userdomain}")
    private String USERDOMAIN;
    @Value("${openstack.keystoneport}")
    private String KEYSTONEPORT;


    public ResponseEntity<String> executeHttpRequest(String targetUrl, String projectName, String domainId) {
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

        ResponseEntity<String> response = null;

        for (int cnt = 0; cnt < RETRY_CNT; ++cnt) {

            try {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                headers.add(AUTH_TOKEN_REQUEST_HEADER_NAME, checkTokenCacheAndGetToken(projectName, domainId));

                HttpEntity<String> request = new HttpEntity<>(headers);

                response = restTemplate.exchange(targetUrl, HttpMethod.GET, request, String.class);
                LOGGER.debug("STATUS CODE: " + response.getStatusCodeValue());
                LOGGER.debug("CACHED TOKEN: " + tokenCache.get(getTokenCacheKey(projectName, domainId)));
                LOGGER.debug("Success");

                return response;
            } catch (HttpClientErrorException he) {
                LOGGER.debug("HttpClientErrorException getStatusCode : {}", he.getStatusCode());
                LOGGER.debug("HttpClientErrorException getResponseBodyAsString : {}", he.getResponseBodyAsString());

                if (he.getStatusCode().equals(HttpStatus.UNAUTHORIZED)) {
                    // 캐싱되어있는 토큰이 만료되었다면 토큰을 새로 받아서 tokenCache에 저장한다
                    getAuthenticationToken(projectName, domainId);
                    LOGGER.debug("TokenCache update done");

                    if (areValidTokenParameters(projectName, domainId)) {
                        LOGGER.debug("**CACHED TOKEN: " + tokenCache.get(getTokenCacheKey(projectName, domainId)));
                        continue;
                    }
                }

                he.printStackTrace();
                // 401 UNAUTHORIZED 가 아니면 다시 던진다
                LOGGER.warn("Request Failed (HttpClientErrorException)", he);
                throw he;
            } catch (RestClientException re) {
                re.printStackTrace();
                LOGGER.warn("Request Failed (RestClientException)", re);
                throw re;
            } catch (Exception e) {
                e.printStackTrace();
                LOGGER.warn("Request Failed (Exception)", e);
                throw e;
            }

        } // end of for loop

        return response;
    }

    private boolean areValidTokenParameters(String projectName, String domainId) {
        return tokenCache.get(getTokenCacheKey(projectName, domainId)) != null;
    }

    private String checkTokenCacheAndGetToken(String projectName, String domainId) {
        String tokenCacheKey = getTokenCacheKey(projectName, domainId);
        return StringUtils.isEmpty(this.tokenCache.get(tokenCacheKey)) ? getAuthenticationToken(projectName, domainId) : this.tokenCache.get(tokenCacheKey);
    }

    // Authentication Token 획득
    private String getAuthenticationToken(String projectName, String domainId) {

        try {
            String tokenCacheKey = getTokenCacheKey(projectName, domainId);

            for (int cnt = 0; cnt < RETRY_CNT; ++cnt) {
                String authTokenUrl = ENDPOINT + ":" + KEYSTONEPORT + AUTH_TOKEN_ENDPOINT;

                // Request Header에 Data type 입력(Application/json)
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);

                // Request Body에 인증정보를 입력하고 HttpEntity 객체 생성
                HttpEntity<String> request = new HttpEntity<>(getProjectScopedAuthenticationTokenRequestBody(projectName, domainId).toString(), headers);

                // POST요청을 보내서 토큰을 받아온다 (받아온 토큰은 Response Header에 저장됨)
                restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
                ResponseEntity<String> response = restTemplate.postForEntity(authTokenUrl, request, String.class);


                if (response.getStatusCode().is2xxSuccessful()) {
                    this.tokenCache.put(tokenCacheKey, response.getHeaders().get(AUTH_TOKEN_RESPONSE_HEADER_NAME).get(0));
                    LOGGER.debug("Got Authentication Token");
                    break;
                }
            }
            return tokenCache.get(tokenCacheKey);
        } catch (RestClientException re) {
            re.printStackTrace();
            LOGGER.warn("Failed to get Authentication Token (RestClientException)", re);
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.warn("Failed to get Authentication Token (Exception)", e);
            return null;
        }
    }

    private String getTokenCacheKey(String projectName, String domainId) {
        if (StringUtils.isEmpty(projectName)) {
            throw new IllegalArgumentException("Project Name is Necessary");
        }
        if (StringUtils.isEmpty(domainId)) {
            throw new IllegalArgumentException("Domain ID is Necessary");
        }

        StringJoiner stringJoiner = new StringJoiner("_");
        return stringJoiner.add(projectName).add(domainId).toString();
    }

    // Authentication Token을 획득하기 위한 HTTP 요청에 담을 Request Body 생성 메서드
    private ObjectNode getProjectScopedAuthenticationTokenRequestBody(String projectName, String domainId) {
        ObjectNode domainID = NODE_FACTORY.objectNode();
        domainID.put("id", USERDOMAIN);
        // domain

        ObjectNode userObj = NODE_FACTORY.objectNode();
        userObj.set("domain", domainID);
        userObj.put("name", USERNAME);
        userObj.put("password", PASSWORD);

        ObjectNode user = NODE_FACTORY.objectNode();
        user.set("user", userObj);
        // user

        ArrayNode methodsObj = NODE_FACTORY.arrayNode();
        methodsObj.add("password");
        // password

        ObjectNode methods = NODE_FACTORY.objectNode();
        methods.set("methods", methodsObj);
        // identity methods

        ObjectNode identity = NODE_FACTORY.objectNode();
        identity.set("methods", methodsObj);
        identity.set("password", user);
        // identity

        // --------------------------------------------------

        ObjectNode projectDomainId = NODE_FACTORY.objectNode();
        projectDomainId.put("id", domainId);
        // project domain

        ObjectNode projectObj = NODE_FACTORY.objectNode();
        projectObj.set("domain", projectDomainId);
        projectObj.put("name", projectName);
        // project

        ObjectNode scope = NODE_FACTORY.objectNode();
        scope.set("project", projectObj);
        // scope

        // --------------------------------------------------

        ObjectNode authObj = NODE_FACTORY.objectNode();
        authObj.set("identity", identity);
        authObj.set("scope", scope);

        ObjectNode auth = NODE_FACTORY.objectNode();
        auth.set("auth", authObj);
        // auth

        return auth;
    }

    private ObjectNode getDomainScopedAuthenticationTokenRequestBody(String domainId) {
        ObjectNode domainID = NODE_FACTORY.objectNode();
        domainID.put("id", USERDOMAIN);
        // domain

        ObjectNode userObj = NODE_FACTORY.objectNode();
        userObj.set("domain", domainID);
        userObj.put("name", USERNAME);
        userObj.put("password", PASSWORD);

        ObjectNode user = NODE_FACTORY.objectNode();
        user.set("user", userObj);
        // user

        ArrayNode methodsObj = NODE_FACTORY.arrayNode();
        methodsObj.add("password");
        // password

        ObjectNode methods = NODE_FACTORY.objectNode();
        methods.set("methods", methodsObj);
        // identity methods

        ObjectNode identity = NODE_FACTORY.objectNode();
        identity.set("methods", methodsObj);
        identity.set("password", user);
        // identity

        // --------------------------------------------------

        ObjectNode projectDomainId = NODE_FACTORY.objectNode();
        projectDomainId.put("id", domainId);
        // project domain

        ObjectNode scope = NODE_FACTORY.objectNode();
        scope.set("domain", projectDomainId);
        // scope

        // --------------------------------------------------

        ObjectNode authObj = NODE_FACTORY.objectNode();
        authObj.set("identity", identity);
        authObj.set("scope", scope);

        ObjectNode auth = NODE_FACTORY.objectNode();
        auth.set("auth", authObj);
        // auth

        return auth;
    }
}
