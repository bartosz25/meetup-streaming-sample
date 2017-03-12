package com.waitingforcode.domain;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum MeetupResponses {

    COMING("yes"), NOT_COMING("no");

    private final String label;

    MeetupResponses(String label) {
        this.label = label;
    }

    @JsonCreator
    public static MeetupResponses convertFromLabel(String label) {
        for (MeetupResponses response : MeetupResponses.values()) {
            if (response.label .equals(label) || response.name().equals(label)) {
                return response;
            }
        }
        throw new IllegalStateException("Unexpected response entry "+label);
    }

}
