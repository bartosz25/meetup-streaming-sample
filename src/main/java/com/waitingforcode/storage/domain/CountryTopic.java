package com.waitingforcode.storage.domain;

import com.datastax.driver.core.LocalDate;
import com.datastax.driver.mapping.annotations.ClusteringColumn;
import com.datastax.driver.mapping.annotations.Column;
import com.datastax.driver.mapping.annotations.PartitionKey;
import com.datastax.driver.mapping.annotations.Table;
import com.google.common.base.MoreObjects;
import com.waitingforcode.domain.*;

import java.util.Collection;
import java.util.stream.Collectors;

@Table(name = "topic_country")
public class CountryTopic {

    @PartitionKey(0)
    @Column(name = "group_country")
    private String groupCountry;

    @PartitionKey(1)
    @Column(name = "event_date")
    private LocalDate eventDate;

    @ClusteringColumn
    @Column(name = "event_name")
    private String eventName;

    @Column(name = "key")
    private String key;

    @Column(name = "event_id")
    private String eventId;

    @Column(name = "member_id")
    private long memberId;

    @Column(name = "member_name")
    private String memberName;

    public String getGroupCountry() {
        return groupCountry;
    }

    public void setGroupCountry(String groupCountry) {
        this.groupCountry = groupCountry;
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

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
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

    public static Collection<CountryTopic> fromMeetup(Meetup meetup) {
        Group group = meetup.getGroup();
        return group.getTopics().stream().map(topic -> convertFromMeetup(meetup, topic))
                .collect(Collectors.toList());
    }

    private static CountryTopic convertFromMeetup(Meetup meetup, Topic topic) {
        Group group = meetup.getGroup();
        Event event = meetup.getEvent();
        Member member = meetup.getMember();
        CountryTopic countryTopic = new CountryTopic();
        countryTopic.setGroupCountry(group.getCountry());
        countryTopic.setEventId(event.getId());
        countryTopic.setEventDate(LocalDate.fromMillisSinceEpoch(event.getTime()));
        countryTopic.setEventName(event.getName());
        countryTopic.setKey(topic.getKeyword());
        countryTopic.setMemberId(member.getId());
        countryTopic.setMemberName(member.getFullName());
        return countryTopic;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("country", groupCountry).add("eventId", eventId)
                .add("eventDate", eventDate).add("eventName", eventName).add("key", key)
                .add("memberId", memberId).add("memberName", memberName).toString();
    }

}
