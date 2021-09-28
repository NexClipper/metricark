package com.nexcloud.api.k8s.controller;

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

import com.nexcloud.api.domain.ResponseData;
import com.nexcloud.api.k8s.service.K8SStatefulsetService;
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
public class K8SStatefulsetController extends SpringBootServletInitializer implements WebApplicationInitializer {
	
	@Autowired
    private K8SStatefulsetService service;
	
	static final Logger logger = LoggerFactory.getLogger(K8SStatefulsetController.class);
	

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
	            name = "statefulset", 
	            value = "replicaset name (ex) '.*' => (전체 statefulset), statefulset name", 
	            required = true, 
	            dataType = "string", 
	            paramType = "path"
	    ),
		@ApiImplicitParam(
	            name = "namespace", 
	            value = "namespace (ex) nexclipper", 
	            required = false, 
	            dataType = "string", 
	            paramType = "query"
	    )
	})
	@ApiResponses(value={
			@ApiResponse( code=200, message="SUCCESS"),
			@ApiResponse( code=500, message="Internal Server Error")
	})
	
	@RequestMapping(value="/cluster/{cluster_id}/statefulset/{statefulset}/creation", method=RequestMethod.GET)
	@ResponseBody
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public ResponseEntity<ResponseData> getReplicasetCreation(    @PathVariable("cluster_id") String cluster_id
																,@PathVariable("statefulset") String statefulset
																,@QueryParam("namespace") String namespace
																) throws Exception {
		
		ResponseEntity<ResponseData> response 			= null;
		try{
			response 									= service.getReplicasetCreation( cluster_id, statefulset, namespace );
		}catch(Exception e){
			
			ResponseData resData	= new ResponseData();
			resData.setResponse_code(Const.INTERNAL_SERVER_ERROR);
			resData.setMessage(Const.FAIL);
			response = new ResponseEntity<ResponseData>(resData, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response;
	}
	
	/**
	* Statefulset Labels 정보
	* @return
	* @throws Exception
	*/
	@ApiOperation(value="Statefulset Labels 정보", httpMethod="GET", notes="Statefulset Labels 정보")
	@ApiImplicitParams({
		@ApiImplicitParam(
				name = "cluster_id",
				value = "Cluster ID (ex) 1",
				required = true,
				dataType = "string",
				paramType = "path"
		),
		@ApiImplicitParam(
	            name = "statefulset", 
	            value = "replicaset name (ex) '.*' => (전체 statefulset), statefulset name", 
	            required = true, 
	            dataType = "string", 
	            paramType = "path"
	    ),
		@ApiImplicitParam(
	            name = "namespace", 
	            value = "namespace (ex) nexclipper", 
	            required = false, 
	            dataType = "string", 
	            paramType = "query"
	    )
	})
	@ApiResponses(value={
			@ApiResponse( code=200, message="SUCCESS"),
			@ApiResponse( code=500, message="Internal Server Error")
	})
	
	@RequestMapping(value="/cluster/{cluster_id}/statefulset/{statefulset}/labels", method=RequestMethod.GET)
	@ResponseBody
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public ResponseEntity<ResponseData> getReplicasetLabels(    @PathVariable("cluster_id") String cluster_id
																,@PathVariable("statefulset") String statefulset
																,@QueryParam("namespace") String namespace
																) throws Exception {
		
		ResponseEntity<ResponseData> response 			= null;
		try{
			response 									= service.getStatefulsetLabels( cluster_id, statefulset, namespace );
		}catch(Exception e){
			
			ResponseData resData	= new ResponseData();
			resData.setResponse_code(Const.INTERNAL_SERVER_ERROR);
			resData.setMessage(Const.FAIL);
			response = new ResponseEntity<ResponseData>(resData, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response;
	}
	
	/**
	* Number of desired pods for a StatefulSet
	* @return
	* @throws Exception
	*/
	@ApiOperation(value="Number of desired pods for a StatefulSet", httpMethod="GET", notes="Statefulset에서 생성하고자 하는 POD개수")
	@ApiImplicitParams({
		@ApiImplicitParam(
				name = "cluster_id",
				value = "Cluster ID (ex) 1",
				required = true,
				dataType = "string",
				paramType = "path"
		),
		@ApiImplicitParam(
	            name = "statefulset", 
	            value = "replicaset name (ex) '.*' => (전체 statefulset), statefulset name", 
	            required = true, 
	            dataType = "string", 
	            paramType = "path"
	    ),
		@ApiImplicitParam(
	            name = "namespace", 
	            value = "namespace (ex) nexclipper", 
	            required = false, 
	            dataType = "string", 
	            paramType = "query"
	    )
	})
	@ApiResponses(value={
			@ApiResponse( code=200, message="SUCCESS"),
			@ApiResponse( code=500, message="Internal Server Error")
	})
	
	@RequestMapping(value="/cluster/{cluster_id}/statefulset/{statefulset}/pod", method=RequestMethod.GET)
	@ResponseBody
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public ResponseEntity<ResponseData> getReplicasetPods(    @PathVariable("cluster_id") String cluster_id
															,@PathVariable("statefulset") String statefulset
															,@QueryParam("namespace") String namespace
															) throws Exception {
		
		ResponseEntity<ResponseData> response 			= null;
		try{
			response 									= service.getReplicasetPods( cluster_id, statefulset, namespace );
		}catch(Exception e){
			
			ResponseData resData	= new ResponseData();
			resData.setResponse_code(Const.INTERNAL_SERVER_ERROR);
			resData.setMessage(Const.FAIL);
			response = new ResponseEntity<ResponseData>(resData, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response;
	}
	
	/**
	* The generation observed by the StatefulSet controller
	* @return
	* @throws Exception
	*/
	@ApiOperation(value="The generation observed by the StatefulSet controller", httpMethod="GET", notes="The generation observed by the StatefulSet controller")
	@ApiImplicitParams({
		@ApiImplicitParam(
				name = "cluster_id",
				value = "Cluster ID (ex) 1",
				required = true,
				dataType = "string",
				paramType = "path"
		),
		@ApiImplicitParam(
	            name = "statefulset", 
	            value = "replicaset name (ex) '.*' => (전체 statefulset), statefulset name", 
	            required = true, 
	            dataType = "string", 
	            paramType = "path"
	    ),
		@ApiImplicitParam(
	            name = "namespace", 
	            value = "namespace (ex) nexclipper", 
	            required = false, 
	            dataType = "string", 
	            paramType = "query"
	    )
	})
	@ApiResponses(value={
			@ApiResponse( code=200, message="SUCCESS"),
			@ApiResponse( code=500, message="Internal Server Error")
	})
	
	@RequestMapping(value="/cluster/{cluster_id}/statefulset/{statefulset}/status/observed", method=RequestMethod.GET)
	@ResponseBody
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public ResponseEntity<ResponseData> getReplicasetStatusObserved( @PathVariable("cluster_id") String cluster_id
																	,@PathVariable("statefulset") String statefulset
																	,@QueryParam("namespace") String namespace
																	) throws Exception {
		
		ResponseEntity<ResponseData> response 			= null;
		try{
			response 									= service.getReplicasetStatusObserved( cluster_id, statefulset, namespace );
		}catch(Exception e){
			
			ResponseData resData	= new ResponseData();
			resData.setResponse_code(Const.INTERNAL_SERVER_ERROR);
			resData.setMessage(Const.FAIL);
			response = new ResponseEntity<ResponseData>(resData, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response;
	}
	
	/**
	* The number of current replicas per StatefulSet
	* @return
	* @throws Exception
	*/
	@ApiOperation(value="The number of current replicas per StatefulSet", httpMethod="GET", notes="Statefulset에서 현재 생성한 POD개수")
	@ApiImplicitParams({
		@ApiImplicitParam(
				name = "cluster_id",
				value = "Cluster ID (ex) 1",
				required = true,
				dataType = "string",
				paramType = "path"
		),
		@ApiImplicitParam(
	            name = "statefulset", 
	            value = "replicaset name (ex) '.*' => (전체 statefulset), statefulset name", 
	            required = true, 
	            dataType = "string", 
	            paramType = "path"
	    ),
		@ApiImplicitParam(
	            name = "namespace", 
	            value = "namespace (ex) nexclipper", 
	            required = false, 
	            dataType = "string", 
	            paramType = "query"
	    )
	})
	@ApiResponses(value={
			@ApiResponse( code=200, message="SUCCESS"),
			@ApiResponse( code=500, message="Internal Server Error")
	})
	
	@RequestMapping(value="/cluster/{cluster_id}/statefulset/{statefulset}/pod/current", method=RequestMethod.GET)
	@ResponseBody
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public ResponseEntity<ResponseData> getReplicasetCurrentPods(@PathVariable("cluster_id") String cluster_id
																,@PathVariable("statefulset") String statefulset
																,@QueryParam("namespace") String namespace
																) throws Exception {
		
		ResponseEntity<ResponseData> response 			= null;
		try{
			response 									= service.getReplicasetCurrentPods( cluster_id, statefulset, namespace );
		}catch(Exception e){
			
			ResponseData resData	= new ResponseData();
			resData.setResponse_code(Const.INTERNAL_SERVER_ERROR);
			resData.setMessage(Const.FAIL);
			response = new ResponseEntity<ResponseData>(resData, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response;
	}
	
	/**
	* The number of ready replicas per StatefulSet
	* @return
	* @throws Exception
	*/
	@ApiOperation(value="The number of ready replicas per StatefulSet", httpMethod="GET", notes="The number of ready replicas per StatefulSet")
	@ApiImplicitParams({
		@ApiImplicitParam(
				name = "cluster_id",
				value = "Cluster ID (ex) 1",
				required = true,
				dataType = "string",
				paramType = "path"
		),
		@ApiImplicitParam(
	            name = "statefulset", 
	            value = "replicaset name (ex) '.*' => (전체 statefulset), statefulset name", 
	            required = true, 
	            dataType = "string", 
	            paramType = "path"
	    ),
		@ApiImplicitParam(
	            name = "namespace", 
	            value = "namespace (ex) nexclipper", 
	            required = false, 
	            dataType = "string", 
	            paramType = "query"
	    )
	})
	@ApiResponses(value={
			@ApiResponse( code=200, message="SUCCESS"),
			@ApiResponse( code=500, message="Internal Server Error")
	})
	
	@RequestMapping(value="/cluster/{cluster_id}/statefulset/{statefulset}/pod/ready", method=RequestMethod.GET)
	@ResponseBody
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public ResponseEntity<ResponseData> getReplicasetReadyPods(@PathVariable("cluster_id") String cluster_id
																,@PathVariable("statefulset") String statefulset
																,@QueryParam("namespace") String namespace
																) throws Exception {
		
		ResponseEntity<ResponseData> response 			= null;
		try{
			response 									= service.getReplicasetReadyPods( cluster_id, statefulset, namespace );
		}catch(Exception e){
			
			ResponseData resData	= new ResponseData();
			resData.setResponse_code(Const.INTERNAL_SERVER_ERROR);
			resData.setMessage(Const.FAIL);
			response = new ResponseEntity<ResponseData>(resData, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response;
	}
}
