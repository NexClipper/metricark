package com.nexcloud.api.openstack.controller;

import com.nexcloud.api.openstack.service.OpenstackClusterService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Configuration
@RestController
@EnableAutoConfiguration
@ComponentScan
@RequestMapping(value = "/v1")
public class OpenstackClusterController {

    static final Logger logger = LoggerFactory.getLogger(OpenstackClusterController.class);

    @Autowired
    OpenstackClusterService service;


    @ApiOperation(value = "Clusters Info", httpMethod = "GET", notes = "Clusters Info")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "SUCCESS"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 503, message = "Service Unavailable")
    })
    @RequestMapping(value = "/clusters")
    @ResponseBody
    public ResponseEntity<String> getClusters() {
        ResponseEntity<String> response;

        try {
            response = service.getClusters();
        } catch (Exception e) {
            e.printStackTrace();
            response = new ResponseEntity<>("failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return response;
    }
}
