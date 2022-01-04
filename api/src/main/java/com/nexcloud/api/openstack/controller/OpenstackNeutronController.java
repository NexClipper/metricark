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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.QueryParam;

@Configuration
@RestController
@EnableAutoConfiguration
@ComponentScan
@RequestMapping(value = "/v0")
public class OpenstackNeutronController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OpenstackCustomController.class);

    @Value("${openstack.neutronport}")
    private String neutronPort;

    private final OpenstackService service;

    @Autowired
    public OpenstackNeutronController(OpenstackService service) {
        this.service = service;
    }


    @ApiOperation("List networks")
    @GetMapping("/networks")
    public ResponseEntity<ResponseData> getNetworks(
            @ApiParam(value = "Project Name (ex) admin", required = true) @QueryParam("projectName") String projectName,
            @ApiParam(value = "Domain ID (ex) default", required = true) @QueryParam("domainId") String domainId,
            @ApiParam(value = "Openstack Endpoint", required = false) @RequestParam(value = "endpoint", required = false) String endpoint
    ) {
        ResponseEntity<ResponseData> response;

        try {
            ResponseEntity<ResponseData> rawResponse = service.accessOpenstack(neutronPort, "v2.0/networks", projectName, domainId, endpoint);
            return service.parseOpenstackNetworks(rawResponse);
        } catch (Exception e) {
            e.printStackTrace();
            response = service.getErrorResponse();
        }

        return response;
    }

    @ApiOperation("Network topology")
    @GetMapping("/topology")
    public ResponseEntity<ResponseData> getTopology(
            @ApiParam(value = "Project Name (ex) admin", required = true) @QueryParam("projectName") String projectName,
            @ApiParam(value = "Domain ID (ex) default", required = true) @QueryParam("domainId") String domainId,
            @ApiParam(value = "Openstack Endpoint", required = false) @RequestParam(value = "endpoint", required = false) String endpoint
    ) {
        return service.getNetworkTopology(neutronPort, projectName, domainId, endpoint);
    }

    @GetMapping("/test")
    public String getAccessOpenstackPayload(String targetUrl, String projectName, String domainId, String endpoint) {
        return service.getAccessOpenstackPayload(targetUrl, projectName, domainId, endpoint);
    }
}
