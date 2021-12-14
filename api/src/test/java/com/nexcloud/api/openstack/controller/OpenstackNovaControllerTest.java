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

@SpringBootTest
@RunWith(SpringRunner.class)
public class OpenstackNovaControllerTest {

    @Autowired
    private OpenstackNovaController controller;

    @DisplayName("엔드포인트 입력하지 않을 시 application.properties에 설정된 엔드포인트로 요청한다")
    @Test
    public void novaControllerTestWithoutEndpoint() {
        //given
        ResponseEntity<ResponseData> response = controller.getServersDetail("admin", "default", null);
        //when

        //then

        System.out.println(response.getBody().getData());
        assertThat(response.getBody().getData()).isNotNull();
    }

    @DisplayName("유효한 엔드포인트를 입력할 시 유효한 응답값을 받아온다")
    @Test
    public void novaControllerTestWithValidEndpoint() {
        //given
        ResponseEntity<ResponseData> response = controller.getServersDetail("admin", "default", "http://192.168.1.14");
        //when

        //then

        System.out.println(response.getBody().getData());
        assertThat(response.getBody().getData()).isNotNull();
    }

    @DisplayName("유효하지 않은 엔드포인트를 입력할 시 오작동한다")
    @Test
    public void novaControllerTestWithInvalidEndpoint() {
        //given
        ResponseEntity<ResponseData> response = controller.getServersDetail("admin", "default", "http://WrongEndpoint");
        //when

        //then

        System.out.println(response.getBody().getData());
        assertThat(response.getBody().getData()).isNull();
    }


}
