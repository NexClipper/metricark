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
        ArrayList<String> parameterset1 = new ArrayList<>(2);
        parameterset1.add("admin");
        parameterset1.add("default");
        parameterset1.add("adasdas0000");

        tokenCache.put(parameterset1, openstackClient.getToken(parameterset1.get(0), parameterset1.get(1)));

        //then
        System.out.println(tokenCache.get(parameterset1));
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

        tokenCache.put(parameterset1, openstackClient.getToken(parameterset1[0], parameterset1[1]));

        //then
        System.out.println(tokenCache.get(parameterset1));
    }
}
