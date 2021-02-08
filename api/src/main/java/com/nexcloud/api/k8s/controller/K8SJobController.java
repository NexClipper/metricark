package com.nexcloud.api.k8s.controller;

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
import com.nexcloud.api.k8s.service.K8SJobService;
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
public class K8SJobController extends SpringBootServletInitializer implements WebApplicationInitializer {
	
	@Autowired
    private K8SJobService service;
	
	static final Logger logger = LoggerFactory.getLogger(K8SJobController.class);
	

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
	            name = "job", 
	            value = "job name (ex) '.*' => (전체 job), nc-grafana-db", 
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
	
	@RequestMapping(value="/cluster/{cluster_id}/job/{job}/creation", method=RequestMethod.GET)
	@ResponseBody
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public ResponseEntity<ResponseData> getJobCreation(  @PathVariable("cluster_id") String cluster_id
														,@PathVariable("job") String job
														,@QueryParam("namespace") String namespace
														) throws Exception {
		
		ResponseEntity<ResponseData> response 			= null;
		try{
			response 									= service.getJobCreation( cluster_id, job, namespace );
		}catch(Exception e){
			
			ResponseData resData	= new ResponseData();
			resData.setResponse_code(Const.INTERNAL_SERVER_ERROR);
			resData.setMessage(Const.FAIL);
			response = new ResponseEntity<ResponseData>(resData, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response;
	}
	
	/**
	* Job Success
	* @return
	* @throws Exception
	*/
	@ApiOperation(value="Job Success", httpMethod="GET", notes="Job Success")
	@ApiImplicitParams({
		@ApiImplicitParam(
				name = "cluster_id",
				value = "Cluster ID (ex) 1",
				required = true,
				dataType = "string",
				paramType = "path"
		),
		@ApiImplicitParam(
	            name = "job", 
	            value = "job name (ex) '.*' => (전체 job), nc-grafana-db", 
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
	
	@RequestMapping(value="/cluster/{cluster_id}/job/{job}/status/successed", method=RequestMethod.GET)
	@ResponseBody
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public ResponseEntity<ResponseData> getJobStatusSuccessed(  @PathVariable("cluster_id") String cluster_id
																,@PathVariable("job") String job
																,@QueryParam("namespace") String namespace
																) throws Exception {
		
		ResponseEntity<ResponseData> response 			= null;
		try{
			response 									= service.getJobStatusSuccessed( cluster_id, job, namespace );
		}catch(Exception e){
			
			ResponseData resData	= new ResponseData();
			resData.setResponse_code(Const.INTERNAL_SERVER_ERROR);
			resData.setMessage(Const.FAIL);
			response = new ResponseEntity<ResponseData>(resData, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response;
	}
	
	/**
	* Job active
	* @return
	* @throws Exception
	*/
	@ApiOperation(value="Job active", httpMethod="GET", notes="Job active")
	@ApiImplicitParams({
		@ApiImplicitParam(
				name = "cluster_id",
				value = "Cluster ID (ex) 1",
				required = true,
				dataType = "string",
				paramType = "path"
		),
		@ApiImplicitParam(
	            name = "job", 
	            value = "job name (ex) '.*' => (전체 job), nc-grafana-db", 
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
	
	@RequestMapping(value="/cluster/{cluster_id}/job/{job}/status/active", method=RequestMethod.GET)
	@ResponseBody
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public ResponseEntity<ResponseData> getJobStatusActive(  @PathVariable("cluster_id") String cluster_id
															,@PathVariable("job") String job
															,@QueryParam("namespace") String namespace
															) throws Exception {
		
		ResponseEntity<ResponseData> response 			= null;
		try{
			response 									= service.getJobStatusActive( cluster_id, job, namespace );
		}catch(Exception e){
			
			ResponseData resData	= new ResponseData();
			resData.setResponse_code(Const.INTERNAL_SERVER_ERROR);
			resData.setMessage(Const.FAIL);
			response = new ResponseEntity<ResponseData>(resData, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response;
	}
	
	/**
	* Job failed
	* @return
	* @throws Exception
	*/
	@ApiOperation(value="Job failed", httpMethod="GET", notes="Job failed")
	@ApiImplicitParams({
		@ApiImplicitParam(
				name = "cluster_id",
				value = "Cluster ID (ex) 1",
				required = true,
				dataType = "string",
				paramType = "path"
		),
		@ApiImplicitParam(
	            name = "job", 
	            value = "job name (ex) '.*' => (전체 job), nc-grafana-db", 
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
	
	@RequestMapping(value="/cluster/{cluster_id}/job/{job}/status/failed", method=RequestMethod.GET)
	@ResponseBody
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public ResponseEntity<ResponseData> getJobStatusFailed(  @PathVariable("cluster_id") String cluster_id
															,@PathVariable("job") String job
															,@QueryParam("namespace") String namespace
															) throws Exception {
		
		ResponseEntity<ResponseData> response 			= null;
		try{
			response 									= service.getJobStatusFailed( cluster_id, job, namespace );
		}catch(Exception e){
			
			ResponseData resData	= new ResponseData();
			resData.setResponse_code(Const.INTERNAL_SERVER_ERROR);
			resData.setMessage(Const.FAIL);
			response = new ResponseEntity<ResponseData>(resData, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response;
	}
	
	/**
	* Job owner Info
	* @return
	* @throws Exception
	*/
	@ApiOperation(value="Job owner Info", httpMethod="GET", notes="Job owner Info")
	@ApiImplicitParams({
		@ApiImplicitParam(
				name = "cluster_id",
				value = "Cluster ID (ex) 1",
				required = true,
				dataType = "string",
				paramType = "path"
		),
		@ApiImplicitParam(
	            name = "job", 
	            value = "job name (ex) '.*' => (전체 job), nc-grafana-db", 
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
	
	@RequestMapping(value="/cluster/{cluster_id}/job/{job}/owner", method=RequestMethod.GET)
	@ResponseBody
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public ResponseEntity<ResponseData> getJobOwner(  @PathVariable("cluster_id") String cluster_id
													,@PathVariable("job") String job
													,@QueryParam("namespace") String namespace
													) throws Exception {
		
		ResponseEntity<ResponseData> response 			= null;
		try{
			response 									= service.getJobOwner( cluster_id, job, namespace );
		}catch(Exception e){
			
			ResponseData resData	= new ResponseData();
			resData.setResponse_code(Const.INTERNAL_SERVER_ERROR);
			resData.setMessage(Const.FAIL);
			response = new ResponseEntity<ResponseData>(resData, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response;
	}
	
	/**
	* Job Info
	* @return
	* @throws Exception
	*/
	@ApiOperation(value="Job info", httpMethod="GET", notes="Job info")
	@ApiImplicitParams({
		@ApiImplicitParam(
				name = "cluster_id",
				value = "Cluster ID (ex) 1",
				required = true,
				dataType = "string",
				paramType = "path"
		),
		@ApiImplicitParam(
	            name = "job", 
	            value = "job name (ex) '.*' => (전체 job), nc-grafana-db", 
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
	
	@RequestMapping(value="/cluster/{cluster_id}/job/{job}/info", method=RequestMethod.GET)
	@ResponseBody
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public ResponseEntity<ResponseData> getJobInfo(  @PathVariable("cluster_id") String cluster_id
													,@PathVariable("job") String job
													,@QueryParam("namespace") String namespace
													) throws Exception {
		
		ResponseEntity<ResponseData> response 			= null;
		try{
			response 									= service.getJobInfo( cluster_id, job, namespace );
		}catch(Exception e){
			
			ResponseData resData	= new ResponseData();
			resData.setResponse_code(Const.INTERNAL_SERVER_ERROR);
			resData.setMessage(Const.FAIL);
			response = new ResponseEntity<ResponseData>(resData, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response;
	}
	
	/**
	* Job Labels
	* @return
	* @throws Exception
	*/
	@ApiOperation(value="Job Labels", httpMethod="GET", notes="Job Labels")
	@ApiImplicitParams({
		@ApiImplicitParam(
				name = "cluster_id",
				value = "Cluster ID (ex) 1",
				required = true,
				dataType = "string",
				paramType = "path"
		),
		@ApiImplicitParam(
	            name = "job", 
	            value = "job name (ex) '.*' => (전체 job), nc-grafana-db", 
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
	
	@RequestMapping(value="/cluster/{cluster_id}/job/{job}/labels", method=RequestMethod.GET)
	@ResponseBody
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public ResponseEntity<ResponseData> getJobLabels(  @PathVariable("cluster_id") String cluster_id
													,@PathVariable("job") String job
													,@QueryParam("namespace") String namespace
													) throws Exception {
		
		ResponseEntity<ResponseData> response 			= null;
		try{
			response 									= service.getJobLabels( cluster_id, job, namespace );
		}catch(Exception e){
			
			ResponseData resData	= new ResponseData();
			resData.setResponse_code(Const.INTERNAL_SERVER_ERROR);
			resData.setMessage(Const.FAIL);
			response = new ResponseEntity<ResponseData>(resData, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response;
	}
}
