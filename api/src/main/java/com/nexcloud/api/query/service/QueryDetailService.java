package com.nexcloud.api.query.service;

import com.nexcloud.api.client.PrometheusClient;
import com.nexcloud.api.client.RedisClient;
import com.nexcloud.api.domain.ResponseData;
import com.nexcloud.util.Const;
import com.nexcloud.util.Util;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class QueryDetailService {
    static final Logger logger = LoggerFactory.getLogger(QueryDetailService.class);

    @Autowired private RedisClient redisClient;
    @Autowired private PrometheusClient prometheusClient;

    /**
     * Pod Detail
     *
     * @param model
     * @throws Exception
     */

    public ResponseEntity<ResponseData> get(String cluster_id, String name)throws Exception{
        ResponseEntity<ResponseData> response = null;
        ResponseData resData = new ResponseData();
        JSONObject responseObject = new JSONObject();
        String pod = null;

        try{
            JSONParser parser = new JSONParser();

            // Redis에서 Pod detail 정보를 가져와서 JSON으로 저장
            JSONObject jsonObject = (JSONObject) parser.parse(redisClient.get("kubernetes","pods"));
            JSONArray itemArray = (JSONArray) jsonObject.get("items");

            for(int i=0; i<itemArray.size(); i++){
                JSONObject items = (JSONObject) itemArray.get(i);
                JSONObject metadata = (JSONObject) items.get("metadata");

                metadata.remove("managedFields");

                items.put("metadata", metadata);

                pod = (String) metadata.get("name");

                if(pod.equals(name)){
                    responseObject.put(name, items);
                }
            }

            resData.setData(responseObject);
            resData.setMessage(Const.SUCCESS);

            response = new ResponseEntity<ResponseData>(resData, HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
            resData.setResponse_code(Const.INTERNAL_SERVER_ERROR);
            resData.setMessage(Const.FAIL);
            resData.setMessage(Util.makeStackTrace(e));
            response = new ResponseEntity<ResponseData>(resData, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return response;
    }
}