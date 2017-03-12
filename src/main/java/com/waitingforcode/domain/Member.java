package com.waitingforcode.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

public class Member {

    @JsonProperty("member_id")
    private long id;

    @JsonProperty("member_name")
    private String fullName;

    @JsonProperty("photo")
    private String photo;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("id", id).add("fullName", fullName)
                .add("photo", photo).toString();
    }
}
