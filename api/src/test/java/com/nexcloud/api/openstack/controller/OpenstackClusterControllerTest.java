package com.nexcloud.api.openstack.controller;

import com.nexcloud.api.domain.ResponseData;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OpenstackClusterControllerTest {

    @Autowired
    OpenstackClusterController openstackClusterController;

    @DisplayName("클러스터 정보를 조회하는 API 테스트")
    @Test
    public void getClusterSuccessTest() {
        //given //when
        ResponseEntity<ResponseData> response = openstackClusterController.getClusters("admin", "default", "http://192.168.1.14");

        //then
        System.out.println(response.getBody());
        assertThat(response.getBody()).isNotNull();
    }

    @DisplayName("클러스터 정보를 조회하는 API 실패 테스트")
    @Test
    public void getClusterFailureTest() {
        //given //when
        ResponseEntity<ResponseData> response = openstackClusterController.getClusters("wrong", "wrong", "http://192.168.1.14");

        //then
        assertThat(response.getBody()).hasToString("Client error");
    }
}
