package com.nexcloud.api.openstack.controller;

import com.nexcloud.api.openstack.service.OpenstackService;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

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
    public ResponseEntity<String> getNetworks(
            @ApiParam(value = "Project Name (ex) admin", required = true) @QueryParam("projectName") String projectName,
            @ApiParam(value = "Domain ID (ex) default", required = true) @QueryParam("domainId") String domainId
    ) {
        ResponseEntity<String> response;

        try {
            response = service.accessOpenstack(neutronPort, "v2.0/networks.json?limit=2", projectName, domainId);
        } catch (HttpClientErrorException he) {
            response = new ResponseEntity<>("Client error", he.getStatusCode());
        } catch (Exception e) {
            e.printStackTrace();
            response = new ResponseEntity<>("Failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return response;
    }
}
