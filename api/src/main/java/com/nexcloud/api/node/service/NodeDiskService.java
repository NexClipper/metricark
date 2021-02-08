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
public class NodeDiskService {
	static final Logger logger = LoggerFactory.getLogger(NodeDiskService.class);
	
	@Autowired private PrometheusClient prometheusClient;

	/**
	 * Node별 Disk usage ( % )
	 * @param model
	 * @throws Exception
	 */
	public ResponseEntity<ResponseData> getNodeDiskUsage( String cluster_id, String node_name, String device, String mountpoint, String start, String end )  throws Exception
	{
		String query								= null;
		ResponseEntity<ResponseData> response 		= null;
		ResponseData resData						= new ResponseData();
		ResponseEntity<String> entityData			= null;
		String param								= null;
		String sub_query							= "";
		try{
			sub_query								= Util.makeSubQuery(start, end);
			
			if( device == null || "".equals(device) )
				device								= ".*";
			if( mountpoint == null || "".equals(mountpoint) )
				mountpoint							= ".*";
			
			param									= "{kubernetes_node=~'"+node_name+"', device=~'"+device+"', mountpoint=~'"+mountpoint+"'}";
			query									= "(sum (node_filesystem_size_bytes{param}) by ( kubernetes_node, mountpoint) - sum (node_filesystem_free_bytes{param}) by ( kubernetes_node, mountpoint)) / sum (node_filesystem_size_bytes{param}) by ( kubernetes_node, mountpoint)";
			
			if( sub_query != null )
				entityData							= prometheusClient.getQueryRange(query+"&"+sub_query, param, param, param );
			else
				entityData							= prometheusClient.getQuery(query, param, param, param );
			
			
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
	
	/**
	 * Node별 Disk used byte
	 * @param model
	 * @throws Exception
	 */
	public ResponseEntity<ResponseData> getNodeDiskUsedByte( String cluster_id, String node_name, String device, String mountpoint, String start, String end )  throws Exception
	{
		String query								= null;
		ResponseEntity<ResponseData> response 		= null;
		ResponseData resData						= new ResponseData();
		ResponseEntity<String> entityData			= null;
		String param								= null;
		String sub_query							= "";
		try{
			sub_query								= Util.makeSubQuery(start, end);
			
			if( device == null || "".equals(device) )
				device								= ".*";
			if( mountpoint == null || "".equals(mountpoint) )
				mountpoint							= ".*";
			
			param									= "{kubernetes_node=~'"+node_name+"', device=~'"+device+"', mountpoint=~'"+mountpoint+"'}";
			query									= "sum (node_filesystem_size_bytes{param}) by ( kubernetes_node, mountpoint) - sum (node_filesystem_free_bytes{param}) by ( kubernetes_node, mountpoint)";
			
			if( sub_query != null )
				entityData							= prometheusClient.getQueryRange(query+"&"+sub_query, param, param );
			else
				entityData							= prometheusClient.getQuery(query, param, param, param );
			
			
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
	
	/**
	 * Node별 Disk total byte
	 * @param model
	 * @throws Exception
	 */
	public ResponseEntity<ResponseData> getNodeDiskTotalByte( String cluster_id, String node_name, String device, String mountpoint, String start, String end )  throws Exception
	{
		String query								= null;
		ResponseEntity<ResponseData> response 		= null;
		ResponseData resData						= new ResponseData();
		ResponseEntity<String> entityData			= null;
		String param								= null;
		String sub_query							= "";
		try{
			sub_query								= Util.makeSubQuery(start, end);
			
			if( device == null || "".equals(device) )
				device								= ".*";
			if( mountpoint == null || "".equals(mountpoint) )
				mountpoint							= ".*";
			
			param									= "{kubernetes_node=~'"+node_name+"', device=~'"+device+"', mountpoint=~'"+mountpoint+"'}";
			query									= "sum (node_filesystem_size_bytes{param})  by ( kubernetes_node, mountpoint) ";
			
			if( sub_query != null )
				entityData							= prometheusClient.getQueryRange(query+"&"+sub_query, param, param );
			else
				entityData							= prometheusClient.getQuery(query, param, param, param );
			
			
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
