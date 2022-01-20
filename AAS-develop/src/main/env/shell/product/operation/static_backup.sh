#!/bin/bash

## static 폴더 백업 ( 매월 1일/15일 새벽 4시 백업 수행 ). 50일 경과한 파일은 삭제
## 0 4 1,15 * * /data/script/static_backup.sh

CURRENT_DATE=$(date +%Y%m%d)
SOURCE_DIR=/home/huser/static
BACKUP_STORAGE=/data/backup/static
BACKUP_FILE_NAME="static_"$CURRENT_DATE".tar"
STORAGE_KEEP_DAYS=50

tar -cvf $BACKUP_STORAGE/$BACKUP_FILE_NAME $SOURCE_DIR

find $BACKUP_STORAGE -ctime +$STORAGE_KEEP_DAYS -exec rm -f {} \;

echo $(date +'%Y-%m-%d %H:%M:%S')' Executed backup static folder'
