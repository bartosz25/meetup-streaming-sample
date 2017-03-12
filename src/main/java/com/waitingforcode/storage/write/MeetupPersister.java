package com.waitingforcode.storage.write;

import com.waitingforcode.domain.Meetup;
import com.waitingforcode.storage.domain.CountryTopic;
import com.waitingforcode.storage.domain.MeetupDay;
import com.waitingforcode.storage.domain.MeetupTopic;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.VoidFunction;

import static com.waitingforcode.json.JsonMapper.getMapper;
import static com.waitingforcode.storage.Cassandra.countryTopicMapper;
import static com.waitingforcode.storage.Cassandra.meetupDayMapper;
import static com.waitingforcode.storage.Cassandra.meetupTopicMapper;

public enum MeetupPersister implements VoidFunction<JavaRDD<ConsumerRecord<Long, byte[]>>> {

    INSTANCE;

    @Override
    public void call(JavaRDD<ConsumerRecord<Long, byte[]>> bytesDataRdd) throws Exception {
        System.out.println("Got new RDD");
        JavaRDD<Meetup> meetupRdd = bytesDataRdd.map(entry -> getMapper().readValue(entry.value(), Meetup.class));

        meetupRdd.foreach(this::saveInCassandra);
    }

    private void saveInCassandra(Meetup meetup) {
        System.out.println("Saving meetup " + meetup);
        // Mappers are thread-safe and are singletons
        // Under-the-hood MappingManager caches them
        MeetupTopic.fromMeetup(meetup).forEach(meetupTopicMapper()::save);

        meetupDayMapper().save(MeetupDay.fromMeetup(meetup));

        CountryTopic.fromMeetup(meetup).forEach(countryTopicMapper()::save);
    }
}
