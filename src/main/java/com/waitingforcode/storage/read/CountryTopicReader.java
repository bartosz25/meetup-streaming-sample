package com.waitingforcode.storage.read;

import com.datastax.driver.core.LocalDate;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.querybuilder.Select;
import com.datastax.driver.mapping.Result;
import com.waitingforcode.storage.create.CountryTopicTable;
import com.waitingforcode.storage.domain.CountryTopic;

import java.util.Collection;

import static com.datastax.driver.core.querybuilder.QueryBuilder.eq;
import static com.waitingforcode.storage.Cassandra.countryTopicMapper;
import static com.waitingforcode.storage.Cassandra.session;
import static com.waitingforcode.storage.KeyspaceInitializer.KEYSPACE;

public final class CountryTopicReader {

    private CountryTopicReader() {
        // prevents init
    }

    public static Collection<CountryTopic> getDistinctCountries() {
        Select selectQuery = QueryBuilder.select().distinct().column("group_country").column("event_date")
                .from(KEYSPACE, CountryTopicTable.NAME);

        ResultSet results = session().execute(selectQuery);
        Result<CountryTopic> mappedResult = countryTopicMapper().map(results);
        return mappedResult.all();
    }

    public static Collection<CountryTopic> getTopicsInCountry(String countryCode) {
        Select.Where selectQuery = QueryBuilder.select()
                .from(KEYSPACE, CountryTopicTable.NAME)
                .where(eq("group_country", countryCode));

        ResultSet results = session().execute(selectQuery);
        Result<CountryTopic> mappedResult = countryTopicMapper().map(results);
        return mappedResult.all();
    }

    public static Collection<CountryTopic> getTopicsInCountryByDate(String countryCode, LocalDate date) {
        Select.Where selectQuery = QueryBuilder.select()
                .from(KEYSPACE, CountryTopicTable.NAME)
                .where(eq("group_country", countryCode)).and(eq("event_date", date));

        ResultSet results = session().execute(selectQuery);
        Result<CountryTopic> mappedResult = countryTopicMapper().map(results);
        return mappedResult.all();
    }

    public static Collection<CountryTopic> getCountriesByDate(LocalDate date) {
        Select.Where selectQuery = QueryBuilder.select()
                .from(KEYSPACE, CountryTopicTable.NAME)
                .where(eq("event_date", date));

        ResultSet results = session().execute(selectQuery);
        Result<CountryTopic> mappedResult = countryTopicMapper().map(results);
        return mappedResult.all();
    }

}
