#!/bin/bash

aws secretsmanager get-secret-value --secret-id AppEnv | jq -r .SecretString | sed 's/\\//g' | base64 -di >>  params.properties

while read line

do

echo $line

export $line

done < params.properties

rm -rf params.properties

java -jar app.jar