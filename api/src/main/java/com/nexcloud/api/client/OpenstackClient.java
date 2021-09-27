package com.nexcloud.api.client;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class OpenstackClient {

    @Value("${openstack.endpoint}")
    private String ENDPOINT;
    @Value("${openstack.username}")
    private String USERNAME;
    @Value("${openstack.password}")
    private String PASSWORD;

    private static final Logger LOGGER = LoggerFactory.getLogger(OpenstackClient.class);
    private static final JsonNodeFactory NODE_FACTORY = JsonNodeFactory.instance;
    private final RestTemplate restTemplate = new RestTemplate();

    private final Integer RETRY_CNT = 5;

    private static String token;

    @PostConstruct
    private void init() {
        this.token = getAuthenticationToken();
    }

    public String getToken() {
        return StringUtils.isEmpty(this.token) ? getAuthenticationToken() : this.token;
    }

    // Authentication Token 획득
    public String getAuthenticationToken() {
        try {
            for (int cnt = 0; cnt < RETRY_CNT; ++cnt) {
                String authTokenUrl = ENDPOINT + "/identity/v3/auth/tokens";
                // Request Header에 Data type 입력(Application/json)
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);

                // Request Body에 인증정보를 입력하고 HttpEntity 객체 생성
                HttpEntity<String> request = new HttpEntity<>(getAuthenticationTokenRequestBody().toString(), headers);

                // POST요청을 보내서 토큰을 받아온다 (Response Header에 위치)
                restTemplate.getMessageConverters().add(new StringHttpMessageConverter());
                ResponseEntity<String> response = restTemplate.postForEntity(authTokenUrl, request, String.class);

                if (response.getStatusCode().is2xxSuccessful()) {
                    this.token = response.getHeaders().get("X-Subject-Token").get(0);
                    break;
                }
            }

            return token;
        } catch (RestClientException re) {
            re.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Authentication Token을 획득하기 위한 HTTP 요청에 담을 Request Body 생성 메서드
    private ObjectNode getAuthenticationTokenRequestBody() {
        ObjectNode domainID = NODE_FACTORY.objectNode();
        domainID.put("id", "default");
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
        projectDomainId.put("id", "default");
        // project domain

        ObjectNode projectObj = NODE_FACTORY.objectNode();
        projectObj.set("domain", projectDomainId);
        projectObj.put("name", "admin");
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

}
