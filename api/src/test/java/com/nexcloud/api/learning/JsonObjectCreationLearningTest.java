package com.nexcloud.api.learning;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.JsonNodeFactory;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.Test;

public class JsonObjectCreationLearningTest {
    JsonNodeFactory nodeFactory = JsonNodeFactory.instance;

    @Test
    public void makeJsonRequestBodyTest() {
        // OpenStack Authentication token 요청 시 POST 요청에 담아야 하는 JSON 객체 생성하기

        ObjectNode domainID = nodeFactory.objectNode();
        domainID.put("id", "default");
        // domain

        ObjectNode userObj = nodeFactory.objectNode();
        userObj.set("domain", domainID);
        userObj.put("name", "admin");
        userObj.put("password", "0000");

        ObjectNode user = nodeFactory.objectNode();
        user.set("user", userObj);
        // user

        ArrayNode methodsObj = nodeFactory.arrayNode();
        methodsObj.add("password");
        // password

        ObjectNode methods = nodeFactory.objectNode();
        methods.set("methods", methodsObj);
        // identity methods

        ObjectNode identity = nodeFactory.objectNode();
        identity.set("methods", methodsObj);
        identity.set("password", user);
        // identity

        // --------------------------------------------------

        ObjectNode projectDomainId = nodeFactory.objectNode();
        projectDomainId.put("id", "default");
        // project domain

        ObjectNode projectObj = nodeFactory.objectNode();
        projectObj.set("domain", projectDomainId);
        projectObj.put("name", "admin");
        // project

        ObjectNode scope = nodeFactory.objectNode();
        scope.set("project", projectObj);
        // scope

        // --------------------------------------------------

        ObjectNode authObj = nodeFactory.objectNode();
        authObj.set("identity", identity);
        authObj.set("scope", scope);

        ObjectNode auth = nodeFactory.objectNode();
        auth.set("auth", authObj);
        // auth

        System.out.println(auth);
    }

    @Test
    public void makeDomainScopedJsonRequestBodyTest() {
        // OpenStack Authentication token 요청 시 POST 요청에 담아야 하는 JSON 객체 생성하기

        ObjectNode domainID = nodeFactory.objectNode();
        domainID.put("id", "default");
        // domain

        ObjectNode userObj = nodeFactory.objectNode();
        userObj.set("domain", domainID);
        userObj.put("name", "admin");
        userObj.put("password", "0000");

        ObjectNode user = nodeFactory.objectNode();
        user.set("user", userObj);
        // user

        ArrayNode methodsObj = nodeFactory.arrayNode();
        methodsObj.add("password");
        // password

        ObjectNode methods = nodeFactory.objectNode();
        methods.set("methods", methodsObj);
        // identity methods

        ObjectNode identity = nodeFactory.objectNode();
        identity.set("methods", methodsObj);
        identity.set("password", user);
        // identity

        // --------------------------------------------------

        ObjectNode projectDomainId = nodeFactory.objectNode();
        projectDomainId.put("id", "default");
        // project domain

        ObjectNode scope = nodeFactory.objectNode();
        scope.set("domain", projectDomainId);
        // scope

        // --------------------------------------------------

        ObjectNode authObj = nodeFactory.objectNode();
        authObj.set("identity", identity);
        authObj.set("scope", scope);

        ObjectNode auth = nodeFactory.objectNode();
        auth.set("auth", authObj);
        // auth

        System.out.println(auth);
    }
}
