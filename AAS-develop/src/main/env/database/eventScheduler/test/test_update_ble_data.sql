create definer = kunsulup@`%` event test_update_ble_data3 on schedule
    every '1' MINUTE
        starts '2021-01-01 00:00:00'
    enable
    comment '1분 마다 BLE 데이터 갱신'
    do
    BEGIN

        -- 근로자 출근 처리 ( 하루 한번 )
        update sensor_log_inout SLI
            inner join (
                select
                    wp_id, coop_mb_id, mb_id, min(sli_in_datetime) as sli_in_datetime
                from sensor_log_inout
                where wp_id = '70d532fe1e9d430ea0170702abfabcee'
                  and si_type = '출입용'
                  and sli_datetime between DATE_ADD(DATE(NOW()), interval -1 day) and DATE(NOW())
                group by wp_id, coop_mb_id, mb_id
            ) SLI2 ON SLI2.wp_id =  SLI.wp_id
                AND  SLI2.coop_mb_id =  SLI.coop_mb_id
                AND  SLI2.mb_id =  SLI.mb_id
                AND  SLI2.sli_in_datetime = SLI.sli_in_datetime
        set SLI.sli_in_datetime = DATE(NOW()) + INTERVAL TIME_TO_SEC(TIME(SLI.sli_in_datetime)) SECOND
          , SLI.sli_out_datetime = DATE(NOW()) + INTERVAL TIME_TO_SEC(TIME(SLI.sli_out_datetime)) SECOND
          , SLI.sli_datetime = DATE(NOW()) + INTERVAL TIME_TO_SEC(TIME(SLI.sli_datetime)) SECOND
        WHERE exists (
                      select 1
                      from sensor_log_recent SLR
                      where SLR.mb_id = SLI.mb_id
                        and SLR.wp_id = SLI.wp_id
                      limit 1
                  )
        ;

        -- 센서 최근 위치 업데이트 ( 매번 변경 )
        update sensor_log_recent
        set
            si_idx =
                case
                    when si_idx = 1905 then 1867
                    when si_idx = 1867 then 1841
                    when si_idx = 1841 then 1898
                    when si_idx = 1898 then 1842
                    when si_idx = 1842 then 1918
                    when si_idx = 1918 then 1904
                    when si_idx = 1904 then 1908
                    when si_idx = 1908 then 1848
                    when si_idx = 1848 then 1905
                    else si_idx
                    end,
            in_out_type = 0,
            slr_datetime = DATE(NOW()) + INTERVAL TIME_TO_SEC(TIME(slr_datetime)) SECOND
        where wp_id = '70d532fe1e9d430ea0170702abfabcee'
        ;

        -- 10분에 한번 추락 감지 이벤트
        insert into falling_accident_recent (mb_id, wp_id, measure_time, dashboard_popup)
        select TU.mb_id, TU.wp_id, TU.measure_time, TU.dashboard_popup
        from (
                 select
                     '01526945491' as mb_id
                      ,'70d532fe1e9d430ea0170702abfabcee' as wp_id
                      ,now() as measure_time
                      ,1 as dashboard_popup
                 union
                 select '01090292837', '70d532fe1e9d430ea0170702abfabcee', now(), 1
             ) TU
        where not exists (
                select 1
                from falling_accident_recent FAR
                where FAR.mb_id =TU.mb_id
                  and FAR.wp_id = TU.wp_id
            )
          and date_format(now(), '%i') % 10 = 0
        ON DUPLICATE KEY UPDATE
                             measure_time = NOW(),
                             dashboard_popup = 1
        ;

        -- 1분에 한번 유해물질 로그 업데이트

        SET @GAS_LOG_MEASURE_TIME = NOW();
        -- SET @GAS_THRES = case when date_format(now(), '%i') % 10 = 0 then 5 else 0 end;
        SET @GAS_THRES = 5;

        insert into gas_log (mac_address, device_id, measure_time, temperature, humidity, o2, h2s, co, ch4, battery)
        select
            DE.mac_address,
            DE.device_id,
            @GAS_LOG_MEASURE_TIME as measure_time,
            cast( (4 + RAND() * 40  + RAND() * @GAS_THRES ) as decimal(3,1) ) as temperature,
            cast( (0 + RAND() * 90 ) as decimal(3,1) ) as humidity,
            cast( (18 + RAND() * 5.4  + RAND() * @GAS_THRES ) as decimal(3,1) ) as o2,
            cast( (0 + RAND() * 10  + RAND() * @GAS_THRES ) as decimal(3,1) ) as h2s,
            cast( (0 + RAND() * 30  + RAND() * @GAS_THRES ) as decimal(3,1) ) as co,
            cast( (0 + RAND() * 15  + RAND() * @GAS_THRES ) as decimal(3,1) ) as ch4,
            100 as battery
        from (
                 select '70-5E-F1-A4-AE-30' as mac_address, '904동 지상 2층' as device_id
                 union
                 select 'AA-BB-CC-DD-EE-FF', '904동 지하 1층'
             ) DE

        ;

        insert gas_log_recent (
            mac_address, measure_time, temperature, o2, h2s, co, ch4, humidity, battery, temperature_level, o2_level, h2s_level, co_level, ch4_level, humidity_level, hazard_phrase, dashboard_popup
        )
        select
            GLR.mac_address
             ,GLR.measure_time
             ,GLR.temperature
             ,GLR.o2
             ,GLR.h2s
             ,GLR.co
             ,GLR.ch4
             ,GLR.humidity
             ,GLR.battery
             ,GLR.temperature_level
             ,GLR.o2_level
             ,GLR.h2s_level
             ,GLR.co_level
             ,GLR.ch4_level
             ,GLR.humidity_level
             ,GLR.hazard_phrase
             ,GLR.dashboard_popup
        from (
                 select
                     RES.mac_address
                      ,RES.measure_time
                      ,RES.temperature
                      ,RES.o2
                      ,RES.h2s
                      ,RES.co
                      ,RES.ch4
                      ,RES.humidity
                      ,RES.battery
                      ,case when RES.temperature_phrase = '' then 0 else 1 end as temperature_level
                      ,case when RES.o2_phrase = '' then 0 else 1 end as o2_level
                      ,case when RES.h2s_phrase = '' then 0 else 1 end as h2s_level
                      ,case when RES.co_phrase = '' then 0 else 1 end as co_level
                      ,case when RES.ch4_phrase = '' then 0 else 1 end as ch4_level
                      ,case when RES.humidity_phrase = '' then 0 else 1 end as humidity_level
                      ,TRIM(TRAILING ',' FROM concat( RES.temperature_phrase, case when RES.temperature_phrase = '' then '' else ',' end,
                                                      RES.o2_phrase, case when RES.o2_phrase = '' then '' else ',' end,
                                                      RES.h2s_phrase, case when RES.h2s_phrase = '' then '' else ',' end,
                                                      RES.co_phrase, case when RES.co_phrase = '' then '' else ',' end,
                                                      RES.ch4_phrase, case when RES.ch4_phrase = '' then '' else ',' end,
                                                      RES.humidity_phrase, case when RES.humidity_phrase = '' then '' else ',' end
                     )) as hazard_phrase
                      ,1 as dashboard_popup
                 from(
                         select
                             GL.mac_address
                              ,GL.measure_time
                              ,GL.temperature
                              ,GL.o2
                              ,GL.h2s
                              ,GL.co
                              ,GL.ch4
                              ,GL.humidity
                              ,GL.battery
                              ,IFNULL( (
                                           select
                                               GC.category_name
                                           from gas_safe_range GSR
                                                    inner join gas_category GC ON GSR.category = GC.category
                                           where GSR.policy_no = 2 and GSR.category = 1
                                             and NOT( GL.temperature between GSR.min and GSR.max )
                                       ), '' ) as temperature_phrase
                              ,IFNULL( (
                                           select
                                               GC.category_name
                                           from gas_safe_range GSR
                                                    inner join gas_category GC ON GSR.category = GC.category
                                           where GSR.policy_no = 2 and GSR.category = 2
                                             and NOT( GL.o2 between GSR.min and GSR.max )
                                       ), '' ) as o2_phrase
                              ,IFNULL( (
                                           select
                                               GC.category_name
                                           from gas_safe_range GSR
                                                    inner join gas_category GC ON GSR.category = GC.category
                                           where GSR.policy_no = 2 and GSR.category = 3
                                             and NOT( GL.h2s between GSR.min and GSR.max )
                                       ), '' ) as h2s_phrase
                              ,IFNULL( (
                                           select
                                               GC.category_name
                                           from gas_safe_range GSR
                                                    inner join gas_category GC ON GSR.category = GC.category
                                           where GSR.policy_no = 2 and GSR.category = 4
                                             and NOT( GL.co between GSR.min and GSR.max )
                                       ), '' ) as co_phrase
                              ,IFNULL( (
                                           select
                                               GC.category_name
                                           from gas_safe_range GSR
                                                    inner join gas_category GC ON GSR.category = GC.category
                                           where GSR.policy_no = 2 and GSR.category = 5
                                             and NOT( GL.ch4 between GSR.min and GSR.max )
                                       ), '' ) as ch4_phrase
                              ,IFNULL( (
                                           select
                                               GC.category_name
                                           from gas_safe_range GSR
                                                    inner join gas_category GC ON GSR.category = GC.category
                                           where GSR.policy_no = 2 and GSR.category = 6
                                             and NOT( GL.humidity between GSR.min and GSR.max )
                                       ), '' ) as humidity_phrase
                         from gas_log GL
                         where GL.mac_address in (
                                                  '70-5E-F1-A4-AE-30', 'AA-BB-CC-DD-EE-FF'
                             )
                           and GL.measure_time = @GAS_LOG_MEASURE_TIME
                     ) RES
             ) GLR
        ON DUPLICATE KEY UPDATE
                             measure_time = GLR.measure_time,
                             temperature = GLR.temperature,
                             o2 = GLR.o2,
                             h2s = GLR.h2s,
                             co = GLR.co,
                             ch4 = GLR.ch4,
                             humidity = GLR.humidity,
                             battery = GLR.battery,
                             temperature_level = GLR.temperature_level,
                             o2_level = GLR.o2_level,
                             h2s_level = GLR.h2s_level,
                             co_level = GLR.co_level,
                             ch4_level = GLR.ch4_level,
                             humidity_level = GLR.humidity_level,
                             hazard_phrase = GLR.hazard_phrase
        ;
    END;

