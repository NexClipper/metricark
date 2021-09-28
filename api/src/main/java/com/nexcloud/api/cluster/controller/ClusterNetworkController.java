package com.nexcloud.api.cluster.controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
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

import com.nexcloud.api.cluster.service.ClusterNetworkService;
import com.nexcloud.api.domain.ResponseData;
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
public class ClusterNetworkController extends SpringBootServletInitializer implements WebApplicationInitializer {
	
	@Autowired
    private ClusterNetworkService netService;
	
	static final Logger logger = LoggerFactory.getLogger(ClusterNetworkController.class);
	

	/**
	* Cluster Total Receive Network Traffic
	* @return
	* @throws Exception
	*/
	@ApiOperation(value="Cluster Total Receive Network Traffic", httpMethod="GET", notes="core")
	@ApiImplicitParams({
		@ApiImplicitParam(
				name = "cluster_id",
				value = "Cluster ID (ex) 1",
				required = true,
				dataType = "string",
				paramType = "path"
		),
		@ApiImplicitParam(
	            name = "start", 
	            value = "조회 시작 시간( Unix Timestamp ) (ex) 1521577239 ", 
	            required = true, 
	            dataType = "string", 
	            paramType = "query"
	    ),
		@ApiImplicitParam(
	            name = "end", 
	            value = "조회 종료시간( Unix Timestamp ) (ex) 1521577239 ", 
	            required = true, 
	            dataType = "string", 
	            paramType = "query"
	    )
	})
	@ApiResponses(value={
			@ApiResponse( code=200, message="SUCCESS"),
			@ApiResponse( code=500, message="Internal Server Error")
	})
	
	@RequestMapping(value="/cluster/{cluster_id}/resources/network/rx", method=RequestMethod.GET)
	@ResponseBody
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public ResponseEntity<ResponseData> getClusterNetworkRxResource(  @PathVariable("cluster_id") String cluster_id
																	,@QueryParam("start") String start
																	,@QueryParam("end") String end
																	) throws Exception {
		
		ResponseEntity<ResponseData> response 			= null;
		try{
			response 									= netService.getClusterNetworkRxResource( cluster_id, start, end );
		}catch(Exception e){
			
			ResponseData resData	= new ResponseData();
			resData.setResponse_code(Const.INTERNAL_SERVER_ERROR);
			resData.setMessage(Const.FAIL);
			response = new ResponseEntity<ResponseData>(resData, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response;
	}

	/**
	*  Cluster Total Send Network Traffic
	* @return
	* @throws Exception
	*/
	@ApiOperation(value="Cluster Total Send Network Traffic", httpMethod="GET", notes="core")
	@ApiImplicitParams({
		@ApiImplicitParam(
				name = "cluster_id",
				value = "Cluster ID (ex) 1",
				required = true,
				dataType = "string",
				paramType = "path"
		),
		@ApiImplicitParam(
	            name = "start", 
	            value = "조회 시작 시간( Unix Timestamp ) (ex) 1521577239 ", 
	            required = true, 
	            dataType = "string", 
	            paramType = "query"
	    ),
		@ApiImplicitParam(
	            name = "end", 
	            value = "조회 종료시간( Unix Timestamp ) (ex) 1521577239 ", 
	            required = true, 
	            dataType = "string", 
	            paramType = "query"
	    )
	})
	@ApiResponses(value={
			@ApiResponse( code=200, message="SUCCESS"),
			@ApiResponse( code=500, message="Internal Server Error")
	})
	
	@RequestMapping(value="/cluster/{cluster_id}/resources/network/tx", method=RequestMethod.GET)
	@ResponseBody
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public ResponseEntity<ResponseData> getClusterNetworkTxResource(  @PathVariable("cluster_id") String cluster_id
																 ,@QueryParam("start") String start
																 ,@QueryParam("end") String end
															  ) throws Exception {
		
		ResponseEntity<ResponseData> response 			= null;
		try{
			response 									= netService.getClusterNetworkTxResource( cluster_id, start, end );
		}catch(Exception e){
			
			ResponseData resData	= new ResponseData();
			resData.setResponse_code(Const.INTERNAL_SERVER_ERROR);
			resData.setMessage(Const.FAIL);
			response = new ResponseEntity<ResponseData>(resData, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response;
	}
}
