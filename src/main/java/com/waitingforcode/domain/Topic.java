package com.waitingforcode.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

public class Topic {

    @JsonProperty("topic_name")
    private String name;

    @JsonProperty("urlkey")
    private String keyword;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("name", name).add("keyword", keyword).toString();
    }
}
