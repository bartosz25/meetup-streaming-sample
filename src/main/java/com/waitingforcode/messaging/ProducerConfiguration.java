package com.waitingforcode.messaging;

import java.io.IOException;
import java.util.Properties;

import static com.waitingforcode.util.ResourceUtil.loadResourceStream;

public final class ProducerConfiguration {

    private ProducerConfiguration() {
        // prevents init
    }

    public static Properties load(String clientId) throws IOException {
        Properties properties = new Properties();
        properties.load(loadResourceStream("kafka-producer.properties"));
        properties.setProperty("client.id", clientId);
        return properties;
    }

}
