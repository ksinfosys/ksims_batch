#!/bin/bash

JAR_FILE="app.jar"
JOB_NAME=$1

java -jar $JAR_FILE --job.name=$JOB_NAME