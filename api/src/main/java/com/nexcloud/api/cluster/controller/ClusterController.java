package com.nexcloud.api.cluster.controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
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

import com.nexcloud.api.cluster.service.ClusterService;
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
public class ClusterController extends SpringBootServletInitializer implements WebApplicationInitializer {
	
	@Autowired
    private ClusterService clusterService;
	
	static final Logger logger = LoggerFactory.getLogger(ClusterController.class);
	

	/**
	* Cluster Info ( Built date, gitCommit, gitVersion, goVersion, Platform )
	* @return
	* @throws Exception
	*/
	@ApiOperation(value="Cluster Info ( Built date, gitCommit, gitVersion, goVersion, Platform, host_ip, kubernetes_io_hostname )", httpMethod="GET", notes="core")
	@ApiImplicitParams({
		@ApiImplicitParam(
				name = "cluster_id",
				value = "Cluster ID (ex) 1",
				required = true,
				dataType = "string",
				paramType = "path"
		),
		@ApiImplicitParam(
				name = "label",
				value = "Label (ex) buildDate, gitCommit, gitVersion, goVersion, platform, host_ip, kubernetes_io_hostname ",
				required = true,
				dataType = "string",
				paramType = "path"
		)
	})
	@ApiResponses(value={
			@ApiResponse( code=200, message="SUCCESS"),
			@ApiResponse( code=500, message="Internal Server Error")
	})
	
	@RequestMapping(value="/cluster/{cluster_id}/label/{label}", method=RequestMethod.GET)
	@ResponseBody
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public ResponseEntity<ResponseData> getClusterInfo( @PathVariable("cluster_id") String cluster_id, @PathVariable("label") String label ) throws Exception {
		
		ResponseEntity<ResponseData> response 			= null;
		try{
			response 									= clusterService.getClusterInfo( cluster_id, label );
		}catch(Exception e){
			
			ResponseData resData	= new ResponseData();
			resData.setResponse_code(Const.INTERNAL_SERVER_ERROR);
			resData.setMessage(Const.FAIL);
			response = new ResponseEntity<ResponseData>(resData, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response;
	}
	
	/**
	* Kubernetes workload list
	* @return
	* @throws Exception
	*/
	@ApiOperation(value="Kubernetes workload list", httpMethod="GET", notes="core")
	@ApiImplicitParams({
		@ApiImplicitParam(
				name = "cluster_id",
				value = "Cluster ID (ex) 1",
				required = true,
				dataType = "string",
				paramType = "path"
		),
		@ApiImplicitParam(
				name = "workload",
				value = "workload (ex) daemonset, deployment, replicaset, pod, statefulset, job, service, ingress ...  ",
				required = true,
				dataType = "string",
				paramType = "path"
		)
	})
	@ApiResponses(value={
			@ApiResponse( code=200, message="SUCCESS"),
			@ApiResponse( code=500, message="Internal Server Error")
	})
	
	@RequestMapping(value="/cluster/{cluster_id}/workload/{workload}", method=RequestMethod.GET)
	@ResponseBody
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public ResponseEntity<ResponseData> getClusterWorkloadList( @PathVariable("cluster_id") String cluster_id , @PathVariable("workload") String workload 
																) throws Exception {
		
		ResponseEntity<ResponseData> response 			= null;
		try{
			response 									= clusterService.getClusterWorkloadList( cluster_id, workload );
		}catch(Exception e){
			
			ResponseData resData	= new ResponseData();
			resData.setResponse_code(Const.INTERNAL_SERVER_ERROR);
			resData.setMessage(Const.FAIL);
			response = new ResponseEntity<ResponseData>(resData, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response;
	}
	
	/**
	* Kubernetes component Status
	* @return
	* @throws Exception
	*/
	@ApiOperation(value="Kubernetes component Status", httpMethod="GET", notes="1:true, 0:false")
	@ApiImplicitParams({
		@ApiImplicitParam(
				name = "cluster_id",
				value = "Cluster ID (ex) 1",
				required = true,
				dataType = "string",
				paramType = "path"
		),
		@ApiImplicitParam(
				name = "component",
				value = "component (ex) kube-controller-manager, kube-scheduler ",
				required = true,
				dataType = "string",
				paramType = "path"
		)
	})
	@ApiResponses(value={
			@ApiResponse( code=200, message="SUCCESS"),
			@ApiResponse( code=500, message="Internal Server Error")
	})
	
	@RequestMapping(value="/cluster/{cluster_id}/component/{component}/status/ready", method=RequestMethod.GET)
	@ResponseBody
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public ResponseEntity<ResponseData> getClusterKubernetesComponetStatusReady( @PathVariable("cluster_id") String cluster_id , @PathVariable("component") String component ) throws Exception {
		
		ResponseEntity<ResponseData> response 			= null;
		try{
			response 									= clusterService.getClusterKubernetesComponetStatusReady( cluster_id, component );
		}catch(Exception e){
			
			ResponseData resData	= new ResponseData();
			resData.setResponse_code(Const.INTERNAL_SERVER_ERROR);
			resData.setMessage(Const.FAIL);
			response = new ResponseEntity<ResponseData>(resData, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response;
	}
	
	/**
	* Kubernetes API Server Status
	* @return
	* @throws Exception
	*/
	@ApiOperation(value="Kubernetes API Server Status", httpMethod="GET", notes="1:true, 0:false")
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
	
	@RequestMapping(value="/cluster/{cluster_id}/component/apiserver/status", method=RequestMethod.GET)
	@ResponseBody
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public ResponseEntity<ResponseData> getClusterKubernetesApiServerStatusReady( @PathVariable("cluster_id") String cluster_id ) throws Exception {
		
		ResponseEntity<ResponseData> response 			= null;
		try{
			response 									= clusterService.getClusterKubernetesApiServerStatusReady( cluster_id );
		}catch(Exception e){
			
			ResponseData resData	= new ResponseData();
			resData.setResponse_code(Const.INTERNAL_SERVER_ERROR);
			resData.setMessage(Const.FAIL);
			response = new ResponseEntity<ResponseData>(resData, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response;
	}
}
