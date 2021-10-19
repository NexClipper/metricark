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
import com.nexcloud.api.k8s.service.K8SPodNetworkService;
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
public class K8SPodNetworkController extends SpringBootServletInitializer implements WebApplicationInitializer {
	
	@Autowired
    private K8SPodNetworkService service;
	
	static final Logger logger = LoggerFactory.getLogger(K8SPodNetworkController.class);
	

	/**
	* Pod receive network traffic( rx )
	* @return
	* @throws Exception
	*/
	@ApiOperation(value="Pod receive network traffic( rx )", httpMethod="GET", notes="Pod receive network traffic( rx )")
	@ApiImplicitParams({
		@ApiImplicitParam(
				name = "cluster_id",
				value = "Cluster ID (ex) 1",
				required = true,
				dataTypeClass = String.class,
				paramType = "path"
		),
		@ApiImplicitParam(
	            name = "pod_name", 
	            value = "pod name (ex) '.*' => (전체 pod), pod name", 
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
	    ),
		@ApiImplicitParam(
	            name = "start", 
	            value = "조회 시작 시간( Unix Timestamp ) (ex) 1521577239 ", 
	            required = false, 
	            dataTypeClass = String.class, 
	            paramType = "query",
	            defaultValue=""
	    ),
		@ApiImplicitParam(
	            name = "end", 
	            value = "조회 종료시간( Unix Timestamp ) (ex) 1521577239 ", 
	            required = false, 
	            dataTypeClass = String.class, 
	            paramType = "query",
	            defaultValue=""
	    )
	})
	@ApiResponses(value={
			@ApiResponse( code=200, message="SUCCESS"),
			@ApiResponse( code=500, message="Internal Server Error")
	})
	
	@RequestMapping(value="/cluster/{cluster_id}/pod/{pod_name}/resource/network/rx", method=RequestMethod.GET)
	@ResponseBody
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public ResponseEntity<ResponseData> getPodResourceNetworkRx(    @PathVariable("cluster_id") String cluster_id
																	,@PathVariable("pod_name") String pod_name
																	,@QueryParam("namespace") String namespace
																	,@QueryParam("start") String start
																	,@QueryParam("end") String end
																	) throws Exception {
		
		ResponseEntity<ResponseData> response 			= null;
		try{
			response 									= service.getPodResourceNetworkRx( cluster_id, pod_name, namespace, start, end );
		}catch(Exception e){
			
			ResponseData resData	= new ResponseData();
			resData.setResponse_code(Const.INTERNAL_SERVER_ERROR);
			resData.setMessage(Const.FAIL);
			response = new ResponseEntity<ResponseData>(resData, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response;
	}
	
	/**
	* Pod send network traffic( tx )
	* @return
	* @throws Exception
	*/
	@ApiOperation(value="Pod send network traffic( tx )", httpMethod="GET", notes="Pod send network traffic( tx )")
	@ApiImplicitParams({
		@ApiImplicitParam(
				name = "cluster_id",
				value = "Cluster ID (ex) 1",
				required = true,
				dataTypeClass = String.class,
				paramType = "path"
		),
		@ApiImplicitParam(
	            name = "pod_name", 
	            value = "pod name (ex) '.*' => (전체 pod), pod name", 
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
	    ),
		@ApiImplicitParam(
	            name = "start", 
	            value = "조회 시작 시간( Unix Timestamp ) (ex) 1521577239 ", 
	            required = false, 
	            dataTypeClass = String.class, 
	            paramType = "query",
	            defaultValue=""
	    ),
		@ApiImplicitParam(
	            name = "end", 
	            value = "조회 종료시간( Unix Timestamp ) (ex) 1521577239 ", 
	            required = false, 
	            dataTypeClass = String.class, 
	            paramType = "query",
	            defaultValue=""
	    )
	})
	@ApiResponses(value={
			@ApiResponse( code=200, message="SUCCESS"),
			@ApiResponse( code=500, message="Internal Server Error")
	})
	
	@RequestMapping(value="/cluster/{cluster_id}/pod/{pod_name}/resource/network/tx", method=RequestMethod.GET)
	@ResponseBody
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public ResponseEntity<ResponseData> getPodResourceNetworkTx(  @PathVariable("cluster_id") String cluster_id
																,@PathVariable("pod_name") String pod_name
																,@QueryParam("namespace") String namespace
																,@QueryParam("start") String start
																,@QueryParam("end") String end
																) throws Exception {
		
		ResponseEntity<ResponseData> response 			= null;
		try{
			response 									= service.getPodResourceNetworkTx( cluster_id, pod_name, namespace, start, end );
		}catch(Exception e){
			
			ResponseData resData	= new ResponseData();
			resData.setResponse_code(Const.INTERNAL_SERVER_ERROR);
			resData.setMessage(Const.FAIL);
			response = new ResponseEntity<ResponseData>(resData, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response;
	}
}
