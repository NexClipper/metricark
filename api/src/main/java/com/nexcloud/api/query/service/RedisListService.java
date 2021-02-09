package com.nexcloud.api.query.service;

import com.nexcloud.api.client.PrometheusClient;
import com.nexcloud.api.client.RedisClient;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class RedisListService {
    static final Logger logger = LoggerFactory.getLogger(QueryService.class);

    @Autowired private RedisClient redisClient;
    @Autowired private PrometheusClient prometheusClient;

    /**
     * Pod List
     *
     * @param model
     * @throws Exception
     */
    public ResponseEntity<ResponseData> getList(String cluster_id, String key, String field) throws Exception {
        ResponseEntity<ResponseData> response = null;
        ResponseData resData = new ResponseData();
        JSONObject responseObject = new JSONObject();

        try {

            if (field.equals("pods")){
                responseObject = podList(key, field);
            } else if (field.equals("daemonsets")){
                responseObject = dsList(key, field);
            }


            resData.setData(responseObject);
            resData.setMessage(Const.SUCCESS);

            response = new ResponseEntity<ResponseData>(resData, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            resData.setResponse_code(Const.INTERNAL_SERVER_ERROR);
            resData.setMessage(Const.FAIL);
            resData.setMessage(Util.makeStackTrace(e));
            response = new ResponseEntity<ResponseData>(resData, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return response;
    }

    private JSONObject podList(String key, String field) throws Exception {
        ResponseEntity<String> cpuData = null;
        ResponseEntity<String> memData = null;
        JSONParser parser = new JSONParser();
        JSONObject attrObj = new JSONObject();
        JSONObject metricObj = new JSONObject();
        JSONObject metrics = new JSONObject();
        JSONObject data = new JSONObject();
        JSONObject res = new JSONObject();
        String cpuParam = "{image!='',name=~'^k8s_.*', pod=~'.*'}";
        String cpuQuery = "sum (rate (container_cpu_usage_seconds_total{param}[5m])) by (pod)";
        String memParam = "{image!='',name=~'^k8s_.*',pod=~'^.*'}";
        String memQuery = "sum (container_memory_working_set_bytes{param}) by (pod)";

        JSONObject jsonObject = (JSONObject) parser.parse(redisClient.get(key, field));
        JSONArray itemArray = (JSONArray) jsonObject.get("items");

        cpuData = prometheusClient.getQuery(cpuQuery, cpuParam);
        metrics = (JSONObject) parser.parse(cpuData.getBody());
        data = (JSONObject) metrics.get("data");
        JSONArray cpuArray = (JSONArray) data.get("result");

        memData = prometheusClient.getQuery(memQuery, memParam);
        metrics = (JSONObject) parser.parse(memData.getBody());
        data = (JSONObject) metrics.get("data");
        JSONArray memArray = (JSONArray) data.get("result");

        for (int i = 0; i < itemArray.size(); i++) {
            JSONObject items = (JSONObject) itemArray.get(i);
            JSONObject metadata = (JSONObject) items.get("metadata");
            JSONObject spec = (JSONObject) items.get("spec");
            attrObj = null;
            attrObj = new JSONObject();
            metricObj = null;
            metricObj = new JSONObject();


            attrObj.put("name", metadata.get("name"));
            attrObj.put("namespace", metadata.get("namespace"));
            attrObj.put("nodeName", spec.get("nodeName"));
            attrObj.put("kind", metadata.get("ownerReferences"));

            for(int j=0; j<cpuArray.size(); j++){
                JSONObject cpu = (JSONObject) cpuArray.get(j);
                JSONObject cpuMetric = (JSONObject) cpu.get("metric");

                JSONObject mem = (JSONObject) memArray.get(j);
                JSONObject memMetric = (JSONObject) mem.get("metric");

                if(metadata.get("name").equals(cpuMetric.get("pod"))){
                    metricObj.put("cpu", cpu.get("value"));
                }
                if(metadata.get("name").equals(memMetric.get("pod"))){
                    metricObj.put("mem", mem.get("value"));
                }
            }

            attrObj.put("metric", metricObj);
            res.put(metadata.get("name"), attrObj);
        }

        return res;
    }

    private JSONObject dsList(String key, String field) throws Exception{
        JSONParser parser = new JSONParser();
        JSONObject attrObj = new JSONObject();
        JSONObject conObj = new JSONObject();
        JSONObject res = new JSONObject();

        JSONObject jsonObject = (JSONObject) parser.parse(redisClient.get(key, field));
        JSONArray itemArray = (JSONArray) jsonObject.get("items");

        for (int i=0; i<itemArray.size(); i++){
            JSONObject items = (JSONObject) itemArray.get(i);
            JSONObject metadata = (JSONObject) items.get("metadata");
            JSONObject spec = (JSONObject) items.get("spec");
            JSONObject template = (JSONObject) spec.get("template");
            JSONObject specDetail = (JSONObject) template.get("spec");
            JSONArray conArray = (JSONArray) specDetail.get("containers");

            attrObj = null;
            attrObj = new JSONObject();
            conObj = null;
            conObj = new JSONObject();

            attrObj.put("name", metadata.get("name"));
            attrObj.put("namespace", metadata.get("namespace"));
            attrObj.put("labels", metadata.get("labels"));
            attrObj.put("creationtime", metadata.get("creationTimestamp"));

            for(int j=0; j<conArray.size(); j++){
                JSONObject con = (JSONObject) conArray.get(j);

                conObj.put(con.get("name"), con.get("image"));
            }
            attrObj.put("containers", conObj);
            attrObj.put("status", items.get("status"));

            res.put(metadata.get("name"), attrObj);
        }

        return res;
    }

}