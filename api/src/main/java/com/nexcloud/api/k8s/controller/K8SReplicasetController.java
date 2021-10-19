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
import com.nexcloud.api.k8s.service.K8SReplicasetService;
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
public class K8SReplicasetController extends SpringBootServletInitializer implements WebApplicationInitializer {
	
	@Autowired
    private K8SReplicasetService service;
	
	static final Logger logger = LoggerFactory.getLogger(K8SReplicasetController.class);
	

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
	            name = "replicaset_name", 
	            value = "replicaset name (ex) '.*' => (전체 replicaset), replicaset name", 
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
	
	@RequestMapping(value="/cluster/{cluster_id}/replicaset/{replicaset_name}/creation", method=RequestMethod.GET)
	@ResponseBody
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public ResponseEntity<ResponseData> getReplicasetCreation(    @PathVariable("cluster_id") String cluster_id
																,@PathVariable("replicaset_name") String replicaset_name
																,@QueryParam("namespace") String namespace
																) throws Exception {
		
		ResponseEntity<ResponseData> response 			= null;
		try{
			response 									= service.getReplicasetCreation( cluster_id, replicaset_name, namespace );
		}catch(Exception e){
			
			ResponseData resData	= new ResponseData();
			resData.setResponse_code(Const.INTERNAL_SERVER_ERROR);
			resData.setMessage(Const.FAIL);
			response = new ResponseEntity<ResponseData>(resData, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response;
	}
	
	/**
	* Replicaset Labels 정보
	* @return
	* @throws Exception
	*/
	@ApiOperation(value="Replicaset Label 정보", httpMethod="GET", notes="Replicaset label정보")
	@ApiImplicitParams({
		@ApiImplicitParam(
				name = "cluster_id",
				value = "Cluster ID (ex) 1",
				required = true,
				dataTypeClass = String.class,
				paramType = "path"
		),
		@ApiImplicitParam(
	            name = "replicaset_name", 
	            value = "replicaset name (ex) '.*' => (전체 replicaset), replicaset name", 
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
	
	@RequestMapping(value="/cluster/{cluster_id}/replicaset/{replicaset_name}/labels", method=RequestMethod.GET)
	@ResponseBody
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public ResponseEntity<ResponseData> getReplicasetLabels(    @PathVariable("cluster_id") String cluster_id
																,@PathVariable("replicaset_name") String replicaset_name
																,@QueryParam("namespace") String namespace
																) throws Exception {
		
		ResponseEntity<ResponseData> response 			= null;
		try{
			response 									= service.getReplicasetLabels( cluster_id, replicaset_name, namespace );
		}catch(Exception e){
			
			ResponseData resData	= new ResponseData();
			resData.setResponse_code(Const.INTERNAL_SERVER_ERROR);
			resData.setMessage(Const.FAIL);
			response = new ResponseEntity<ResponseData>(resData, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response;
	}
	
	/**
	* Replicaset Sequence number representing a specific generation of the desired state(원하는 상태의 특정 세대를 나타내는 시퀀스 번호)
	* @return
	* @throws Exception
	*/
	@ApiOperation(value="Replicaset Sequence number representing a specific generation of the desired state", httpMethod="GET", notes="원하는 상태의 특정 세대를 나타내는 시퀀스 번호")
	@ApiImplicitParams({
		@ApiImplicitParam(
				name = "cluster_id",
				value = "Cluster ID (ex) 1",
				required = true,
				dataTypeClass = String.class,
				paramType = "path"
		),
		@ApiImplicitParam(
	            name = "replicaset_name", 
	            value = "replicaset name (ex) '.*' => (전체 replicaset), replicaset name", 
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
	
	@RequestMapping(value="/cluster/{cluster_id}/replicaset/{replicaset_name}/metadata", method=RequestMethod.GET)
	@ResponseBody
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public ResponseEntity<ResponseData> getReplicasetMetadata(    @PathVariable("cluster_id") String cluster_id
																 ,@PathVariable("replicaset_name") String replicaset_name
																 ,@QueryParam("namespace") String namespace
															 	) throws Exception {
		
		ResponseEntity<ResponseData> response 			= null;
		try{
			response 									= service.getReplicasetMetadata( cluster_id, replicaset_name, namespace );
		}catch(Exception e){
			
			ResponseData resData	= new ResponseData();
			resData.setResponse_code(Const.INTERNAL_SERVER_ERROR);
			resData.setMessage(Const.FAIL);
			response = new ResponseEntity<ResponseData>(resData, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response;
	}
	
	/**
	* Information about the ReplicaSet's owner
	* @return
	* @throws Exception
	*/
	@ApiOperation(value="Information about the ReplicaSet's owner", httpMethod="GET", notes="Information about the ReplicaSet's owner")
	@ApiImplicitParams({
		@ApiImplicitParam(
				name = "cluster_id",
				value = "Cluster ID (ex) 1",
				required = true,
				dataTypeClass = String.class,
				paramType = "path"
		),
		@ApiImplicitParam(
	            name = "replicaset_name", 
	            value = "replicaset name (ex) '.*' => (전체 replicaset), replicaset name", 
	            required = true, 
	            dataTypeClass = String.class, 
	            paramType = "path"
	    ),
		@ApiImplicitParam(
	            name = "owner_name", 
	            value = "Owner name (ex) exporterhub", 
	            required = false, 
	            dataTypeClass = String.class, 
	            paramType = "query"
	    ),
		@ApiImplicitParam(
	            name = "owner_kind", 
	            value = "Owner Kind (ex) Deployment, Daemonset..", 
	            required = false, 
	            dataTypeClass = String.class, 
	            paramType = "query"
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
	
	@RequestMapping(value="/cluster/{cluster_id}/replicaset/{replicaset_name}/owner", method=RequestMethod.GET)
	@ResponseBody
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public ResponseEntity<ResponseData> getReplicasetOwner(   @PathVariable("cluster_id") String cluster_id
															 ,@PathVariable("replicaset_name") String replicaset_name
															 ,@QueryParam("owner_name") String owner_name
															 ,@QueryParam("owner_kind") String owner_kind
															 ,@QueryParam("namespace") String namespace
															 ) throws Exception {
		
		ResponseEntity<ResponseData> response 			= null;
		try{
			response 									= service.getReplicasetOwner( cluster_id, replicaset_name, owner_kind, owner_name, namespace );
		}catch(Exception e){
			
			ResponseData resData	= new ResponseData();
			resData.setResponse_code(Const.INTERNAL_SERVER_ERROR);
			resData.setMessage(Const.FAIL);
			response = new ResponseEntity<ResponseData>(resData, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response;
	}
	
	/**
	* Number of desired pods for a ReplicaSet
	* @return
	* @throws Exception
	*/
	@ApiOperation(value="Number of desired pods for a ReplicaSet", httpMethod="GET", notes="Number of desired pods for a ReplicaSet")
	@ApiImplicitParams({
		@ApiImplicitParam(
				name = "cluster_id",
				value = "Cluster ID (ex) 1",
				required = true,
				dataTypeClass = String.class,
				paramType = "path"
		),
		@ApiImplicitParam(
	            name = "replicaset_name", 
	            value = "replicaset name (ex) '.*' => (전체 replicaset), replicaset name", 
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
	
	@RequestMapping(value="/cluster/{cluster_id}/replicaset/{replicaset_name}/spec/replicas", method=RequestMethod.GET)
	@ResponseBody
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public ResponseEntity<ResponseData> getReplicasetSpecReplicas(    @PathVariable("cluster_id") String cluster_id
																	 ,@PathVariable("replicaset_name") String replicaset_name
																	 ,@QueryParam("namespace") String namespace
																 	) throws Exception {
		
		ResponseEntity<ResponseData> response 			= null;
		try{
			response 									= service.getReplicasetSpecReplicas( cluster_id, replicaset_name, namespace );
		}catch(Exception e){
			
			ResponseData resData	= new ResponseData();
			resData.setResponse_code(Const.INTERNAL_SERVER_ERROR);
			resData.setMessage(Const.FAIL);
			response = new ResponseEntity<ResponseData>(resData, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response;
	}
	
	/**
	* The number of replicas per ReplicaSet
	* @return
	* @throws Exception
	*/
	@ApiOperation(value="The number of replicas per ReplicaSet", httpMethod="GET", notes="The number of replicas per ReplicaSet")
	@ApiImplicitParams({
		@ApiImplicitParam(
				name = "cluster_id",
				value = "Cluster ID (ex) 1",
				required = true,
				dataTypeClass = String.class,
				paramType = "path"
		),
		@ApiImplicitParam(
	            name = "replicaset_name", 
	            value = "replicaset name (ex) '.*' => (전체 replicaset), replicaset name", 
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
	
	@RequestMapping(value="/cluster/{cluster_id}/replicaset/{replicaset_name}/status/replicas", method=RequestMethod.GET)
	@ResponseBody
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public ResponseEntity<ResponseData> getReplicasetStatusReplicas(    @PathVariable("cluster_id") String cluster_id
																	 ,@PathVariable("replicaset_name") String replicaset_name
																	 ,@QueryParam("namespace") String namespace
																 	) throws Exception {
		
		ResponseEntity<ResponseData> response 			= null;
		try{
			response 									= service.getReplicasetStatusReplicas( cluster_id, replicaset_name, namespace );
		}catch(Exception e){
			
			ResponseData resData	= new ResponseData();
			resData.setResponse_code(Const.INTERNAL_SERVER_ERROR);
			resData.setMessage(Const.FAIL);
			response = new ResponseEntity<ResponseData>(resData, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response;
	}
	
	/**
	* The number of ready replicas per ReplicaSet
	* @return
	* @throws Exception
	*/
	@ApiOperation(value="The number of ready replicas per ReplicaSet", httpMethod="GET", notes="The number of ready replicas per ReplicaSet")
	@ApiImplicitParams({
		@ApiImplicitParam(
				name = "cluster_id",
				value = "Cluster ID (ex) 1",
				required = true,
				dataTypeClass = String.class,
				paramType = "path"
		),
		@ApiImplicitParam(
	            name = "replicaset_name", 
	            value = "replicaset name (ex) '.*' => (전체 replicaset), replicaset name", 
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
	
	@RequestMapping(value="/cluster/{cluster_id}/replicaset/{replicaset_name}/status/readyreplicas", method=RequestMethod.GET)
	@ResponseBody
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public ResponseEntity<ResponseData> getReplicasetStatusReayReplicas(    @PathVariable("cluster_id") String cluster_id
																		 ,@PathVariable("replicaset_name") String replicaset_name
																		 ,@QueryParam("namespace") String namespace
																	 	) throws Exception {
		
		ResponseEntity<ResponseData> response 			= null;
		try{
			response 									= service.getReplicasetStatusReayReplicas( cluster_id, replicaset_name, namespace );
		}catch(Exception e){
			
			ResponseData resData	= new ResponseData();
			resData.setResponse_code(Const.INTERNAL_SERVER_ERROR);
			resData.setMessage(Const.FAIL);
			response = new ResponseEntity<ResponseData>(resData, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response;
	}
}
