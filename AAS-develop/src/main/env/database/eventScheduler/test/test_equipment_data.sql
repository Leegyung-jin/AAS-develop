create definer = kunsulup@`%` event test_equipment_data on schedule
    every '10' MINUTE
        starts '2021-01-01 08:05:30'
    enable
    comment '10분 마다 장비 차량 데이터 갱신'
    do
    BEGIN

        -- ================================================================
        -- 장비 및 차량 컴포넌트 테스트
        -- 현장 : (BLE) 하남감일 70d532fe1e9d430ea0170702abfabcee
        -- 근로자 : 01400010010~1 ( 협력사 : 9991100004 )
        -- ================================================================

        DECLARE curr_hour INT ;
        DECLARE curr_minute INT ;

        SET curr_hour = (SELECT cast( date_format(now(), '%H') as signed ) );
        SET curr_minute = (SELECT cast( date_format(now(), '%i') as signed ) );

        -- 하루 한번 출근 처리
        IF curr_hour = 8 and curr_minute < 10 then

            -- 01400010010~1 ( 센서 출근 )
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
            select 285, '게이트', '24_10001', '출입용', '출입게이트2', '',
                   WP.wp_id, WP.wp_name, WP.cc_id, WP.cc_name,
                   WPW.wpw_id, WPW.coop_mb_id, WPW.coop_mb_name, WPW.worker_mb_id, WPW.worker_mb_name,
                   285, '게이트', '24_10001', '출입용',
                   STR_TO_DATE( CONCAT( DATE_FORMAT(NOW(), '%Y-%m-%d'), ' 07:02:02'), '%Y-%m-%d %H:%i:%s'),
                   285, '게이트', '24_10001', '출입용',
                   STR_TO_DATE( CONCAT( DATE_FORMAT(NOW(), '%Y-%m-%d'), ' 07:03:02'), '%Y-%m-%d %H:%i:%s'),
                   STR_TO_DATE( CONCAT( DATE_FORMAT(NOW(), '%Y-%m-%d'), ' 07:03:02'), '%Y-%m-%d %H:%i:%s'),
                   null
            from work_place WP
                     inner join work_place_worker WPW ON WPW.wp_id = WP.wp_id
            where WP.wp_id = '70d532fe1e9d430ea0170702abfabcee'
              and WPW.worker_mb_id IN (
                                       '01400010010', '01400010011'
                )
            ;

        ELSE
            select 1;
        END IF;


        -- 8시 이후 22시까지 10분마다
        IF curr_hour >= 8 and curr_hour < 22 then

            -- 01400010010~1 ( 단말 상태 업데이트 )
            insert into worker_device_status(mb_id, app_version, os_type, os_version, device_model, battery, charge_check, ble_check, loc_check, si_code)
            select
                temp_user.user_key, app_version, os_type, os_version, device_model, battery, charge_check, ble_check, loc_check, si_code
            from worker_device_status, (
                select '01400010010' as user_key
                union select '01400010011'
            ) temp_user
            where mb_id = '01026945491'
            on duplicate KEY UPDATE
                upt_datetime = now()
            ;

            update gps_location
            set measure_time = now()
            where mb_id in ( '01400010010', '01400010011' )
               or device_id in ( '하남감일_GPS식별자#4', '하남감일_GPS식별자#5' )
            ;

            delete from sensor_log_recent
            where mb_id in (
                            '01400010010', '01400010011'
                )
            ;

            -- 01400010010 최근 센서 로그 추가
            insert into sensor_log_recent (wp_id, si_idx, coop_mb_id, mb_id, in_out_type, slr_datetime)
            select
                WPW.wp_id,
                temp_user.sensor, WPW.coop_mb_id, WPW.worker_mb_id, temp_user.in_out_type, now()
            from work_place_worker WPW
                     inner join (
                select '01400010010' as user_key, 1842 as sensor, 0 as in_out_type
                union select '01400010011' as user_key, 1842 as sensor, 1 as in_out_type
            ) temp_user ON WPW.worker_mb_id = temp_user.user_key
            where WPW.wp_id = '70d532fe1e9d430ea0170702abfabcee'
            ;

        ELSE
            select 1;
        END IF;

    END;

