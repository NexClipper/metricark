package com.nexcloud.api.vmcheck.service;

import com.nexcloud.api.client.PrometheusClient;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class VmService {

    private final PrometheusClient client;
    private static final JSONParser PARSER = new JSONParser();

    @Autowired
    private VmService(PrometheusClient client) {
        this.client = client;
    }

    public ResponseEntity<JSONArray> getVmList(String query) {
        ResponseEntity<JSONArray> response;
        JSONArray jsonArray;

        String prometheusResponse = client.getQuery(query).getBody();

        try {
            JSONObject jsonObject = (JSONObject) PARSER.parse(prometheusResponse);
            JSONObject data = (JSONObject) jsonObject.get("data");
            jsonArray = (JSONArray) data.get("result");
            response = new ResponseEntity<>(jsonArray, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            response = new ResponseEntity<>(new JSONArray(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return response;
    }
}
