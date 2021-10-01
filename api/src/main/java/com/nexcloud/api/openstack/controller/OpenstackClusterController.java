package com.nexcloud.api.openstack.controller;

import com.nexcloud.api.openstack.service.OpenstackService;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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


    @ApiOperation(value = "Clusters Info", httpMethod = "GET", notes = "Clusters Info")
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
    @RequestMapping(value = "/clusters")
    public ResponseEntity<String> getClusters(
            @QueryParam("projectName") String projectName,
            @QueryParam("domainId") String domainId
    ) {
        ResponseEntity<String> response;

        try {
            response = service.accessOpenstack(senlinPort, "/v1/clusters", projectName, domainId);
        } catch (HttpClientErrorException he) {
            response = new ResponseEntity<>("Failed", he.getStatusCode());
        } catch (Exception e) {
            e.printStackTrace();
            response = new ResponseEntity<>("Failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return response;
    }

    @ApiOperation(value = "Cluster Detail Info", httpMethod = "GET", notes = "Cluster Detail Info")
    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "clusterId",
                    value = "Cluster ID (ex) 1",
                    required = true,
                    dataType = "string",
                    paramType = "path"
            ),
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
    @RequestMapping(value = "/clusters/{clusterId}")
    public ResponseEntity<String> getClusterDetail(
            @PathVariable String clusterId,
            @QueryParam("projectName") String projectName,
            @QueryParam("domainId") String domainId
    ) {
        ResponseEntity<String> response;

        try {
            response = service.accessOpenstack(senlinPort, String.format("/v1/clusters/%s", clusterId), projectName, domainId);
        } catch (HttpClientErrorException he) {
            response = new ResponseEntity<>("Failed", he.getStatusCode());
        } catch (Exception e) {
            e.printStackTrace();
            response = new ResponseEntity<>("Failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return response;
    }

    @ApiOperation(value = "Build Info", httpMethod = "GET", notes = "Build Info")
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
    @RequestMapping(value = "/build-info")
    public ResponseEntity<String> getBuildInfo(
            @QueryParam("projectName") String projectName,
            @QueryParam("domainId") String domainId
    ) {
        ResponseEntity<String> response;

        try {
            response = service.accessOpenstack(senlinPort, "/v1/build-info", projectName, domainId);
        } catch (Exception e) {
            e.printStackTrace();
            response = new ResponseEntity<>("failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return response;
    }

    @ApiOperation(value = "Profiles Info", httpMethod = "GET", notes = "Profiles Info")
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
    @RequestMapping(value = "/profiles")
    public ResponseEntity<String> getProfilesInfo(
            @QueryParam("projectName") String projectName,
            @QueryParam("domainId") String domainId
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

    @ApiOperation(value = "Profile Detail Info", httpMethod = "GET", notes = "Profile Detail Info")
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
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 503, message = "Service Unavailable")
    })
    @RequestMapping(value = "/profiles/{profileId}")
    public ResponseEntity<String> getProfileDetail(
            @PathVariable String profileId,
            @QueryParam("projectName") String projectName,
            @QueryParam("domainId") String domainId
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

    @ApiOperation(value = "Collect Attributes Across a Cluster", httpMethod = "GET", notes = "Collect Attributes Across a Cluster")
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
            @ApiResponse(code = 202, message = "Accepted"),
            @ApiResponse(code = 400, message = "Bad Request"),
            @ApiResponse(code = 401, message = "Unauthorized"),
            @ApiResponse(code = 403, message = "Forbidden"),
            @ApiResponse(code = 404, message = "Not Found"),
            @ApiResponse(code = 500, message = "Internal Server Error"),
            @ApiResponse(code = 503, message = "Service Unavailable")
    })
    @RequestMapping(value = "/clusters/{clusterId}/attrs/{path}")
    public ResponseEntity<String> getProfileDetail(
            @PathVariable String clusterId,
            @PathVariable String path,
            @QueryParam("projectName") String projectName,
            @QueryParam("domainId") String domainId
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
