create definer = kunsulup@`%` event sensor_log_trace_partition on schedule
    every '1' month
        starts '2021-10-01 05:10:00'
    enable
    comment 'sensor_log_trace 로그 테이블 파티션 생성 및 삭제'
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
                    VALUE('sensor_log_trace_partition', 1, 0, v_txt_call_stack );
            END;

        SET @table_name = 'sensor_log_trace';
        SET @interval_expr = 6;

        SET @limit_date = DATE_FORMAT( DATE_ADD(NOW(), INTERVAL (@interval_expr + 1) MONTH), '%Y-%m-01' );
        SET @partition_name = concat(
                @table_name, '_', DATE_FORMAT( DATE_ADD(NOW(), INTERVAL @interval_expr MONTH), '%Y%m' )
            ) ;

        SELECT count(*) INTO @exists_cnt FROM information_schema.partitions
        where TABLE_NAME = @table_name
          AND PARTITION_NAME = @partition_name;

        IF @exists_cnt = 0 THEN
            SET @query = concat(
                    'ALTER TABLE ',@table_name,' ADD PARTITION ',
                    '( PARTITION ', @partition_name, ' VALUES LESS THAN ',
                    '( TO_DAYS( \'', @limit_date , '\' ) ) )' );
            PREPARE stmt1 FROM @query;
            EXECUTE stmt1;
            DEALLOCATE PREPARE stmt1;

            INSERT INTO ope_event_log (event_name, event_kind, result, result_desc)
                VALUE('sensor_log_trace_partition', 1, 1, concat( 'create partitions ', @partition_name));

            SELECT concat( 'create partitions ', @partition_name );
        ELSE
            INSERT INTO ope_event_log (event_name, event_kind, result, result_desc)
                VALUE('sensor_log_trace_partition', 1, 0, concat( 'exists partitions ', @partition_name));
            SELECT concat( 'exists partitions ', @partition_name );
        END IF;


        /*
        SET @drop_interval_expr = 11;
        SET @drop_partition_name = concat(
                @table_name, '_', DATE_FORMAT( DATE_SUB(NOW(), INTERVAL @drop_interval_expr MONTH), '%Y%m' )
            ) ;

        SELECT count(*) INTO @exists_drop_cnt FROM information_schema.partitions
        where TABLE_NAME = @table_name
          AND PARTITION_NAME = @drop_partition_name;

        IF @exists_drop_cnt = 1 THEN
            SET @query = concat('ALTER TABLE ',@table_name,' DROP PARTITION ', @drop_partition_name );
            PREPARE stmt1 FROM @query;
            EXECUTE stmt1;
            DEALLOCATE PREPARE stmt1;

            INSERT INTO ope_event_log (event_name, event_kind, result, result_desc)
                VALUE('sensor_log_trace_partition', 1, 1, concat( 'drop partitions ', @drop_partition_name));

            SELECT concat( 'drop partitions ', @drop_partition_name );
        ELSE
            INSERT INTO ope_event_log (event_name, event_kind, result, result_desc)
                VALUE('sensor_log_trace_partition', 1, 0, concat( 'not exists partitions ', @drop_partition_name));
            SELECT concat( 'not exists partitions ', @drop_partition_name );
        END IF;
         */
    END;

