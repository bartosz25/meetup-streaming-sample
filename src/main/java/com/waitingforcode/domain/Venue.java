package com.waitingforcode.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

public class Venue {

    @JsonProperty("venue_id")
    private long id;

    @JsonProperty("venue_name")
    private String name;

    @JsonProperty("lon")
    private double longitude;

    @JsonProperty("lat")
    private double latitude;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("id", id).add("name", name).add("lon/lat", longitude+"/"+latitude)
                .toString();
    }
}
