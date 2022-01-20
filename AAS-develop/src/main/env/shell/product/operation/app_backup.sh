#!/bin/bash

## app 폴더 백업 ( 매월 1일/15일 새벽 5시 백업 수행 ). 50일 경과한 파일은 삭제
## 0 5 1,15 * * /data/script/app_backup.sh

CURRENT_DATE=$(date +%Y%m%d)
SOURCE_DIR=/home/huser/app
BACKUP_STORAGE=/data/backup/app
BACKUP_FILE_NAME="app_"$CURRENT_DATE".tar"
STORAGE_KEEP_DAYS=50

tar -cvf $BACKUP_STORAGE/$BACKUP_FILE_NAME $SOURCE_DIR

find $BACKUP_STORAGE -ctime +$STORAGE_KEEP_DAYS -exec rm -f {} \;

echo $(date +'%Y-%m-%d %H:%M:%S')' Executed backup app folder'