package com.nexcloud.api.query.service;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.nexcloud.api.client.PrometheusClient;
import com.nexcloud.api.client.RedisClient;
import com.nexcloud.api.domain.ResponseData;
import com.nexcloud.util.Const;
import com.nexcloud.util.Util;

@Service
public class QueryService {
	static final Logger logger = LoggerFactory.getLogger(QueryService.class);
	
	@Autowired private PrometheusClient prometheusClient;
	
	@Autowired private RedisClient redisClient;

	/**
	 * Cluster Built Date
	 * @param model
	 * @throws Exception
	 */
	public ResponseEntity<ResponseData> getDynamicQuery( String cluster_id, String query, String start, String end )  throws Exception
	{
		ResponseEntity<ResponseData> response 		= null;
		ResponseData resData						= new ResponseData();
		ResponseEntity<String> entityData			= null;
		String sub_query							= null;
		try{
			sub_query								= Util.makeSubQuery(start, end);
			/*
			String param[]							= null;
			
			
			String buffer[]							= Util.split(query, "{}");
			List<String> dummy						= new ArrayList<String>();
			
			// Multi Query조회시
			if( query.startsWith("{") )
			{
				query								= "{param}";
				param								= new String[1];
				param[0]							= "{"+buffer[0]+"}";
			}
			else
			{
				query									= "";
				for( int i=0;i<buffer.length;i++)
				{
					if( i % 2 != 0 )
					{
						query							+= "{param}";
						dummy.add("{"+buffer[i]+"}");
					}
					else
					{
						query							+= buffer[i];
					}
				}
				param									= new String[dummy.size()];
				for( int i=0;i<dummy.size();i++ )
					param[i]							= dummy.get(i);
			}
			
			
			//entityData								= prometheusClient.getDynamicQuery(query, param);
			*/
						
			if( sub_query != null )
				entityData							= prometheusClient.getDynamicRangeQuery("{param}&"+sub_query, query );
			else
				entityData							= prometheusClient.getDynamicQuery("{param}", query );
			

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
	 * Cluster Built Date
	 * @param model
	 * @throws Exception
	 */
	public ResponseEntity<ResponseData> getDynamicQuery_backup( String cluster_id, String query, String start, String end )  throws Exception
	{
		ResponseEntity<ResponseData> response 		= null;
		ResponseData resData						= new ResponseData();
		ResponseEntity<String> entityData			= null;
		String sub_query							= null;
		try{
			sub_query								= Util.makeSubQuery(start, end);
			String param[]							= null;
			
			
			String buffer[]							= Util.split(query, "{}");
			List<String> dummy						= new ArrayList<String>();
			
			// Multi Query조회시
			if( query.startsWith("{") )
			{
				query								= "{param}";
				param								= new String[1];
				param[0]							= "{"+buffer[0]+"}";
			}
			else
			{
				query									= "";
				for( int i=0;i<buffer.length;i++)
				{
					if( i % 2 != 0 )
					{
						query							+= "{param}";
						dummy.add("{"+buffer[i]+"}");
					}
					else
					{
						query							+= buffer[i];
					}
				}
				param									= new String[dummy.size()];
				for( int i=0;i<dummy.size();i++ )
					param[i]							= dummy.get(i);
			}
			
			
			//entityData								= prometheusClient.getDynamicQuery(query, param);
			
			if( sub_query != null )
				entityData							= prometheusClient.getDynamicRangeQuery(query+"&"+sub_query, param );
			else
				entityData							= prometheusClient.getDynamicQuery(query, param );
			

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
	 * 
	 * @param promql
	 * @param endPoint
	 * @return
	 * @throws Exception
	 */
	public ResponseEntity<ResponseData> getDirectQuery( String promql, String endPoint )  throws Exception
	{
		ResponseEntity<ResponseData> response 		= null;
		ResponseData resData						= new ResponseData();
		ResponseEntity<String> entityData			= null;
		try{
			entityData							= prometheusClient.getDirectQuery(promql, endPoint);

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
//
//	/**
//	 * Redis Test
//	 * @param model
//	 * @throws Exception
//	 */
//	public ResponseEntity<ResponseData> getRedisValue( String cluster_id, String key, String field )  throws Exception
//	{
//		ResponseEntity<ResponseData> response 		= null;
//		ResponseData resData						= new ResponseData();
//		try{
//
//            resData.setData(redisClient.get(key, field));
//			resData.setMessage(Const.SUCCESS);
//
//			response = new ResponseEntity<ResponseData>(resData, HttpStatus.OK);
//		}catch(Exception e){
//			e.printStackTrace();
//			resData.setResponse_code(Const.INTERNAL_SERVER_ERROR);
//			resData.setMessage(Const.FAIL);
//			resData.setMessage(Util.makeStackTrace(e));
//			response = new ResponseEntity<ResponseData>(resData, HttpStatus.INTERNAL_SERVER_ERROR);
//		}
//
//		return response;
//	}
}
