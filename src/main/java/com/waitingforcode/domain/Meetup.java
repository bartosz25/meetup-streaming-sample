package com.waitingforcode.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

public class Meetup {

    /**
     * Unique numeric identifier
     */
    @JsonProperty("rsvp_id")
    private long id;

    /**
     * Venue information - appears only if
     * meetup is public
     */
    private Venue venue;

    private String visibility;

    private Event event;

    private Group group;

    @JsonProperty("guests")
    private int broughtMembers;

    private MeetupResponses response;

    private Member member;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public int getBroughtMembers() {
        return broughtMembers;
    }

    public void setBroughtMembers(int broughtMembers) {
        this.broughtMembers = broughtMembers;
    }

    public MeetupResponses getResponse() {
        return response;
    }

    public void setResponse(MeetupResponses response) {
        this.response = response;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Venue getVenue() {
        return venue;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }

    public String getVisibility() {
        return visibility;
    }

    public void setVisibility(String visibility) {
        this.visibility = visibility;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("id", id).add("venue", venue).add("visibility", visibility)
                .add("event", event).add("group", group).add("broughtMembers", broughtMembers)
                .add("response", response).add("member", member)
                .toString();
    }
}
