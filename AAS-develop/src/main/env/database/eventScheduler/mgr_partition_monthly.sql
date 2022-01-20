create definer = kunsulup@`%` event mgr_partition_monthly on schedule
    every '1' month
        starts '2021-10-01 05:00:00'
    enable
    comment '월별로 대상 테이블의 파티션 생성 및 삭제'
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
                    VALUE('mgr_partition_monthly', 1, 0, v_txt_call_stack );
            END;

        -- #########################################
        -- 월별 파티션 관리 대상 테이블
        -- 파티션이름은 '테이블명'_p'yyyyMM' 형식으로 지정
        -- #########################################
        DECLARE v_counter INT DEFAULT 0;

        SET @partition_table_names = '[
                "noise_meter_log"
        ]';

        select JSON_LENGTH(@partition_table_names);
        WHILE v_counter < JSON_LENGTH(@partition_table_names) DO




          SET v_counter = v_counter + 1;
        END WHILE;


        SET @table_name = 'safety_hook_event_log';
        SET @interval_expr = 6;

        SET @limit_date = DATE_FORMAT( DATE_ADD(NOW(), INTERVAL (@interval_expr + 1) MONTH), '%Y-%m-01' );
        SET @partition_name = concat(
                @table_name, '_p', DATE_FORMAT( DATE_ADD(NOW(), INTERVAL @interval_expr MONTH), '%Y%m' )
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
                VALUE('safety_hook_event_log_partition', 1, 1, concat( 'create partitions ', @partition_name));

            SELECT concat( 'create partitions ', @partition_name );
        ELSE
            INSERT INTO ope_event_log (event_name, event_kind, result, result_desc)
                VALUE('safety_hook_event_log_partition', 1, 0, concat( 'exists partitions ', @partition_name));
            SELECT concat( 'exists partitions ', @partition_name );
        END IF;


        SET @drop_interval_expr = 3;
        SET @drop_partition_name = concat(
                @table_name, '_p', DATE_FORMAT( DATE_SUB(NOW(), INTERVAL @drop_interval_expr MONTH), '%Y%m' )
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
                VALUE('safety_hook_event_log_partition', 1, 1, concat( 'drop partitions ', @drop_partition_name));

            SELECT concat( 'drop partitions ', @drop_partition_name );
        ELSE
            INSERT INTO ope_event_log (event_name, event_kind, result, result_desc)
                VALUE('safety_hook_event_log_partition', 1, 0, concat( 'not exists partitions ', @drop_partition_name));
            SELECT concat( 'not exists partitions ', @drop_partition_name );
        END IF;
    END;

