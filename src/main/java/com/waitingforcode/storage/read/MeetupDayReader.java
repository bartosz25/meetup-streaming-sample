package com.waitingforcode.storage.read;

import com.datastax.driver.core.*;
import com.datastax.driver.mapping.Result;
import com.waitingforcode.storage.create.MeetupDayTable;
import com.waitingforcode.storage.domain.MeetupDay;
import com.waitingforcode.util.Pagination;

import java.util.Collection;

import static com.waitingforcode.storage.Cassandra.meetupDayMapper;
import static com.waitingforcode.storage.Cassandra.session;
import static com.waitingforcode.storage.KeyspaceInitializer.KEYSPACE;

public final class MeetupDayReader {

    private static final int RESULTS_PER_PAGE = 4;

    private MeetupDayReader() {
        // prevents init
    }

    // http://docs.datastax.com/en/developer/java-driver/2.1/manual/paging/
    public static Collection<MeetupDay> getMeetupsByDay(LocalDate day, Pagination pagination) {
        PreparedStatement selectQuery = session()
                .prepare("SELECT * FROM " + KEYSPACE + " ." + MeetupDayTable.NAME + " WHERE event_date = :eventDate");
        Statement boundStatement = selectQuery.bind()
                .setDate("eventDate", day)
                .setFetchSize(pagination.getMax());
        ResultSet results = session().execute(boundStatement);
        Result<MeetupDay> mappedResult = meetupDayMapper().map(results);

        // PagingState doesn't allow random jumps, e.g. reading the rows from offset 100
        // They must be read page by page, offset by offset
        // Solution to that is to make client fetching more results as expected. If the page holds 20
        // items and we display 10 pages, we should take 200 rows
        // TODO : optimally that should return a kind of PagedResult(PaginationState, Collection<MeetupDay>) to deal with fetching
        return mappedResult.all();
    }

    public static Collection<MeetupDay> getAllMeetups(Pagination pagination) {
        Statement selectMeetupsByDays = new SimpleStatement("SELECT * FROM "+ KEYSPACE + " ." + MeetupDayTable.NAME);
        selectMeetupsByDays.setFetchSize(pagination.getMax());
        ResultSet results = session().execute(selectMeetupsByDays);
        Result<MeetupDay> mappedResult = meetupDayMapper().map(results);
        return mappedResult.all();
    }

}
