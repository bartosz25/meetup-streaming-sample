package com.waitingforcode.storage;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.QueryOptions;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.SocketOptions;
import com.datastax.driver.mapping.Mapper;
import com.datastax.driver.mapping.MappingManager;
import com.waitingforcode.storage.domain.CountryTopic;
import com.waitingforcode.storage.domain.MeetupDay;
import com.waitingforcode.storage.domain.MeetupTopic;

public final class Cassandra {

    private static final int DEFAULT_FETCH_SIZE = 100;

    private static final Cluster CLUSTER;
    static {
        SocketOptions socketOptions = new SocketOptions();
        socketOptions.setConnectTimeoutMillis(150_000);
        CLUSTER = Cluster.builder().addContactPoint("127.0.0.1").withPort(9042)
                .withSocketOptions(socketOptions)
                .withQueryOptions(new QueryOptions().setFetchSize(DEFAULT_FETCH_SIZE))
                .build();
    }
    private static final Session SESSION = CLUSTER.connect();
    private static final MappingManager MAPPING_MANAGER = new MappingManager(SESSION);

    private Cassandra() {
        // prevents init
    }

    public static Cluster cluster() {
        return CLUSTER;
    }

    public static Session session() {
        return SESSION;
    }

    public static MappingManager mappingManager() {
        return MAPPING_MANAGER;
    }

    public static Mapper<MeetupDay> meetupDayMapper() {
        return MAPPING_MANAGER.mapper(MeetupDay.class);
    }

    public static Mapper<CountryTopic> countryTopicMapper() {
        return MAPPING_MANAGER.mapper(CountryTopic.class);
    }

    public static Mapper<MeetupTopic> meetupTopicMapper() {
        return MAPPING_MANAGER.mapper(MeetupTopic.class);
    }
}
