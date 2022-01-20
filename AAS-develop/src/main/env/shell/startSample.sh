#!/bin/bash

USER_HOME_DIR=/home/huser
LOG_BASE=$USER_HOME_DIR/log/kafka
NOHUP_LOG_FILE=$LOG_BASE/nohup_kafka.log

APP_NAME=kafka
APP_HOME=/mnt/kafka/current

KAFKA_OPTS="$KAFKA_OPTS -Djava.security.auth.login.config=$APP_HOME/config/kafka_jaas.conf"
export KAFKA_OPTS

function extractPid(){
  echo `ps -ef | grep java | grep 'kafka.Kafka' | grep -v grep | awk '{print $2}'`
}


case "$1" in
start)
  PID=$( extractPid )
  if [ -z "$PID" ]; then
    if [ -f "$NOHUP_LOG_FILE" ]; then
      LOG_SUB_FIX=`date "+%Y%m%d_%H%M%S"`
      ## mv $NOHUP_LOG_FILE $LOG_BASE/nohup_kafka_$LOG_SUB_FIX.log
    fi
    echo "$APP_NAME starting...."
    nohup $APP_HOME/bin/kafka-server-start.sh $APP_HOME/config/server.properties >> $NOHUP_LOG_FILE 2>&1 &
    sleep 3
    $0 status

  else
    echo "$APP_NAME is running ( PID = $PID )"
  fi
  ;;
stop)
  PID=$( extractPid )
  if [ -z "$PID" ]; then
    echo "$APP_NAME is NOT running"
    exit 1
  else
    echo "try kill $APP_NAME ( PID = $PID )"
    kill ${PID}
    sleep 7
    $0 status
  fi
  ;;
restart)
  $0 stop
  $0 start
  ;;
status)
  PID=$( extractPid )
  if [ -z "$PID" ]; then
    echo "$APP_NAME is NOT running"
    exit 1
  else
    echo "$APP_NAME is running ( PID = $PID )"
  fi
  ;;
*)
  $0 status
  echo "Usage: $0 {start|stop|status|restart}"
esac

exit 0
