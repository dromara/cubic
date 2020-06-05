#!/bin/bash

cd "${0%/*}"
cd ..
BASE_DIR=`pwd`
echo $BASE_DIR
BASE_UI_DIR="cubic-ui"

mvn -v
if [ $? -ne 0 ]; then
    echo "command mvn not found, Install the maven before executing the script！"
    exit 0;
fi

#打包agent
echo "================ starting to build cubic ui ================"
cd "$BASE_UI_DIR"
echo `pwd`
npm run build:prod
echo "================ starting to build cubic agent ================"
cd ..
./mvnw clean package  -DskipTests
echo "================ building cubic agent finished ================"



