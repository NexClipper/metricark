package com.nexcloud.api.openstack.service;

import com.nexcloud.api.client.OpenstackClient;
import com.nexcloud.api.domain.ResponseData;
import com.nexcloud.util.Const;
import com.nexcloud.util.Util;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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

    public ResponseEntity<ResponseData> accessOpenstack(String port, String uri, String projectName, String domainId, String endpoint) {

        if (StringUtils.isEmpty(endpoint)) {
            endpoint = ENDPOINT;
        }

        String targetUrl = endpoint + ":" + port + uri;
        return executeAccessOpenstack(targetUrl, projectName, domainId, endpoint);
    }

    public ResponseEntity<ResponseData> accessOpenstack(String uri, String projectName, String domainId, String endpoint) {

        if (StringUtils.isEmpty(endpoint)) {
            endpoint = ENDPOINT;
        }

        String targetUrl = ENDPOINT + uri;
        return executeAccessOpenstack(targetUrl, projectName, domainId, endpoint);
    }

    public ResponseEntity<ResponseData> parseOpenstackNetworks(ResponseEntity<ResponseData> rawResponse) {

        ResponseEntity<ResponseData> response;
        ResponseData resData = new ResponseData();

        try {

            String rawResponseBody = (String) rawResponse.getBody().getData();
            JSONObject jsonObject = (JSONObject) PARSER.parse(rawResponseBody);
            JSONArray jsonArray = (JSONArray) jsonObject.get("networks");

            resData.setData(jsonArray);
            resData.setStatus("success");
            resData.setResponse_code(200);
            resData.setMessage(Const.SUCCESS);

            response = new ResponseEntity<>(resData, HttpStatus.OK);
        } catch (Exception e) {
            LOGGER.warn("Parsing failed", e);
            resData.setResponse_code(Const.INTERNAL_SERVER_ERROR);
            resData.setMessage(Const.FAIL);
            resData.setMessage(Util.makeStackTrace(e));
            response = new ResponseEntity<>(resData, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return response;
    }

    public ResponseEntity<ResponseData> getErrorResponse() {
        ResponseData resData = new ResponseData();
        resData.setResponse_code(Const.INTERNAL_SERVER_ERROR);
        resData.setMessage(Const.FAIL);

        return new ResponseEntity<>(resData, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<ResponseData> executeAccessOpenstack(String targetUrl, String projectName, String domainId, String endpoint) {
        ResponseEntity<ResponseData> response;
        ResponseData resData = new ResponseData();
        ResponseEntity<String> entityData;

        try {
            entityData = openstackClient.executeHttpRequest(targetUrl, projectName, domainId, endpoint);

            resData.setData(entityData.getBody());
            resData.setStatus("success");

            resData.setResponse_code(entityData.getStatusCodeValue());
            resData.setMessage(Const.SUCCESS);

            response = new ResponseEntity<>(resData, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            resData.setResponse_code(Const.INTERNAL_SERVER_ERROR);
            resData.setMessage(Const.FAIL);
            resData.setMessage(Util.makeStackTrace(e));
            response = new ResponseEntity<>(resData, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return response;
    }


    public ResponseEntity<ResponseData> getNetworkTopology(String port, String projectName, String domainId, String endpoint) {
        // 1. 오픈스택 API에서 응답 받아옴
        ResponseEntity<ResponseData> networksResponse = accessOpenstack(port, "/v2.0/networks", projectName, domainId, endpoint);
        ResponseEntity<ResponseData> portsResponse = accessOpenstack(port, "/v2.0/ports", projectName, domainId, endpoint);
        ResponseEntity<ResponseData> routersResponse = accessOpenstack(port, "/v2.0/routers", projectName, domainId, endpoint);

        // 2. payload 추출
        try {
            JSONObject networks = extractPayload(networksResponse);
            JSONObject ports = extractPayload(portsResponse);
            JSONObject routers = extractPayload(routersResponse);

            // 3. networks, ports, routers 결합
            JSONArray networksArray = (JSONArray) networks.get("networks");
            JSONArray portsArray = (JSONArray) ports.get("ports");
            JSONArray routersArray = (JSONArray) routers.get("routers");

            // 4. 반환
            JSONObject result = new JSONObject();
            result.put("networks", networksArray);
            result.put("ports", portsArray);
            result.put("routers", routersArray);

            String topology = result.toJSONString();

            ResponseEntity<ResponseData> response;
            ResponseData resData = new ResponseData();

            resData.setData(topology);
            resData.setStatus("success");
            resData.setResponse_code(200);
            resData.setMessage(Const.SUCCESS);

            response = new ResponseEntity<>(resData, HttpStatus.OK);

            return response;

        } catch (Exception e) {
            return getErrorResponse();
        }
    }

    private JSONObject extractPayload(ResponseEntity<ResponseData> rawResponse) {
        try {
            String payload = (String) rawResponse.getBody().getData();
            JSONObject parsed = (JSONObject) PARSER.parse(payload);

            return parsed;
        } catch (ParseException e) {
            throw new RuntimeException("Parsing failed");
        }
    }
}
