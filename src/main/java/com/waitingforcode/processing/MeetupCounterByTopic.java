package com.waitingforcode.processing;

import org.apache.spark.SparkConf;

/**
 * Counts the number of meetups for given topic with Spark batch job.
 */
public class MeetupCounterByTopic {

    private static final SparkConf configuration = new SparkConf().setAppName("Receivers Test").setMaster("local[2]")
            .set("spark.cassandra.connection.host", "192.168.123.10")
            .set("spark.cassandra.auth.username", "cassandra")
            .set("spark.cassandra.auth.password", "cassandra");

    // https://github.com/datastax/spark-cassandra-connector/blob/master/doc/1_connecting.md


}
