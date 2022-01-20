create definer = kunsulup@`%` event member_otp_drop_partition on schedule
    every '1' MONTH
        starts '2021-04-01 05:01:00'
    enable
    comment 'member_otp Partition 삭제'
    do
    BEGIN
        DECLARE v_txt_call_stack text;
        DECLARE v_vch_sql_state varchar(5);
        DECLARE v_int_error_no int;
        DECLARE v_txt_error_msg text;
        DECLARE EXIT HANDLER FOR SQLEXCEPTION
            BEGIN
                GET DIAGNOSTICS CONDITION 1 v_vch_sql_state = RETURNED_SQLSTATE
                    , v_int_error_no = MYSQL_ERRNO
                    , v_txt_error_msg = MESSAGE_TEXT;

                SET v_txt_call_stack = CONCAT('{'
                    ,' "RETURNED_SQLSTATE" : "',v_vch_sql_state, '", '
                    ,' "MYSQL_ERRNO" : "',v_int_error_no, '", '
                    ,' "MESSAGE_TEXT" : "',v_txt_error_msg, '", '
                                                  '}');

                INSERT INTO ope_event_log (event_name, event_kind, result, result_desc)
                    VALUE('member_otp_drop_partition', 1, 0, v_txt_call_stack );
            END;


        SET @table_name = 'member_otp';
        SET @interval_expr = 3;

        SET @partition_name = concat(
                @table_name, '_p', DATE_FORMAT( DATE_SUB(NOW(), INTERVAL @interval_expr MONTH), '%Y%m' )
        ) ;

        SELECT count(*) INTO @exists_cnt FROM information_schema.partitions
        where TABLE_NAME = @table_name
          AND PARTITION_NAME = @partition_name;

        IF @exists_cnt = 1 THEN
            SET @query = concat('ALTER TABLE ',@table_name,' DROP PARTITION ', @partition_name );
            PREPARE stmt1 FROM @query;
            EXECUTE stmt1;
            DEALLOCATE PREPARE stmt1;

            INSERT INTO ope_event_log (event_name, event_kind, result, result_desc)
                VALUE('member_otp_drop_partition', 1, 1, concat( 'drop partitions ', @partition_name));

            SELECT concat( 'drop partitions ', @partition_name );
        ELSE
            INSERT INTO ope_event_log (event_name, event_kind, result, result_desc)
                VALUE('member_otp_drop_partition', 1, 0, concat( 'not exists partitions ', @partition_name));
            SELECT concat( 'not exists partitions ', @partition_name );
        END IF;

    END;