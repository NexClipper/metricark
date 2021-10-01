package com.nexcloud.api.client;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BifunctionLearningTest {

    @Autowired
    private OpenstackClient openstackClient;

    @DisplayName("Bifunction Test")
    @Test
    public void bifunctionTest() {
        //given //when
        BiFunction<String, String, String> fun1 = (projectName, domainId) -> openstackClient.getAuthenticationToken(projectName, domainId);

        //then
        String result = fun1.apply("admin", "default");
        System.out.println(result);
        assertThat(result).startsWith("gAAAAA");
        assertThat(result).hasSize(183);
    }

    @DisplayName("data structure test")
    @Test
    public void dataStructureTest(){
        //given
        Map<ArrayList<String>, String> tokenCache = new HashMap<>();
        //when
        ArrayList<String> authenticationInfo = new ArrayList<>();
        authenticationInfo.add("admin");
        authenticationInfo.add("default");

        tokenCache.put(authenticationInfo, openstackClient.checkTokenCacheAndGetToken(authenticationInfo.get(0), authenticationInfo.get(1)));

        //then
        System.out.println(tokenCache.get(authenticationInfo));
        assertThat(tokenCache.get(new ArrayList<String>())).isNull();
    }

    @DisplayName("data structure test2")
    @Test
    public void dataStructureTest2(){
        //given
        Map<String[], String> tokenCache = new HashMap<>();
        //when
        String[] parameterset1 = new String[2];
        parameterset1[0] = "admin";
        parameterset1[1] = "default";

        tokenCache.put(parameterset1, openstackClient.checkTokenCacheAndGetToken(parameterset1[0], parameterset1[1]));

        //then
        System.out.println(tokenCache.get(parameterset1));
    }

    @DisplayName("토큰 저장 테스트")
    @Test
    public void test1() {
        //given
        Map<ArrayList<String>, String> tokenCache = new HashMap<>();

        //when
        tokenCache.put(getTokenCacheKey("pn1", "di1"), "key1");
        tokenCache.put(getTokenCacheKey("pn2", "di2"), "key2");

        //then
        assertThat(tokenCache.get(getTokenCacheKey("pn1", "di1"))).hasToString("key1");
        assertThat(tokenCache.get(getTokenCacheKey("pn2", "di2"))).hasToString("key2");
    }

    private ArrayList<String> getTokenCacheKey(String projectName, String domainId) {
        ArrayList<String> tokenCacheKey = new ArrayList<>();
        tokenCacheKey.add(projectName);
        tokenCacheKey.add(domainId);

        return tokenCacheKey;
    }
}
