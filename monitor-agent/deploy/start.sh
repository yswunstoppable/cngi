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
   echo "monitor-agent程序开始启动"
else
   echo "已启动,杀掉进程后重启"
   echo $PID
   kill -9 $PID
fi
nohup java -Djava.library.path=$APPDIRFILE/lib/ -server  -Xms64m -Xmx128m  -jar $APPDIRFILE/$RUN_NAME >/dev/null 2>&1 &
