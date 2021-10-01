package com.nexcloud.api.openstack.controller;

import com.nexcloud.api.openstack.service.OpenstackService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.QueryParam;

@Configuration
@RestController
@EnableAutoConfiguration
@ComponentScan
@RequestMapping(value = "/compute/v2.1")
public class OpenstackNovaController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OpenstackNovaController.class);

    private final OpenstackService service;

    @Autowired
    public OpenstackNovaController(OpenstackService service) {
        this.service = service;
    }

    @ApiOperation(value = "Servers Info", httpMethod = "GET", notes = "Servers Info")
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "projectName",
                    value = "Project Name (ex) admin",
                    required = true,
                    dataType = "string",
                    paramType = "query"
            ),
            @ApiImplicitParam(
                    name = "domainId",
                    value = "Domain ID (ex) default",
                    required = true,
                    dataType = "string",
                    paramType = "query"
            )
    })
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "SUCCESS"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 503, message = "Service Unavailable")
    })
    @RequestMapping(value = "/servers")
    public ResponseEntity<String> getServers(
            @QueryParam("projectName") String projectName,
            @QueryParam("domainId") String domainId
    ) {
        ResponseEntity<String> response;

        try {
            response = service.accessOpenstack("/compute/v2.1/servers", projectName, domainId);
        } catch (Exception e) {
            e.printStackTrace();
            response = new ResponseEntity<>("failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return response;
    }
}
