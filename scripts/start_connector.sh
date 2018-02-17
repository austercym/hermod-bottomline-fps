#!/usr/bin/env bash

nohup java -Djava.security.auth.login.config=/etc/ssl/kafka-client/kafka_client_jaas.conf -Xms4096m -Xmx4096m -XX:+UseParallelGC -XX:NewRatio=2 -cp "./*:config/"  org.springframework.boot.loader.JarLauncher --spring.config.name=hermod-bottomline-fps  &> nohup.out &
echo $! > pid.file