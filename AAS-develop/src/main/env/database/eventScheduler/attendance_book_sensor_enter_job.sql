create definer = kunsulup@`%` event attendance_book_sensor_enter_job on schedule
    every '1' minute
        starts '2021-01-01 00:00:00'
    enable
    comment '센서 출근자에 대한 출근부 출근 처리'
    do
    BEGIN
        DECLARE v_txt_call_stack text;
        DECLARE v_vch_sql_state varchar(5);
        DECLARE v_int_error_no int;
        DECLARE v_txt_error_msg text;
        DECLARE curr_hour INT ;
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
                    VALUE('attendance_book_sensor_enter_job', 1, 0, v_txt_call_stack );
            END;


        SET curr_hour = (SELECT cast( date_format(now(), '%H') as signed ) );

        -- 출근처리
        IF curr_hour > 4 and curr_hour < 18 then
            -- ####################################################
            -- ## 센서 로그 기반 출근부 업데이트
            -- ####################################################
            -- 센서로그가 있으나 출근 정보가 없는 경우 출근 정보 생성
            insert into attendance_book (
                working_day, wp_id, mb_id, mb_level, coop_mb_id,
                work_in_date, work_in_method, work_in_mac_addr, work_in_device_nm, work_in_si_idx, work_in_desc,
                work_out_date, work_out_method, work_out_mac_addr, work_out_device_nm, work_out_si_idx, work_out_desc,
                working_comment, work_status, create_date)
            select
                RES.working_day,
                RES.wp_id,
                RES.worker_mb_id,
                2 as mb_level,
                RES.coop_mb_id,
                RES.work_in_date,
                RES.work_in_method,
                RES.work_in_mac_addr,
                RES.work_in_device_nm,
                RES.work_in_si_idx,
                RES.work_in_desc,
                RES.work_out_date,
                RES.work_out_method,
                RES.work_out_mac_addr,
                RES.work_out_device_nm,
                RES.work_out_si_idx,
                RES.work_out_desc,
                RES.working_comment,
                RES.work_status,
                NOW()
            from (
                     select
                         DATE_FORMAT(NOW(), '%Y%m%d') as working_day,
                         RES.wp_id,
                         RES.worker_mb_id,
                         RES.coop_mb_id,
                         RES.work_start as work_in_date,
                         4 as work_in_method,
                         null as work_in_mac_addr,
                         null as work_in_device_nm,
                         null as work_in_si_idx,
                         'enter based sensor log' as work_in_desc,
                         null as work_out_date,
                         null as work_out_method,
                         null as work_out_mac_addr,
                         null as work_out_device_nm,
                         null as work_out_si_idx,
                         null as work_out_desc,
                         null as working_comment,
                         1 as work_status
                     from (
                              select WPW.wp_id,
                                     WPW.worker_mb_id,
                                     WPW.coop_mb_id,
                                     MIN(SLI.sli_in_datetime)                  as work_start
                              from work_place_worker WPW
                              inner join sensor_log_inout SLI ON WPW.wp_id = SLI.wp_id  AND WPW.worker_mb_id = SLI.mb_id
                              where WPW.wpw_out = 0
                                and SLI.sli_in_datetime IS NOT NULL
                                and SLI.sli_in_datetime >= DATE(NOW())
                                and SLI.sli_in_datetime < date_add(DATE(NOW()), interval 1 day)
                              group by WPW.wp_id, WPW.worker_mb_id
                              having exists (
                                      select 1 from work_place WP where WP.wp_id = WPW.wp_id AND WP.wp_end_status = 0
                              )
                             and not exists (
                                  select 1
                                  from attendance_book ATDB
                                  where ATDB.wp_id = WPW.wp_id
                                    AND ATDB.mb_id = WPW.worker_mb_id
                                    AND ATDB.working_day = DATE_FORMAT(NOW(), '%Y%m%d')
                                  limit 1
                              )
                          ) RES
                 ) RES
            ON DUPLICATE KEY UPDATE
                working_comment = attendance_book.working_comment
            ;

            INSERT INTO ope_event_log (event_name, event_kind, result, result_desc)
                VALUE('attendance_book_sensor_enter_job', 1, 1, 'SUCCESS' );
        ELSE
            select 1;
        END IF;



    END;



