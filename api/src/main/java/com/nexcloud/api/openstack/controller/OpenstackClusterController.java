package com.nexcloud.api.openstack.controller;

import com.nexcloud.api.domain.ResponseData;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<ResponseData> getBuildInfo(
            @ApiParam(value = "Project Name (ex) admin", required = true) @QueryParam("projectName") String projectName,
            @ApiParam(value = "Domain ID (ex) default", required = true) @QueryParam("domainId") String domainId,
            @ApiParam(value = "Openstack Endpoint", required = false) @RequestParam(value = "endpoint", required = false) String endpoint
    ) {
        ResponseEntity<ResponseData> response;

        try {
            response = service.accessOpenstack(senlinPort, "/v1/build-info", projectName, domainId, endpoint);
        } catch (Exception e) {
            e.printStackTrace();
            response = service.getErrorResponse();
        }

        return response;
    }

    @ApiOperation("List profiles")
    @ApiResponse(code = 503, message = "Service Unavailable")
    @GetMapping("/profiles")
    public ResponseEntity<ResponseData> getProfilesInfo(
            @ApiParam(value = "Project Name (ex) admin", required = true) @QueryParam("projectName") String projectName,
            @ApiParam(value = "Domain ID (ex) default", required = true) @QueryParam("domainId") String domainId,
            @ApiParam(value = "Openstack Endpoint", required = false) @RequestParam(value = "endpoint", required = false) String endpoint
    ) {
        ResponseEntity<ResponseData> response;

        try {
            response = service.accessOpenstack(senlinPort, "/v1/profiles", projectName, domainId, endpoint);
        } catch (Exception e) {
            e.printStackTrace();
            response = service.getErrorResponse();
        }

        return response;
    }

    @ApiOperation("Show profile details")
    @ApiResponse(code = 503, message = "Service Unavailable")
    @GetMapping("/profiles/{profileId}")
    public ResponseEntity<ResponseData> getProfileDetail(
            @ApiParam(value = "Profile ID", required = true) @PathVariable String profileId,
            @ApiParam(value = "Project Name (ex) admin", required = true) @QueryParam("projectName") String projectName,
            @ApiParam(value = "Domain ID (ex) default", required = true) @QueryParam("domainId") String domainId,
            @ApiParam(value = "Openstack Endpoint", required = false) @RequestParam(value = "endpoint", required = false) String endpoint
    ) {
        ResponseEntity<ResponseData> response;

        try {
            response = service.accessOpenstack(senlinPort, String.format("/v1/profiles/%s", profileId), projectName, domainId, endpoint);
        } catch (Exception e) {
            e.printStackTrace();
            response = service.getErrorResponse();
        }

        return response;
    }

    @ApiOperation("List clusters")
    @ApiResponse(code = 503, message = "Service Unavailable")
    @GetMapping("/clusters")
    public ResponseEntity<ResponseData> getClusters(
            @ApiParam(value = "Project Name (ex) admin", required = true) @QueryParam("projectName") String projectName,
            @ApiParam(value = "Domain ID (ex) default", required = true) @QueryParam("domainId") String domainId,
            @ApiParam(value = "Openstack Endpoint", required = false) @RequestParam(value = "endpoint", required = false) String endpoint
    ) {
        ResponseEntity<ResponseData> response;

        try {
            response = service.accessOpenstack(senlinPort, "/v1/clusters", projectName, domainId, endpoint);
        } catch (Exception e) {
            e.printStackTrace();
            response = service.getErrorResponse();
        }

        return response;
    }

    @ApiOperation("Show cluster details")
    @ApiResponse(code = 503, message = "Service Unavailable")
    @GetMapping("/clusters/{clusterId}")
    public ResponseEntity<ResponseData> getClusterDetail(
            @ApiParam(value = "Cluster ID", required = true) @PathVariable String clusterId,
            @ApiParam(value = "Project Name (ex) admin", required = true) @QueryParam("projectName") String projectName,
            @ApiParam(value = "Domain ID (ex) default", required = true) @QueryParam("domainId") String domainId,
            @ApiParam(value = "Openstack Endpoint", required = false) @RequestParam(value = "endpoint", required = false) String endpoint
    ) {
        ResponseEntity<ResponseData> response;

        try {
            response = service.accessOpenstack(senlinPort, String.format("/v1/clusters/%s", clusterId), projectName, domainId, endpoint);
        } catch (Exception e) {
            e.printStackTrace();
            response = service.getErrorResponse();
        }

        return response;
    }

    @ApiOperation("Collect Attributes Across a Cluster")
    @ApiResponses({
            @ApiResponse(code = 202, message = "Accepted"),
            @ApiResponse(code = 503, message = "Service Unavailable")
    })
    @GetMapping("/clusters/{clusterId}/attrs/{path}")
    public ResponseEntity<ResponseData> getAttributes(
            @ApiParam(value = "Cluster ID", required = true) @PathVariable String clusterId,
            @ApiParam(value = "Path", required = true) @PathVariable String path,
            @ApiParam(value = "Project Name (ex) admin", required = true) @QueryParam("projectName") String projectName,
            @ApiParam(value = "Domain ID (ex) default", required = true) @QueryParam("domainId") String domainId,
            @ApiParam(value = "Openstack Endpoint", required = false) @RequestParam(value = "endpoint", required = false) String endpoint
    ) {
        ResponseEntity<ResponseData> response;

        try {
            response = service.accessOpenstack(senlinPort, String.format("/v1/clusters/%s/attrs/%s", clusterId, path), projectName, domainId, endpoint);
        } catch (Exception e) {
            e.printStackTrace();
            response = service.getErrorResponse();
        }

        return response;
    }
}
