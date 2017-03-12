package com.waitingforcode.storage.create;

import com.datastax.driver.core.DataType;
import com.datastax.driver.core.schemabuilder.SchemaBuilder;

import java.util.Arrays;
import java.util.Collection;

import static com.waitingforcode.storage.KeyspaceInitializer.KEYSPACE;

/**
 * Access patterns:
 * 1. Topics per country  (group_country = ?)
 * 2. Events per country and day (group_country = ? AND event_date = ?)
 */
public class CountryTopicTable extends Table {

    // TODO : rename to country_topic
    public static final String NAME = "topic_country";

    private static final String CREATE_TABLE_QUERY = SchemaBuilder.createTable(KEYSPACE, NAME)
            .ifNotExists()
            .addPartitionKey("group_country", DataType.text())
            .addPartitionKey("event_date", DataType.date())
            .addClusteringColumn("event_name", DataType.text())
            .addColumn("key", DataType.text())
            .addColumn("event_id", DataType.text())
            .addColumn("member_id", DataType.bigint())
            .addColumn("member_name", DataType.text())
            .withOptions().memtableFlushPeriodInMillis(3000).getQueryString();

    // According to that, https://docs.datastax.com/en/cql/3.3/cql/cql_using/useSecondaryIndex.html
    // We can read events by date thanks to secondary index without defining group_country
    // And the same for reading by country without defining event_date
    private static final String CREATE_EVENT_DATE_INDEX = SchemaBuilder.createIndex(NAME +"_event_date")
            .onTable(KEYSPACE, NAME).andColumn("event_date").getQueryString();
    private static final String CREATE_GROUP_COUNTRY_INDEX = SchemaBuilder.createIndex(NAME +"_group_country")
            .onTable(KEYSPACE, NAME).andColumn("group_country").getQueryString();


    @Override
    public String createQuery() {
        return CREATE_TABLE_QUERY;
    }

    @Override
    public Collection<String> createIndexQueries() {
        return Arrays.asList(CREATE_EVENT_DATE_INDEX, CREATE_GROUP_COUNTRY_INDEX);
    }


}
