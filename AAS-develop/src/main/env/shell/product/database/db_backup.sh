#!/bin/bash
# repair : $> mysqldump --routines --triggers -u아이디 -p패스워드 < 불러올파일명.sql
# 00 01 * * * /home/huser/script/db_backup.sh
DATE=$(date +%Y%m%d)
DB_USER=hulan
DB_PW=hl!@1352
BACKUP_DB_NAME=hulan
BACKUP_DIR=/home/huser/db_dump/
BACKUP_FILE_NAME=$BACKUP_DIR"backup_"$BACKUP_DB_NAME"_"$DATE.sql
KEEP_DAYS=3

mysqldump --routines --triggers -u $DB_USER -p$DB_PW $BACKUP_DB_NAME > $BACKUP_FILE_NAME

find $BACKUP_DIR -ctime +$KEEP_DAYS -exec rm -f {} \;

## BACKUP STORAGE
BACKUP_STORAGE=/data/backup/database
STORAGE_KEEP_DAYS=10

cp $BACKUP_FILE_NAME $BACKUP_STORAGE/.

find $BACKUP_STORAGE -ctime +$STORAGE_KEEP_DAYS -exec rm -f {} \;

echo $(date +'%Y-%m-%d %H:%M:%S')' Executed backup Dbdump('$BACKUP_FILE_NAME')'
