package com.waitingforcode.storage.read;

import com.datastax.driver.core.LocalDate;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Statement;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.querybuilder.Select;
import com.datastax.driver.mapping.Result;
import com.waitingforcode.storage.create.MeetupTopicTable;
import com.waitingforcode.storage.domain.MeetupTopic;

import java.util.Collection;

import static com.datastax.driver.core.querybuilder.QueryBuilder.eq;
import static com.waitingforcode.storage.Cassandra.meetupTopicMapper;
import static com.waitingforcode.storage.Cassandra.session;
import static com.waitingforcode.storage.KeyspaceInitializer.KEYSPACE;

public final class MeetupTopicReader {

    private MeetupTopicReader() {
        // prevents init
    }

    public static Collection<MeetupTopic> getMeetupsByTopic(String topicKey) {
        Statement selectQuery = QueryBuilder.select()
                .from(KEYSPACE, MeetupTopicTable.NAME)
                .where(eq("key", topicKey))
                .setFetchSize(5000);

        ResultSet results = session().execute(selectQuery);
        Result<MeetupTopic> mappedResult = meetupTopicMapper().map(results);
        return mappedResult.all();
    }

    public static Collection<MeetupTopic> getDistinctTopics() {
        Select selectQuery = QueryBuilder.select().distinct().column("key")
                .from(KEYSPACE, MeetupTopicTable.NAME);

        ResultSet results = session().execute(selectQuery);
        Result<MeetupTopic> mappedResult = meetupTopicMapper().map(results);
        return mappedResult.all();
    }

    public static Collection<MeetupTopic> getMeetupsForTopicPerDay(String topicKey, LocalDate day) {
        Select.Where selectQuery = QueryBuilder.select().from(KEYSPACE, MeetupTopicTable.NAME)
                .where(eq("key", topicKey)).and(eq("event_date", day));

        ResultSet results = session().execute(selectQuery);
        Result<MeetupTopic> mappedResult = meetupTopicMapper().map(results);
        return mappedResult.all();
    }

}
