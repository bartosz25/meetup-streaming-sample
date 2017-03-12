#!/bin/bash


CASSANDRA_HOME="/home/streaming_user/programs/cassandra-current"
DOCKER_HOST="$(hostname --ip-address)"

sed -ri 's/^(# )?('"listen_address"':).*/\2 '"$DOCKER_HOST"'/' "$CASSANDRA_HOME/conf/cassandra.yaml"
sed -ri 's/^(# )?('"rpc_address"':).*/\2 '"$DOCKER_HOST"'/' "$CASSANDRA_HOME/conf/cassandra.yaml"
sed -ri 's/^(# )?('"broadcast_address"':).*/\2 '"$DOCKER_HOST"'/' "$CASSANDRA_HOME/conf/cassandra.yaml"
sed -ri 's/^(# )?('"broadcast_rpc_address"':).*/\2 '"$DOCKER_HOST"'/' "$CASSANDRA_HOME/conf/cassandra.yaml"
sed -ri 's/(- seeds:).*/\1 "'"$DOCKER_HOST"'"/' "$CASSANDRA_HOME/conf/cassandra.yaml"

exec "$@"
#advertised.listeners=PLAINTEXT://your.host.name:9092
echo "advertised.listeners=PLAINTEXT://$DOCKER_HOST:9092"  >> /home/streaming_user/programs/kafka-current/config/server.properties

if [ ! -f /tmp/cassandra.log ]; then

    # Start ZooKeeper
    # > http://unix.stackexchange.com/questions/86247/what-does-ampersand-mean-at-the-end-of-a-shell-script-line explains what & mean
    echo "Starting ZooKeeper...."
    exec /home/streaming_user/programs/kafka-current/bin/zookeeper-server-start.sh /home/streaming_user/programs/kafka-current/config/zookeeper.properties > /tmp/zookeeper.log &


    # Start Kafka
    echo "Starting Kafka...."
    exec /home/streaming_user/programs/kafka-current/bin/kafka-server-start.sh /home/streaming_user/programs/kafka-current/config/server.properties > /tmp/kafka.log &


    # Start Cassandra
    echo "Starting Cassandra...."
    exec /home/streaming_user/programs/cassandra-current/bin/cassandra -f  > /tmp/cassandra.log &

    echo "Waiting 15 seconds before Kafka starts...."
    sleep 15s
    # Prepare topics
    exec /home/streaming_user/programs/kafka-current/bin/kafka-topics.sh --delete --zookeeper localhost:2181 --topic meetups > /tmp/delete_topic.log &
    exec /home/streaming_user/programs/kafka-current/bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 2 --topic meetups > /tmp/create_topic.log &
fi