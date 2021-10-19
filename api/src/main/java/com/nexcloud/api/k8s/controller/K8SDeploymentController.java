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
import com.nexcloud.api.k8s.service.K8SDeploymentService;
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
public class K8SDeploymentController extends SpringBootServletInitializer implements WebApplicationInitializer {
	
	@Autowired
    private K8SDeploymentService service;
	
	static final Logger logger = LoggerFactory.getLogger(K8SDeploymentController.class);
	
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
				dataTypeClass = String.class,
				paramType = "path"
		),
		@ApiImplicitParam(
	            name = "deployment_name", 
	            value = "deployment Name (ex) '.*' => (전체 deployment), provbee", 
	            required = true, 
	            dataTypeClass = String.class, 
	            paramType = "path"
	    ),
		@ApiImplicitParam(
	            name = "namespace", 
	            value = "namespace (ex) kube-system", 
	            required = false, 
	            dataTypeClass = String.class, 
	            paramType = "query"
	    )
	})
	@ApiResponses(value={
			@ApiResponse( code=200, message="SUCCESS"),
			@ApiResponse( code=500, message="Internal Server Error")
	})
	
	@RequestMapping(value="/cluster/{cluster_id}/deployment/{deployment_name}/creation", method=RequestMethod.GET)
	@ResponseBody
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public ResponseEntity<ResponseData> getdeploymentCreation(   @PathVariable("cluster_id") String cluster_id
																,@PathVariable("deployment_name") String deployment_name
																,@QueryParam("namespace") String namespace
																) throws Exception {
		
		ResponseEntity<ResponseData> response 			= null;
		try{
			response 									= service.getdeploymentCreation( cluster_id, deployment_name, namespace );
		}catch(Exception e){
			
			ResponseData resData	= new ResponseData();
			resData.setResponse_code(Const.INTERNAL_SERVER_ERROR);
			resData.setMessage(Const.FAIL);
			response = new ResponseEntity<ResponseData>(resData, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response;
	}

	/**
	* Deployment Labels 정보
	* @return
	* @throws Exception
	*/
	@ApiOperation(value="Deployment Label 정보", httpMethod="GET", notes="Deployment label정보")
	@ApiImplicitParams({
		@ApiImplicitParam(
				name = "cluster_id",
				value = "Cluster ID (ex) 1",
				required = true,
				dataTypeClass = String.class,
				paramType = "path"
		),
		@ApiImplicitParam(
	            name = "deployment_name", 
	            value = "deployment Name (ex) '.*' => (전체 deployment), provbee", 
	            required = true, 
	            dataTypeClass = String.class, 
	            paramType = "path"
	    ),
		@ApiImplicitParam(
	            name = "namespace", 
	            value = "namespace (ex) kube-system", 
	            required = false, 
	            dataTypeClass = String.class, 
	            paramType = "query"
	    )
	})
	@ApiResponses(value={
			@ApiResponse( code=200, message="SUCCESS"),
			@ApiResponse( code=500, message="Internal Server Error")
	})
	
	@RequestMapping(value="/cluster/{cluster_id}/deployment/{deployment_name}/labels", method=RequestMethod.GET)
	@ResponseBody
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public ResponseEntity<ResponseData> getdeploymentLabels(   @PathVariable("cluster_id") String cluster_id
																,@PathVariable("deployment_name") String deployment_name
																,@QueryParam("namespace") String namespace
																) throws Exception {
		
		ResponseEntity<ResponseData> response 			= null;
		try{
			response 									= service.getDeploymentLabels( cluster_id, deployment_name, namespace );
		}catch(Exception e){
			
			ResponseData resData	= new ResponseData();
			resData.setResponse_code(Const.INTERNAL_SERVER_ERROR);
			resData.setMessage(Const.FAIL);
			response = new ResponseEntity<ResponseData>(resData, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response;
	}
	
	/**
	* deployment Metadata 정보
	* @return
	* @throws Exception
	*/
	@ApiOperation(value="deployment metadata 정보", httpMethod="GET", notes="deployment metadata 정보")
	@ApiImplicitParams({
		@ApiImplicitParam(
				name = "cluster_id",
				value = "Cluster ID (ex) 1",
				required = true,
				dataTypeClass = String.class,
				paramType = "path"
		),
		@ApiImplicitParam(
	            name = "deployment_name", 
	            value = "deployment Name (ex) '.*' => (전체 deployment), provbee", 
	            required = true, 
	            dataTypeClass = String.class, 
	            paramType = "path"
	    ),
		@ApiImplicitParam(
	            name = "namespace", 
	            value = "namespace (ex) kube-system", 
	            required = false, 
	            dataTypeClass = String.class, 
	            paramType = "query"
	    )
	})
	@ApiResponses(value={
			@ApiResponse( code=200, message="SUCCESS"),
			@ApiResponse( code=500, message="Internal Server Error")
	})
	
	@RequestMapping(value="/cluster/{cluster_id}/deployment/{deployment_name}/metadata", method=RequestMethod.GET)
	@ResponseBody
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public ResponseEntity<ResponseData> getDeploymentMetadata(   @PathVariable("cluster_id") String cluster_id
																,@PathVariable("deployment_name") String deployment_name
																,@QueryParam("namespace") String namespace
																) throws Exception {
		
		ResponseEntity<ResponseData> response 			= null;
		try{
			response 									= service.getDeploymentMetadata( cluster_id, deployment_name, namespace );
		}catch(Exception e){
			
			ResponseData resData	= new ResponseData();
			resData.setResponse_code(Const.INTERNAL_SERVER_ERROR);
			resData.setMessage(Const.FAIL);
			response = new ResponseEntity<ResponseData>(resData, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response;
	}
	
	/**
	* deployment status condition (배포의 현재 상태 조건.)
	* @return
	* @throws Exception
	*/
	@ApiOperation(value="deployment status condition", httpMethod="GET", notes="배포의 현재 상태 조건")
	@ApiImplicitParams({
		@ApiImplicitParam(
				name = "cluster_id",
				value = "Cluster ID (ex) 1",
				required = true,
				dataTypeClass = String.class,
				paramType = "path"
		),
		@ApiImplicitParam(
	            name = "deployment_name", 
	            value = "deployment Name (ex) '.*' => (전체 deployment), provbee", 
	            required = true, 
	            dataTypeClass = String.class, 
	            paramType = "path"
	    ),
		@ApiImplicitParam(
	            name = "condition", 
	            value = "condition (ex) '.*' => (전체 컨디션), Available, Progressing", 
	            required = true, 
	            dataTypeClass = String.class, 
	            paramType = "path"
	    ),
		@ApiImplicitParam(
	            name = "status", 
	            value = "status Name (ex) '.*' => (배포 상태 전체), true, false, unknown", 
	            required = true, 
	            dataTypeClass = String.class, 
	            paramType = "path"
	    ),
		@ApiImplicitParam(
	            name = "namespace", 
	            value = "namespace (ex) kube-system", 
	            required = false, 
	            dataTypeClass = String.class, 
	            paramType = "query"
	    )
	})
	@ApiResponses(value={
			@ApiResponse( code=200, message="SUCCESS"),
			@ApiResponse( code=500, message="Internal Server Error")
	})
	
	@RequestMapping(value="/cluster/{cluster_id}/deployment/{deployment_name}/condition/{condition}/status/{status}", method=RequestMethod.GET)
	@ResponseBody
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public ResponseEntity<ResponseData> getDeploymentStatusCondition(  @PathVariable("cluster_id") String cluster_id
																	   ,@PathVariable("deployment_name") String deployment_name
																	   ,@PathVariable("condition") String condition
																	   ,@PathVariable("status") String status
																	   ,@QueryParam("namespace") String namespace
																		) throws Exception {
					
		ResponseEntity<ResponseData> response 			= null;
		try{
			response 									= service.getDeploymentStatusCondition( cluster_id, deployment_name, condition, status, namespace );
		}catch(Exception e){
			
			ResponseData resData	= new ResponseData();
			resData.setResponse_code(Const.INTERNAL_SERVER_ERROR);
			resData.setMessage(Const.FAIL);
			response = new ResponseEntity<ResponseData>(resData, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response;
	}
	
	/**
	* deployment status observed (The generation observed by the deployment controller )
	* @return
	* @throws Exception
	*/
	@ApiOperation(value="deployment status observed", httpMethod="GET", notes="The generation observed by the deployment controller")
	@ApiImplicitParams({
		@ApiImplicitParam(
				name = "cluster_id",
				value = "Cluster ID (ex) 1",
				required = true,
				dataTypeClass = String.class,
				paramType = "path"
		),
		@ApiImplicitParam(
	            name = "deployment_name", 
	            value = "deployment Name (ex) '.*' => (전체 deployment), provbee", 
	            required = true, 
	            dataTypeClass = String.class, 
	            paramType = "path"
	    ),
		@ApiImplicitParam(
	            name = "namespace", 
	            value = "namespace (ex) kube-system", 
	            required = false, 
	            dataTypeClass = String.class, 
	            paramType = "query"
	    )
	})
	@ApiResponses(value={
			@ApiResponse( code=200, message="SUCCESS"),
			@ApiResponse( code=500, message="Internal Server Error")
	})
	
	@RequestMapping(value="/cluster/{cluster_id}/deployment/{deployment_name}/status/observed", method=RequestMethod.GET)
	@ResponseBody
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public ResponseEntity<ResponseData> getDeploymentStatusObserved(   @PathVariable("cluster_id") String cluster_id
																	   ,@PathVariable("deployment_name") String deployment_name
																	   ,@QueryParam("namespace") String namespace
																		) throws Exception {
					
		ResponseEntity<ResponseData> response 			= null;
		try{
			response 									= service.getDeploymentStatusObserved( cluster_id, deployment_name, namespace );
		}catch(Exception e){
			
			ResponseData resData	= new ResponseData();
			resData.setResponse_code(Const.INTERNAL_SERVER_ERROR);
			resData.setMessage(Const.FAIL);
			response = new ResponseEntity<ResponseData>(resData, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response;
	}
	
	/**
	* deployment status replicas number (The number of replicas per deployment)
	* @return
	* @throws Exception
	*/
	@ApiOperation(value="deployment status replicas number", httpMethod="GET", notes="The number of replicas per deployment")
	@ApiImplicitParams({
		@ApiImplicitParam(
				name = "cluster_id",
				value = "Cluster ID (ex) 1",
				required = true,
				dataTypeClass = String.class,
				paramType = "path"
		),
		@ApiImplicitParam(
	            name = "deployment_name", 
	            value = "deployment Name (ex) '.*' => (전체 deployment), provbee", 
	            required = true, 
	            dataTypeClass = String.class, 
	            paramType = "path"
	    ),
		@ApiImplicitParam(
	            name = "namespace", 
	            value = "namespace (ex) kube-system", 
	            required = false, 
	            dataTypeClass = String.class, 
	            paramType = "query"
	    )
	})
	@ApiResponses(value={
			@ApiResponse( code=200, message="SUCCESS"),
			@ApiResponse( code=500, message="Internal Server Error")
	})
	
	@RequestMapping(value="/cluster/{cluster_id}/deployment/{deployment_name}/status/replicas", method=RequestMethod.GET)
	@ResponseBody
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public ResponseEntity<ResponseData> getDeploymentStatusReplicas(   @PathVariable("cluster_id") String cluster_id
																	   ,@PathVariable("deployment_name") String deployment_name
																	   ,@QueryParam("namespace") String namespace
																		) throws Exception {
					
		ResponseEntity<ResponseData> response 			= null;
		try{
			response 									= service.getDeploymentStatusReplicas( cluster_id, deployment_name, namespace );
		}catch(Exception e){
			
			ResponseData resData	= new ResponseData();
			resData.setResponse_code(Const.INTERNAL_SERVER_ERROR);
			resData.setMessage(Const.FAIL);
			response = new ResponseEntity<ResponseData>(resData, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response;
	}
	
	/**
	* deployment status available replicas number (The number of available replicas per deployment)
	* @return
	* @throws Exception
	*/
	@ApiOperation(value="deployment status available replicas number", httpMethod="GET", notes="The number of available replicas per deployment")
	@ApiImplicitParams({
		@ApiImplicitParam(
				name = "cluster_id",
				value = "Cluster ID (ex) 1",
				required = true,
				dataTypeClass = String.class,
				paramType = "path"
		),
		@ApiImplicitParam(
	            name = "deployment_name", 
	            value = "deployment Name (ex) '.*' => (전체 deployment), provbee", 
	            required = true, 
	            dataTypeClass = String.class, 
	            paramType = "path"
	    ),
		@ApiImplicitParam(
	            name = "namespace", 
	            value = "namespace (ex) kube-system", 
	            required = false, 
	            dataTypeClass = String.class, 
	            paramType = "query"
	    )
	})
	@ApiResponses(value={
			@ApiResponse( code=200, message="SUCCESS"),
			@ApiResponse( code=500, message="Internal Server Error")
	})
	
	@RequestMapping(value="/cluster/{cluster_id}/deployment/{deployment_name}/status/availablereplica", method=RequestMethod.GET)
	@ResponseBody
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public ResponseEntity<ResponseData> getDeploymentStatusAvailableReplicas(   @PathVariable("cluster_id") String cluster_id
																			   ,@PathVariable("deployment_name") String deployment_name
																			   ,@QueryParam("namespace") String namespace
																				) throws Exception {
					
		ResponseEntity<ResponseData> response 			= null;
		try{
			response 									= service.getDeploymentStatusAvailableReplicas( cluster_id, deployment_name, namespace );
		}catch(Exception e){
			
			ResponseData resData	= new ResponseData();
			resData.setResponse_code(Const.INTERNAL_SERVER_ERROR);
			resData.setMessage(Const.FAIL);
			response = new ResponseEntity<ResponseData>(resData, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response;
	}
	
	/**
	* deployment status unavailable replicas number (The number of unavailable replicas per deployment)
	* @return
	* @throws Exception
	*/
	@ApiOperation(value="deployment status unavailable replicas number", httpMethod="GET", notes="The number of unavailable replicas per deployment")
	@ApiImplicitParams({
		@ApiImplicitParam(
				name = "cluster_id",
				value = "Cluster ID (ex) 1",
				required = true,
				dataTypeClass = String.class,
				paramType = "path"
		),
		@ApiImplicitParam(
	            name = "deployment_name", 
	            value = "deployment Name (ex) '.*' => (전체 deployment), provbee", 
	            required = true, 
	            dataTypeClass = String.class, 
	            paramType = "path"
	    ),
		@ApiImplicitParam(
	            name = "namespace", 
	            value = "namespace (ex) kube-system", 
	            required = false, 
	            dataTypeClass = String.class, 
	            paramType = "query"
	    )
	})
	@ApiResponses(value={
			@ApiResponse( code=200, message="SUCCESS"),
			@ApiResponse( code=500, message="Internal Server Error")
	})
	
	@RequestMapping(value="/cluster/{cluster_id}/deployment/{deployment_name}/status/unavailablereplica", method=RequestMethod.GET)
	@ResponseBody
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public ResponseEntity<ResponseData> getDeploymentStatusUnAvailableReplicas(   @PathVariable("cluster_id") String cluster_id
																			     ,@PathVariable("deployment_name") String deployment_name
																			     ,@QueryParam("namespace") String namespace
																				) throws Exception {
					
		ResponseEntity<ResponseData> response 			= null;
		try{
			response 									= service.getDeploymentStatusUnAvailableReplicas( cluster_id, deployment_name, namespace );
		}catch(Exception e){
			
			ResponseData resData	= new ResponseData();
			resData.setResponse_code(Const.INTERNAL_SERVER_ERROR);
			resData.setMessage(Const.FAIL);
			response = new ResponseEntity<ResponseData>(resData, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response;
	}
	
	/**
	* deployment status updated replicas number (The number of updated replicas per deployment)
	* @return
	* @throws Exception
	*/
	@ApiOperation(value="deployment status updated replicas number", httpMethod="GET", notes="The number of updated replicas per deployment")
	@ApiImplicitParams({
		@ApiImplicitParam(
				name = "cluster_id",
				value = "Cluster ID (ex) 1",
				required = true,
				dataTypeClass = String.class,
				paramType = "path"
		),
		@ApiImplicitParam(
	            name = "deployment_name", 
	            value = "deployment Name (ex) '.*' => (전체 deployment), provbee", 
	            required = true, 
	            dataTypeClass = String.class, 
	            paramType = "path"
	    ),
		@ApiImplicitParam(
	            name = "namespace", 
	            value = "namespace (ex) kube-system", 
	            required = false, 
	            dataTypeClass = String.class, 
	            paramType = "query"
	    )
	})
	@ApiResponses(value={
			@ApiResponse( code=200, message="SUCCESS"),
			@ApiResponse( code=500, message="Internal Server Error")
	})
	
	@RequestMapping(value="/cluster/{cluster_id}/deployment/{deployment_name}/status/updated", method=RequestMethod.GET)
	@ResponseBody
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public ResponseEntity<ResponseData> getDeploymentStatusUpdatedReplicas(   @PathVariable("cluster_id") String cluster_id
																		     ,@PathVariable("deployment_name") String deployment_name
																		     ,@QueryParam("namespace") String namespace
																			) throws Exception {
					
		ResponseEntity<ResponseData> response 			= null;
		try{
			response 									= service.getDeploymentStatusUpdatedReplicas( cluster_id, deployment_name, namespace );
		}catch(Exception e){
			
			ResponseData resData	= new ResponseData();
			resData.setResponse_code(Const.INTERNAL_SERVER_ERROR);
			resData.setMessage(Const.FAIL);
			response = new ResponseEntity<ResponseData>(resData, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response;
	}
	
	/**
	* Whether the deployment is paused and will not be processed by the deployment controller(배포가 일시 중지되고 배포 컨트롤러에서 처리되지 않는지 여부)
	* @return
	* @throws Exception
	*/
	@ApiOperation(value="Whether the deployment is paused and will not be processed by the deployment controller", httpMethod="GET", notes="배포가 일시 중지되고 배포 컨트롤러에서 처리되지 않는지 여부")
	@ApiImplicitParams({
		@ApiImplicitParam(
				name = "cluster_id",
				value = "Cluster ID (ex) 1",
				required = true,
				dataTypeClass = String.class,
				paramType = "path"
		),
		@ApiImplicitParam(
	            name = "deployment_name", 
	            value = "deployment Name (ex) '.*' => (전체 deployment), provbee", 
	            required = true, 
	            dataTypeClass = String.class, 
	            paramType = "path"
	    ),
		@ApiImplicitParam(
	            name = "namespace", 
	            value = "namespace (ex) kube-system", 
	            required = false, 
	            dataTypeClass = String.class, 
	            paramType = "query"
	    )
	})
	@ApiResponses(value={
			@ApiResponse( code=200, message="SUCCESS"),
			@ApiResponse( code=500, message="Internal Server Error")
	})
	
	@RequestMapping(value="/cluster/{cluster_id}/deployment/{deployment_name}/spec/paused", method=RequestMethod.GET)
	@ResponseBody
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public ResponseEntity<ResponseData> getDeploymentSpecPausedReplicas(  @PathVariable("cluster_id") String cluster_id
																	     ,@PathVariable("deployment_name") String deployment_name
																	     ,@QueryParam("namespace") String namespace
																		) throws Exception {
					
		ResponseEntity<ResponseData> response 			= null;
		try{
			response 									= service.getDeploymentSpecPausedReplicas( cluster_id, deployment_name, namespace );
		}catch(Exception e){
			
			ResponseData resData	= new ResponseData();
			resData.setResponse_code(Const.INTERNAL_SERVER_ERROR);
			resData.setMessage(Const.FAIL);
			response = new ResponseEntity<ResponseData>(resData, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response;
	}
	
	/**
	* Number of desired pods for a deployment(배포를 위해 원하는 포드 수)
	* @return
	* @throws Exception
	*/
	@ApiOperation(value="Number of desired pods for a deployment", httpMethod="GET", notes="배포를 위해 원하는 포드 수")
	@ApiImplicitParams({
		@ApiImplicitParam(
				name = "cluster_id",
				value = "Cluster ID (ex) 1",
				required = true,
				dataTypeClass = String.class,
				paramType = "path"
		),
		@ApiImplicitParam(
	            name = "deployment_name", 
	            value = "deployment Name (ex) '.*' => (전체 deployment), provbee", 
	            required = true, 
	            dataTypeClass = String.class, 
	            paramType = "path"
	    ),
		@ApiImplicitParam(
	            name = "namespace", 
	            value = "namespace (ex) kube-system", 
	            required = false, 
	            dataTypeClass = String.class, 
	            paramType = "query"
	    )
	})
	@ApiResponses(value={
			@ApiResponse( code=200, message="SUCCESS"),
			@ApiResponse( code=500, message="Internal Server Error")
	})
	
	@RequestMapping(value="/cluster/{cluster_id}/deployment/{deployment_name}/spec/replicas", method=RequestMethod.GET)
	@ResponseBody
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public ResponseEntity<ResponseData> getDeploymentSpecReplicas(  @PathVariable("cluster_id") String cluster_id
															     ,@PathVariable("deployment_name") String deployment_name
															     ,@QueryParam("namespace") String namespace
																) throws Exception {
					
		ResponseEntity<ResponseData> response 			= null;
		try{
			response 									= service.getDeploymentSpecReplicas( cluster_id, deployment_name, namespace );
		}catch(Exception e){
			
			ResponseData resData	= new ResponseData();
			resData.setResponse_code(Const.INTERNAL_SERVER_ERROR);
			resData.setMessage(Const.FAIL);
			response = new ResponseEntity<ResponseData>(resData, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response;
	}
}
