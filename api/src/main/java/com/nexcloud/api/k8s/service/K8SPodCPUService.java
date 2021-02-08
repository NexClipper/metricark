package com.nexcloud.api.k8s.service;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.nexcloud.api.client.PrometheusClient;
import com.nexcloud.api.domain.ResponseData;
import com.nexcloud.util.Const;
import com.nexcloud.util.Util;

@Service
public class K8SPodCPUService {
	static final Logger logger = LoggerFactory.getLogger(K8SPodCPUService.class);
	
	@Autowired private PrometheusClient prometheusClient;

	/**
	 * Pod CPU 사용량 ( Core )
	 * @param model
	 * @throws Exception
	 */
	public ResponseEntity<ResponseData> getPodResourceCpuUsage( String cluster_id, String pod_name, String namespace, String start, String end )  throws Exception
	{
		String query								= null;
		ResponseEntity<ResponseData> response 		= null;
		ResponseData resData						= new ResponseData();
		ResponseEntity<String> entityData			= null;
		String param								= null;
		String sub_query							= null;
		try{
			sub_query								= Util.makeSubQuery(start, end);
			if( namespace == null || "".equals(namespace) )
				namespace							= ".*";
			param									= "{image!='',name=~'^k8s_.*', pod=~'"+pod_name+"'}";
			query									= "sum (rate (container_cpu_usage_seconds_total{param}[5m])) by (pod)";
			
			//entityData								= prometheusClient.getQuery(query, param );
			if( sub_query != null )
				entityData							= prometheusClient.getQueryRange(query+"&"+sub_query, param );
			else
				entityData							= prometheusClient.getQuery(query, param );
			
			JSONParser parser						= new JSONParser();
			//JSON데이터를 넣어 JSON Object 로 만들어 준다.
            JSONObject jsonObject 					= (JSONObject) parser.parse(entityData.getBody());
            resData.setData((JSONObject)jsonObject.get("data"));
            resData.setStatus((String)jsonObject.get("status"));
            
			resData.setResponse_code(entityData.getStatusCodeValue());
			resData.setMessage(Const.SUCCESS);

			response = new ResponseEntity<ResponseData>(resData, HttpStatus.OK);
		}catch(Exception e){
			e.printStackTrace();
			resData.setResponse_code(Const.INTERNAL_SERVER_ERROR);
			resData.setMessage(Const.FAIL);
			resData.setMessage(Util.makeStackTrace(e));
			response = new ResponseEntity<ResponseData>(resData, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response;
	}
}
