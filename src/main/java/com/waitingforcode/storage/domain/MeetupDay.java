package com.waitingforcode.storage.domain;

import com.datastax.driver.core.LocalDate;
import com.datastax.driver.mapping.annotations.ClusteringColumn;
import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;
import com.google.common.base.MoreObjects;
import com.waitingforcode.domain.Event;
import com.waitingforcode.domain.Group;
import com.waitingforcode.domain.Meetup;
import com.waitingforcode.domain.Member;

@Table(name = "meetup_day")
public class MeetupDay {

    @PartitionKey
    @Column(name = "event_date")
    private LocalDate eventDate;

    @ClusteringColumn
    @Column(name = "event_name")
    private String eventName;

    @Column(name = "event_id")
    private String eventId;

    @Column(name = "member_id")
    private long memberId;

    @Column(name = "member_name")
    private String memberName;

    @Column(name = "group_id")
    private long groupId;

    @Column(name = "group_name")
    private String groupName;

    @Column(name = "group_city")
    private String groupCity;

    @Column(name = "group_country")
    private String groupCountry;

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public LocalDate getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDate eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public long getMemberId() {
        return memberId;
    }

    public void setMemberId(long memberId) {
        this.memberId = memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public long getGroupId() {
        return groupId;
    }

    public void setGroupId(long groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupCity() {
        return groupCity;
    }

    public void setGroupCity(String groupCity) {
        this.groupCity = groupCity;
    }

    public String getGroupCountry() {
        return groupCountry;
    }

    public void setGroupCountry(String groupCountry) {
        this.groupCountry = groupCountry;
    }

    public static MeetupDay fromMeetup(Meetup meetup) {
        Event event = meetup.getEvent();
        Group group = meetup.getGroup();
        Member member = meetup.getMember();
        MeetupDay meetupDay = new MeetupDay();
        meetupDay.setEventId(event.getId());
        meetupDay.setEventName(event.getName());
        meetupDay.setEventDate(LocalDate.fromMillisSinceEpoch(event.getTime()));
        meetupDay.setGroupId(group.getId());
        meetupDay.setGroupCity(group.getCity());
        meetupDay.setGroupCountry(group.getCountry());
        meetupDay.setMemberId(member.getId());
        meetupDay.setMemberName(member.getFullName());
        return meetupDay;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("eventId", eventId).add("eventName", eventName).add("eventDate", eventDate)
                .add("groupId", groupId).add("groupCity", groupCity).add("groupCountry", groupCountry)
                .add("memberId", memberId).add("memberName", memberName).toString();
    }

}
