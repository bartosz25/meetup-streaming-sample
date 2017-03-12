FROM ubuntu:16.04

RUN apt-get update
RUN apt-get install wget -y

# Java 8
RUN apt-get update && \
    apt-get upgrade -y && \
    apt-get install -y  software-properties-common && \
    add-apt-repository ppa:webupd8team/java -y && \
    apt-get update && \
    echo oracle-java8-installer shared/accepted-oracle-license-v1-1 select true | /usr/bin/debconf-set-selections && \
    apt-get install -y oracle-java8-installer && \
    apt-get clean
ENV JAVA_HOME /usr/lib/jvm/java-8-oracle/

RUN useradd -ms /bin/bash streaming_user

USER streaming_user
WORKDIR /home/streaming_user
RUN mkdir /home/streaming_user/installs
RUN mkdir /home/streaming_user/programs

# Scala 2.11.6
RUN wget --quiet http://downloads.lightbend.com/scala/2.11.6/scala-2.11.6.tgz -P /home/streaming_user/installs
RUN tar -xvzf /home/streaming_user/installs/scala-2.11.6.tgz -C /home/streaming_user/programs
RUN echo "export SCALA_HOME=/home/streaming_user/programs/scala-2.11.0" >> /home/streaming_user/.bashrc
RUN echo "export PATH=$SCALA_HOME/bin:$PATH" >> /home/streaming_user/.bashrc
#RUN source /home/streaming_user/.bashrc

# Kafka 0.10.1.1
RUN wget --quiet http://apache.mediamirrors.org/kafka/0.10.1.1/kafka_2.11-0.10.1.1.tgz -P /home/streaming_user/installs
RUN tar -xvzf /home/streaming_user/installs/kafka_2.11-0.10.1.1.tgz -C /home/streaming_user/programs
RUN ln -s /home/streaming_user/programs/kafka_2.11-0.10.1.1 /home/streaming_user/programs/kafka-current
ARG kafkaConfig=/home/streaming_user/programs/kafka-current/config/server.properties
RUN echo "delete.topic.enable=true" >> $kafkaConfig
RUN echo "auto.create.topics.enable=false" >> $kafkaConfig

# Cassandra 3.10
RUN wget --quiet http://mirrors.standaloneinstaller.com/apache/cassandra/3.10/apache-cassandra-3.10-bin.tar.gz -P /home/streaming_user/installs
RUN tar -xvzf /home/streaming_user/installs/apache-cassandra-3.10-bin.tar.gz -C /home/streaming_user/programs
RUN ln -s /home/streaming_user/programs/apache-cassandra-3.10 /home/streaming_user/programs/cassandra-current
ARG cassandraConfig=/home/streaming_user/programs/cassandra-current/conf
RUN sed -i -E "s/MAX_HEAP_SIZE=\".*\"/MAX_HEAP_SIZE=\"1000M\"/" $cassandraConfig/cassandra-env.sh

USER root
COPY ./script/db_runners.sh /home/streaming_user/db_runners.sh
RUN chmod +x /home/streaming_user/db_runners.sh


USER streaming_user

# Thanks to && commands can be chained together
ENTRYPOINT /home/streaming_user/db_runners.sh && /bin/bash