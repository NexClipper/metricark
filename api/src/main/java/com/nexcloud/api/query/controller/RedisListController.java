package com.nexcloud.api.query.controller;

import com.nexcloud.api.domain.ResponseData;
import com.nexcloud.api.query.service.RedisListService;
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
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.io.Serializable;

@Configuration
@RestController
@EnableAutoConfiguration
@ComponentScan
@RequestMapping(value = "/v1")
public class RedisListController extends SpringBootServletInitializer implements Serializable, WebApplicationInitializer {

    @Autowired
    private RedisListService service;

    static final Logger logger = LoggerFactory.getLogger(RedisListController.class);

    /**
     * Redis Pod list
     * @return
     * @Throws Exception
     */
    @ApiOperation(value = "Redis GET Pod list", httpMethod = "GET", notes = "")
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "cluster_id",
                    value = "Cluster ID (ex) 1",
                    required = true,
                    dataType = "string",
                    paramType = "path"
            ),
            @ApiImplicitParam(
                    name = "key",
                    value = "Redis Key (ex) kubernetes ",
                    required = true,
                    dataType = "string",
                    paramType = "path",
                    defaultValue=""
            ),
            @ApiImplicitParam(
                    name = "field",
                    value = "Redis Field (ex) pods, daemonsets ",
                    required = true,
                    dataType = "string",
                    paramType = "path"
            )
    })
    @ApiResponses(value={
            @ApiResponse( code=200, message="SUCCESS"),
            @ApiResponse( code=500, message="Internal Server Error")
    })

    @RequestMapping(value="/cluster/{cluster_id}/redis/key/{key}/field/{field}", method= RequestMethod.GET)
    @ResponseBody
    @Consumes({ MediaType.APPLICATION_JSON })
    @Produces({ MediaType.APPLICATION_JSON })

    public ResponseEntity<ResponseData> getRedisValue(@PathVariable("cluster_id") String cluster_id
            , @PathVariable(value = "key") String key
            , @PathVariable(value = "field") String field
    ) throws Exception {

        ResponseEntity<ResponseData> response 			= null;
        try{
            response 									= service.getList( cluster_id, key, field);
        }catch(Exception e){

            ResponseData resData	= new ResponseData();
            resData.setResponse_code(Const.INTERNAL_SERVER_ERROR);
            resData.setMessage(Const.FAIL);
            response = new ResponseEntity<ResponseData>(resData, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return response;
    }
}
