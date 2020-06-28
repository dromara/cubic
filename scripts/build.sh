#!/bin/bash

cd "${0%/*}"
cd ..

echo " install-file to maven repository linux-tools.jar"
mvn install:install-file -Dfile=DependLib/linux-tools-1.8.jar -DgroupId=com.sun -DartifactId=linux-tools -Dversion=1.8 -Dpackaging=jar

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
npm install --save xterm
npm install --save xterm-addon-fit
npm install --save xterm-addon-attach
npm install --save xterm-addon-web-links
cd "$BASE_UI_DIR"
echo `pwd`

npm install
npm run build:prod
echo "================ starting to build cubic agent ================"
cd ..
./mvnw clean package  -DskipTests
echo "================ building cubic agent finished ================"



