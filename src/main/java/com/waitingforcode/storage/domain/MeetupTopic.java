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
import com.waitingforcode.domain.Topic;

import java.util.Collection;
import java.util.stream.Collectors;

@Table(name = "meetup_topic")
public class MeetupTopic {

    @PartitionKey
    @Column(name = "key")
    private String key;

    @ClusteringColumn(0)
    @Column(name = "event_id")
    private String eventId;

    @ClusteringColumn(1)
    @Column(name = "event_date")
    private LocalDate eventDate;

    @Column(name = "event_name")
    private String eventName;

    @Column(name = "name")
    private String name;

    @Column(name = "group_id")
    private long groupId;

    @Column(name = "group_name")
    private String groupName;

    @Column(name = "group_city")
    private String groupCity;

    @Column(name = "group_country")
    private String groupCountry;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public static Collection<MeetupTopic> fromMeetup(Meetup meetup) {
        Group group = meetup.getGroup();
        return group.getTopics().stream().map(topic -> convertTopic(meetup, topic))
                .collect(Collectors.toList());
    }

    private static MeetupTopic convertTopic(Meetup meetup, Topic topic) {
        Event event = meetup.getEvent();
        Group group = meetup.getGroup();
        MeetupTopic meetupTopic = new MeetupTopic();
        meetupTopic.setEventId(event.getId());
        meetupTopic.setEventName(event.getName());
        meetupTopic.setEventDate(LocalDate.fromMillisSinceEpoch(event.getTime()));
        meetupTopic.setGroupId(group.getId());
        meetupTopic.setGroupName(group.getName());
        meetupTopic.setGroupCity(group.getCity());
        meetupTopic.setGroupCountry(group.getCountry());
        meetupTopic.setKey(topic.getKeyword());
        meetupTopic.setName(topic.getName());
        return meetupTopic;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).add("key", key).add("name", name)
                .add("eventId", eventId).add("eventName", eventName).add("eventDate", eventDate)
                .add("groupId", groupId).add("groupCity", groupCity).add("groupCountry", groupCountry)
                .toString();
    }

}
