package com.nexcloud.api.node.controller;

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
import com.nexcloud.api.node.service.NodeContainerMemoryService;
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
public class NodeContainerMemoryController extends SpringBootServletInitializer implements WebApplicationInitializer {
	
	@Autowired
    private NodeContainerMemoryService service;
	
	static final Logger logger = LoggerFactory.getLogger(NodeContainerMemoryController.class);
	
	/**
	* Docker Memory Used byte
	* @return
	* @throws Exception
	*/
	@ApiOperation(value="Docker Memory Used byte", httpMethod="GET", notes="Docker Memory Used byte")
	@ApiImplicitParams({
		@ApiImplicitParam(
				name = "cluster_id",
				value = "Cluster ID (ex) 1",
				required = true,
				dataTypeClass = String.class,
				paramType = "path"
		),
		@ApiImplicitParam(
	            name = "node_name", 
	            value = " Node Name(ex)'.*'=>(전체노드), node1", 
	            required = true, 
	            dataTypeClass = String.class, 
	            paramType = "path"
	    ),
		@ApiImplicitParam(
	            name = "container", 
	            value = " docker container name(ex) '.*'=>(전체 docker container)", 
	            required = true, 
	            dataTypeClass = String.class, 
	            paramType = "path"
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
	
	@RequestMapping(value="/cluster/{cluster_id}/nodes/{node_name}/containers/{container}/resource/memory/used", method=RequestMethod.GET)
	@ResponseBody
	@Consumes({ MediaType.APPLICATION_JSON })
	@Produces({ MediaType.APPLICATION_JSON })
	public ResponseEntity<ResponseData> getNodeContainerMemoryUsedByte(  @PathVariable("cluster_id") String cluster_id
																		,@PathVariable("node_name") String node_name
																		,@PathVariable("container") String container
																		,@QueryParam("start") String start
																		,@QueryParam("end") String end
																	  ) throws Exception {
		
		ResponseEntity<ResponseData> response 			= null;
		try{
			response 									= service.getNodeContainerMemoryUsedByte( cluster_id, node_name, container, start, end );
		}catch(Exception e){
			
			ResponseData resData	= new ResponseData();
			resData.setResponse_code(Const.INTERNAL_SERVER_ERROR);
			resData.setMessage(Const.FAIL);
			response = new ResponseEntity<ResponseData>(resData, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return response;
	}
}
