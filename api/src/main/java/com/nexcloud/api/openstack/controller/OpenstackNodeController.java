package com.nexcloud.api.openstack.controller;

import com.nexcloud.api.domain.ResponseData;
import com.nexcloud.api.openstack.service.OpenstackService;
import com.nexcloud.util.Const;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.QueryParam;

@Configuration
@RestController
@EnableAutoConfiguration
@ComponentScan
@RequestMapping(value = "/v1")
public class OpenstackNodeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OpenstackNodeController.class);

    @Value("${openstack.senlinport}")
    private String senlinPort;

    private final OpenstackService service;

    @Autowired
    public OpenstackNodeController(OpenstackService service) {
        this.service = service;
    }


    @ApiOperation("List nodes")
    @ApiResponse(code = 503, message = "Service Unavailable")
    @GetMapping("/nodes")
    public ResponseEntity<ResponseData> getNodes(
            @ApiParam(value = "Project Name (ex) admin", required = true) @QueryParam("projectName") String projectName,
            @ApiParam(value = "Domain ID (ex) default", required = true) @QueryParam("domainId") String domainId
    ) {
        ResponseEntity<ResponseData> response;

        try {
            response = service.accessOpenstack(senlinPort, "/v1/nodes", projectName, domainId);
        } catch (Exception e) {
            e.printStackTrace();
            response = service.getErrorResponse();
        }

        return response;
    }

    @ApiOperation("Show node details")
    @ApiResponse(code = 503, message = "Service Unavailable")
    @GetMapping("/nodes/{nodeId}")
    public ResponseEntity<ResponseData> getNodeDetail(
            @ApiParam(value = "Node ID", required = true) @PathVariable String nodeId,
            @ApiParam(value = "Project Name (ex) admin", required = true) @QueryParam("projectName") String projectName,
            @ApiParam(value = "Domain ID (ex) default", required = true) @QueryParam("domainId") String domainId
    ) {
        ResponseEntity<ResponseData> response;

        try {
            response = service.accessOpenstack(senlinPort, String.format("/v1/nodes/%s", nodeId), projectName, domainId);
        } catch (Exception e) {
            e.printStackTrace();
            response = service.getErrorResponse();
        }

        return response;
    }
}
