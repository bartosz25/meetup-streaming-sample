package com.waitingforcode.messaging;

import com.google.api.client.http.*;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.waitingforcode.domain.Meetup;

import java.io.IOException;
import java.io.InputStream;

import static com.waitingforcode.json.JsonMapper.getMapper;

/**
 * Listens for new events from Meetup.com's API and pushes them
 * to appropriated Kafka topic.
 */
public final class MeetupListener {

    private static final String MEETUP_ENDPOINT = "http://stream.meetup.com/2/rsvps";

    private static final HttpTransport HTTP_TRANSPORT = new NetHttpTransport();

    /**
     * Bytes representing new entry, reading from the end, i.e. 123 is the first byte of new
     * entry, 34 the second one and so on.
     */
    private static final int[] NEW_ENTRY_MARK_BYTES = new int[]{58, 34, 101, 117, 110, 101, 118, 34, 123};

    private final MeetupKafkaProducer producer;

    public MeetupListener() {
        try {
            producer = new MeetupKafkaProducer();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void listen() throws IOException {
        System.out.println("Connecting....");
        HttpRequest request = getRequest();
        HttpResponse response = request.execute();
        InputStream stream =  response.getContent();
        int readByte;
        StringBuffer stringBuffer = new StringBuffer();
        int[] currentReadBytes = new int[9];
        while ((readByte = stream.read()) != -1) {
            stringBuffer.append((char) readByte);
            currentReadBytes = moveBytes(currentReadBytes, readByte);
            if (compareArrays(NEW_ENTRY_MARK_BYTES, currentReadBytes) && stringBuffer.length() > 9) {
                int length = stringBuffer.length()-9;
                // TODO : here the bytes could be sent directly without the need of mappings
                // But for debugging reasons, they're first translated to printable JSON and sent further as it
                Meetup meetup = getMeetupEntry(stringBuffer, length);
                producer.sendMeetup(meetup);
                stringBuffer.delete(0, length);
            }
        }
    }

    private HttpRequest getRequest() throws IOException {
        HttpRequestFactory requestFactory = HTTP_TRANSPORT.createRequestFactory((request) -> {});
        GenericUrl url = new GenericUrl(MEETUP_ENDPOINT);
        return requestFactory.buildGetRequest(url);
    }

    private Meetup getMeetupEntry(StringBuffer stringBuffer, int length) throws IOException {
        String meetupEntry = stringBuffer.substring(0, length).toString();
        return getMapper().readValue(meetupEntry, Meetup.class);
    }

    private int[] moveBytes(int[] initial, int newByte) {
        for (int i = initial.length-1; i > 0; i--) {
            initial[i] = initial[i-1];
        }
        initial[0] = newByte;
        return initial;
    }

    private boolean compareArrays(int[] newVenueBytes, int[] currentReadBytes) {
        if (newVenueBytes.length != currentReadBytes.length) {
            return false;
        }
        for (int i = 0; i < newVenueBytes.length; i++) {
            if (newVenueBytes[i] != currentReadBytes[i]) {
                return false;
            }
        }
        return true;
    }


}
