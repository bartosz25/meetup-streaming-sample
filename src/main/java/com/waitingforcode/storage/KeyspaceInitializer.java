package com.waitingforcode.storage;

import com.datastax.driver.core.KeyspaceMetadata;
import com.datastax.driver.core.schemabuilder.SchemaBuilder;
import com.waitingforcode.storage.create.CountryTopicTable;
import com.waitingforcode.storage.create.MeetupDayTable;
import com.waitingforcode.storage.create.MeetupTopicTable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.datastax.driver.core.schemabuilder.SchemaBuilder.createKeyspace;
import static com.waitingforcode.storage.Cassandra.cluster;
import static com.waitingforcode.storage.Cassandra.session;

public final class KeyspaceInitializer {

    public static final String KEYSPACE = "meetup";

    private KeyspaceInitializer() {
        // prevents init
    }

    public static void initialize() {
        System.out.println("Preparing Keyspace...");
        List<KeyspaceMetadata> keyspaces = cluster().getMetadata().getKeyspaces();
        boolean keyspaceExists = keyspaces.stream().filter(keyspace -> keyspace.getName().equals(KEYSPACE)).findFirst().isPresent();
        //KeyspaceMetadata keyspace = cluster().getMetadata().getKeyspaces(KEYSPACE);
        if (keyspaceExists) {
            String dropQuery = SchemaBuilder.dropKeyspace(KEYSPACE).ifExists().getQueryString();
            session().execute(dropQuery);
        }
        Map<String, Object> replicationOptions = new HashMap<>();
        replicationOptions.put("class", "SimpleStrategy");
        replicationOptions.put("replication_factor", "1");
        String createKeyspaceQuery = createKeyspace(KEYSPACE).with().replication(replicationOptions).getQueryString();
            session().execute(createKeyspaceQuery);
        session().execute("USE " + KEYSPACE);
        MeetupDayTable meetupDayTable = new MeetupDayTable();
        MeetupTopicTable meetupTopicTable = new MeetupTopicTable();
        CountryTopicTable countryTopicTable = new CountryTopicTable();

        // meetup_day
        session().execute(meetupDayTable.createQuery());
        meetupDayTable.createIndexQueries().forEach(session()::execute);
        // meetup_topic
        session().execute(meetupTopicTable.createQuery());
        meetupTopicTable.createIndexQueries().forEach(session()::execute);
        // topic_country
        session().execute(countryTopicTable.createQuery());
        countryTopicTable.createIndexQueries().forEach(session()::execute);


    }

}
