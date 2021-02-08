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
import com.nexcloud.api.node.service.NodeMemoryService;
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
public class NodeMemoryController extends SpringBootServletInitializer implements WebApplicationInitializer {
	
	@Autowired
    private NodeMemoryService memoryService;
	
	static final Logger logger = LoggerFactory.getLogger(NodeMemoryController.class);
	

	/**
	* Node별 Memory 정보
	* @return
	* @throws Exception
	*/
	@ApiOperation(value="Node별 Memory 정보", httpMethod="GET", notes="core")
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
	            required = false, 
	            dataType = "string", 
	            paramType = "query"
	    ),
		@ApiImplicitParam(
	            name = "end", 
	            value = "조회 종료시간( Unix Timestamp ) (ex) 1521577239 ", 
	            required = false, 
	            dataType = "string", 
	            paramType = "query"
	    )
	})
	@ApiResponses(value={
			@ApiResponse( code=200, message="SUCCESS"),
			@ApiResponse( code=500, message="Internal Server Error")
	})
	
	@RequestMapping(value="/cluster/{cluster_id}/nodes/resources/memory", method=RequestMethod.GET)
	@ResponseBody
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public ResponseEntity<ResponseData> getNodeMemoryResource(   @PathVariable("cluster_id") String cluster_id
																,@QueryParam("start") String start
																,@QueryParam("end") String end
																) throws Exception {
		
		ResponseEntity<ResponseData> response 			= null;
		try{
			response 									= memoryService.getNodeMemoryResource( cluster_id, start, end );
		}catch(Exception e){
			
			ResponseData resData	= new ResponseData();
			resData.setResponse_code(Const.INTERNAL_SERVER_ERROR);
			resData.setMessage(Const.FAIL);
			response = new ResponseEntity<ResponseData>(resData, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response;
	}

	/**
	*  특정 Node Memory  Usage (%)
	* @return
	* @throws Exception
	*/
	@ApiOperation(value="특정 Node Memory Usage (%)", httpMethod="GET")
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
	            paramType = "query"
	    ),
		@ApiImplicitParam(
	            name = "end", 
	            value = "조회 종료시간( Unix Timestamp ) (ex) 1521577239 ", 
	            required = false, 
	            dataType = "string", 
	            paramType = "query"
	    )
	})
	@ApiResponses(value={
			@ApiResponse( code=200, message="SUCCESS"),
			@ApiResponse( code=500, message="Internal Server Error")
	})
	
	@RequestMapping(value="/cluster/{cluster_id}/nodes/{node_name}/resources/memory/used/usage", method=RequestMethod.GET)
	@ResponseBody
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public ResponseEntity<ResponseData> getNodeMemoryUsageResource(  @PathVariable("cluster_id") String cluster_id
																	,@PathVariable("node_name") String node_name
																	,@QueryParam("start") String start
																	,@QueryParam("end") String end
															  ) throws Exception {
		
		ResponseEntity<ResponseData> response 			= null;
		try{
			response 									= memoryService.getNodeMemoryUsageResource( cluster_id, node_name, start, end );
		}catch(Exception e){
			
			ResponseData resData	= new ResponseData();
			resData.setResponse_code(Const.INTERNAL_SERVER_ERROR);
			resData.setMessage(Const.FAIL);
			response = new ResponseEntity<ResponseData>(resData, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response;
	}
	
	/**
	*  특정 Node Memory Used byte
	* @return
	* @throws Exception
	*/
	@ApiOperation(value="특정 Node Memory Used byte", httpMethod="GET")
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
	            paramType = "query"
	    ),
		@ApiImplicitParam(
	            name = "end", 
	            value = "조회 종료시간( Unix Timestamp ) (ex) 1521577239 ", 
	            required = false, 
	            dataType = "string", 
	            paramType = "query"
	    )
	})
	@ApiResponses(value={
			@ApiResponse( code=200, message="SUCCESS"),
			@ApiResponse( code=500, message="Internal Server Error")
	})
	
	@RequestMapping(value="/cluster/{cluster_id}/nodes/{node_name}/resources/memory/used/byte", method=RequestMethod.GET)
	@ResponseBody
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public ResponseEntity<ResponseData> getNodeMemoryUsedByteResource(    @PathVariable("cluster_id") String cluster_id
																		 ,@PathVariable("node_name") String node_name
																	 	 ,@QueryParam("start") String start
																	 	 ,@QueryParam("end") String end
															  ) throws Exception {
		
		ResponseEntity<ResponseData> response 			= null;
		try{
			response 									= memoryService.getNodeMemoryUsedByteResource( cluster_id, node_name, start, end );
		}catch(Exception e){
			
			ResponseData resData	= new ResponseData();
			resData.setResponse_code(Const.INTERNAL_SERVER_ERROR);
			resData.setMessage(Const.FAIL);
			response = new ResponseEntity<ResponseData>(resData, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response;
	}
	
	/**
	*  특정 Node Capacity Memory
	* @return
	* @throws Exception
	*/
	@ApiOperation(value="특정 Node Capacity Memory", httpMethod="GET", notes="core")
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
	            paramType = "query"
	    ),
		@ApiImplicitParam(
	            name = "end", 
	            value = "조회 종료시간( Unix Timestamp ) (ex) 1521577239 ", 
	            required = false, 
	            dataType = "string", 
	            paramType = "query"
	    )
	})
	@ApiResponses(value={
			@ApiResponse( code=200, message="SUCCESS"),
			@ApiResponse( code=500, message="Internal Server Error")
	})
	
	@RequestMapping(value="/cluster/{cluster_id}/nodes/{node_name}/resources/memory/capacity", method=RequestMethod.GET)
	@ResponseBody
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public ResponseEntity<ResponseData> getNodeMemoryCapacityResource(    @PathVariable("cluster_id") String cluster_id
																		 ,@PathVariable("node_name") String node_name
																		 ,@QueryParam("start") String start
																		 ,@QueryParam("end") String end
															  ) throws Exception {
		
		ResponseEntity<ResponseData> response 			= null;
		try{
			response 									= memoryService.getNodeMemoryCapacityResource( cluster_id, node_name, start, end );
		}catch(Exception e){
			
			ResponseData resData	= new ResponseData();
			resData.setResponse_code(Const.INTERNAL_SERVER_ERROR);
			resData.setMessage(Const.FAIL);
			response = new ResponseEntity<ResponseData>(resData, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response;
	}
	
	/**
	*  특정 Node Allocatable Memory
	* @return
	* @throws Exception
	*/
	@ApiOperation(value="특정 Node Allocatable Memory", httpMethod="GET", notes="core")
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
	            paramType = "query"
	    ),
		@ApiImplicitParam(
	            name = "end", 
	            value = "조회 종료시간( Unix Timestamp ) (ex) 1521577239 ", 
	            required = false, 
	            dataType = "string", 
	            paramType = "query"
	    )
	})
	@ApiResponses(value={
			@ApiResponse( code=200, message="SUCCESS"),
			@ApiResponse( code=500, message="Internal Server Error")
	})
	
	@RequestMapping(value="/cluster/{cluster_id}/nodes/{node_name}/resources/memory/allocatable", method=RequestMethod.GET)
	@ResponseBody
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public ResponseEntity<ResponseData> getNodeMemoryAllocatableResource(    @PathVariable("cluster_id") String cluster_id
																			,@PathVariable("node_name") String node_name
																			,@QueryParam("start") String start
																			,@QueryParam("end") String end
															  ) throws Exception {
		
		ResponseEntity<ResponseData> response 			= null;
		try{
			response 									= memoryService.getNodeMemoryAllocatableResource( cluster_id, node_name, start, end );
		}catch(Exception e){
			
			ResponseData resData	= new ResponseData();
			resData.setResponse_code(Const.INTERNAL_SERVER_ERROR);
			resData.setMessage(Const.FAIL);
			response = new ResponseEntity<ResponseData>(resData, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response;
	}
	
	/**
	*  특정 Node Request Memory
	* @return
	* @throws Exception
	*/
	@ApiOperation(value="특정 Node Request Memory", httpMethod="GET", notes="core")
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
	            paramType = "query"
	    ),
		@ApiImplicitParam(
	            name = "end", 
	            value = "조회 종료시간( Unix Timestamp ) (ex) 1521577239 ", 
	            required = false, 
	            dataType = "string", 
	            paramType = "query"
	    )
	})
	@ApiResponses(value={
			@ApiResponse( code=200, message="SUCCESS"),
			@ApiResponse( code=500, message="Internal Server Error")
	})
	
	@RequestMapping(value="/cluster/{cluster_id}/nodes/{node_name}/resources/memory/request", method=RequestMethod.GET)
	@ResponseBody
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public ResponseEntity<ResponseData> getNodeMemoryRequestResource(    @PathVariable("cluster_id") String cluster_id
																		,@PathVariable("node_name") String node_name
																		,@QueryParam("start") String start
																		,@QueryParam("end") String end
															  ) throws Exception {
		
		ResponseEntity<ResponseData> response 			= null;
		try{
			response 									= memoryService.getNodeMemoryRequestResource( cluster_id, node_name, start, end );
		}catch(Exception e){
			
			ResponseData resData	= new ResponseData();
			resData.setResponse_code(Const.INTERNAL_SERVER_ERROR);
			resData.setMessage(Const.FAIL);
			response = new ResponseEntity<ResponseData>(resData, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response;
	}
	
	/**
	*  특정 Node Swap total Memory
	* @return
	* @throws Exception
	*/
	@ApiOperation(value="특정 Node Swap total Memory", httpMethod="GET", notes="core")
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
	            paramType = "query"
	    ),
		@ApiImplicitParam(
	            name = "end", 
	            value = "조회 종료시간( Unix Timestamp ) (ex) 1521577239 ", 
	            required = false, 
	            dataType = "string", 
	            paramType = "query"
	    )
	})
	@ApiResponses(value={
			@ApiResponse( code=200, message="SUCCESS"),
			@ApiResponse( code=500, message="Internal Server Error")
	})
	
	@RequestMapping(value="/cluster/{cluster_id}/nodes/{node_name}/resources/memory/swap/total", method=RequestMethod.GET)
	@ResponseBody
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public ResponseEntity<ResponseData> getNodeMemorySwapTotalResource(     @PathVariable("cluster_id") String cluster_id
																			,@PathVariable("node_name") String node_name
																			,@QueryParam("start") String start
																			,@QueryParam("end") String end
															  ) throws Exception {
		
		ResponseEntity<ResponseData> response 			= null;
		try{
			response 									= memoryService.getNodeMemorySwapTotalResource( cluster_id, node_name, start, end );
		}catch(Exception e){
			
			ResponseData resData	= new ResponseData();
			resData.setResponse_code(Const.INTERNAL_SERVER_ERROR);
			resData.setMessage(Const.FAIL);
			response = new ResponseEntity<ResponseData>(resData, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response;
	}
	
	/**
	*  특정 Node Swap cache Memory
	* @return
	* @throws Exception
	*/
	@ApiOperation(value="특정 Node Swap cache Memory", httpMethod="GET", notes="core")
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
	            paramType = "query"
	    ),
		@ApiImplicitParam(
	            name = "end", 
	            value = "조회 종료시간( Unix Timestamp ) (ex) 1521577239 ", 
	            required = false, 
	            dataType = "string", 
	            paramType = "query"
	    )
	})
	@ApiResponses(value={
			@ApiResponse( code=200, message="SUCCESS"),
			@ApiResponse( code=500, message="Internal Server Error")
	})
	
	@RequestMapping(value="/cluster/{cluster_id}/nodes/{node_name}/resources/memory/swap/cache", method=RequestMethod.GET)
	@ResponseBody
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public ResponseEntity<ResponseData> getNodeMemorySwapCacheResource(     @PathVariable("cluster_id") String cluster_id
																			,@PathVariable("node_name") String node_name
																			,@QueryParam("start") String start
																			,@QueryParam("end") String end
															  ) throws Exception {
		
		ResponseEntity<ResponseData> response 			= null;
		try{
			response 									= memoryService.getNodeMemorySwapCacheResource( cluster_id, node_name, start, end );
		}catch(Exception e){
			
			ResponseData resData	= new ResponseData();
			resData.setResponse_code(Const.INTERNAL_SERVER_ERROR);
			resData.setMessage(Const.FAIL);
			response = new ResponseEntity<ResponseData>(resData, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response;
	}
	
	/**
	*  특정 Node Swap free Memory
	* @return
	* @throws Exception
	*/
	@ApiOperation(value="특정 Node Swap free Memory", httpMethod="GET", notes="core")
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
	            paramType = "query"
	    ),
		@ApiImplicitParam(
	            name = "end", 
	            value = "조회 종료시간( Unix Timestamp ) (ex) 1521577239 ", 
	            required = false, 
	            dataType = "string", 
	            paramType = "query"
	    )
	})
	@ApiResponses(value={
			@ApiResponse( code=200, message="SUCCESS"),
			@ApiResponse( code=500, message="Internal Server Error")
	})
	
	@RequestMapping(value="/cluster/{cluster_id}/nodes/{node_name}/resources/memory/swap/free", method=RequestMethod.GET)
	@ResponseBody
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public ResponseEntity<ResponseData> getNodeMemorySwapFreeResource(       @PathVariable("cluster_id") String cluster_id
																			,@PathVariable("node_name") String node_name
																			,@QueryParam("start") String start
																			,@QueryParam("end") String end
															  ) throws Exception {
		
		ResponseEntity<ResponseData> response 			= null;
		try{
			response 									= memoryService.getNodeMemorySwapFreeResource( cluster_id, node_name, start, end );
		}catch(Exception e){
			
			ResponseData resData	= new ResponseData();
			resData.setResponse_code(Const.INTERNAL_SERVER_ERROR);
			resData.setMessage(Const.FAIL);
			response = new ResponseEntity<ResponseData>(resData, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response;
	}
}
