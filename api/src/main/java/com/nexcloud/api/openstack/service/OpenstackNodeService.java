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
public class OpenstackNodeService {

    @Value("${openstack.endpoint}")
    private String ENDPOINT;

    @Autowired
    private OpenstackClient openstackClient;
    private static final Integer RETRY_CNT = 5;

    private static final Logger LOGGER = LoggerFactory.getLogger(OpenstackClient.class);
    private final RestTemplate restTemplate = new RestTemplate();

    public ResponseEntity<String> getNodes() {

        String clusterNodesUrl = ENDPOINT + ":8778" + "/v1/nodes";

        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

        ResponseEntity<String> response = null;
        try {
            for(int cnt = 0; cnt < RETRY_CNT ; ++cnt) {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                headers.add("X-Auth-Token", openstackClient.getToken());

                HttpEntity<String> request = new HttpEntity<>(headers);

                response = restTemplate.exchange(clusterNodesUrl, HttpMethod.GET, request, String.class);

                if (response.getStatusCode().equals(HttpStatus.UNAUTHORIZED)) {
                    openstackClient.getAuthenticationToken();
                }
            }
            return response;
        } catch (RestClientException re) {
            re.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }
    }
}
