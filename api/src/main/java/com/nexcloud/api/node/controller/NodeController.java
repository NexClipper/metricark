package com.nexcloud.api.node.controller;

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
import com.nexcloud.api.node.service.NodeService;
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
public class NodeController extends SpringBootServletInitializer implements WebApplicationInitializer {
	
	@Autowired
    private NodeService nodeService;
	
	static final Logger logger = LoggerFactory.getLogger(NodeController.class);
	
	/**
	* Unix creation timestamp
	* @return
	* @throws Exception
	*/
	@ApiOperation(value="Unix creation timestamp", httpMethod="GET", notes="Unix creation timestamp")
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
	            value = " Node Name(ex) '.*'=>(전체노드), node1", 
	            required = true, 
	            dataType = "string", 
	            paramType = "path"
	    )
	})
	@ApiResponses(value={
			@ApiResponse( code=200, message="SUCCESS"),
			@ApiResponse( code=500, message="Internal Server Error")
	})
	
	@RequestMapping(value="/cluster/{cluster_id}/nodes/{node_name}/creationtime", method=RequestMethod.GET)
	@ResponseBody
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public ResponseEntity<ResponseData> getClusterNodeCreationTime( @PathVariable("cluster_id") String cluster_id ,@PathVariable("node_name") String node_name ) throws Exception {
		
		ResponseEntity<ResponseData> response 			= null;
		try{
			response 									= nodeService.getClusterNodeCreationTime( cluster_id, node_name );
		}catch(Exception e){
			
			ResponseData resData	= new ResponseData();
			resData.setResponse_code(Const.INTERNAL_SERVER_ERROR);
			resData.setMessage(Const.FAIL);
			response = new ResponseEntity<ResponseData>(resData, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response;
	}
	
	/**
	* Kubernetes Node information
	* @return
	* @throws Exception
	*/
	@ApiOperation(value="Kubernetes Node information", httpMethod="GET", notes="core")
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
	            value = " Node Name(ex) '.*'=>(전체노드), node1", 
	            required = true, 
	            dataType = "string", 
	            paramType = "path"
	    )
	})
	@ApiResponses(value={
			@ApiResponse( code=200, message="SUCCESS"),
			@ApiResponse( code=500, message="Internal Server Error")
	})
	
	@RequestMapping(value="/cluster/{cluster_id}/nodes/{node_name}/info", method=RequestMethod.GET)
	@ResponseBody
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public ResponseEntity<ResponseData> getClusterNodeInfo( @PathVariable("cluster_id") String cluster_id,@PathVariable("node_name") String node_name ) throws Exception {
		
		ResponseEntity<ResponseData> response 			= null;
		try{
			response 									= nodeService.getClusterNodeInfo( cluster_id, node_name );
		}catch(Exception e){
			
			ResponseData resData	= new ResponseData();
			resData.setResponse_code(Const.INTERNAL_SERVER_ERROR);
			resData.setMessage(Const.FAIL);
			response = new ResponseEntity<ResponseData>(resData, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response;
	}
	
	/**
	* Kubernetes Node labels
	* @return
	* @throws Exception
	*/
	@ApiOperation(value="Kubernetes Node labels", httpMethod="GET", notes="core")
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
	            value = " Node Name(ex) '.*'=>(전체노드), node1", 
	            required = true, 
	            dataType = "string", 
	            paramType = "path"
	    )
	})
	@ApiResponses(value={
			@ApiResponse( code=200, message="SUCCESS"),
			@ApiResponse( code=500, message="Internal Server Error")
	})
	
	@RequestMapping(value="/cluster/{cluster_id}/nodes/{node_name}/labels", method=RequestMethod.GET)
	@ResponseBody
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public ResponseEntity<ResponseData> getClusterNodeLabels( @PathVariable("cluster_id") String cluster_id,@PathVariable("node_name") String node_name ) throws Exception {
		
		ResponseEntity<ResponseData> response 			= null;
		try{
			response 									= nodeService.getClusterNodeLabels( cluster_id, node_name );
		}catch(Exception e){
			
			ResponseData resData	= new ResponseData();
			resData.setResponse_code(Const.INTERNAL_SERVER_ERROR);
			resData.setMessage(Const.FAIL);
			response = new ResponseEntity<ResponseData>(resData, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response;
	}
	
	/**
	* Kubernetes Node uname
	* @return
	* @throws Exception
	*/
	@ApiOperation(value="Kubernetes Node uname info", httpMethod="GET", notes="core")
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
	            value = " Node Name(ex) '.*'=>(전체노드), node1", 
	            required = true, 
	            dataType = "string", 
	            paramType = "path"
	    )
	})
	@ApiResponses(value={
			@ApiResponse( code=200, message="SUCCESS"),
			@ApiResponse( code=500, message="Internal Server Error")
	})
	
	@RequestMapping(value="/cluster/{cluster_id}/nodes/{node_name}/uname", method=RequestMethod.GET)
	@ResponseBody
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public ResponseEntity<ResponseData> getClusterNodeUname( @PathVariable("cluster_id") String cluster_id,@PathVariable("node_name") String node_name ) throws Exception {
		
		ResponseEntity<ResponseData> response 			= null;
		try{
			response 									= nodeService.getClusterNodeUname( cluster_id, node_name );
		}catch(Exception e){
			
			ResponseData resData	= new ResponseData();
			resData.setResponse_code(Const.INTERNAL_SERVER_ERROR);
			resData.setMessage(Const.FAIL);
			response = new ResponseEntity<ResponseData>(resData, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response;
	}
	
	/**
	* Kubernetes pod allocatable in node
	* @return
	* @throws Exception
	*/
	@ApiOperation(value="Kubernetes pod allocatable in node", httpMethod="GET", notes="Node에 할당된 POD생성 개수")
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
	            value = " Node Name(ex) node1", 
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
	
	@RequestMapping(value="/cluster/{cluster_id}/nodes/{node_name}/pod/allocatable", method=RequestMethod.GET)
	@ResponseBody
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public ResponseEntity<ResponseData> getClusterNodePodAllocatable(   @PathVariable("cluster_id") String cluster_id
																		,@PathVariable("node_name") String node_name
																		,@QueryParam("start") String start
																		,@QueryParam("end") String end
																	  ) throws Exception {
		
		ResponseEntity<ResponseData> response 			= null;
		try{
			response 									= nodeService.getClusterNodePodAllocatable( cluster_id, node_name, start, end );
		}catch(Exception e){
			
			ResponseData resData	= new ResponseData();
			resData.setResponse_code(Const.INTERNAL_SERVER_ERROR);
			resData.setMessage(Const.FAIL);
			response = new ResponseEntity<ResponseData>(resData, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response;
	}
	
	/**
	* Kubernetes pod capacity in node
	* @return
	* @throws Exception
	*/
	@ApiOperation(value="Kubernetes pod capacity in node", httpMethod="GET", notes="Node에 생성가능한 POD생성 개수")
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
	            value = " Node Name(ex) node1", 
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
	
	@RequestMapping(value="/cluster/{cluster_id}/nodes/{node_name}/pod/capacity", method=RequestMethod.GET)
	@ResponseBody
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public ResponseEntity<ResponseData> getClusterNodePodCapacity(   @PathVariable("cluster_id") String cluster_id
																	,@PathVariable("node_name") String node_name
																	,@QueryParam("start") String start
																	,@QueryParam("end") String end
																  ) throws Exception {
		
		ResponseEntity<ResponseData> response 			= null;
		try{
			response 									= nodeService.getClusterNodePodCapacity( cluster_id, node_name, start, end );
		}catch(Exception e){
			
			ResponseData resData	= new ResponseData();
			resData.setResponse_code(Const.INTERNAL_SERVER_ERROR);
			resData.setMessage(Const.FAIL);
			response = new ResponseEntity<ResponseData>(resData, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response;
	}
	
	/**
	* Kubernetes pod requested in node
	* @return
	* @throws Exception
	*/
	@ApiOperation(value="Kubernetes pod requested in node", httpMethod="GET", notes="Node에 생성가능한 POD생성 개수")
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
	            value = " Node Name(ex) node1", 
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
	
	@RequestMapping(value="/cluster/{cluster_id}/nodes/{node_name}/pod/requested", method=RequestMethod.GET)
	@ResponseBody
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public ResponseEntity<ResponseData> getClusterNodePodRequested(   @PathVariable("cluster_id") String cluster_id
																	,@PathVariable("node_name") String node_name
																	,@QueryParam("start") String start
																	,@QueryParam("end") String end
																  ) throws Exception {
		
		ResponseEntity<ResponseData> response 			= null;
		try{
			response 									= nodeService.getClusterNodePodRequested( cluster_id, node_name, start, end );
		}catch(Exception e){
			
			ResponseData resData	= new ResponseData();
			resData.setResponse_code(Const.INTERNAL_SERVER_ERROR);
			resData.setMessage(Const.FAIL);
			response = new ResponseEntity<ResponseData>(resData, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response;
	}
	
	/**
	* Whether a node can schedule new pods
	* @return
	* @throws Exception
	*/
	@ApiOperation(value="Whether a node can schedule new pods", httpMethod="GET", notes="1:unschedulable, 0:schedulable")
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
	            value = " Node Name(ex) '.*'=>(전체노드), node1", 
	            required = true, 
	            dataType = "string", 
	            paramType = "path"
	    )
	})
	@ApiResponses(value={
			@ApiResponse( code=200, message="SUCCESS"),
			@ApiResponse( code=500, message="Internal Server Error")
	})
	
	@RequestMapping(value="/cluster/{cluster_id}/nodes/{node_name}/unschedulable", method=RequestMethod.GET)
	@ResponseBody
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public ResponseEntity<ResponseData> getClusterNodeSchedulable( @PathVariable("cluster_id") String cluster_id ,@PathVariable("node_name") String node_name ) throws Exception {
		
		ResponseEntity<ResponseData> response 			= null;
		try{
			response 									= nodeService.getClusterNodeSchedulable( cluster_id, node_name );
		}catch(Exception e){
			
			ResponseData resData	= new ResponseData();
			resData.setResponse_code(Const.INTERNAL_SERVER_ERROR);
			resData.setMessage(Const.FAIL);
			response = new ResponseEntity<ResponseData>(resData, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response;
	}
}
