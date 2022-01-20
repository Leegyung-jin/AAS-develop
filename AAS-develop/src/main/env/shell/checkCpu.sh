#!/bin/bash

## CPU 사용량 체크 ( 임시)
## * * * * * /data/script/checkCpu.sh

CURRENT_DATE=$(date +%Y%m%d)

SET=$(seq 0 18)
for i in $SET
do
    CPU_USAGE=`top -b -n1 | grep -Po '[0-9.]+ id' | awk '{print 100-$1}'`
    echo $(date +'%Y-%m-%d %H:%M:%S')' CPU_USAGE '$CPU_USAGE >> /data/log/RESOURCE/cpuUsage_$CURRENT_DATE.log
    sleep 3s
done

