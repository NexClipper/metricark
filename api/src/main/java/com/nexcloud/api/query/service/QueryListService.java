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
public class QueryListService {
    static final Logger logger = LoggerFactory.getLogger(QueryListService.class);

    @Autowired private RedisClient redisClient;
    @Autowired private PrometheusClient prometheusClient;

    /**
     * Get Kubernetes infos
     *
     * @param cluster_id
     * @param key
     * @param field
     * @param node
     * @return
     * @throws Exception
     */
    public ResponseEntity<ResponseData> getList(String cluster_id, String key, String field, String node) throws Exception {
        ResponseEntity<ResponseData> response = null;
        ResponseData resData = new ResponseData();
        JSONObject responseObject = new JSONObject();

        try {

            // field parameter에 해당하는 정보를 조회해서 responseObject에 담는다
            if (field.equals("pods")){
                responseObject = podList(key, field);
            } else if (field.equals("daemonsets")){
                responseObject = dsList(key, field);
            } else if (field.equals("deployments")){
                responseObject = dpList(key, field);
            } else if (field.equals("replicasets")){
                responseObject = rpList(key, field);
            } else if (field.equals("statefulsets")){
                responseObject = sfList(key, field);
            } else if (field.equals("nodes")){
                responseObject = nodeList(key, field, node);
            } else if (field.equals("services")){
                responseObject = svcList(key, field);
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

    /*
    Get pod list
     */
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
//        JSONArray cpuArray = (JSONArray) data.get("result");

        memData = prometheusClient.getQuery(memQuery, memParam);
        metrics = (JSONObject) parser.parse(memData.getBody());
        data = (JSONObject) metrics.get("data");
//        JSONArray memArray = (JSONArray) data.get("result");

        for (int i = 0; i < itemArray.size(); i++) {
            JSONObject items = (JSONObject) itemArray.get(i);
            JSONObject metadata = (JSONObject) items.get("metadata");
            JSONObject spec = (JSONObject) items.get("spec");
            JSONObject status = (JSONObject) items.get("status");
            attrObj = null;
            attrObj = new JSONObject();
            metricObj = null;
            metricObj = new JSONObject();


            // JSONObject는 HashMap을 상속. HashMap의 put메서드로 값을 저장
            attrObj.put("name", metadata.get("name"));
            attrObj.put("namespace", metadata.get("namespace"));
            attrObj.put("nodeName", spec.get("nodeName"));
            attrObj.put("kind", metadata.get("ownerReferences"));
            attrObj.put("status", status.get("phase"));

//            for(int j=0; j<cpuArray.size(); j++){
//                JSONObject cpu = (JSONObject) cpuArray.get(j);
//                JSONObject cpuMetric = (JSONObject) cpu.get("metric");
//
//                JSONObject mem = (JSONObject) memArray.get(j);
//                JSONObject memMetric = (JSONObject) mem.get("metric");
//
//                if(metadata.get("name").equals(cpuMetric.get("pod"))){
//                    metricObj.put("cpu", cpu.get("value"));
//                }
//                if(metadata.get("name").equals(memMetric.get("pod"))){
//                    metricObj.put("mem", mem.get("value"));
//                }
//            }

            attrObj.put("metric", metricObj);
            res.put(metadata.get("name"), attrObj);
        }

        return res;
    }

    /*
    Get daemonset list
     */
    private JSONObject dsList(String key, String field) throws Exception{
        JSONParser parser = new JSONParser();
        JSONObject attrObj = new JSONObject();
        JSONObject conObj = new JSONObject();
        JSONObject res = new JSONObject();

        // Redis에서 key, field에 해당하는 정보를 가져옴
        JSONObject jsonObject = (JSONObject) parser.parse(redisClient.get(key, field));
        // HashMap의 get 메서드로 "items"에 해당하는 value 추출
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

            // Container 각각의 이미지를 conObj에 입력
            for(int j=0; j<conArray.size(); j++){
                JSONObject con = (JSONObject) conArray.get(j);

                conObj.put(con.get("name"), con.get("image"));
            }

            attrObj.put("containers", conObj);
            attrObj.put("status", items.get("status"));
            attrObj.put("pods", pods((String) metadata.get("name")));

            res.put(metadata.get("name"), attrObj);
        }

        return res;
    }

    /*
    Get deployment list
     */
    private JSONObject dpList(String key, String field) throws Exception{
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
            attrObj.put("replicasets", rps((String) metadata.get("name")));

            res.put(metadata.get("name"), attrObj);
        }

        return res;
    }

    /*
    Get replicaset list
     */
    private JSONObject rpList(String key, String field) throws Exception{
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
            attrObj.put("pods", pods((String) metadata.get("name")));

            res.put(metadata.get("name"), attrObj);
        }

        return res;
    }

    /*
    Get statefulset list
     */
    private JSONObject sfList(String key, String field) throws Exception{
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
            attrObj.put("pods", pods((String) metadata.get("name")));


            res.put(metadata.get("name"), attrObj);
        }

        return res;
    }

    /*
    Get node list
     */
    private JSONObject nodeList(String key, String field, String name) throws Exception{
        JSONParser parser = new JSONParser();
        JSONObject res = new JSONObject();

        String node = null;

        JSONObject jsonObject = (JSONObject) parser.parse(redisClient.get(key, field));
        JSONArray itemArray = (JSONArray) jsonObject.get("items");

        for (int i=0; i<itemArray.size(); i++){
            JSONObject items = (JSONObject) itemArray.get(i);
            JSONObject metadata = (JSONObject) items.get("metadata");

            metadata.remove("managedFields");

            items.put("metadata", metadata);

            node = (String) metadata.get("name");

            if (name == null || "".equals(name)){
                res.put(metadata.get("name"), items);
            } else{
                if(node.equals(name)){
                    res.put(metadata.get("name"), items);
                }
            }

        }

        return res;
    }

    /*
    Get service list
     */
    private JSONObject svcList(String key, String field) throws Exception{
        JSONParser parser = new JSONParser();
        JSONObject attrObj = new JSONObject();
        JSONObject res = new JSONObject();

        JSONObject jsonObject = (JSONObject) parser.parse(redisClient.get(key, field));
        JSONArray itemArray = (JSONArray) jsonObject.get("items");

        for (int i=0; i<itemArray.size(); i++){
            JSONObject items = (JSONObject) itemArray.get(i);
            JSONObject metadata = (JSONObject) items.get("metadata");

            attrObj = null;
            attrObj = new JSONObject();

            attrObj.put("name", metadata.get("name"));
            attrObj.put("namespace", metadata.get("namespace"));
            attrObj.put("labels", metadata.get("labels"));
            attrObj.put("creationtime", metadata.get("creationTimestamp"));
            attrObj.put("spec", items.get("spec"));
            attrObj.put("status", items.get("status"));

            res.put(metadata.get("name"), attrObj);
        }

        return res;
    }

    /*
    Get pods of specific daemonset
     */
    private JSONObject pods(String name) throws Exception{
        JSONParser parser = new JSONParser();
        JSONObject res = new JSONObject();

        int pod = 1;

        JSONObject jsonObject = (JSONObject) parser.parse(redisClient.get("kubernetes", "pods"));
        JSONArray itemArray = (JSONArray) jsonObject.get("items");

        for (int i=0; i<itemArray.size(); i++){
            JSONObject items = (JSONObject) itemArray.get(i);
            JSONObject metadata = (JSONObject) items.get("metadata");
            JSONArray ownerArray = (JSONArray) metadata.get("ownerReferences");

            JSONObject owner = (JSONObject) ownerArray.get(0);
            String oName = (String) owner.get("name");

            if(oName.equals(name)){
                res.put("pod"+ pod++, metadata.get("name"));
            }

        }

        return res;
    }

    private JSONObject rps(String name) throws Exception{
        JSONParser parser = new JSONParser();
        JSONObject res = new JSONObject();

        int rp = 1;

        JSONObject jsonObject = (JSONObject) parser.parse(redisClient.get("kubernetes", "replicasets"));
        JSONArray itemArray = (JSONArray) jsonObject.get("items");

        for (int i=0; i<itemArray.size(); i++){
            JSONObject items = (JSONObject) itemArray.get(i);
            JSONObject metadata = (JSONObject) items.get("metadata");
            JSONArray ownerArray = (JSONArray) metadata.get("ownerReferences");

            JSONObject owner = (JSONObject) ownerArray.get(0);
            String oName = (String) owner.get("name");

            if(oName.equals(name)){
                res.put("replicaset"+ rp++, metadata.get("name"));
            }

        }

        return res;
    }
}