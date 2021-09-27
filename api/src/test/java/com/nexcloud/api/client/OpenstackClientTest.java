package com.nexcloud.api.client;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OpenstackClientTest {

    @Autowired
    private OpenstackClient openstackClient;

    @DisplayName("Authentication Token 획득 테스트. 토큰은 특정 문자열(gAAAAA)로 시작하고 길이는 183자이다.")
    @Test
    public void testGetAuthenticationToken() {
        //given //when
        String authToken = openstackClient.getAuthenticationToken();

        //then
        System.out.println(authToken);
        assertThat(authToken).startsWith("gAAAAA");
        assertThat(authToken).hasSize(183);
    }
}
