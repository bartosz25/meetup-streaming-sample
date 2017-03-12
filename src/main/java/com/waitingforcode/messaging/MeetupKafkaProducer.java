package com.waitingforcode.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.waitingforcode.domain.Meetup;
import com.waitingforcode.json.JsonMapper;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.io.IOException;

public class MeetupKafkaProducer {

    private static final String TOPIC_NAME = "meetups";

    private final KafkaProducer<Long, byte[]> producer;

    public MeetupKafkaProducer() throws IOException {
        producer = new KafkaProducer<>(ProducerConfiguration.load("rvsps_producer"));
    }

    public void sendMeetup(Meetup meetup) throws JsonProcessingException {
        System.out.println("Sending new meetup data " + meetup);
        ProducerRecord<Long, byte[]> record = new ProducerRecord<>(TOPIC_NAME, meetup.getId(),
                JsonMapper.getMapper().writeValueAsBytes(meetup));
        producer.send(record);
        producer.flush();
    }

}
