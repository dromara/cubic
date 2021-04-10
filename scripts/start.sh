#!/bin/bash
cd "${0%/*}"

CUBIC_AGNT_DIR="agent-dist"
CUBIC_PROXY_DIR="agent-proxy-dist"

cd ..
CUBIC_DIR=`pwd`
if [[ ! -w "$CUBIC_PROXY_DIR" ]] ; then
    echo "请先进行编译在执行启动脚本，或者执行quick_start_build.sh启动"
    exit 0;
fi

echo "kill old application --start--"
PROCESS=`ps -ef|grep 'cubic-agent'|grep -v grep|grep -v PPID|awk '{ print $2}'`
for i in $PROCESS
do
  echo "Kill the $1 process [ $i ]"
  kill -9 $i
done
echo "kill old application --end--"

JAVA_OPTS=" -Xms512M -Xmx512M"
echo "准备启动应用 AGENT_DIR $CUBIC_PROXY_DIR/$AGENT_DIR"
BUILD_ID=cubic nohup java -jar -javaagent:${CUBIC_DIR}/${CUBIC_AGNT_DIR}/cubic-agent.jar ${JAVA_OPTS}   ${CUBIC_DIR}/${CUBIC_PROXY_DIR}/cubic-proxy.jar --spring.profiles.active=test >/data/logs/cubic.log 2>&1 &


