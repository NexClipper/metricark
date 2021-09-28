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
import com.nexcloud.api.k8s.service.K8SPodService;
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
public class K8SPodController extends SpringBootServletInitializer implements WebApplicationInitializer {
	
	@Autowired
    private K8SPodService service;
	
	static final Logger logger = LoggerFactory.getLogger(K8SPodController.class);
	
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
	            name = "pod_name", 
	            value = "pod name (ex) '.*' => (전체 pod), pod name", 
	            required = true, 
	            dataType = "string", 
	            paramType = "path"
	    ),
		@ApiImplicitParam(
	            name = "namespace", 
	            value = "namespace (ex) kube-system", 
	            required = false, 
	            dataType = "string", 
	            paramType = "query"
	    )
	})
	@ApiResponses(value={
			@ApiResponse( code=200, message="SUCCESS"),
			@ApiResponse( code=500, message="Internal Server Error")
	})
	
	@RequestMapping(value="/cluster/{cluster_id}/pod/{pod_name}/creation", method=RequestMethod.GET)
	@ResponseBody
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public ResponseEntity<ResponseData> getPodCreation(  @PathVariable("cluster_id") String cluster_id 
														,@PathVariable("pod_name") String pod_name
														,@QueryParam("namespace") String namespace
														) throws Exception {
		
		ResponseEntity<ResponseData> response 			= null;
		try{
			response 									= service.getPodCreation( cluster_id, pod_name, namespace );
		}catch(Exception e){
			
			ResponseData resData	= new ResponseData();
			resData.setResponse_code(Const.INTERNAL_SERVER_ERROR);
			resData.setMessage(Const.FAIL);
			response = new ResponseEntity<ResponseData>(resData, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response;
	}
	
	/**
	* 전체 POD갯수
	* @return
	* @throws Exception
	*/
	@ApiOperation(value="전체 POD갯수", httpMethod="GET", notes="전체 POD갯수")
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
	
	@RequestMapping(value="/cluster/{cluster_id}/pod", method=RequestMethod.GET)
	@ResponseBody
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public ResponseEntity<ResponseData> getPodTotal(    @PathVariable("cluster_id") String cluster_id ) throws Exception {
		
		ResponseEntity<ResponseData> response 			= null;
		try{
			response 									= service.getPodTotal( cluster_id );
		}catch(Exception e){
			
			ResponseData resData	= new ResponseData();
			resData.setResponse_code(Const.INTERNAL_SERVER_ERROR);
			resData.setMessage(Const.FAIL);
			response = new ResponseEntity<ResponseData>(resData, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response;
	}
	
	/**
	* Running상태 POD갯수
	* @return
	* @throws Exception
	*/
	@ApiOperation(value="Running상태 POD갯수", httpMethod="GET", notes="전체 POD갯수")
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
	
	@RequestMapping(value="/cluster/{cluster_id}/pod/running", method=RequestMethod.GET)
	@ResponseBody
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public ResponseEntity<ResponseData> getPodRunning(    @PathVariable("cluster_id") String cluster_id ) throws Exception {
		
		ResponseEntity<ResponseData> response 			= null;
		try{
			response 									= service.getPodRunning( cluster_id );
		}catch(Exception e){
			
			ResponseData resData	= new ResponseData();
			resData.setResponse_code(Const.INTERNAL_SERVER_ERROR);
			resData.setMessage(Const.FAIL);
			response = new ResponseEntity<ResponseData>(resData, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response;
	}

	/**
	* Information about a container in a pod
	* @return
	* @throws Exception
	*/
	@ApiOperation(value="Information about a container in a pod", httpMethod="GET", notes="Information about a container in a pod")
	@ApiImplicitParams({
		@ApiImplicitParam(
				name = "cluster_id",
				value = "Cluster ID (ex) 1",
				required = true,
				dataType = "string",
				paramType = "path"
		),
		@ApiImplicitParam(
	            name = "pod_name", 
	            value = "pod name (ex) '.*' => (전체 pod), pod name", 
	            required = true, 
	            dataType = "string", 
	            paramType = "path"
	    ),
		@ApiImplicitParam(
	            name = "namespace", 
	            value = "namespace (ex) kube-system", 
	            required = false, 
	            dataType = "string", 
	            paramType = "query"
	    )
	})
	@ApiResponses(value={
			@ApiResponse( code=200, message="SUCCESS"),
			@ApiResponse( code=500, message="Internal Server Error")
	})
	
	@RequestMapping(value="/cluster/{cluster_id}/pod/{pod_name}/container/info", method=RequestMethod.GET)
	@ResponseBody
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public ResponseEntity<ResponseData> getPodContainerInfo(    @PathVariable("cluster_id") String cluster_id
																,@PathVariable("pod_name") String pod_name
																,@QueryParam("namespace") String namespace
																) throws Exception {
		
		ResponseEntity<ResponseData> response 			= null;
		try{
			response 									= service.getPodContainerInfo( cluster_id, pod_name, namespace );
		}catch(Exception e){
			
			ResponseData resData	= new ResponseData();
			resData.setResponse_code(Const.INTERNAL_SERVER_ERROR);
			resData.setMessage(Const.FAIL);
			response = new ResponseEntity<ResponseData>(resData, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response;
	}
	
	/**
	* The number of requested limit resource by a container
	* @return
	* @throws Exception
	*/
	@ApiOperation(value="The number of requested limit resource by a container( CPU & Memory )", httpMethod="GET", notes="The number of requested limit resource by a container( CPU & Memory )")
	@ApiImplicitParams({
		@ApiImplicitParam(
				name = "cluster_id",
				value = "Cluster ID (ex) 1",
				required = true,
				dataType = "string",
				paramType = "path"
		),
		@ApiImplicitParam(
	            name = "pod_name", 
	            value = "pod name (ex) '.*' => (전체 pod), pod name", 
	            required = true, 
	            dataType = "string", 
	            paramType = "path"
	    ),
		@ApiImplicitParam(
	            name = "namespace", 
	            value = "namespace (ex) kube-system", 
	            required = false, 
	            dataType = "string", 
	            paramType = "query"
	    )
	})
	@ApiResponses(value={
			@ApiResponse( code=200, message="SUCCESS"),
			@ApiResponse( code=500, message="Internal Server Error")
	})
	
	@RequestMapping(value="/cluster/{cluster_id}/pod/{pod_name}/resource/limit", method=RequestMethod.GET)
	@ResponseBody
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public ResponseEntity<ResponseData> getPodContainerResourceLimit(    @PathVariable("cluster_id") String cluster_id
																		,@PathVariable("pod_name") String pod_name
																		,@QueryParam("namespace") String namespace
																		) throws Exception {
		
		ResponseEntity<ResponseData> response 			= null;
		try{
			response 									= service.getPodContainerResourceLimit( cluster_id, pod_name, namespace );
		}catch(Exception e){
			
			ResponseData resData	= new ResponseData();
			resData.setResponse_code(Const.INTERNAL_SERVER_ERROR);
			resData.setMessage(Const.FAIL);
			response = new ResponseEntity<ResponseData>(resData, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response;
	}
	
	/**
	* The number of CPU cores requested limit by an container
	* @return
	* @throws Exception
	*/
	@ApiOperation(value="The number of CPU cores requested limit by an container", httpMethod="GET", notes="The number of CPU cores requested limit by an container")
	@ApiImplicitParams({
		@ApiImplicitParam(
				name = "cluster_id",
				value = "Cluster ID (ex) 1",
				required = true,
				dataType = "string",
				paramType = "path"
		),
		@ApiImplicitParam(
	            name = "pod_name", 
	            value = "pod name (ex) '.*' => (전체 pod), pod name", 
	            required = true, 
	            dataType = "string", 
	            paramType = "path"
	    ),
		@ApiImplicitParam(
	            name = "namespace", 
	            value = "namespace (ex) kube-system", 
	            required = false, 
	            dataType = "string", 
	            paramType = "query"
	    )
	})
	@ApiResponses(value={
			@ApiResponse( code=200, message="SUCCESS"),
			@ApiResponse( code=500, message="Internal Server Error")
	})
	
	@RequestMapping(value="/cluster/{cluster_id}/pod/{pod_name}/resource/limit/cpu", method=RequestMethod.GET)
	@ResponseBody
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public ResponseEntity<ResponseData> getPodContainerResourceLimitCpu(    @PathVariable("cluster_id") String cluster_id
																			,@PathVariable("pod_name") String pod_name
																			,@QueryParam("namespace") String namespace
																			) throws Exception {
		
		ResponseEntity<ResponseData> response 			= null;
		try{
			response 									= service.getPodContainerResourceLimitCpu( cluster_id, pod_name, namespace );
		}catch(Exception e){
			
			ResponseData resData	= new ResponseData();
			resData.setResponse_code(Const.INTERNAL_SERVER_ERROR);
			resData.setMessage(Const.FAIL);
			response = new ResponseEntity<ResponseData>(resData, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response;
	}
	
	/**
	* The number of Memory cores requested limit by an container
	* @return
	* @throws Exception
	*/
	@ApiOperation(value="The number of Memory cores requested limit by an container", httpMethod="GET", notes="The number of Memory cores requested limit by an container")
	@ApiImplicitParams({
		@ApiImplicitParam(
				name = "cluster_id",
				value = "Cluster ID (ex) 1",
				required = true,
				dataType = "string",
				paramType = "path"
		),
		@ApiImplicitParam(
	            name = "pod_name", 
	            value = "pod name (ex) '.*' => (전체 pod), pod name", 
	            required = true, 
	            dataType = "string", 
	            paramType = "path"
	    ),
		@ApiImplicitParam(
	            name = "namespace", 
	            value = "namespace (ex) kube-system", 
	            required = false, 
	            dataType = "string", 
	            paramType = "query"
	    )
	})
	@ApiResponses(value={
			@ApiResponse( code=200, message="SUCCESS"),
			@ApiResponse( code=500, message="Internal Server Error")
	})
	
	@RequestMapping(value="/cluster/{cluster_id}/pod/{pod_name}/resource/limit/memory", method=RequestMethod.GET)
	@ResponseBody
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public ResponseEntity<ResponseData> getPodContainerResourceLimitMemory(      @PathVariable("cluster_id") String cluster_id
																				,@PathVariable("pod_name") String pod_name
																				,@QueryParam("namespace") String namespace
																				) throws Exception {
		
		ResponseEntity<ResponseData> response 			= null;
		try{
			response 									= service.getPodContainerResourceLimitMemory( cluster_id, pod_name, namespace );
		}catch(Exception e){
			
			ResponseData resData	= new ResponseData();
			resData.setResponse_code(Const.INTERNAL_SERVER_ERROR);
			resData.setMessage(Const.FAIL);
			response = new ResponseEntity<ResponseData>(resData, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response;
	}
	
	/**
	* The number of requested request resource by a container
	* @return
	* @throws Exception
	*/
	@ApiOperation(value="The number of requested request resource by a container( CPU & Memory )", httpMethod="GET", notes="The number of requested request resource by a container( CPU & Memory )")
	@ApiImplicitParams({
		@ApiImplicitParam(
				name = "cluster_id",
				value = "Cluster ID (ex) 1",
				required = true,
				dataType = "string",
				paramType = "path"
		),
		@ApiImplicitParam(
	            name = "pod_name", 
	            value = "pod name (ex) '.*' => (전체 pod), pod name", 
	            required = true, 
	            dataType = "string", 
	            paramType = "path"
	    ),
		@ApiImplicitParam(
	            name = "namespace", 
	            value = "namespace (ex) kube-system", 
	            required = false, 
	            dataType = "string", 
	            paramType = "query"
	    )
	})
	@ApiResponses(value={
			@ApiResponse( code=200, message="SUCCESS"),
			@ApiResponse( code=500, message="Internal Server Error")
	})
	
	@RequestMapping(value="/cluster/{cluster_id}/pod/{pod_name}/resource/request", method=RequestMethod.GET)
	@ResponseBody
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public ResponseEntity<ResponseData> getPodContainerResourceRequest(    @PathVariable("cluster_id") String cluster_id
																		,@PathVariable("pod_name") String pod_name
																		,@QueryParam("namespace") String namespace
																		) throws Exception {
		
		ResponseEntity<ResponseData> response 			= null;
		try{
			response 									= service.getPodContainerResourceRequest( cluster_id, pod_name, namespace );
		}catch(Exception e){
			
			ResponseData resData	= new ResponseData();
			resData.setResponse_code(Const.INTERNAL_SERVER_ERROR);
			resData.setMessage(Const.FAIL);
			response = new ResponseEntity<ResponseData>(resData, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response;
	}
	
	/**
	* The number of CPU cores requested request by an container
	* @return
	* @throws Exception
	*/
	@ApiOperation(value="The number of CPU cores requested request by an container", httpMethod="GET", notes="The number of CPU cores requested request by an container")
	@ApiImplicitParams({
		@ApiImplicitParam(
				name = "cluster_id",
				value = "Cluster ID (ex) 1",
				required = true,
				dataType = "string",
				paramType = "path"
		),
		@ApiImplicitParam(
	            name = "pod_name", 
	            value = "pod name (ex) '.*' => (전체 pod), pod name", 
	            required = true, 
	            dataType = "string", 
	            paramType = "path"
	    ),
		@ApiImplicitParam(
	            name = "namespace", 
	            value = "namespace (ex) kube-system", 
	            required = false, 
	            dataType = "string", 
	            paramType = "query"
	    )
	})
	@ApiResponses(value={
			@ApiResponse( code=200, message="SUCCESS"),
			@ApiResponse( code=500, message="Internal Server Error")
	})
	
	@RequestMapping(value="/cluster/{cluster_id}/pod/{pod_name}/resource/request/cpu", method=RequestMethod.GET)
	@ResponseBody
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public ResponseEntity<ResponseData> getPodContainerResourceRequestCpu(   @PathVariable("cluster_id") String cluster_id
																			,@PathVariable("pod_name") String pod_name
																			,@QueryParam("namespace") String namespace
																			) throws Exception {
		
		ResponseEntity<ResponseData> response 			= null;
		try{
			response 									= service.getPodContainerResourceRequestCpu( cluster_id, pod_name, namespace );
		}catch(Exception e){
			
			ResponseData resData	= new ResponseData();
			resData.setResponse_code(Const.INTERNAL_SERVER_ERROR);
			resData.setMessage(Const.FAIL);
			response = new ResponseEntity<ResponseData>(resData, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response;
	}
	
	/**
	* The number of Memory cores requested request by an container
	* @return
	* @throws Exception
	*/
	@ApiOperation(value="The number of Memory cores requested request by an container", httpMethod="GET", notes="The number of Memory cores requested request by an container")
	@ApiImplicitParams({
		@ApiImplicitParam(
				name = "cluster_id",
				value = "Cluster ID (ex) 1",
				required = true,
				dataType = "string",
				paramType = "path"
		),
		@ApiImplicitParam(
	            name = "pod_name", 
	            value = "pod name (ex) '.*' => (전체 pod), pod name", 
	            required = true, 
	            dataType = "string", 
	            paramType = "path"
	    ),
		@ApiImplicitParam(
	            name = "namespace", 
	            value = "namespace (ex) kube-system", 
	            required = false, 
	            dataType = "string", 
	            paramType = "query"
	    )
	})
	@ApiResponses(value={
			@ApiResponse( code=200, message="SUCCESS"),
			@ApiResponse( code=500, message="Internal Server Error")
	})
	
	@RequestMapping(value="/cluster/{cluster_id}/pod/{pod_name}/resource/request/memory", method=RequestMethod.GET)
	@ResponseBody
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public ResponseEntity<ResponseData> getPodContainerResourceRequestMemory(    @PathVariable("cluster_id") String cluster_id
																				,@PathVariable("pod_name") String pod_name
																				,@QueryParam("namespace") String namespace
																				) throws Exception {
		
		ResponseEntity<ResponseData> response 			= null;
		try{
			response 									= service.getPodContainerResourceRequestMemory( cluster_id, pod_name, namespace );
		}catch(Exception e){
			
			ResponseData resData	= new ResponseData();
			resData.setResponse_code(Const.INTERNAL_SERVER_ERROR);
			resData.setMessage(Const.FAIL);
			response = new ResponseEntity<ResponseData>(resData, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response;
	}
	
	/**
	* Describes the last reason the container was in terminated state
	* @return
	* @throws Exception
	*/
	@ApiOperation(value="Describes the last reason the container was in terminated state", httpMethod="GET", notes="1:true, 0:false")
	@ApiImplicitParams({
		@ApiImplicitParam(
				name = "cluster_id",
				value = "Cluster ID (ex) 1",
				required = true,
				dataType = "string",
				paramType = "path"
		),
		@ApiImplicitParam(
	            name = "pod_name", 
	            value = "pod name (ex) '.*' => (전체 pod), pod name", 
	            required = true, 
	            dataType = "string", 
	            paramType = "path"
	    ),
		@ApiImplicitParam(
	            name = "reason", 
	            value = "reaon (ex) '.*' => (전체 reaon), Completed, ContainerCannotRun, DeadlineExceeded, Error, Evicted, OOMKilled", 
	            required = true, 
	            dataType = "string", 
	            paramType = "path"
	    ),
		@ApiImplicitParam(
	            name = "namespace", 
	            value = "namespace (ex) kube-system", 
	            required = false, 
	            dataType = "string", 
	            paramType = "query"
	    )
	})
	@ApiResponses(value={
			@ApiResponse( code=200, message="SUCCESS"),
			@ApiResponse( code=500, message="Internal Server Error")
	})
	
	@RequestMapping(value="/cluster/{cluster_id}/pod/{pod_name}/status/reason/{reason}", method=RequestMethod.GET)
	@ResponseBody
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public ResponseEntity<ResponseData> getPodContainerStatusReason(    @PathVariable("cluster_id") String cluster_id
																		,@PathVariable("pod_name") String pod_name
																		,@PathVariable("reason") String reason
																		,@QueryParam("namespace") String namespace
																		) throws Exception {
		
		ResponseEntity<ResponseData> response 			= null;
		try{
			response 									= service.getPodContainerStatusReason( cluster_id, pod_name, reason, namespace );
		}catch(Exception e){
			
			ResponseData resData	= new ResponseData();
			resData.setResponse_code(Const.INTERNAL_SERVER_ERROR);
			resData.setMessage(Const.FAIL);
			response = new ResponseEntity<ResponseData>(resData, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response;
	}
	
	/**
	* Describes whether the containers readiness check succeeded
	* @return
	* @throws Exception
	*/
	@ApiOperation(value="Describes whether the containers readiness check succeeded", httpMethod="GET", notes="1: true, 0:false ")
	@ApiImplicitParams({
		@ApiImplicitParam(
				name = "cluster_id",
				value = "Cluster ID (ex) 1",
				required = true,
				dataType = "string",
				paramType = "path"
		),
		@ApiImplicitParam(
	            name = "pod_name", 
	            value = "pod name (ex) '.*' => (전체 pod), pod name", 
	            required = true, 
	            dataType = "string", 
	            paramType = "path"
	    ),
		@ApiImplicitParam(
	            name = "namespace", 
	            value = "namespace (ex) kube-system", 
	            required = false, 
	            dataType = "string", 
	            paramType = "query"
	    )
	})
	@ApiResponses(value={
			@ApiResponse( code=200, message="SUCCESS"),
			@ApiResponse( code=500, message="Internal Server Error")
	})
	
	@RequestMapping(value="/cluster/{cluster_id}/pod/{pod_name}/status/ready", method=RequestMethod.GET)
	@ResponseBody
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public ResponseEntity<ResponseData> getPodContainerStatusReay(    @PathVariable("cluster_id") String cluster_id
																	,@PathVariable("pod_name") String pod_name
																	,@QueryParam("namespace") String namespace
																		) throws Exception {
		
		ResponseEntity<ResponseData> response 			= null;
		try{
			response 									= service.getPodContainerStatusReay( cluster_id, pod_name, namespace );
		}catch(Exception e){
			
			ResponseData resData	= new ResponseData();
			resData.setResponse_code(Const.INTERNAL_SERVER_ERROR);
			resData.setMessage(Const.FAIL);
			response = new ResponseEntity<ResponseData>(resData, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response;
	}
	
	/**
	* The number of container restarts per container
	* @return
	* @throws Exception
	*/
	@ApiOperation(value="The number of container restarts per container", httpMethod="GET", notes="pod restart number")
	@ApiImplicitParams({
		@ApiImplicitParam(
				name = "cluster_id",
				value = "Cluster ID (ex) 1",
				required = true,
				dataType = "string",
				paramType = "path"
		),
		@ApiImplicitParam(
	            name = "pod_name", 
	            value = "pod name (ex) '.*' => (전체 pod), pod name", 
	            required = true, 
	            dataType = "string", 
	            paramType = "path"
	    ),
		@ApiImplicitParam(
	            name = "namespace", 
	            value = "namespace (ex) kube-system", 
	            required = false, 
	            dataType = "string", 
	            paramType = "query"
	    )
	})
	@ApiResponses(value={
			@ApiResponse( code=200, message="SUCCESS"),
			@ApiResponse( code=500, message="Internal Server Error")
	})
	
	@RequestMapping(value="/cluster/{cluster_id}/pod/{pod_name}/status/restarts", method=RequestMethod.GET)
	@ResponseBody
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public ResponseEntity<ResponseData> getPodContainerStatusRestarts(   @PathVariable("cluster_id") String cluster_id
																		,@PathVariable("pod_name") String pod_name
																		,@QueryParam("namespace") String namespace
																		) throws Exception {
		
		ResponseEntity<ResponseData> response 			= null;
		try{
			response 									= service.getPodContainerStatusRestarts( cluster_id, pod_name, namespace );
		}catch(Exception e){
			
			ResponseData resData	= new ResponseData();
			resData.setResponse_code(Const.INTERNAL_SERVER_ERROR);
			resData.setMessage(Const.FAIL);
			response = new ResponseEntity<ResponseData>(resData, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response;
	}
	
	/**
	* Describes whether the container is currently in running state
	* @return
	* @throws Exception
	*/
	@ApiOperation(value="Describes whether the container is currently in running state", httpMethod="GET", notes="1:실행가능 상태, 0:실행불가능한 상태")
	@ApiImplicitParams({
		@ApiImplicitParam(
				name = "cluster_id",
				value = "Cluster ID (ex) 1",
				required = true,
				dataType = "string",
				paramType = "path"
		),
		@ApiImplicitParam(
	            name = "pod_name", 
	            value = "pod name (ex) '.*' => (전체 pod), pod name", 
	            required = true, 
	            dataType = "string", 
	            paramType = "path"
	    ),
		@ApiImplicitParam(
	            name = "namespace", 
	            value = "namespace (ex) kube-system", 
	            required = false, 
	            dataType = "string", 
	            paramType = "query"
	    )
	})
	@ApiResponses(value={
			@ApiResponse( code=200, message="SUCCESS"),
			@ApiResponse( code=500, message="Internal Server Error")
	})
	
	@RequestMapping(value="/cluster/{cluster_id}/pod/{pod_name}/status/running", method=RequestMethod.GET)
	@ResponseBody
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public ResponseEntity<ResponseData> getPodContainerStatusRunning(   @PathVariable("cluster_id") String cluster_id
																		,@PathVariable("pod_name") String pod_name
																		,@QueryParam("namespace") String namespace
																		) throws Exception {
		
		ResponseEntity<ResponseData> response 			= null;
		try{
			response 									= service.getPodContainerStatusRunning( cluster_id, pod_name, namespace );
		}catch(Exception e){
			
			ResponseData resData	= new ResponseData();
			resData.setResponse_code(Const.INTERNAL_SERVER_ERROR);
			resData.setMessage(Const.FAIL);
			response = new ResponseEntity<ResponseData>(resData, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response;
	}
	
	/**
	* Describes whether the container is currently in terminated state
	* @return
	* @throws Exception
	*/
	@ApiOperation(value="Describes whether the container is currently in terminated state", httpMethod="GET", notes="1:종료된 상태, 0:종료되지않은 상태")
	@ApiImplicitParams({
		@ApiImplicitParam(
				name = "cluster_id",
				value = "Cluster ID (ex) 1",
				required = true,
				dataType = "string",
				paramType = "path"
		),
		@ApiImplicitParam(
	            name = "pod_name", 
	            value = "pod name (ex) '.*' => (전체 pod), pod name", 
	            required = true, 
	            dataType = "string", 
	            paramType = "path"
	    ),
		@ApiImplicitParam(
	            name = "namespace", 
	            value = "namespace (ex) kube-system", 
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
	
	@RequestMapping(value="/cluster/{cluster_id}/pod/{pod_name}/status/terminated", method=RequestMethod.GET)
	@ResponseBody
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public ResponseEntity<ResponseData> getPodContainerStatusTerminated(   @PathVariable("cluster_id") String cluster_id
																		,@PathVariable("pod_name") String pod_name
																		,@QueryParam("namespace") String namespace
																		) throws Exception {
		
		ResponseEntity<ResponseData> response 			= null;
		try{
			response 									= service.getPodContainerStatusTerminated( cluster_id, pod_name, namespace );
		}catch(Exception e){
			
			ResponseData resData	= new ResponseData();
			resData.setResponse_code(Const.INTERNAL_SERVER_ERROR);
			resData.setMessage(Const.FAIL);
			response = new ResponseEntity<ResponseData>(resData, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response;
	}
	
	/**
	* Describes the reason the container is currently in terminated state
	* @return
	* @throws Exception
	*/
	@ApiOperation(value="Describes the reason the container is currently in terminated state", httpMethod="GET", notes="1:종료된 상태이유, 0:종료된 상태이유가 아님")
	@ApiImplicitParams({
		@ApiImplicitParam(
				name = "cluster_id",
				value = "Cluster ID (ex) 1",
				required = true,
				dataType = "string",
				paramType = "path"
		),
		@ApiImplicitParam(
	            name = "pod_name", 
	            value = "pod name (ex) '.*' => (전체 pod), pod name", 
	            required = true, 
	            dataType = "string", 
	            paramType = "path"
	    ),
		@ApiImplicitParam(
	            name = "reason", 
	            value = "종료이유 (ex) '.*' => (전체 이유), Completed, ContainerCannotRun, DeadlineExceeded, Error, Evicted, OOMKilled", 
	            required = true, 
	            dataType = "string", 
	            paramType = "path"
	    ),
		@ApiImplicitParam(
	            name = "namespace", 
	            value = "namespace (ex) kube-system", 
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
	
	@RequestMapping(value="/cluster/{cluster_id}/pod/{pod_name}/status/terminated/reason/{reason}", method=RequestMethod.GET)
	@ResponseBody
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public ResponseEntity<ResponseData> getPodContainerStatusTerminatedReason(   @PathVariable("cluster_id") String cluster_id
																				,@PathVariable("pod_name") String pod_name
																				,@PathVariable("reason") String reason
																				,@QueryParam("namespace") String namespace
																				) throws Exception {
		
		ResponseEntity<ResponseData> response 			= null;
		try{
			response 									= service.getPodContainerStatusTerminatedReason( cluster_id, pod_name, reason, namespace );
		}catch(Exception e){
			
			ResponseData resData	= new ResponseData();
			resData.setResponse_code(Const.INTERNAL_SERVER_ERROR);
			resData.setMessage(Const.FAIL);
			response = new ResponseEntity<ResponseData>(resData, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response;
	}
	
	/**
	* Describes whether the container is currently in waiting state
	* @return
	* @throws Exception
	*/
	@ApiOperation(value="Describes whether the container is currently in waiting state", httpMethod="GET", notes="1:대기중, 0:대기중이 아님")
	@ApiImplicitParams({
		@ApiImplicitParam(
				name = "cluster_id",
				value = "Cluster ID (ex) 1",
				required = true,
				dataType = "string",
				paramType = "path"
		),
		@ApiImplicitParam(
	            name = "pod_name", 
	            value = "pod name (ex) '.*' => (전체 pod), pod name", 
	            required = true, 
	            dataType = "string", 
	            paramType = "path"
	    ),
		@ApiImplicitParam(
	            name = "namespace", 
	            value = "namespace (ex) kube-system", 
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
	
	@RequestMapping(value="/cluster/{cluster_id}/pod/{pod_name}/status/waiting", method=RequestMethod.GET)
	@ResponseBody
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public ResponseEntity<ResponseData> getPodContainerStatusWaiting(   @PathVariable("cluster_id") String cluster_id
																		,@PathVariable("pod_name") String pod_name
																		,@QueryParam("namespace") String namespace
																		) throws Exception {
		
		ResponseEntity<ResponseData> response 			= null;
		try{
			response 									= service.getPodContainerStatusWaiting( cluster_id, pod_name, namespace );
		}catch(Exception e){
			
			ResponseData resData	= new ResponseData();
			resData.setResponse_code(Const.INTERNAL_SERVER_ERROR);
			resData.setMessage(Const.FAIL);
			response = new ResponseEntity<ResponseData>(resData, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response;
	}
	
	/**
	* Describes the reason the container is currently in waiting state
	* @return
	* @throws Exception
	*/
	@ApiOperation(value="Describes the reason the container is currently in terminated state", httpMethod="GET", notes="1:대기상태이유, 0:대기상태이유가 아님")
	@ApiImplicitParams({
		@ApiImplicitParam(
				name = "cluster_id",
				value = "Cluster ID (ex) 1",
				required = true,
				dataType = "string",
				paramType = "path"
		),
		@ApiImplicitParam(
	            name = "pod_name", 
	            value = "pod name (ex) '.*' => (전체 pod), pod name", 
	            required = true, 
	            dataType = "string", 
	            paramType = "path"
	    ),
		@ApiImplicitParam(
	            name = "reason", 
	            value = "종료이유 (ex) '.*' => (전체 이유), ContainerCreating, CrashLoopBackOff, CreateContainerConfigError, CreateContainerError, ErrImagePull, ImagePullBackOff, InvalidImageName", 
	            required = true, 
	            dataType = "string", 
	            paramType = "path"
	    ),
		@ApiImplicitParam(
	            name = "namespace", 
	            value = "namespace (ex) kube-system", 
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
	
	@RequestMapping(value="/cluster/{cluster_id}/pod/{pod_name}/status/waiting/reason/{reason}", method=RequestMethod.GET)
	@ResponseBody
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public ResponseEntity<ResponseData> getPodContainerStatusWaitingReason(   @PathVariable("cluster_id") String cluster_id
																			,@PathVariable("pod_name") String pod_name
																			,@PathVariable("reason") String reason
																			,@QueryParam("namespace") String namespace
																			) throws Exception {
		
		ResponseEntity<ResponseData> response 			= null;
		try{
			response 									= service.getPodContainerStatusWaitingReason( cluster_id, pod_name, reason, namespace );
		}catch(Exception e){
			
			ResponseData resData	= new ResponseData();
			resData.setResponse_code(Const.INTERNAL_SERVER_ERROR);
			resData.setMessage(Const.FAIL);
			response = new ResponseEntity<ResponseData>(resData, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response;
	}
	
	/**
	* Unix creation timestamp
	* @return
	* @throws Exception
	*/
	/*
	@ApiOperation(value="Unix creation timestamp", httpMethod="GET", notes="POD생성 시간 ( unix time )")
	@ApiImplicitParams({
		@ApiImplicitParam(
				name = "cluster_id",
				value = "Cluster ID (ex) 1",
				required = true,
				dataType = "string",
				paramType = "path"
		),
		@ApiImplicitParam(
	            name = "pod_name", 
	            value = "pod name (ex) '.*' => (전체 pod), pod name", 
	            required = true, 
	            dataType = "string", 
	            paramType = "path"
	    ),
		@ApiImplicitParam(
	            name = "namespace", 
	            value = "namespace (ex) kube-system", 
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
	
	@RequestMapping(value="/cluster/{cluster_id}/pod/{pod_name}/created", method=RequestMethod.GET)
	@ResponseBody
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public ResponseEntity<ResponseData> getPodContainerCreated(   @PathVariable("cluster_id") String cluster_id
																,@PathVariable("pod_name") String pod_name
																,@QueryParam("namespace") String namespace
																) throws Exception {
		
		ResponseEntity<ResponseData> response 			= null;
		try{
			response 									= service.getPodContainerCreated( cluster_id, pod_name, namespace );
		}catch(Exception e){
			
			ResponseData resData	= new ResponseData();
			resData.setResponse_code(Const.INTERNAL_SERVER_ERROR);
			resData.setMessage(Const.FAIL);
			response = new ResponseEntity<ResponseData>(resData, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response;
	}
	*/
	
	/**
	* Information about pod.
	* @return
	* @throws Exception
	*/
	@ApiOperation(value="Information about pod.", httpMethod="GET", notes="Information about pod.")
	@ApiImplicitParams({
		@ApiImplicitParam(
				name = "cluster_id",
				value = "Cluster ID (ex) 1",
				required = true,
				dataType = "string",
				paramType = "path"
		),
		@ApiImplicitParam(
	            name = "pod_name", 
	            value = "pod name (ex) '.*' => (전체 pod), pod name", 
	            required = true, 
	            dataType = "string", 
	            paramType = "path"
	    ),
		@ApiImplicitParam(
	            name = "namespace", 
	            value = "namespace (ex) kube-system", 
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
	
	@RequestMapping(value="/cluster/{cluster_id}/pod/{pod_name}/info", method=RequestMethod.GET)
	@ResponseBody
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public ResponseEntity<ResponseData> getPodInfo(   @PathVariable("cluster_id") String cluster_id
													,@PathVariable("pod_name") String pod_name
													,@QueryParam("namespace") String namespace
													) throws Exception {
		
		ResponseEntity<ResponseData> response 			= null;
		try{
			response 									= service.getPodInfo( cluster_id, pod_name, namespace );
		}catch(Exception e){
			
			ResponseData resData	= new ResponseData();
			resData.setResponse_code(Const.INTERNAL_SERVER_ERROR);
			resData.setMessage(Const.FAIL);
			response = new ResponseEntity<ResponseData>(resData, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response;
	}
	
	/**
	* Kubernetes labels converted to Prometheus labels
	* @return
	* @throws Exception
	*/
	@ApiOperation(value="Kubernetes labels converted to Prometheus labels", httpMethod="GET", notes="Yml파일에 설정한 POD Label정보( 'label_'로 시작하는 attribute를 파싱하여 사용해야함.")
	@ApiImplicitParams({
		@ApiImplicitParam(
				name = "cluster_id",
				value = "Cluster ID (ex) 1",
				required = true,
				dataType = "string",
				paramType = "path"
		),
		@ApiImplicitParam(
	            name = "pod_name", 
	            value = "pod name (ex) '.*' => (전체 pod), pod name", 
	            required = true, 
	            dataType = "string", 
	            paramType = "path"
	    ),
		@ApiImplicitParam(
	            name = "namespace", 
	            value = "namespace (ex) kube-system", 
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
	
	@RequestMapping(value="/cluster/{cluster_id}/pod/{pod_name}/labels", method=RequestMethod.GET)
	@ResponseBody
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public ResponseEntity<ResponseData> getPodLabels(   @PathVariable("cluster_id") String cluster_id
														,@PathVariable("pod_name") String pod_name
														,@QueryParam("namespace") String namespace
														) throws Exception {
		
		ResponseEntity<ResponseData> response 			= null;
		try{
			response 									= service.getPodLabels( cluster_id, pod_name, namespace );
		}catch(Exception e){
			
			ResponseData resData	= new ResponseData();
			resData.setResponse_code(Const.INTERNAL_SERVER_ERROR);
			resData.setMessage(Const.FAIL);
			response = new ResponseEntity<ResponseData>(resData, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response;
	}
	
	/**
	* Information about the Pod's owner
	* @return
	* @throws Exception
	*/
	@ApiOperation(value="Information about the Pod's owner", httpMethod="GET", notes="포드 소유자에 대한 정보")
	@ApiImplicitParams({
		@ApiImplicitParam(
				name = "cluster_id",
				value = "Cluster ID (ex) 1",
				required = true,
				dataType = "string",
				paramType = "path"
		),
		@ApiImplicitParam(
	            name = "pod_name", 
	            value = "pod name (ex) '.*' => (전체 pod), pod name", 
	            required = true, 
	            dataType = "string", 
	            paramType = "path"
	    ),
		@ApiImplicitParam(
	            name = "namespace", 
	            value = "namespace (ex) kube-system", 
	            required = false, 
	            dataType = "string", 
	            paramType = "query",
	            defaultValue=""
	    ),
		@ApiImplicitParam(
	            name = "owner_kind", 
	            value = "owner kind (ex) '.*' => (전체 ), DaemonSet, ReplicaSet, StatefulSet, Node, Job", 
	            required = false, 
	            dataType = "string", 
	            paramType = "query",
	            defaultValue=""
	    ),
		@ApiImplicitParam(
	            name = "owner_name", 
	            value = "owner name (ex) '.*' => (전체 ), Owner Name", 
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
	
	@RequestMapping(value="/cluster/{cluster_id}/pod/{pod_name}/owner", method=RequestMethod.GET)
	@ResponseBody
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public ResponseEntity<ResponseData> getPodOwner(     @PathVariable("cluster_id") String cluster_id
														,@PathVariable("pod_name") String pod_name
														,@QueryParam("namespace") String namespace
														,@QueryParam("owner_kind") String owner_kind
														,@QueryParam("owner_name") String owner_name
														) throws Exception {
		
		ResponseEntity<ResponseData> response 			= null;
		try{
			response 									= service.getPodOwner( cluster_id, pod_name, namespace, owner_kind, owner_name );
		}catch(Exception e){
			
			ResponseData resData	= new ResponseData();
			resData.setResponse_code(Const.INTERNAL_SERVER_ERROR);
			resData.setMessage(Const.FAIL);
			response = new ResponseEntity<ResponseData>(resData, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response;
	}
	
	/**
	* Describes the restart policy in use by this pod
	* @return
	* @throws Exception
	*/
	@ApiOperation(value="Describes the restart policy in use by this pod", httpMethod="GET", notes="Attribute의 'type'필드")
	@ApiImplicitParams({
		@ApiImplicitParam(
				name = "cluster_id",
				value = "Cluster ID (ex) 1",
				required = true,
				dataType = "string",
				paramType = "path"
		),
		@ApiImplicitParam(
	            name = "pod_name", 
	            value = "pod name (ex) '.*' => (전체 pod), pod name", 
	            required = true, 
	            dataType = "string", 
	            paramType = "path"
	    ),
		@ApiImplicitParam(
	            name = "namespace", 
	            value = "namespace (ex) kube-system", 
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
	
	@RequestMapping(value="/cluster/{cluster_id}/pod/{pod_name}/policy", method=RequestMethod.GET)
	@ResponseBody
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public ResponseEntity<ResponseData> getPodPolicy(     @PathVariable("cluster_id") String cluster_id
														 ,@PathVariable("pod_name") String pod_name
														 ,@QueryParam("namespace") String namespace
													 	) throws Exception {
		
		ResponseEntity<ResponseData> response 			= null;
		try{
			response 									= service.getPodPolicy( cluster_id, pod_name, namespace );
		}catch(Exception e){
			
			ResponseData resData	= new ResponseData();
			resData.setResponse_code(Const.INTERNAL_SERVER_ERROR);
			resData.setMessage(Const.FAIL);
			response = new ResponseEntity<ResponseData>(resData, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response;
	}
	
	/**
	* Information about persistentvolumeclaim volumes in a pod
	* @return
	* @throws Exception
	*/
	@ApiOperation(value="Information about persistentvolumeclaim volumes in a pod", httpMethod="GET", notes="Information about persistentvolumeclaim volumes in a pod")
	@ApiImplicitParams({
		@ApiImplicitParam(
				name = "cluster_id",
				value = "Cluster ID (ex) 1",
				required = true,
				dataType = "string",
				paramType = "path"
		),
		@ApiImplicitParam(
	            name = "pod_name", 
	            value = "pod name (ex) '.*' => (전체 pod), pod name", 
	            required = true, 
	            dataType = "string", 
	            paramType = "path"
	    ),
		@ApiImplicitParam(
	            name = "namespace", 
	            value = "namespace (ex) kube-system", 
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
	
	@RequestMapping(value="/cluster/{cluster_id}/pod/{pod_name}/volumes/claim/info", method=RequestMethod.GET)
	@ResponseBody
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public ResponseEntity<ResponseData> getPodPersistentVolumeClaimInfo(  @PathVariable("cluster_id") String cluster_id
																		 ,@PathVariable("pod_name") String pod_name
																		 ,@QueryParam("namespace") String namespace
																	 	) throws Exception {
		
		ResponseEntity<ResponseData> response 			= null;
		try{
			response 									= service.getPodPersistentVolumeClaimInfo( cluster_id, pod_name, namespace );
		}catch(Exception e){
			
			ResponseData resData	= new ResponseData();
			resData.setResponse_code(Const.INTERNAL_SERVER_ERROR);
			resData.setMessage(Const.FAIL);
			response = new ResponseEntity<ResponseData>(resData, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response;
	}
	
	/**
	* Describes whether a persistentvolumeclaim is mounted read only
	* @return
	* @throws Exception
	*/
	@ApiOperation(value="Describes whether a persistentvolumeclaim is mounted read only", httpMethod="GET", notes="1:읽기 전용으로 마운트됨, 0:읽기전용으로 마운드되지 않음")
	@ApiImplicitParams({
		@ApiImplicitParam(
				name = "cluster_id",
				value = "Cluster ID (ex) 1",
				required = true,
				dataType = "string",
				paramType = "path"
		),
		@ApiImplicitParam(
	            name = "pod_name", 
	            value = "pod name (ex) '.*' => (전체 pod), pod name", 
	            required = true, 
	            dataType = "string", 
	            paramType = "path"
	    ),
		@ApiImplicitParam(
	            name = "namespace", 
	            value = "namespace (ex) kube-system", 
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
	
	@RequestMapping(value="/cluster/{cluster_id}/pod/{pod_name}/volumes/claim/readonly", method=RequestMethod.GET)
	@ResponseBody
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public ResponseEntity<ResponseData> getPodPersistentVolumeClaimReadOnly(  @PathVariable("cluster_id") String cluster_id
																			 ,@PathVariable("pod_name") String pod_name
																			 ,@QueryParam("namespace") String namespace
																		 	) throws Exception {
		
		ResponseEntity<ResponseData> response 			= null;
		try{
			response 									= service.getPodPersistentVolumeClaimReadOnly( cluster_id, pod_name, namespace );
		}catch(Exception e){
			
			ResponseData resData	= new ResponseData();
			resData.setResponse_code(Const.INTERNAL_SERVER_ERROR);
			resData.setMessage(Const.FAIL);
			response = new ResponseEntity<ResponseData>(resData, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response;
	}
	
	/**
	* Start time in unix timestamp for a pod
	* @return
	* @throws Exception
	*/
	@ApiOperation(value="Start time in unix timestamp for a pod", httpMethod="GET", notes="Start time in unix timestamp for a pod")
	@ApiImplicitParams({
		@ApiImplicitParam(
				name = "cluster_id",
				value = "Cluster ID (ex) 1",
				required = true,
				dataType = "string",
				paramType = "path"
		),
		@ApiImplicitParam(
	            name = "pod_name", 
	            value = "pod name (ex) '.*' => (전체 pod), pod name", 
	            required = true, 
	            dataType = "string", 
	            paramType = "path"
	    ),
		@ApiImplicitParam(
	            name = "namespace", 
	            value = "namespace (ex) kube-system", 
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
	
	@RequestMapping(value="/cluster/{cluster_id}/pod/{pod_name}/starttime", method=RequestMethod.GET)
	@ResponseBody
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public ResponseEntity<ResponseData> getPodStartTime(  @PathVariable("cluster_id") String cluster_id
														 ,@PathVariable("pod_name") String pod_name
														 ,@QueryParam("namespace") String namespace
													 	) throws Exception {
		
		ResponseEntity<ResponseData> response 			= null;
		try{
			response 									= service.getPodStartTime( cluster_id, pod_name, namespace );
		}catch(Exception e){
			
			ResponseData resData	= new ResponseData();
			resData.setResponse_code(Const.INTERNAL_SERVER_ERROR);
			resData.setMessage(Const.FAIL);
			response = new ResponseEntity<ResponseData>(resData, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response;
	}
	
	/**
	* The pods current phase
	* @return
	* @throws Exception
	*/
	@ApiOperation(value="The pods current phase", httpMethod="GET", notes="1:true, 0:false")
	@ApiImplicitParams({
		@ApiImplicitParam(
				name = "cluster_id",
				value = "Cluster ID (ex) 1",
				required = true,
				dataType = "string",
				paramType = "path"
		),
		@ApiImplicitParam(
	            name = "pod_name", 
	            value = "pod name (ex) '.*' => (전체 pod), pod name", 
	            required = true, 
	            dataType = "string", 
	            paramType = "path"
	    ),
		@ApiImplicitParam(
	            name = "phase", 
	            value = "phase (ex) '.*' => (전체 phase), Failed, Pending, Running, Succeeded, Unknown", 
	            required = true, 
	            dataType = "string", 
	            paramType = "path"
	    ),
		@ApiImplicitParam(
	            name = "namespace", 
	            value = "namespace (ex) kube-system", 
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
	
	@RequestMapping(value="/cluster/{cluster_id}/pod/{pod_name}/status/phase/{phase}", method=RequestMethod.GET)
	@ResponseBody
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public ResponseEntity<ResponseData> getPodStatusPhase(    @PathVariable("cluster_id") String cluster_id
															 ,@PathVariable("pod_name") String pod_name
															 ,@PathVariable("phase") String phase
															 ,@QueryParam("namespace") String namespace
														 	) throws Exception {
		
		ResponseEntity<ResponseData> response 			= null;
		try{
			response 									= service.getPodStatusPhase( cluster_id, pod_name, phase, namespace );
		}catch(Exception e){
			
			ResponseData resData	= new ResponseData();
			resData.setResponse_code(Const.INTERNAL_SERVER_ERROR);
			resData.setMessage(Const.FAIL);
			response = new ResponseEntity<ResponseData>(resData, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response;
	}
	
	/**
	* Describes whether the pod is ready to serve requests
	* @return
	* @throws Exception
	*/
	@ApiOperation(value="Describes whether the pod is ready to serve requests", httpMethod="GET", notes="1:true, 0:false")
	@ApiImplicitParams({
		@ApiImplicitParam(
				name = "cluster_id",
				value = "Cluster ID (ex) 1",
				required = true,
				dataType = "string",
				paramType = "path"
		),
		@ApiImplicitParam(
	            name = "pod_name", 
	            value = "pod name (ex) '.*' => (전체 pod), pod name", 
	            required = true, 
	            dataType = "string", 
	            paramType = "path"
	    ),
		@ApiImplicitParam(
	            name = "condition", 
	            value = "condition (ex) '.*' => (전체 condition), false, true, unknown", 
	            required = true, 
	            dataType = "string", 
	            paramType = "path"
	    ),
		@ApiImplicitParam(
	            name = "namespace", 
	            value = "namespace (ex) kube-system", 
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
	
	@RequestMapping(value="/cluster/{cluster_id}/pod/{pod_name}/status/ready/{condition}", method=RequestMethod.GET)
	@ResponseBody
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public ResponseEntity<ResponseData> getPodStatusReady(    @PathVariable("cluster_id") String cluster_id
															 ,@PathVariable("pod_name") String pod_name
															 ,@PathVariable("condition") String condition
															 ,@QueryParam("namespace") String namespace
														 	) throws Exception {
		
		ResponseEntity<ResponseData> response 			= null;
		try{
			response 									= service.getPodStatusReady( cluster_id, pod_name, condition, namespace );
		}catch(Exception e){
			
			ResponseData resData	= new ResponseData();
			resData.setResponse_code(Const.INTERNAL_SERVER_ERROR);
			resData.setMessage(Const.FAIL);
			response = new ResponseEntity<ResponseData>(resData, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response;
	}
	
	/**
	* Describes the status of the scheduling process for the pod
	* @return
	* @throws Exception
	*/
	@ApiOperation(value="Describes the status of the scheduling process for the pod", httpMethod="GET", notes="1:true, 0:false")
	@ApiImplicitParams({
		@ApiImplicitParam(
				name = "cluster_id",
				value = "Cluster ID (ex) 1",
				required = true,
				dataType = "string",
				paramType = "path"
		),
		@ApiImplicitParam(
	            name = "pod_name", 
	            value = "pod name (ex) '.*' => (전체 pod), pod name", 
	            required = true, 
	            dataType = "string", 
	            paramType = "path"
	    ),
		@ApiImplicitParam(
	            name = "condition", 
	            value = "condition (ex) '.*' => (전체 condition), false, true, unknown", 
	            required = true, 
	            dataType = "string", 
	            paramType = "path"
	    ),
		@ApiImplicitParam(
	            name = "namespace", 
	            value = "namespace (ex) kube-system", 
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
	
	@RequestMapping(value="/cluster/{cluster_id}/pod/{pod_name}/status/scheduled/{condition}", method=RequestMethod.GET)
	@ResponseBody
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public ResponseEntity<ResponseData> getPodStatusScheduled(@PathVariable("cluster_id") String cluster_id
															 ,@PathVariable("pod_name") String pod_name
															 ,@PathVariable("condition") String condition
															 ,@QueryParam("namespace") String namespace
														 	) throws Exception {
		
		ResponseEntity<ResponseData> response 			= null;
		try{
			response 									= service.getPodStatusScheduled( cluster_id, pod_name, condition, namespace );
		}catch(Exception e){
			
			ResponseData resData	= new ResponseData();
			resData.setResponse_code(Const.INTERNAL_SERVER_ERROR);
			resData.setMessage(Const.FAIL);
			response = new ResponseEntity<ResponseData>(resData, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response;
	}
	
	/**
	* Cluster Pod usage (%)
	* @return
	* @throws Exception
	*/
	@ApiOperation(value="Cluster Pod usage (%)", httpMethod="GET", notes="백분률")
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
	
	@RequestMapping(value="/cluster/{cluster_id}/pod/usage", method=RequestMethod.GET)
	@ResponseBody
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public ResponseEntity<ResponseData> getClusterPodUsage(  @PathVariable("cluster_id") String cluster_id
															,@QueryParam("start") String start
															,@QueryParam("end") String end
															) throws Exception {
		
		ResponseEntity<ResponseData> response 			= null;
		try{
			response 									= service.getClusterPodUsage( cluster_id, start, end );
		}catch(Exception e){
			
			ResponseData resData	= new ResponseData();
			resData.setResponse_code(Const.INTERNAL_SERVER_ERROR);
			resData.setMessage(Const.FAIL);
			response = new ResponseEntity<ResponseData>(resData, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response;
	}
	
	/**
	* Cluster pod allocatable ( count )
	* @return
	* @throws Exception
	*/
	@ApiOperation(value="Cluster Pod allocatable ( count )", httpMethod="GET", notes="갯수")
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
	
	@RequestMapping(value="/cluster/{cluster_id}/pod/allocatable", method=RequestMethod.GET)
	@ResponseBody
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public ResponseEntity<ResponseData> getClusterPodAllocatable(  @PathVariable("cluster_id") String cluster_id
																,@QueryParam("start") String start
																,@QueryParam("end") String end
																) throws Exception {
			
		ResponseEntity<ResponseData> response 			= null;
		try{
			response 									= service.getClusterPodAllocatable( cluster_id, start, end );
		}catch(Exception e){
			
			ResponseData resData	= new ResponseData();
			resData.setResponse_code(Const.INTERNAL_SERVER_ERROR);
			resData.setMessage(Const.FAIL);
			response = new ResponseEntity<ResponseData>(resData, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response;
	}
	
	/**
	* Cluster pod capacity ( count )
	* @return
	* @throws Exception
	*/
	@ApiOperation(value="Cluster Pod capacity ( count )", httpMethod="GET", notes="갯수")
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
	
	@RequestMapping(value="/cluster/{cluster_id}/pod/capacity", method=RequestMethod.GET)
	@ResponseBody
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public ResponseEntity<ResponseData> getClusterPodCapacity(  @PathVariable("cluster_id") String cluster_id
																,@QueryParam("start") String start
																,@QueryParam("end") String end
																) throws Exception {
			
		ResponseEntity<ResponseData> response 			= null;
		try{
			response 									= service.getClusterPodCapacity( cluster_id, start, end );
		}catch(Exception e){
			
			ResponseData resData	= new ResponseData();
			resData.setResponse_code(Const.INTERNAL_SERVER_ERROR);
			resData.setMessage(Const.FAIL);
			response = new ResponseEntity<ResponseData>(resData, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response;
	}
	
	/**
	* Cluster pod requested ( count )
	* @return
	* @throws Exception
	*/
	@ApiOperation(value="Cluster Pod requested ( count )", httpMethod="GET", notes="갯수")
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
	
	@RequestMapping(value="/cluster/{cluster_id}/pod/requested", method=RequestMethod.GET)
	@ResponseBody
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public ResponseEntity<ResponseData> getClusterPodRequested(  @PathVariable("cluster_id") String cluster_id
																,@QueryParam("start") String start
																,@QueryParam("end") String end
																) throws Exception {
			
		ResponseEntity<ResponseData> response 			= null;
		try{
			response 									= service.getClusterPodRequested( cluster_id, start, end );
		}catch(Exception e){
			
			ResponseData resData	= new ResponseData();
			resData.setResponse_code(Const.INTERNAL_SERVER_ERROR);
			resData.setMessage(Const.FAIL);
			response = new ResponseEntity<ResponseData>(resData, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response;
	}
}
