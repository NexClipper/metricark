package com.nexcloud.api.query.controller;

import com.nexcloud.api.domain.ResponseData;
import com.nexcloud.api.query.service.QueryDetailService;
import com.nexcloud.util.Const;
import io.swagger.annotations.*;
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
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.io.Serializable;

@Configuration
@RestController
@EnableAutoConfiguration
@ComponentScan
@RequestMapping(value = "/v1")

public class QueryDetailController extends SpringBootServletInitializer implements Serializable, WebApplicationInitializer {

    @Autowired
    private QueryDetailService service;

    static final Logger logger = LoggerFactory.getLogger(QueryListController.class);

    /**
     * Redis Pod Detail
     * @return
     * @Throws Exception
     */
    @ApiOperation(value = "GET Pods Detail", httpMethod = "GET", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "cluster_id",
                    value = "Cluster ID (ex) 1",
                    required = true,
                    dataType = "string",
                    paramType = "path"
            ),
            @ApiImplicitParam(
                    name = "name",
                    value = "Pod Name",
                    required = true,
                    dataType = "string",
                    paramType = "path"
            )
    })
    @ApiResponses(value={
            @ApiResponse( code=200, message="SUCCESS"),
            @ApiResponse( code=500, message="Internal Server Error")
    })

    @RequestMapping(value="/cluster/{cluster_id}/query/pods/name/{name}", method= RequestMethod.GET)
    @ResponseBody
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })

    public ResponseEntity<ResponseData> getRedisValue(@PathVariable("cluster_id") String cluster_id
            , @PathVariable(value = "name") String name
    ) throws Exception {

        ResponseEntity<ResponseData> response 			= null;
        try{
            response 									= service.get( cluster_id, name);
        }catch(Exception e){

            ResponseData resData	= new ResponseData();
            resData.setResponse_code(Const.INTERNAL_SERVER_ERROR);
            resData.setMessage(Const.FAIL);
            response = new ResponseEntity<ResponseData>(resData, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return response;
    }

}