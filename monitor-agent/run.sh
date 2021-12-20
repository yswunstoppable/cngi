#!/bin/bash

case "$1" in
start)
   nohup java -classpath ".:/opt/cngi/agent/lib/*:/opt/cngi/agent/target/classes" com.occamedu.monitor.agent.MonitorAgentApplication  >/opt/logs/cngi/agent/output.log 2>&1 &
   echo $!>./run.pid
   cat run.pid
   ;;
stop)
   ID=`ps -ef | grep OccamApiApplication | grep -v grep | awk '{print $2}'`
   if [ $? -eq 0 ]; then
     echo "processid:$ID"
     kill -9 $ID
   else
     echo "process not exit"
     exit
   fi
   rm ./run.pid
   ;;
restart)
   $0 stop
   $0 start
   ;;
status)
   if [ -e ./run.pid ]; then
      echo monitor-agent.sh is running, pid=`cat ./run.pid`
   else
      echo monitor-agent.sh is NOT running
      exit 1
   fi
   ;;
*)
   echo "Usage: $0 {start|stop|status|restart}"
esac

exit 0

