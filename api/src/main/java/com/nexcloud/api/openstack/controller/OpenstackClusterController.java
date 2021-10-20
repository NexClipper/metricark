package com.nexcloud.api.openstack.controller;

import com.nexcloud.api.openstack.service.OpenstackService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
import org.springframework.web.client.HttpClientErrorException;

import javax.ws.rs.QueryParam;


@Configuration
@RestController
@EnableAutoConfiguration
@ComponentScan
@RequestMapping(value = "/v1")
public class OpenstackClusterController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OpenstackClusterController.class);

    @Value("${openstack.senlinport}")
    private String senlinPort;

    private final OpenstackService service;

    @Autowired
    public OpenstackClusterController(OpenstackService service) {
        this.service = service;
    }


    @ApiOperation("Show build information")
    @ApiResponse(code = 503, message = "Service Unavailable")
    @GetMapping("/build-info")
    public ResponseEntity<String> getBuildInfo(
            @ApiParam(value = "Project Name (ex) admin", required = true) @QueryParam("projectName") String projectName,
            @ApiParam(value = "Domain ID (ex) default", required = true) @QueryParam("domainId") String domainId
    ) {
        ResponseEntity<String> response;

        try {
            response = service.accessOpenstack(senlinPort, "/v1/build-info", projectName, domainId);
        } catch (HttpClientErrorException he) {
            response = new ResponseEntity<>("Client error", he.getStatusCode());
        } catch (Exception e) {
            e.printStackTrace();
            response = new ResponseEntity<>("Failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return response;
    }

    @ApiOperation("List profiles")
    @ApiResponse(code = 503, message = "Service Unavailable")
    @GetMapping("/profiles")
    public ResponseEntity<String> getProfilesInfo(
            @ApiParam(value = "Project Name (ex) admin", required = true) @QueryParam("projectName") String projectName,
            @ApiParam(value = "Domain ID (ex) default", required = true) @QueryParam("domainId") String domainId
    ) {
        ResponseEntity<String> response;

        try {
            response = service.accessOpenstack(senlinPort, "/v1/profiles", projectName, domainId);
        } catch (HttpClientErrorException he) {
            response = new ResponseEntity<>("Client error", he.getStatusCode());
        } catch (Exception e) {
            e.printStackTrace();
            response = new ResponseEntity<>("Failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return response;
    }

    @ApiOperation("Show profile details")
    @ApiResponse(code = 503, message = "Service Unavailable")
    @GetMapping("/profiles/{profileId}")
    public ResponseEntity<String> getProfileDetail(
            @ApiParam(value = "Profile ID", required = true) @PathVariable String profileId,
            @ApiParam(value = "Project Name (ex) admin", required = true) @QueryParam("projectName") String projectName,
            @ApiParam(value = "Domain ID (ex) default", required = true) @QueryParam("domainId") String domainId
    ) {
        ResponseEntity<String> response;

        try {
            response = service.accessOpenstack(senlinPort, String.format("/v1/profiles/%s", profileId), projectName, domainId);
        } catch (HttpClientErrorException he) {
            response = new ResponseEntity<>("Client error", he.getStatusCode());
        } catch (Exception e) {
            e.printStackTrace();
            response = new ResponseEntity<>("Failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return response;
    }

    @ApiOperation("List clusters")
    @ApiResponse(code = 503, message = "Service Unavailable")
    @GetMapping("/clusters")
    public ResponseEntity<String> getClusters(
            @ApiParam(value = "Project Name (ex) admin", required = true) @QueryParam("projectName") String projectName,
            @ApiParam(value = "Domain ID (ex) default", required = true) @QueryParam("domainId") String domainId
    ) {
        ResponseEntity<String> response;

        try {
            response = service.accessOpenstack(senlinPort, "/v1/clusters", projectName, domainId);
        } catch (HttpClientErrorException he) {
            response = new ResponseEntity<>("Client error", he.getStatusCode());
        } catch (Exception e) {
            e.printStackTrace();
            response = new ResponseEntity<>("Failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return response;
    }

    @ApiOperation("Show cluster details")
    @ApiResponse(code = 503, message = "Service Unavailable")
    @GetMapping("/clusters/{clusterId}")
    public ResponseEntity<String> getClusterDetail(
            @ApiParam(value = "Cluster ID", required = true) @PathVariable String clusterId,
            @ApiParam(value = "Project Name (ex) admin", required = true) @QueryParam("projectName") String projectName,
            @ApiParam(value = "Domain ID (ex) default", required = true) @QueryParam("domainId") String domainId
    ) {
        ResponseEntity<String> response;

        try {
            response = service.accessOpenstack(senlinPort, String.format("/v1/clusters/%s", clusterId), projectName, domainId);
        } catch (HttpClientErrorException he) {
            response = new ResponseEntity<>("Client error", he.getStatusCode());
        } catch (Exception e) {
            e.printStackTrace();
            response = new ResponseEntity<>("Failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return response;
    }

    @ApiOperation("Collect Attributes Across a Cluster")
    @ApiResponses({
            @ApiResponse(code = 202, message = "Accepted"),
            @ApiResponse(code = 503, message = "Service Unavailable")
    })
    @GetMapping("/clusters/{clusterId}/attrs/{path}")
    public ResponseEntity<String> getProfileDetail(
            @ApiParam(value = "Cluster ID", required = true) @PathVariable String clusterId,
            @ApiParam(value = "Path", required = true) @PathVariable String path,
            @ApiParam(value = "Project Name (ex) admin", required = true) @QueryParam("projectName") String projectName,
            @ApiParam(value = "Domain ID (ex) default", required = true) @QueryParam("domainId") String domainId
    ) {
        ResponseEntity<String> response;

        try {
            response = service.accessOpenstack(senlinPort, String.format("/v1/clusters/%s/attrs/%s", clusterId, path), projectName, domainId);
        } catch (HttpClientErrorException he) {
            response = new ResponseEntity<>("Client error", he.getStatusCode());
        } catch (Exception e) {
            e.printStackTrace();
            response = new ResponseEntity<>("Failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return response;
    }
}
