package com.nexcloud.api.openstack.service;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OpenstackClusterServiceTest {

    @Autowired
    OpenstackClusterService openstackClusterService;

    @DisplayName("클러스터 정보를 조회하는 API 테스트")
    @Test
    public void name() {
        //given //when
        ResponseEntity<String> response = openstackClusterService.getClusters();

        //then
        System.out.println(response.getBody());
        assertThat(response.getBody()).isNotNull();

    }
}
