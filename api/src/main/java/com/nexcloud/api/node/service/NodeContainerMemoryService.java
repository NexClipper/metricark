package com.nexcloud.api.node.service;

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
public class NodeContainerMemoryService {
	static final Logger logger = LoggerFactory.getLogger(NodeContainerMemoryService.class);
	
	@Autowired private PrometheusClient prometheusClient;

	/**
	 * Docker Memory Used byte
	 * @param model
	 * @throws Exception
	 */
	public ResponseEntity<ResponseData> getNodeContainerMemoryUsedByte( String cluster_id, String node_name, String container, String start, String end ) throws Exception
	{
		String query								= null;
		ResponseEntity<ResponseData> response 		= null;
		ResponseData resData						= new ResponseData();
		String param								= null;
		String sub_query							= "";
		try{
			sub_query								= Util.makeSubQuery(start, end);
			param									= "{image!='',name!~'^k8s_',kubernetes_io_hostname=~'^"+node_name+"',name=~'"+container+"'}";
			query									= "sum (rate (container_memory_working_set_bytes{param}[1m])) by (kubernetes_io_hostname, name, image)";
			ResponseEntity<String> entityData		= null;
			
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
			resData.setResponse_code(Const.INTERNAL_SERVER_ERROR);
			resData.setMessage(Const.FAIL);
			response = new ResponseEntity<ResponseData>(resData, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response;
	}
}
