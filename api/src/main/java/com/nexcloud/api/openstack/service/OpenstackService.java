package com.nexcloud.api.openstack.service;

import com.nexcloud.api.client.OpenstackClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class OpenstackService {
    private static final String AUTH_TOKEN_HEADER_NAME = "X-Auth-Token";
    private static final Integer RETRY_CNT = 5;
    private static final Logger LOGGER = LoggerFactory.getLogger(OpenstackService.class);
    private final RestTemplate restTemplate = new RestTemplate();

    private final OpenstackClient openstackClient;

    @Autowired
    public OpenstackService(OpenstackClient openstackClient) {
        this.openstackClient = openstackClient;
    }

    @Value("${openstack.endpoint}")
    private String ENDPOINT;

    public ResponseEntity<String> accessOpenstack(String port, String uri) {

        String targetUrl = ENDPOINT + ":" + port + uri;

        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

        ResponseEntity<String> response = null;
        try {
            for (int cnt = 0; cnt < RETRY_CNT; ++cnt) {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                headers.add(AUTH_TOKEN_HEADER_NAME, openstackClient.getToken());

                HttpEntity<String> request = new HttpEntity<>(headers);

                response = restTemplate.exchange(targetUrl, HttpMethod.GET, request, String.class);

                if (response.getStatusCode().equals(HttpStatus.UNAUTHORIZED)) {
                    openstackClient.getAuthenticationToken();
                } else if (response.getStatusCode().is2xxSuccessful()) {
                    break;
                }
            }
            LOGGER.debug("Success");
            return response;
        } catch (RestClientException re) {
            re.printStackTrace();
            LOGGER.warn("Failed (RestClientException)", re);
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.warn("Failed (Exception)", e);
            return null;
        }
    }
}
