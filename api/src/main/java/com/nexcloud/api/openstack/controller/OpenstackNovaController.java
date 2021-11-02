package com.nexcloud.api.openstack.controller;

import com.nexcloud.api.openstack.service.OpenstackService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import javax.ws.rs.QueryParam;

@Configuration
@RestController
@EnableAutoConfiguration
@ComponentScan
@RequestMapping(value = "v0")
public class OpenstackNovaController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OpenstackNovaController.class);

    private final OpenstackService service;

    @Autowired
    public OpenstackNovaController(OpenstackService service) {
        this.service = service;
    }

    @ApiOperation("List Servers Detailed")
    @GetMapping("/servers/detail")
    public ResponseEntity<String> getServers(
            @ApiParam(value = "Project Name (ex) admin", required = true) @QueryParam("projectName") String projectName,
            @ApiParam(value = "Domain ID (ex) default", required = true) @QueryParam("domainId") String domainId
    ) {
        ResponseEntity<String> response;

        try {
            response = service.accessOpenstack("/compute/v2.1/servers/detail", projectName, domainId);
        } catch (HttpClientErrorException he) {
            response = new ResponseEntity<>("Client error", he.getStatusCode());
        } catch (Exception e) {
            e.printStackTrace();
            response = new ResponseEntity<>("Failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return response;
    }
}
