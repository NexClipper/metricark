package com.nexcloud.api.client;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class OpenstackClientTest {

    @Autowired
    private OpenstackClient openstackclient;

    private static final int RETRY = 5;

    @DisplayName("동일한 url 호출은 오픈스택 API로 요청이 전달되지 않는다")
    @Test
    public void duplicatedUrlTest() {
        //given
        int neutronApiCounter = 0;
        int novaApiCounter = 0;

        //when
        // neutronapi 5번 호출
        for (int i = 0; i < RETRY; i++) {
            if (openstackclient.executeHttpRequest("http://192.168.1.14:9696/v2.0/networks", "admin", "default").getStatusCode().equals(HttpStatus.OK)) {
                neutronApiCounter++;
            }
        }


        // novaapi 5번 호출
        for (int i = 0; i < RETRY; i++) {
            if(openstackclient.executeHttpRequest("http://192.168.1.14:80/compute/v2.1/servers/detail", "admin", "default").getStatusCode().equals(HttpStatus.OK)) {
                novaApiCounter++;
            }
        }


        //then
        assertThat(neutronApiCounter).isLessThan(RETRY);
        System.out.println("neutronApiCounter: " + neutronApiCounter);
        assertThat(novaApiCounter).isLessThan(RETRY);
        System.out.println("novaApiCounter: " + novaApiCounter);

    }


}
