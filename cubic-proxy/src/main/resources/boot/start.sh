#!/bin/bash
export JAVA_HOME=${JAVA_HOME8}
export PATH=$JAVA_HOME/bin:$PATH

cd `dirname $0`
DEPLOY_DIR=`pwd`
LOGS_DIR=$DEPLOY_DIR/logs
if [ ! -d $LOGS_DIR ]; then
    mkdir $LOGS_DIR
fi

#skywalking
JAVA_OPTS=""
WORK_HOME="$(cd `dirname $0`/ >/dev/null; pwd)";
SKY_WALKING_AGENT="/data/agent/agent-skywalking/skywalking-agent.jar";
if [ -f $SKY_WALKING_AGENT ]; then
    JAVA_OPTS="$JAVA_OPTS -javaagent:$SKY_WALKING_AGENT"
    JAVA_OPTS="$JAVA_OPTS -Dskywalking.agent.service_name=matrix-proxy";
    export JAVA_OPTS;
fi

#设置并行计算默认线程数
JAVA_OPTS="$JAVA_OPTS  -Xmx2688M -Xms2688M -Xmn960M -XX:MaxMetaspaceSize=512M -XX:MetaspaceSize=512M -XX:+UseConcMarkSweepGC -XX:+UseCMSInitiatingOccupancyOnly -XX:CMSInitiatingOccupancyFraction=70 -XX:+ExplicitGCInvokesConcurrentAndUnloadsClasses   -XX:+ParallelRefProcEnabled -XX:+CMSScavengeBeforeRemark   -XX:+PrintGCDetails   -XX:+PrintGCDateStamps -Xloggc:/data/logs/matrix-proxy/gc.log -XX:ErrorFile=/data/logs/matrix-proxy/err_gc.log ";


STDOUT_FILE=$LOGS_DIR/stdout.log
nohup java -jar $JAVA_OPTS  $DEPLOY_DIR/matrix-proxy.jar --log.stdout=0 >> $STDOUT_FILE 2>&1 &

echo "OK!"
PIDS=`ps -ef | grep java | grep "$DEPLOY_DIR" |grep -v grep | awk '{print $2}'`
echo "PID: $PIDS"
echo "STDOUT: $STDOUT_FILE"


