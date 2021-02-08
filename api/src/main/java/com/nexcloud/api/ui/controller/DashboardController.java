package com.nexcloud.api.ui.controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
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
import com.nexcloud.api.ui.service.DashboardService;
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
public class DashboardController extends SpringBootServletInitializer implements WebApplicationInitializer {
	
	@Autowired
    private DashboardService service;
	
	static final Logger logger = LoggerFactory.getLogger(DashboardController.class);
	
	/**
	* UI Dashboard Area#1
	* Controller, API Server Status, Scheduler, Nodes Count, Namespace Count, Docker Container Count
	* Daemonset, Deployment, Replicaset, Statefulset, Pods, PVCs
	* @return
	* @throws Exception
	*/
	@ApiOperation(value="UI Dashboard Area#1", httpMethod="GET", notes="Controller, API Server Status, Scheduler, Nodes Count, Namespace Count, Docker Container Count, Daemonset, Deployment, Replicaset, Statefulset, Pods, PVCs")
	@ApiImplicitParams({
		@ApiImplicitParam(
				name = "cluster_id",
				value = "Cluster ID (ex) 1",
				required = true,
				dataType = "string",
				paramType = "path"
		)
	})
	@ApiResponses(value={
			@ApiResponse( code=200, message="SUCCESS"),
			@ApiResponse( code=500, message="Internal Server Error")
	})
	
	@RequestMapping(value="/cluster/{cluster_id}/nexcloud/dashboard/area1", method=RequestMethod.GET)
	@ResponseBody
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public ResponseEntity<ResponseData> getClusterDashboardArea1( @PathVariable("cluster_id") String cluster_id ) throws Exception {
		
		ResponseEntity<ResponseData> response 			= null;
		try{
			response 									= service.getClusterDashboardArea1( cluster_id );
		}catch(Exception e){
			
			ResponseData resData	= new ResponseData();
			resData.setResponse_code(Const.INTERNAL_SERVER_ERROR);
			resData.setMessage(Const.FAIL);
			response = new ResponseEntity<ResponseData>(resData, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response;
	}
	
	/**
	* UI Dashboard Area#2
	* Cluster CPU Usage(%), Used(core), Total(core) / Memory Usage(%), Used(byte), Total(byte) / Pod Usage(%),Used( count), Total(count) 
	* @return
	* @throws Exception
	*/
	@ApiOperation(value="UI Dashboard Area#2", httpMethod="GET", notes="Cluster CPU Usage(%), Used(core), Total(core) / Memory Usage(%), Used(byte), Total(byte) / Pod Usage(%),Used( count), Total(count)")
	@ApiImplicitParams({
		@ApiImplicitParam(
				name = "cluster_id",
				value = "Cluster ID (ex) 1",
				required = true,
				dataType = "string",
				paramType = "path"
		)
	})
	@ApiResponses(value={
			@ApiResponse( code=200, message="SUCCESS"),
			@ApiResponse( code=500, message="Internal Server Error")
	})
	
	@RequestMapping(value="/cluster/{cluster_id}/nexcloud/dashboard/area2", method=RequestMethod.GET)
	@ResponseBody
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public ResponseEntity<ResponseData> getClusterDashboardArea2( @PathVariable("cluster_id") String cluster_id ) throws Exception {
		
		ResponseEntity<ResponseData> response 			= null;
		try{
			response 									= service.getClusterDashboardArea2( cluster_id );
		}catch(Exception e){
			
			ResponseData resData	= new ResponseData();
			resData.setResponse_code(Const.INTERNAL_SERVER_ERROR);
			resData.setMessage(Const.FAIL);
			response = new ResponseEntity<ResponseData>(resData, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response;
	}
	
	/**
	* UI Dashboard Area#3
	* POD CPU, POD Memory, Node CPU, Node Memory 
	* @return
	* @throws Exception
	*/
	@ApiOperation(value="UI Dashboard Area#3", httpMethod="GET", notes="POD CPU, POD Memory, Node CPU, Node Memory")
	@ApiImplicitParams({
		@ApiImplicitParam(
				name = "cluster_id",
				value = "Cluster ID (ex) 1",
				required = true,
				dataType = "string",
				paramType = "path"
		)
	})
	@ApiResponses(value={
			@ApiResponse( code=200, message="SUCCESS"),
			@ApiResponse( code=500, message="Internal Server Error")
	})
	
	@RequestMapping(value="/cluster/{cluster_id}/nexcloud/dashboard/area3", method=RequestMethod.GET)
	@ResponseBody
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public ResponseEntity<ResponseData> getClusterDashboardArea3( @PathVariable("cluster_id") String cluster_id ) throws Exception {
		
		ResponseEntity<ResponseData> response 			= null;
		try{
			response 									= service.getClusterDashboardArea3( cluster_id );
		}catch(Exception e){
			
			ResponseData resData	= new ResponseData();
			resData.setResponse_code(Const.INTERNAL_SERVER_ERROR);
			resData.setMessage(Const.FAIL);
			response = new ResponseEntity<ResponseData>(resData, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response;
	}
}
