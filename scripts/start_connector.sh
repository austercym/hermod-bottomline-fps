#!/usr/bin/env bash

nohup java -javaagent:./config/jmx_prometheus_javaagent-0.2.0.jar=8080:./config/metrics.yaml -Djava.security.auth.login.config=/etc/ssl/kafka-client/kafka_client_jaas.conf -Xms4096m -Xmx4096m -XX:+UseParallelGC -XX:NewRatio=2 -cp "./*:config/"  org.springframework.boot.loader.JarLauncher --spring.config.name=hermod-bottomline-fps  &> nohup.out &
echo $! > pid.file