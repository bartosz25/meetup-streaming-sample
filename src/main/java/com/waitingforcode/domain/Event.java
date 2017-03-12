package com.waitingforcode.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

public class    Event {

    @JsonProperty("event_id")
    private String id;

    @JsonProperty("event_name")
    private String name;

    @JsonProperty("event_url")
    private String url;

    private long time;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id).add("name", name).add("url", url).add("time", time).toString();
    }
}
