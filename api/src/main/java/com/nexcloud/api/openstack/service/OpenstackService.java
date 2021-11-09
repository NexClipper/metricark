package com.nexcloud.api.openstack.service;

import com.nexcloud.api.client.OpenstackClient;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class OpenstackService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OpenstackService.class);
    private static final JSONParser PARSER = new JSONParser();

    private final OpenstackClient openstackClient;

    @Autowired
    public OpenstackService(OpenstackClient openstackClient) {
        this.openstackClient = openstackClient;
    }

    @Value("${openstack.endpoint}")
    private String ENDPOINT;

    public ResponseEntity<String> accessOpenstack(String port, String uri, String projectName, String domainId) {

        String targetUrl = ENDPOINT + ":" + port + uri;
        return openstackClient.executeHttpRequest(targetUrl, projectName, domainId);
    }

    public ResponseEntity<String> accessOpenstack(String uri, String projectName, String domainId) {

        String targetUrl = ENDPOINT + uri;
        return openstackClient.executeHttpRequest(targetUrl, projectName, domainId);
    }

    public ResponseEntity<JSONArray> parseOpenstackNetworks(ResponseEntity<String> rawResponse) {

        try {
            String rawResponseBody = rawResponse.getBody();
            JSONObject jsonObject = (JSONObject) PARSER.parse(rawResponseBody);
            JSONArray jsonArray = (JSONArray) jsonObject.get("networks");
            return ResponseEntity.ok(jsonArray);

        } catch (Exception e) {
            LOGGER.warn("Parsing failed", e);
            return ResponseEntity.status(500).build();
        }
    }
}
