#!/bin/bash

docker rm my_streaming_context

#docker rmi streaming_context -f

docker build -t streaming_context .

docker run -p 127.0.0.1:9042:9042 -p 127.0.0.1:9160:9160  -p 9092:9092 --name my_streaming_context -i -t streaming_context

