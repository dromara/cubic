#!/bin/bash

cd "${0%/*}"
cd ..

mvn -v
if [ $? -ne 0 ]; then
    echo "command mvn not found, Install the maven before executing the script！"
    exit 0;
fi

#打包agent
echo "================ starting to build cubic agent ================"
./mvnw package  -DskipTests
echo "================ building cubic agent finished ================"



