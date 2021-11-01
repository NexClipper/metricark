package com.nexcloud.api.vmcheck.controller;

import com.nexcloud.api.vmcheck.service.VmService;
import io.swagger.annotations.ApiOperation;
import org.json.simple.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VmController {

    private final VmService service;

    @Autowired
    private VmController(VmService service) {
        this.service = service;
    }

    @ApiOperation("List VMs")
    @GetMapping("vms")
    public ResponseEntity<JSONArray> getVmList() {
        return service.getVmList("openstack_nova_server_local_gb");
    }
}
