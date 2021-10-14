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
public class OpenstackNodeController {

    private static final Logger LOGGER = LoggerFactory.getLogger(OpenstackNodeController.class);

    @Value("${openstack.senlinport}")
    private String senlinPort;

    private final OpenstackService service;

    @Autowired
    public OpenstackNodeController(OpenstackService service) {
        this.service = service;
    }


    @ApiOperation(value = "Nodes Info", httpMethod = "GET", notes = "Nodes Info")
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
    @RequestMapping(value = "/nodes")
    public ResponseEntity<String> getNodes(
            @QueryParam("projectName") String projectName,
            @QueryParam("domainId") String domainId
    ) {
        ResponseEntity<String> response;

        try {
            response = service.accessOpenstack(senlinPort, "/v1/nodes", projectName, domainId);
        } catch (HttpClientErrorException he) {
            response = new ResponseEntity<>("Client error", he.getStatusCode());
        } catch (Exception e) {
            e.printStackTrace();
            response = new ResponseEntity<>("Failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return response;
    }

    @ApiOperation(value = "Node Detail Info", httpMethod = "GET", notes = "Node Detail Info")
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
    @RequestMapping(value = "/nodes/{nodeId}")
    public ResponseEntity<String> getNodeDetail(
            @PathVariable String nodeId,
            @QueryParam("projectName") String projectName,
            @QueryParam("domainId") String domainId
    ) {
        ResponseEntity<String> response;

        try {
            response = service.accessOpenstack(senlinPort, String.format("/v1/nodes/%s", nodeId), projectName, domainId);
        } catch (HttpClientErrorException he) {
            response = new ResponseEntity<>("Client error", he.getStatusCode());
        } catch (Exception e) {
            e.printStackTrace();
            response = new ResponseEntity<>("Failed", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return response;
    }
}
