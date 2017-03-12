package com.waitingforcode.domain;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

import java.util.Collection;

public class Group {

    @JsonProperty("group_id")
    private long id;

    @JsonProperty("group_city")
    private String city;

    @JsonProperty("group_country")
    private String country;

    private String name;

    private String state;

    @JsonProperty("group_topics")
    private Collection<Topic> topics;

    @JsonProperty("group_urlname")
    private String urlName;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Collection<Topic> getTopics() {
        return topics;
    }

    public void setTopics(Collection<Topic> topics) {
        this.topics = topics;
    }

    public String getUrlName() {
        return urlName;
    }

    public void setUrlName(String urlName) {
        this.urlName = urlName;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("id", id).add("name", name).add("city", city)
                .add("country", country).add("state", state).add("topics", topics).toString();
    }
}
