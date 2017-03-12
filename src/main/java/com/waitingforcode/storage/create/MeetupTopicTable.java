package com.waitingforcode.storage.create;

import com.datastax.driver.core.DataType;
import com.datastax.driver.core.schemabuilder.SchemaBuilder;

import java.util.Arrays;
import java.util.Collection;

import static com.waitingforcode.storage.KeyspaceInitializer.KEYSPACE;

/**
 * Access patterns:
 * 1. Topic with the most of events
 * 2. The most of topics in given city
 * 3. The most of topics in given country
 */
public class MeetupTopicTable extends Table {

    public static final String NAME = "meetup_topic";

    private static final String CREATE_TABLE_QUERY = SchemaBuilder.createTable(KEYSPACE, NAME)
            .ifNotExists()
            .addPartitionKey("key", DataType.text())
            .addClusteringColumn("event_id", DataType.text())
            .addClusteringColumn("event_date", DataType.date())
            .addColumn("event_name", DataType.text())
            .addColumn("name", DataType.text())
            .addColumn("group_id", DataType.bigint())
            .addColumn("group_name", DataType.text())
            .addColumn("group_city", DataType.text())
            .addColumn("group_country", DataType.text()).getQueryString();

    private static final String CITY_INDEX_QUERY = SchemaBuilder.createIndex(NAME+"_group_city")
            .onTable(KEYSPACE, NAME).andColumn("group_city").getQueryString();

    private static final String COUNTRY_INDEX_QUERY = SchemaBuilder.createIndex(NAME+"_group_country")
            .onTable(KEYSPACE, NAME).andColumn("group_country").getQueryString();

    @Override
    public String createQuery() {
        return CREATE_TABLE_QUERY;
    }

    @Override
    public Collection<String> createIndexQueries() {
        return Arrays.asList(CITY_INDEX_QUERY, COUNTRY_INDEX_QUERY);
    }

}
