package com.nexcloud.api.client;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class AuthenticationTokenTest {

    private final OpenstackClient openStackClient = new OpenstackClient();

    @DisplayName("Authentication Token 획득 테스트. 토큰은 특정 문자열(gAAAAA)로 시작하고 길이는 183자이다.")
    @Test
    public void testGetAuthenticationToken() {
        //given //when
        String authToken = openStackClient.getAuthenticationToken();

        //then
        System.out.println(authToken);
        assertThat(authToken).startsWith("gAAAAA");
        assertThat(authToken).hasSize(183);
    }
}
