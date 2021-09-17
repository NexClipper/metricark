package com.nexcloud.api.query.service;

import com.nexcloud.api.client.PrometheusClient;
import com.nexcloud.api.client.RedisClient;
import com.nexcloud.api.domain.ResponseData;
import com.nexcloud.util.Const;
import com.nexcloud.util.Util;

import java.util.Arrays;

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
     * 원본 json object 에서 filterKeys 에 입력된 키값을 추출 한다
     * 
     * param  JSONObject orgObject  필터링을 위한 원본 json 결과
     * param  String[] filterKeys   필터링 키 리스트
     * 
     * JSONObject
     */
    private JSONObject responseFilter(JSONObject orgObject, String[] filterKeys) {

    	// 최종 결과를 위한 JSONObject
        JSONObject responseObject = new JSONObject();

    	for(String key : filterKeys) {
    		// 필터키 유효성 검증
    		if (key != null && !key.trim().isEmpty()) {

    			// 필터키를 레벨별로 분화 시킨다
    			String[] subkeys = key.split("\\.");

    			// 필키를 찾아서 레벨에 맞게 구성된 JSONObject 아이템으로 가져온다. 
    			Object value = makeJsonItem(searchKey(orgObject, subkeys), subkeys);

    			// 결과 오브젝트에 없으면 바로 등록, 있으면 기존 결과에 머징한 값으로 등록한다.
    			responseObject.put(
    					subkeys[0],
    					responseObject.get(subkeys[0]) == null
    					? value
    					: deepMerge((JSONObject)value, (JSONObject)responseObject.get(subkeys[0]))
    				);
    		}
    	}

    	return responseObject;
    }

    /**
     * 특정 JSONObject를 타겟으로 머징한다.
     * 
     * JSONObject source, target
     * 
     */
	public JSONObject deepMerge(JSONObject source, JSONObject target) {

		// 전체 키 대상 검증/복제
	    for (Object key: source.keySet()) {
	            Object value = source.get(key);

	            // 키가 없으면 바로 등록
	            if (!target.containsKey(key)) {
	                // new value for "key":
	                target.put(key, value);
	            }
	            // 이미 존재하는 키면 JSONObject 일 경우 하위 레이어에 대한 추가복제 - recursive
	            else {
	                // existing value for "key" - recursively deep merge:
	                if (value instanceof JSONObject) {
	                    deepMerge((JSONObject)value, (JSONObject)target.get(key));
	                } else {
	                    target.put(key, value);
	                }
	            }
	    }
	    return target;
	}

	/**
	 * 특정 object를 입력된 키의 위치에 맞게 할당하는 JSONObject 를 생성한다.
	 * 
	 * Object obj  value로 할당될 값
	 * String[] keys  value가 할당될 상대위치 키값
	 * 
	 */
	Object makeJsonItem(Object obj, String[] keys) {
		Object result = null;

		if (keys.length == 1) {
			// 해당 키면 바로 등록
			result = obj;
		} else {
			// 하위키면 JSONObject를 만들어서 하위키를 추가로 만들어서 해당 키에 할당
			JSONObject json = new JSONObject();
			json.put(keys[1], makeJsonItem(obj, Arrays.copyOfRange(keys, 1, keys.length)));
			result = json;
		}
		
		return result;
	}

	/**
	 * 특정 키의 json object 의 value 값을 가져온다.
	 * 
	 * JSONObject jsonObj 값을 추출할 원본 JSONObject
	 * String[] keys 값을 추출하기 위해 접근할 상대위치 키값
	 * 
	 */
	Object searchKey(JSONObject jsonObj, String[] keys) {
		Object value = jsonObj;

		String key = keys[0];
		if (keys.length == 1) {
			// 해당 키면 바로 추출
			value = jsonObj.get(key);
		} else {
			// 해당 키의 하위키가 존재하면서 하위가 동일하게 JSONObject 일 경우 추가 탐색 
			if (jsonObj.containsKey(key) && jsonObj.get(key).getClass() == JSONObject.class) {
				value = searchKey((JSONObject) jsonObj.get(key), Arrays.copyOfRange(keys, 1, keys.length));
			}
			// JSONObject가 아닐 경우 : 잘못된 접근이므로 null
			else {
				value = null;
			}
		}

		return value;
	}
    
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
    public ResponseEntity<ResponseData> getList(String cluster_id, String key, String field, String node, String[] filterKeys) throws Exception {
        ResponseEntity<ResponseData> response = null;
        ResponseData resData = new ResponseData();
        JSONObject responseObject = new JSONObject();

        try {

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

            if (filterKeys != null && filterKeys.length > 0) {
            	responseObject = responseFilter(responseObject, filterKeys);
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