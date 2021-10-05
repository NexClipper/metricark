package com.nexcloud.api.domain;

import java.util.Objects;

public class OpenstackTokenCacheKey {

    private final String projectName;
    private final String domainId;

    public OpenstackTokenCacheKey(String projectName, String domainId) {
        this.projectName = projectName;
        this.domainId = domainId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OpenstackTokenCacheKey that = (OpenstackTokenCacheKey) o;
        return projectName.equals(that.projectName) && domainId.equals(that.domainId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(projectName, domainId);
    }
}
