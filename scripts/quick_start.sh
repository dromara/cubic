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


JAVA_OPTS=" -Xms512M -Xmx512M"
echo "准备启动应用 AGENT_DIR $CUBIC_PROXY_DIR/$AGENT_DIR"
java -jar -javaagent:${CUBIC_DIR}/${CUBIC_AGNT_DIR}/cubic-agent.jar ${JAVA_OPTS} -Dcubic.agent.service_name=cubic-proxy ${CUBIC_DIR}/${CUBIC_PROXY_DIR}/cubic-proxy.jar


