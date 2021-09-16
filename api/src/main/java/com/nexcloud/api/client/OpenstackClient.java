package com.nexcloud.api.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class OpenstackClient {
    @Value("${openstack.endpoint}")
    private String ENDPOINT;
    private final RestTemplate restTemplate = new RestTemplate();

    public ResponseEntity<String> getResponse(String payload) {
        restTemplate.getMessageConverters().add(new StringHttpMessageConverter());

        ResponseEntity<String> resData = null;
        try {
//            resData = restTemplate.getForEntity(ENDPOINT + )
        } catch (RestClientException re) {
            re.printStackTrace();
            return null;
        } catch (Exception e) {
            e.printStackTrace();

            return null;
        }

        return resData;
    }

    // Authentication Token 획득


}
