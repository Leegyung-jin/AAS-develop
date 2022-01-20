create definer = kunsulup@`%` event attendance_book_midnight_job on schedule
    every '1' day
        starts '2021-01-01 00:05:00'
    enable
    comment '전일의 출근 정보 업데이트. ( 퇴근 처리 및 안전조회 참석 시간, 앱 사용 여부 )'
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
                    VALUE('member_otp_create_partition', 1, 0, v_txt_call_stack );
            END;

        -- ####################################################
        -- ## 센서 로그 기반 출근부 업데이트
        -- ####################################################
        -- 센서로그가 있으나 전일 출근 정보가 없는 경우 출근/퇴근 정보 생성
        -- 출근 정보가 있는 경우 퇴근 정보 업데이트
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
                 date_format(date_sub(DATE(NOW()), interval 1 day), '%Y%m%d') as working_day,
                 RES.wp_id,
                 RES.worker_mb_id,
                 RES.coop_mb_id,
                 RES.work_start as work_in_date,
                 99 as work_in_method,
                 null as work_in_mac_addr,
                 null as work_in_device_nm,
                 null as work_in_si_idx,
                 'system based sensor log' as work_in_desc,
                 RES.work_end as work_out_date,
                 99 as work_out_method,
                 null as work_out_mac_addr,
                 null as work_out_device_nm,
                 null as work_out_si_idx,
                 'system based sensor log' as work_out_desc,
                 'system create based sensor log' as working_comment,
                 2 as work_status
             from (
                      select WPW.wp_id,
                             WPW.worker_mb_id,
                             WPW.coop_mb_id,
                             MIN(SLI.sli_in_datetime)                  as work_start,
                             MAX(IFNULL(SLI.sli_out_datetime, date_add(SLI.sli_in_datetime, interval 1 second)))      as work_end
                      from work_place_worker WPW
                      inner join work_place WP ON WP.wp_id = WPW.wp_id
                      inner join sensor_log_inout SLI ON WPW.wp_id = SLI.wp_id
                          AND WPW.worker_mb_id = SLI.mb_id
                          and SLI.sli_in_datetime IS NOT NULL
                          and SLI.sli_in_datetime >= date_sub(DATE(NOW()), interval 1 day)
                          and SLI.sli_in_datetime < DATE(NOW())
                      where WP.wp_end_status = 0
                        AND WPW.wpw_out = 0
                      group by WPW.wp_id, WPW.worker_mb_id
                  ) RES
         ) RES
        ON DUPLICATE KEY UPDATE
             work_status = 2,
             working_comment = case
                                   when attendance_book.work_out_date is null or attendance_book.work_out_date < RES.work_out_date then concat( IFNULL(attendance_book.working_comment, ''), ' system update based sensor log')
                                   else attendance_book.working_comment
                 end,
             work_out_method = case
                                   when attendance_book.work_out_date is null or attendance_book.work_out_date < RES.work_out_date then RES.work_out_method
                                   else attendance_book.work_out_method
                 end,
             work_out_mac_addr = case
                                     when attendance_book.work_out_date is null or attendance_book.work_out_date < RES.work_out_date then RES.work_out_mac_addr
                                     else attendance_book.work_out_mac_addr
                 end,
             work_out_device_nm = case
                                      when attendance_book.work_out_date is null or attendance_book.work_out_date < RES.work_out_date then RES.work_out_device_nm
                                      else attendance_book.work_out_device_nm
                 end,
             work_out_si_idx = case
                                   when attendance_book.work_out_date is null or attendance_book.work_out_date < RES.work_out_date then RES.work_out_si_idx
                                   else attendance_book.work_out_si_idx
                 end,
             work_out_desc = case
                                 when attendance_book.work_out_date is null or attendance_book.work_out_date < RES.work_out_date then RES.work_out_desc
                                 else attendance_book.work_out_desc
                 end,
             work_out_date = case
                                 when attendance_book.work_out_date is null or attendance_book.work_out_date < RES.work_out_date then RES.work_out_date
                                 else attendance_book.work_out_date
                 end
        ;

        -- ####################################################
        -- ## 센서 로그 기반 안전조회 참석 시간 업데이트
        -- ####################################################
        -- 안전조회 참석 시간 업데이트
        insert into attendance_book (
            working_day, wp_id, mb_id, mb_level, coop_mb_id,
            work_in_date, work_in_method, work_in_mac_addr, work_in_device_nm, work_in_si_idx, work_in_desc,
            work_out_date, work_out_method, work_out_mac_addr, work_out_device_nm, work_out_si_idx, work_out_desc,
            working_comment, work_status, safety_edu_start, safety_edu_end, create_date)
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
            RES.work_in_date,
            RES.work_out_date,
            NOW()
        from (
             select
                 date_format(date_sub(DATE(NOW()), interval 1 day), '%Y%m%d') as working_day,
                 RES.wp_id,
                 RES.worker_mb_id,
                 RES.coop_mb_id,
                 RES.work_start as work_in_date,
                 99 as work_in_method,
                 null as work_in_mac_addr,
                 null as work_in_device_nm,
                 null as work_in_si_idx,
                 'system based sensor log' as work_in_desc,
                 RES.work_end as work_out_date,
                 99 as work_out_method,
                 null as work_out_mac_addr,
                 null as work_out_device_nm,
                 null as work_out_si_idx,
                 'system based sensor log' as work_out_desc,
                 'system create based sensor log' as working_comment,
                 2 as work_status
             from (
                  select WPW.wp_id,
                         WPW.worker_mb_id,
                         WPW.coop_mb_id,
                         MIN(SLI.sli_in_datetime)                  as work_start,
                         MAX(IFNULL(SLI.sli_out_datetime, date_add(SLI.sli_in_datetime, interval 1 second)))      as work_end
                  from work_place_worker WPW
                  inner join work_place WP ON WP.wp_id = WPW.wp_id
                  inner join sensor_log_inout SLI ON WPW.wp_id = SLI.wp_id
                              AND WPW.worker_mb_id = SLI.mb_id
                              and SLI.sli_in_datetime IS NOT NULL
                              AND SLI.si_type = '안전조회'
                              and SLI.sli_in_datetime >= date_sub(DATE(NOW()), interval 1 day)
                              and SLI.sli_in_datetime < DATE(NOW())
                  where WP.wp_end_status = 0
                    AND WPW.wpw_out = 0
                  group by WPW.wp_id, WPW.worker_mb_id
             ) RES
        ) RES
        ON DUPLICATE KEY UPDATE
                             safety_edu_start = RES.work_in_date,
                             safety_edu_end = RES.work_out_date
        ;

        -- ########################################################
        -- ## 앱 사용 여부 업데이트
        -- ########################################################
        update attendance_book
            left join worker_device_status WDS ON WDS.mb_id = attendance_book.mb_id
        set attendance_book.use_app = case
                          when WDS.upt_datetime IS NOT NULL AND WDS.upt_datetime >= date_sub(DATE(NOW()), interval 1 day)  THEN 1
                          ELSE 0
            END
        where attendance_book.use_app is null
          and attendance_book.working_day = date_format( date_sub( now(), interval 1 day ), '%Y%m%d')
        ;

        -- ####################################################
        -- ## 퇴근(현장 부재) 처리
        -- ####################################################
        update attendance_book
        set
            work_status =  2,
            working_comment = case
                                  when attendance_book.work_out_date is null then 'system update'
                                  else attendance_book.working_comment
                end,
            work_out_method = case
                                  when attendance_book.work_out_date is null then 99
                                  else attendance_book.work_out_method
                end,
            work_out_mac_addr = case
                                        when attendance_book.work_out_date is null then null
                                        else attendance_book.work_out_mac_addr
                end,
            work_out_device_nm = case
                                    when attendance_book.work_out_date is null then null
                                    else attendance_book.work_out_device_nm
                end,
            work_out_si_idx = case
                                     when attendance_book.work_out_date is null then null
                                     else attendance_book.work_out_si_idx
                end,
            work_out_desc = case
                                when attendance_book.work_out_date is null then 'system force out'
                                else attendance_book.work_out_desc
                end,
            work_out_date = case
                                when attendance_book.work_out_date is null then date_add(attendance_book.work_in_date, interval 1 second)
                                else attendance_book.work_out_date
                end
        where work_status != 2
        and attendance_book.working_day = date_format( date_sub( now(), interval 1 day ), '%Y%m%d')
        ;


        -- ########################################################
        -- ## 통계 업데이트
        -- ########################################################
        insert into attendance_book_month_stat(working_month, wp_id, mb_id, coop_mb_id, working_day_count, safety_edu_count, use_app_count, create_date)
        select
            RES.working_month,
            RES.wp_id,
            RES.mb_id,
            RES.coop_mb_id,
            RES.working_day_count,
            RES.safety_edu_count,
            RES.use_app_count,
            RES.create_date
        from (
             select
                 FILT2.month as working_month,
                 FILT2.wp_id,
                 FILT2.mb_id,
                 FILT2.coop_mb_id,
                 SUM( FILT2.attendance ) as working_day_count,
                 SUM( FILT2.safety ) as safety_edu_count,
                 SUM( FILT2.use_app ) as use_app_count,
                 now() as create_date
             from (
                      select
                          DATE_FORMAT( date(ATB.working_day), '%Y%m') as month,
                          ATB.wp_id,
                          ATB.mb_id,
                          ATB.coop_mb_id,
                          1 as attendance,
                          case when ATB.safety_edu_start IS NOT NULL then 1 else 0 end as safety,
                          IFNULL( ATB.use_app, 0) as use_app
                      from attendance_book ATB
                      where ATB.working_day like concat(date_format(date_sub(now(), interval 1 day ), '%Y%m'), '%')
                      and ATB.coop_mb_id IS NOT NULL
                      and ATB.mb_level = 2
             ) FILT2
             group by FILT2.month, FILT2.wp_id, FILT2.mb_id, FILT2.coop_mb_id
         ) RES
        ON DUPLICATE KEY UPDATE
             working_day_count = RES.working_day_count,
             safety_edu_count = RES.safety_edu_count,
             use_app_count = RES.use_app_count
        ;

        INSERT INTO ope_event_log (event_name, event_kind, result, result_desc)
        VALUE('attendance_book_midnight_job', 1, 1, 'SUCCESS' );

    END;



