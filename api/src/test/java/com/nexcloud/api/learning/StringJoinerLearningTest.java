package com.nexcloud.api.learning;

import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.StringJoiner;

import static org.assertj.core.api.Assertions.assertThat;

public class StringJoinerLearningTest {
    private static final StringJoiner JOINER = new StringJoiner("_");

    @DisplayName("projectName, domainId를 합쳐서 String타입의 key 생성")
    @Test
    public void createKeyTest() {
        //given
        String projectName = "senlin";
        String domainId = "default";

        //when
        String key = JOINER.add(projectName).add(domainId).toString();

        //then
        assertThat(key).hasToString("senlin_default");
    }
}
