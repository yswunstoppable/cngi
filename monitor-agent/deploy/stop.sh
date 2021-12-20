#!/bin/sh
RUN_NAME="monitor-agent-release.jar"
export LANG="en_US.UTF-8"
APPDIRFILE=`pwd`
cd $APPDIRFILE
echo $APPDIRFILE/$RUN_NAME
PID=`ps -ef|grep $RUN_NAME|grep -v grep|awk '{printf $2}'`
echo $PID
if [ ! -n "$PID" ];
then
   echo "monitor-agent程序未启动"
else
   echo "已杀掉进程"
   echo $PID
   kill -9 $PID
fi
