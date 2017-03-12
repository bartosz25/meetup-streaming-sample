package com.waitingforcode.processing;

import com.waitingforcode.storage.write.MeetupPersister;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.ByteArrayDeserializer;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka010.ConsumerStrategies;
import org.apache.spark.streaming.kafka010.KafkaUtils;
import org.apache.spark.streaming.kafka010.LocationStrategies;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class MeetupDataProcessor {

    private static final String TOPIC = "meetups"; // TODO : unformize with messsaging's part

    private static final long BATCH_INTERVAL = 500L;

    private final JavaStreamingContext streamingContext;

    static Map<String, Object> kafkaParams = new HashMap<>();

    public MeetupDataProcessor() {
        // TODO : read config from .properties
        SparkConf configuration = new SparkConf().setAppName("Receivers Test").setMaster("local[6]");
        JavaSparkContext batchContext  = new JavaSparkContext(configuration);
        streamingContext = new JavaStreamingContext(batchContext, Durations.milliseconds(BATCH_INTERVAL));

        kafkaParams.put("bootstrap.servers", "localhost:9092");
        kafkaParams.put("key.deserializer", LongDeserializer.class);
        kafkaParams.put("value.deserializer", ByteArrayDeserializer.class);
        kafkaParams.put("client.id", "meetup_data_processor_"+System.currentTimeMillis());
        kafkaParams.put("group.id", "meetup_data_processor_"+System.currentTimeMillis());
        kafkaParams.put("auto.offset.reset", "earliest");
        kafkaParams.put("enable.auto.commit", true);
    }

    public void processStreamData() {
        System.out.println("Starting data processing....");
        JavaInputDStream<ConsumerRecord<Long, byte[]>> stream = createDStream();
        //System.out.println("Stream = " + stream.toString());
        stream.foreachRDD(MeetupPersister.INSTANCE);

        // Manadatory - otherwise processing doesn't start !
        streamingContext.start();
    }

    private JavaInputDStream<ConsumerRecord<Long, byte[]>> createDStream() {
        return KafkaUtils.createDirectStream(
                streamingContext,
                LocationStrategies.PreferConsistent(),
                ConsumerStrategies.Subscribe(Collections.singleton(TOPIC), kafkaParams)
        );
    }

}
