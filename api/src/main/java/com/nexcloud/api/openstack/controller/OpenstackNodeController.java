package com.nexcloud.api.openstack.controller;

import com.nexcloud.api.openstack.service.OpenstackNodeService;
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
public class OpenstackNodeController {

    // v1/nodes http 입력을 openstack의 v1/nodes로 연결?

    @Autowired
    private OpenstackNodeService service;

    static final Logger logger = LoggerFactory.getLogger(OpenstackNodeController.class);

    @ApiOperation(value = "Nodes Info", httpMethod = "GET", notes = "Nodes Info")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "SUCCESS"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @RequestMapping(value = "/nodes")
    @ResponseBody
    public ResponseEntity<String> getNode() {
        ResponseEntity<String> response;

        try {
            response = service.getNodes();
        } catch (Exception e) {
            e.printStackTrace();
            response = new ResponseEntity<>("failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return response;
    }
}
