package com.nexcloud.api.client;

import com.nexcloud.api.openstack.service.OpenstackNodeService;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class NodesTest {

    private final OpenstackNodeService openstackNodeService = new OpenstackNodeService();

    @DisplayName("노드정보를 조회하는 API 테스트")
    @Test
    public void nodesTest() {
        //given //when
        ResponseEntity<String> response = openstackNodeService.getNodes();

        //then
        System.out.println(response.getBody());
        assertThat(response.getBody()).isNotNull();
    }
}
