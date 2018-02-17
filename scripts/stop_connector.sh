#!/usr/bin/env bash

if [ -f "pid.file" ]
then
     kill  $(cat pid.file)
else
     echo "pid.file not found. Kill the process manually."
fi