create definer = kunsulup@`%` event test_smart_qr_data on schedule
    every '10' MINUTE
        starts '2021-01-01 08:05:30'
    enable
    comment '1분 마다 BLE 데이터 갱신'
    do
    BEGIN

        -- ================================================================
        -- QR Reader 컴포넌트 테스트
        -- 현장 : QAH 아파트 89e2c4ef05144c0494029769a86d8513
        -- QR 근로자 - 821500002001 ~ 4
        -- 기타 근로자 - 821500002005
        -- 센서 1920 ~ 1924
        -- ================================================================

        DECLARE curr_hour INT ;
        DECLARE curr_minute INT ;

        SET curr_hour = (SELECT cast( date_format(now(), '%H') as signed ) );
        SET curr_minute = (SELECT cast( date_format(now(), '%i') as signed ) );

        -- 하루 한번 출근 처리
        IF curr_hour = 8 and curr_minute < 10 then

            -- 821500002001~4 ( Smart QR 출근 )
            insert into attendance_book (working_day,
                                         wp_id, mb_id, mb_level, coop_mb_id,
                                         work_in_date, work_in_method, work_in_mac_addr, work_in_device_nm, work_in_si_idx,
                                         work_in_temperature, work_in_desc,
                                         work_out_date, work_out_method, work_out_mac_addr, work_out_device_nm,
                                         work_out_si_idx, work_out_desc, working_comment, work_status,
                                         safety_edu_start, safety_edu_end, use_app,
                                         create_date
            )
            select
                DATE_FORMAT(NOW(), '%Y%m%d') as working_day,
                wp_id, mb_id, mb_level, coop_mb_id,
                STR_TO_DATE( CONCAT( DATE_FORMAT(NOW(), '%Y-%m-%d'), ' 06:01:02'), '%Y-%m-%d %H:%i:%s'), work_in_method, work_in_mac_addr, work_in_device_nm, work_in_si_idx,
                '36.5' as work_in_temperature, work_in_desc,
                null as work_out_date, null as work_out_method, null as work_out_mac_addr, null as work_out_device_nm,
                null as work_out_si_idx, null as work_out_desc, null as working_comment, 1 as work_status,
                null as safety_edu_start, null as safety_edu_end, null as use_app,
                STR_TO_DATE( CONCAT( DATE_FORMAT(NOW(), '%Y-%m-%d'), ' 06:01:02'), '%Y-%m-%d %H:%i:%s')
            from attendance_book
            where wp_id = '89e2c4ef05144c0494029769a86d8513'
              and mb_id != '821500002005'
              and working_day = DATE_FORMAT(DATE_SUB(NOW(), interval 1 day), '%Y%m%d')
            on duplicate KEY UPDATE
                working_comment = concat( 'TEST UPDATE ', DATE_FORMAT(NOW(), '%Y-%m-%d %H:%i:%s'))
            ;

            -- 821500002001 ~ 821500002005 ( 센서 출근 )
            insert into sensor_log_inout (
                sd_idx, sd_name, si_code, si_type, si_place1, si_place2,
                wp_id, wp_name, cc_id, cc_name,
                wpw_id, coop_mb_id, coop_mb_name, mb_id, mb_name,
                sli_in_sd_idx, sli_in_sd_name, sli_in_si_code, sli_in_si_type,
                sli_in_datetime,
                sli_out_sd_idx, sli_out_sd_name, sli_out_si_code, sli_out_si_type,
                sli_out_datetime,
                sli_datetime,
                sli_middle_datetime
            )
            select SLI.sd_idx, SLI.sd_name, SLI.si_code, SLI.si_type, SLI.si_place1, SLI.si_place2,
                   SLI.wp_id, SLI.wp_name, SLI.cc_id, SLI.cc_name,
                   WPW.wpw_id, WPW.coop_mb_id, WPW.coop_mb_name, WPW.worker_mb_id, WPW.worker_mb_name,
                   SLI.sli_in_sd_idx, SLI.sli_in_sd_name, SLI.sli_in_si_code, SLI.sli_in_si_type,
                   STR_TO_DATE( CONCAT( DATE_FORMAT(NOW(), '%Y-%m-%d'), ' 06:02:02'), '%Y-%m-%d %H:%i:%s'),
                   SLI.sli_out_sd_idx, SLI.sli_out_sd_name, SLI.sli_out_si_code, SLI.sli_out_si_type,
                   STR_TO_DATE( CONCAT( DATE_FORMAT(NOW(), '%Y-%m-%d'), ' 06:03:02'), '%Y-%m-%d %H:%i:%s'),
                   STR_TO_DATE( CONCAT( DATE_FORMAT(NOW(), '%Y-%m-%d'), ' 06:03:02'), '%Y-%m-%d %H:%i:%s'),
                   SLI.sli_middle_datetime
            from sensor_log_inout SLI
            inner join work_place_worker WPW ON WPW.wp_id = SLI.wp_id
            where SLI.sli_idx = 73686
            and WPW.worker_mb_id IN (
                '821500002001', '821500002002', '821500002003', '821500002004', '821500002005',
                '01400010003', '01400010004', '01400010007'
            )
            ;
        ELSE
            select 1;
        END IF;


        -- 8시 이후 22시까지 10분마다
        IF curr_hour >= 8 and curr_hour < 22 then

            -- 821500002001~3 ( 단말 상태 업데이트 )
            insert into worker_device_status(mb_id, app_version, os_type, os_version, device_model, battery, charge_check, ble_check, loc_check, si_code)
            select
                temp_user.user_key, app_version, os_type, os_version, device_model, battery, charge_check, ble_check, loc_check, si_code
            from worker_device_status, (
                select '821500002001' as user_key
                union select '821500002002'
                union select '821500002003'
                union select '01400010003'
                union select '01400010004'
            ) temp_user
            where mb_id = '01026945491'
            on duplicate KEY UPDATE
                upt_datetime = now()
            ;

            -- 821500002001~4 최근 센서 로그 삭제
            delete from sensor_log_recent
            where mb_id in (
                            '821500002001', '821500002002', '821500002003', '821500002004',
                            '01400010003', '01400010004'
                );

            -- 821500002001~2 최근 센서 로그 추가
            insert into sensor_log_recent (wp_id, si_idx, coop_mb_id, mb_id, in_out_type, slr_datetime)
            select
                WPW.wp_id,
                temp_user.sensor, WPW.coop_mb_id, WPW.worker_mb_id, temp_user.in_out_type, now()
            from work_place_worker WPW
                     inner join (
                select '821500002001' as user_key, 1920 as sensor, 0 as in_out_type
                union select '821500002002', 1921, 2 as in_out_type
                union select '01400010003', 1921, 1 as in_out_type
                union select '01400010004', 1921, 0 as in_out_type
            ) temp_user ON WPW.worker_mb_id = temp_user.user_key
            where WPW.wp_id = '89e2c4ef05144c0494029769a86d8513'
            on duplicate KEY UPDATE
                slr_datetime = now()
            ;

        ELSE
            select 1;
        END IF;

    END;

