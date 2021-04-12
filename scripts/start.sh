#!/bin/bash
cd "${0%/*}"

CUBIC_AGNT_DIR="agent-dist"
CUBIC_PROXY_DIR="agent-proxy-dist"

TOOLS_PATH=$JAVA_HOME/lib/tools.jar

if [ ! -f "$TOOLS_PATH" ] ; then
	echo "$TOOLS_PATH doesn't exist !" >&2
	exit 1
fi

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
BUILD_ID=cubic nohup java -jar -javaagent:${CUBIC_DIR}/${CUBIC_AGNT_DIR}/cubic-agent.jar -Xbootclasspath/a:${TOOLS_PATH} ${JAVA_OPTS} -Dcubic.agent.service_name=cubic-proxy ${CUBIC_DIR}/${CUBIC_PROXY_DIR}/cubic-proxy.jar &


