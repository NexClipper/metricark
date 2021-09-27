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
import com.nexcloud.api.k8s.service.K8SDaemonsetService;
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
public class K8SDaemonsetController extends SpringBootServletInitializer implements WebApplicationInitializer {
	
	@Autowired
    private K8SDaemonsetService service;
	
	static final Logger logger = LoggerFactory.getLogger(K8SDaemonsetController.class);
	
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
	            name = "daemonset_name", 
	            value = "Daemonset Name (ex) '.*' => (전체 daemonset), klevr-agent", 
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
	
	@RequestMapping(value="/cluster/{cluster_id}/daemonset/{daemonset_name}/creation", method=RequestMethod.GET)
	@ResponseBody
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public ResponseEntity<ResponseData> getDaemonsetCreation(   @PathVariable("cluster_id") String cluster_id
																,@PathVariable("daemonset_name") String daemonset_name
																,@QueryParam("namespace") String namespace
																) throws Exception {
		
		ResponseEntity<ResponseData> response 			= null;
		try{
			response 									= service.getDaemonsetCreation( cluster_id, daemonset_name, namespace );
		}catch(Exception e){
			
			ResponseData resData	= new ResponseData();
			resData.setResponse_code(Const.INTERNAL_SERVER_ERROR);
			resData.setMessage(Const.FAIL);
			response = new ResponseEntity<ResponseData>(resData, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response;
	}

	/**
	* Daemonset Labels 정보
	* @return
	* @throws Exception
	*/
	@ApiOperation(value="Daemonset Label 정보", httpMethod="GET", notes="Daemonset label정보")
	@ApiImplicitParams({
		@ApiImplicitParam(
				name = "cluster_id",
				value = "Cluster ID (ex) 1",
				required = true,
				dataType = "string",
				paramType = "path"
		),
		@ApiImplicitParam(
	            name = "daemonset_name", 
	            value = "Daemonset Name (ex) '.*' => (전체 daemonset), klevr-agent", 
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
	
	@RequestMapping(value="/cluster/{cluster_id}/daemonset/{daemonset_name}/labels", method=RequestMethod.GET)
	@ResponseBody
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public ResponseEntity<ResponseData> getDaemonsetLabels(   @PathVariable("cluster_id") String cluster_id
																,@PathVariable("daemonset_name") String daemonset_name
																,@QueryParam("namespace") String namespace
																) throws Exception {
		
		ResponseEntity<ResponseData> response 			= null;
		try{
			response 									= service.getDaemonsetLabels( cluster_id, daemonset_name, namespace );
		}catch(Exception e){
			
			ResponseData resData	= new ResponseData();
			resData.setResponse_code(Const.INTERNAL_SERVER_ERROR);
			resData.setMessage(Const.FAIL);
			response = new ResponseEntity<ResponseData>(resData, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response;
	}
	
	/**
	* Daemonset Metadata 정보
	* @return
	* @throws Exception
	*/
	@ApiOperation(value="Daemonset Metadata 정보", httpMethod="GET", notes="Daemonset Metadata 정보")
	@ApiImplicitParams({
		@ApiImplicitParam(
				name = "cluster_id",
				value = "Cluster ID (ex) 1",
				required = true,
				dataType = "string",
				paramType = "path"
		),
		@ApiImplicitParam(
	            name = "daemonset_name", 
	            value = "Daemonset Name (ex) '.*' => (전체 daemonset), klevr-agent", 
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
	
	@RequestMapping(value="/cluster/{cluster_id}/daemonset/{daemonset_name}/metadata", method=RequestMethod.GET)
	@ResponseBody
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public ResponseEntity<ResponseData> getDaemonsetMetadata(   @PathVariable("cluster_id") String cluster_id
																,@PathVariable("daemonset_name") String daemonset_name
																,@QueryParam("namespace") String namespace
																) throws Exception {
		
		ResponseEntity<ResponseData> response 			= null;
		try{
			response 									= service.getDaemonsetMetadata( cluster_id, daemonset_name, namespace );
		}catch(Exception e){
			
			ResponseData resData	= new ResponseData();
			resData.setResponse_code(Const.INTERNAL_SERVER_ERROR);
			resData.setMessage(Const.FAIL);
			response = new ResponseEntity<ResponseData>(resData, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response;
	}
	
	/**
	* Daemonset Current Number Scheduled (적어도 하나의 데몬 포드를 실행하고 있어야하는 노드의 수.)
	* @return
	* @throws Exception
	*/
	@ApiOperation(value="Daemonset Current Number Scheduled", httpMethod="GET", notes="적어도 하나의 데몬 포드를 실행하고 있어야하는 노드의 수")
	@ApiImplicitParams({
		@ApiImplicitParam(
				name = "cluster_id",
				value = "Cluster ID (ex) 1",
				required = true,
				dataType = "string",
				paramType = "path"
		),
		@ApiImplicitParam(
	            name = "daemonset_name", 
	            value = "Daemonset Name (ex) '.*' => (전체 daemonset), klevr-agent", 
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
	
	@RequestMapping(value="/cluster/{cluster_id}/daemonset/{daemonset_name}/status/current", method=RequestMethod.GET)
	@ResponseBody
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public ResponseEntity<ResponseData> getDaemonsetStatusCurrentScheduled(  @PathVariable("cluster_id") String cluster_id
																			,@PathVariable("daemonset_name") String daemonset_name
																			,@QueryParam("namespace") String namespace
																			) throws Exception {
					
		ResponseEntity<ResponseData> response 			= null;
		try{
			response 									= service.getDaemonsetStatusCurrentScheduled( cluster_id, daemonset_name, namespace );
		}catch(Exception e){
			
			ResponseData resData	= new ResponseData();
			resData.setResponse_code(Const.INTERNAL_SERVER_ERROR);
			resData.setMessage(Const.FAIL);
			response = new ResponseEntity<ResponseData>(resData, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response;
	}
	
	/**
	* Daemonset Desired Number Scheduled (데몬 포드를 실행해야하는 노드의 수.)
	* @return
	* @throws Exception
	*/
	@ApiOperation(value="Daemonset Desired Number Scheduled", httpMethod="GET", notes="데몬 포드를 실행해야하는 노드의 수.")
	@ApiImplicitParams({
		@ApiImplicitParam(
				name = "cluster_id",
				value = "Cluster ID (ex) 1",
				required = true,
				dataType = "string",
				paramType = "path"
		),
		@ApiImplicitParam(
	            name = "daemonset_name", 
	            value = "Daemonset Name (ex) '.*' => (전체 daemonset), klevr-agent", 
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
	
	@RequestMapping(value="/cluster/{cluster_id}/daemonset/{daemonset_name}/status/desired", method=RequestMethod.GET)
	@ResponseBody
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public ResponseEntity<ResponseData> getDaemonsetStatusDesiredScheduled(  @PathVariable("cluster_id") String cluster_id
																			,@PathVariable("daemonset_name") String daemonset_name
																			,@QueryParam("namespace") String namespace
																			) throws Exception {
					
		ResponseEntity<ResponseData> response 			= null;
		try{
			response 									= service.getDaemonsetStatusDesiredScheduled( cluster_id, daemonset_name, namespace );
		}catch(Exception e){
			
			ResponseData resData	= new ResponseData();
			resData.setResponse_code(Const.INTERNAL_SERVER_ERROR);
			resData.setMessage(Const.FAIL);
			response = new ResponseEntity<ResponseData>(resData, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response;
	}
	
	/**
	* Daemonset available Number (데몬 포드를 실행해야하고 하나 이상의 데몬 포드가 실행 중이고 사용 가능한 노드 수)
	* @return
	* @throws Exception
	*/
	@ApiOperation(value="Daemonset available Number", httpMethod="GET", notes="데몬 포드를 실행해야하고 하나 이상의 데몬 포드가 실행 중이고 사용 가능한 노드 수")
	@ApiImplicitParams({
		@ApiImplicitParam(
				name = "cluster_id",
				value = "Cluster ID (ex) 1",
				required = true,
				dataType = "string",
				paramType = "path"
		),
		@ApiImplicitParam(
	            name = "daemonset_name", 
	            value = "Daemonset Name (ex) '.*' => (전체 daemonset), klevr-agent", 
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
	
	@RequestMapping(value="/cluster/{cluster_id}/daemonset/{daemonset_name}/status/available", method=RequestMethod.GET)
	@ResponseBody
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public ResponseEntity<ResponseData> getDaemonsetStatusAvailable(  @PathVariable("cluster_id") String cluster_id
																	 ,@PathVariable("daemonset_name") String daemonset_name
																	 ,@QueryParam("namespace") String namespace
																	) throws Exception {
					
		ResponseEntity<ResponseData> response 			= null;
		try{
			response 									= service.getDaemonsetStatusAvailable( cluster_id, daemonset_name, namespace );
		}catch(Exception e){
			
			ResponseData resData	= new ResponseData();
			resData.setResponse_code(Const.INTERNAL_SERVER_ERROR);
			resData.setMessage(Const.FAIL);
			response = new ResponseEntity<ResponseData>(resData, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response;
	}
	
	/**
	* Daemonset misscheduled Number (데몬 포드를 실행하고 있지만 실행되지 않아야하는 노드의 수)
	* @return
	* @throws Exception
	*/
	@ApiOperation(value="Daemonset misscheduled Number", httpMethod="GET", notes="데몬 포드를 실행하고 있지만 실행되지 않아야하는 노드의 수")
	@ApiImplicitParams({
		@ApiImplicitParam(
				name = "cluster_id",
				value = "Cluster ID (ex) 1",
				required = true,
				dataType = "string",
				paramType = "path"
		),
		@ApiImplicitParam(
	            name = "daemonset_name", 
	            value = "Daemonset Name (ex) '.*' => (전체 daemonset), klevr-agent", 
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
	
	@RequestMapping(value="/cluster/{cluster_id}/daemonset/{daemonset_name}/status/misscheduled", method=RequestMethod.GET)
	@ResponseBody
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public ResponseEntity<ResponseData> getDaemonsetStatusMisscheduled(  @PathVariable("cluster_id") String cluster_id
																		 ,@PathVariable("daemonset_name") String daemonset_name
																		 ,@QueryParam("namespace") String namespace
																		) throws Exception {
					
		ResponseEntity<ResponseData> response 			= null;
		try{
			response 									= service.getDaemonsetStatusMisscheduled( cluster_id, daemonset_name, namespace );
		}catch(Exception e){
			
			ResponseData resData	= new ResponseData();
			resData.setResponse_code(Const.INTERNAL_SERVER_ERROR);
			resData.setMessage(Const.FAIL);
			response = new ResponseEntity<ResponseData>(resData, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response;
	}
	
	/**
	* Daemonset ready Number (데몬 포드를 실행해야하고 하나 이상의 데몬 포드가 실행 중이고 준비가되어 있어야하는 노드의 수)
	* @return
	* @throws Exception
	*/
	@ApiOperation(value="Daemonset ready Number", httpMethod="GET", notes="데몬 포드를 실행해야하고 하나 이상의 데몬 포드가 실행 중이고 준비가되어 있어야하는 노드의 수")
	@ApiImplicitParams({
		@ApiImplicitParam(
				name = "cluster_id",
				value = "Cluster ID (ex) 1",
				required = true,
				dataType = "string",
				paramType = "path"
		),
		@ApiImplicitParam(
	            name = "daemonset_name", 
	            value = "Daemonset Name (ex) '.*' => (전체 daemonset), klevr-agent", 
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
	
	@RequestMapping(value="/cluster/{cluster_id}/daemonset/{daemonset_name}/status/ready", method=RequestMethod.GET)
	@ResponseBody
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public ResponseEntity<ResponseData> getDaemonsetStatusReady(  @PathVariable("cluster_id") String cluster_id
																 ,@PathVariable("daemonset_name") String daemonset_name
																 ,@QueryParam("namespace") String namespace
																) throws Exception {
					
		ResponseEntity<ResponseData> response 			= null;
		try{
			response 									= service.getDaemonsetStatusReady( cluster_id, daemonset_name, namespace );
		}catch(Exception e){
			
			ResponseData resData	= new ResponseData();
			resData.setResponse_code(Const.INTERNAL_SERVER_ERROR);
			resData.setMessage(Const.FAIL);
			response = new ResponseEntity<ResponseData>(resData, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response;
	}
	
	/**
	* Daemonset unavailable Number (데몬 포드를 실행해야하고 데몬 포드가 실행 중이고 사용 가능한 노드가없는 노드 수)
	* @return
	* @throws Exception
	*/
	@ApiOperation(value="Daemonset unavailable Number", httpMethod="GET", notes="데몬 포드를 실행해야하고 데몬 포드가 실행 중이고 사용 가능한 노드가없는 노드 수")
	@ApiImplicitParams({
		@ApiImplicitParam(
				name = "cluster_id",
				value = "Cluster ID (ex) 1",
				required = true,
				dataType = "string",
				paramType = "path"
		),
		@ApiImplicitParam(
	            name = "daemonset_name", 
	            value = "Daemonset Name (ex) '.*' => (전체 daemonset), klevr-agent", 
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
	
	@RequestMapping(value="/cluster/{cluster_id}/daemonset/{daemonset_name}/status/unavailable", method=RequestMethod.GET)
	@ResponseBody
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public ResponseEntity<ResponseData> getDaemonsetStatusUnAvailable(  @PathVariable("cluster_id") String cluster_id
																	 ,@PathVariable("daemonset_name") String daemonset_name
																	 ,@QueryParam("namespace") String namespace
																	) throws Exception {
					
		ResponseEntity<ResponseData> response 			= null;
		try{
			response 									= service.getDaemonsetStatusUnAvailable( cluster_id, daemonset_name, namespace );
		}catch(Exception e){
			
			ResponseData resData	= new ResponseData();
			resData.setResponse_code(Const.INTERNAL_SERVER_ERROR);
			resData.setMessage(Const.FAIL);
			response = new ResponseEntity<ResponseData>(resData, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response;
	}
	
	/**
	* Daemonset updated scheduled Number (업데이트 된 데몬 포드를 실행중인 총 노드 수)
	* @return
	* @throws Exception
	*/
	@ApiOperation(value="Daemonset updated scheduled Number", httpMethod="GET", notes="업데이트 된 데몬 포드를 실행중인 총 노드 수")
	@ApiImplicitParams({
		@ApiImplicitParam(
				name = "cluster_id",
				value = "Cluster ID (ex) 1",
				required = true,
				dataType = "string",
				paramType = "path"
		),
		@ApiImplicitParam(
	            name = "daemonset_name", 
	            value = "Daemonset Name (ex) '.*' => (전체 daemonset), klevr-agent", 
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
	
	@RequestMapping(value="/cluster/{cluster_id}/daemonset/{daemonset_name}/status/updated", method=RequestMethod.GET)
	@ResponseBody
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public ResponseEntity<ResponseData> getDaemonsetStatusUpdated(   @PathVariable("cluster_id") String cluster_id
																	 ,@PathVariable("daemonset_name") String daemonset_name
																	 ,@QueryParam("namespace") String namespace
																	) throws Exception {
					
		ResponseEntity<ResponseData> response 			= null;
		try{
			response 									= service.getDaemonsetStatusUpdated( cluster_id, daemonset_name, namespace );
		}catch(Exception e){
			
			ResponseData resData	= new ResponseData();
			resData.setResponse_code(Const.INTERNAL_SERVER_ERROR);
			resData.setMessage(Const.FAIL);
			response = new ResponseEntity<ResponseData>(resData, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response;
	}
}
