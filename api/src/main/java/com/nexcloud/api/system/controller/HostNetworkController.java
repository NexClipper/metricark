package com.nexcloud.api.system.controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.nexcloud.api.domain.ResponseData;
import com.nexcloud.api.system.service.HostNetworkService;
import com.nexcloud.util.Const;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Configuration
@RestController
@EnableAutoConfiguration
@ComponentScan
@RequestMapping(value = "/v1")
public class HostNetworkController extends SpringBootServletInitializer implements WebApplicationInitializer {
	
	@Autowired
    private HostNetworkService service;
	
	static final Logger logger = LoggerFactory.getLogger(HostNetworkController.class);
	
	/**
	* Process receive network traffic
	* @return
	* @throws Exception
	*/
	@ApiOperation(value="Process receive network traffic", httpMethod="GET", notes="Process receive network traffic")
	@ApiImplicitParams({
		@ApiImplicitParam(
				name = "cluster_id",
				value = "Cluster ID (ex) 1",
				required = true,
				dataType = "string",
				paramType = "path"
		),
		@ApiImplicitParam(
	            name = "node_name", 
	            value = "node name (ex) '.*' => (전체 node), node1", 
	            required = true, 
	            dataType = "string", 
	            paramType = "path"
	    ),
		@ApiImplicitParam(
	            name = "start", 
	            value = "조회 시작 시간( Unix Timestamp ) (ex) 1521577239 ", 
	            required = false, 
	            dataType = "string", 
	            paramType = "query",
	            defaultValue=""
	    ),
		@ApiImplicitParam(
	            name = "end", 
	            value = "조회 종료시간( Unix Timestamp ) (ex) 1521577239 ", 
	            required = false, 
	            dataType = "string", 
	            paramType = "query",
	            defaultValue=""
	    )
	})
	@ApiResponses(value={
			@ApiResponse( code=200, message="SUCCESS"),
			@ApiResponse( code=500, message="Internal Server Error")
	})
	
	@RequestMapping(value="/cluster/{cluster_id}/nodes/{node_name}/process/resource/network/rx", method=RequestMethod.GET)
	@ResponseBody
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public ResponseEntity<ResponseData> getProcessResourceNetworkRx(  @PathVariable("cluster_id") String cluster_id
																	,@PathVariable("node_name") String node_name
																	,@QueryParam("start") String start
																	,@QueryParam("end") String end
																	) throws Exception {
		
		ResponseEntity<ResponseData> response 			= null;
		try{
			response 									= service.getProcessResourceNetworkRx( cluster_id, node_name, start, end );
		}catch(Exception e){
			
			ResponseData resData	= new ResponseData();
			resData.setResponse_code(Const.INTERNAL_SERVER_ERROR);
			resData.setMessage(Const.FAIL);
			response = new ResponseEntity<ResponseData>(resData, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response;
	}
	
	/**
	* Process send network traffic
	* @return
	* @throws Exception
	*/
	@ApiOperation(value="Process send network traffic", httpMethod="GET", notes="Process send network traffic")
	@ApiImplicitParams({
		@ApiImplicitParam(
				name = "cluster_id",
				value = "Cluster ID (ex) 1",
				required = true,
				dataType = "string",
				paramType = "path"
		),
		@ApiImplicitParam(
	            name = "node_name", 
	            value = "node name (ex) '.*' => (전체 node), node1", 
	            required = true, 
	            dataType = "string", 
	            paramType = "path"
	    ),
		@ApiImplicitParam(
	            name = "start", 
	            value = "조회 시작 시간( Unix Timestamp ) (ex) 1521577239 ", 
	            required = false, 
	            dataType = "string", 
	            paramType = "query",
	            defaultValue=""
	    ),
		@ApiImplicitParam(
	            name = "end", 
	            value = "조회 종료시간( Unix Timestamp ) (ex) 1521577239 ", 
	            required = false, 
	            dataType = "string", 
	            paramType = "query",
	            defaultValue=""
	    )
	})
	@ApiResponses(value={
			@ApiResponse( code=200, message="SUCCESS"),
			@ApiResponse( code=500, message="Internal Server Error")
	})
	
	@RequestMapping(value="/cluster/{cluster_id}/nodes/{node_name}/process/resource/network/tx", method=RequestMethod.GET)
	@ResponseBody
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public ResponseEntity<ResponseData> getProcessResourceNetworkTx(  @PathVariable("cluster_id") String cluster_id
																	,@PathVariable("node_name") String node_name
																	,@QueryParam("start") String start
																	,@QueryParam("end") String end
																	) throws Exception {
		
		ResponseEntity<ResponseData> response 			= null;
		try{
			response 									= service.getProcessResourceNetworkTx( cluster_id, node_name, start, end );
		}catch(Exception e){
			
			ResponseData resData	= new ResponseData();
			resData.setResponse_code(Const.INTERNAL_SERVER_ERROR);
			resData.setMessage(Const.FAIL);
			response = new ResponseEntity<ResponseData>(resData, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response;
	}
}
