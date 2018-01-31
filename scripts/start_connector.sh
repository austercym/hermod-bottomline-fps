#!/usr/bin/env bash

nohup java -Xms4096m -Xmx8192m -XX:+UseParallelGC -XX:NewRatio=2 -cp "./*:config/"  org.springframework.boot.loader.JarLauncher --spring.config.name=hermod-bottomline-fps  &> nohup.out &
echo $! > pid.file