package com.nexcloud.api.openstack.controller;

import com.nexcloud.api.domain.ResponseData;
import com.nexcloud.api.openstack.service.OpenstackService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.QueryParam;

@Configuration
@RestController
@EnableAutoConfiguration
@ComponentScan
public class OpenstackKeystoneController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OpenstackKeystoneController.class);

    @Value("${openstack.keystoneport}")
    private String keystoneport;

    private final OpenstackService service;

    @Autowired
    public OpenstackKeystoneController(OpenstackService service) {
        this.service = service;
    }

    @ApiOperation("Lists projects")
    @GetMapping("/identity/projects")
    public ResponseEntity<ResponseData> getNodeDetail(
            @ApiParam(value = "Project Name (ex) admin", required = true) @QueryParam("projectName") String projectName,
            @ApiParam(value = "Domain ID (ex) default", required = true) @QueryParam("domainId") String domainId,
            @ApiParam(value = "Openstack Endpoint", required = false) @RequestParam(value = "endpoint", required = false) String endpoint
    ) {
        ResponseEntity<ResponseData> response;

        try {
            response = service.accessOpenstack(keystoneport, "/identity/v3/projects", projectName, domainId, endpoint);
        } catch (Exception e) {
            e.printStackTrace();
            response = service.getErrorResponse();
        }

        return response;
    }

}
