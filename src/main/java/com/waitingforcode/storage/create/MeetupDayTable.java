package com.waitingforcode.storage.create;

import com.datastax.driver.core.DataType;
import com.datastax.driver.core.schemabuilder.SchemaBuilder;

import java.util.Arrays;
import java.util.Collection;

import static com.waitingforcode.storage.KeyspaceInitializer.KEYSPACE;

/**
 * Access patterns:
 * 1. Meetup by day
 * 2. Find all meetups
 * 3. Find all meetups by:
 *   3.1. Member
 *   3.2. Group
 *   3.3. City
 */
public class MeetupDayTable extends Table {

    public static final String NAME = "meetup_day";

    private static final String CREATE_TABLE_QUERY = SchemaBuilder.createTable(KEYSPACE, NAME)
            .ifNotExists()
            .addPartitionKey("event_date", DataType.date())
            .addClusteringColumn("event_name", DataType.text())
            .addColumn("event_id", DataType.text())
            .addColumn("member_id", DataType.bigint())
            .addColumn("member_name", DataType.text())
            .addColumn("group_id", DataType.bigint())
            .addColumn("group_name", DataType.text())
            .addColumn("group_city", DataType.text())
            .addColumn("group_country", DataType.text())
            .withOptions().memtableFlushPeriodInMillis(3000).getQueryString();

    private static final String CREATE_MEMBER_INDEX_QUERY = SchemaBuilder.createIndex(NAME +"_member")
            .onTable(KEYSPACE, NAME).andColumn("member_id").getQueryString();

    private static final String CREATE_GROUP_INDEX_QUERY = SchemaBuilder.createIndex(NAME +"_group")
            .onTable(KEYSPACE, NAME).andColumn("group_id").getQueryString();

    private static final String CREATE_CITY_INDEX_QUERY = SchemaBuilder.createIndex(NAME +"_city")
            .onTable(KEYSPACE, NAME).andColumn("group_city").getQueryString();

    @Override
    public String createQuery() {
        return CREATE_TABLE_QUERY;
    }

    @Override
    public Collection<String> createIndexQueries() {
        return Arrays.asList(CREATE_MEMBER_INDEX_QUERY, CREATE_GROUP_INDEX_QUERY, CREATE_CITY_INDEX_QUERY);
    }
}
