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
mvn clean package  -Dmaven.test.skip -Denforcer.skip=true
echo "================ building cubic agent finished ================"


#打包proxy
echo "================ starting to build cubic proxy ================"
#mvn clean package -am -pl bistoury-proxy -Pprod -Dmaven.test.skip=true -Denforcer.skip=true
echo "================ building cubic proxy finished ================"
