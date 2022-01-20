create definer = kunsulup@`%` event test_update_gps_data on schedule
    every '10' MINUTE
        starts '2021-01-01 00:00:00'
    enable
    comment '10분마다 GPS 데이터 갱신'
    do
    BEGIN

        -- 근로자 출근 처리
        update sensor_log_inout SLI
            inner join (
                select
                    wp_id, coop_mb_id, mb_id, min(sli_in_datetime) as sli_in_datetime
                from sensor_log_inout
                where wp_id = 'd044dcca224647f89d320d3951922e67'
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
                      from gps_location GL
                      where GL.mb_id = SLI.mb_id
                        and GL.wp_id = SLI.wp_id
                      limit 1
                  )
        ;

        -- 덤프 디바이스(O) 근로자(X) 잘 나옴
        update gps_location
        set longitude =  126.43168553294424, latitude = 37.47078204430431, measure_time = now()
        where device_id =  'ForkLiftTrucks';

        -- 덤프 디바이스(X) 근로자(O) 잘 나옴
        update gps_location
        set longitude =  126.43168553294424, latitude = 37.47278204430431, measure_time = now()
        where mb_id =  '01526945491';

        -- 덤프 디바이스(O) 근로자(O) 잘 나옴
        update gps_location
        set longitude =  126.43168553294424, latitude = 37.46978204430431, measure_time = now()
        where device_id =  'DMP3';

        update gps_location
        set longitude =  126.43168553294424 + cast( 0 + (RAND() * 0.001) as decimal(7,5) )
          ,latitude = 37.46988204430431 + cast( 0 + (RAND() * 0.001) as decimal(7,5) )
          , measure_time = now()
        where mb_id =  '01726945491';


        -- 지게차 디바이스(O) 근로자(X) 잘 나옴
        update gps_location
        set longitude =  126.43168553294424, latitude = 37.471780204430431, measure_time = now()
        where device_id =  '01231358701';

        -- 지게차 디바이스(X) 근로자(O) 잘 나옴
        update gps_location
        set longitude =  126.43268553294424, latitude = 37.47078204430431, measure_time = now()
        where mb_id =  '02026945491';

        -- 지게차 디바이스(O) 근로자(O) 잘 나옴
        update gps_location
        set longitude =  126.43568553294424, latitude = 37.46978204430431, measure_time = now()
        where device_id =  'ForkLift4';

        update gps_location
        set longitude =  126.43568553294424, latitude = 37.47078204430431, measure_time = now()
        where mb_id =  '02126945491';

        -- 근로자
        update gps_location
        set longitude =  126.43068553294424 + cast( 0 + (RAND() * 0.001) as decimal(7,5) )
          , latitude = 37.47178204430431 + cast( 0 + (RAND() * 0.001) as decimal(7,5) )
          , measure_time = now()
        where mb_id =  '821026945491';

        update gps_location
        set sensor_scan = 50000
        where mb_id =  '821026945491';

        update gps_location
        set longitude =  126.43578553294424, latitude = 37.47178204430431, measure_time = now(), sensor_scan = null
        where wp_id = 'd044dcca224647f89d320d3951922e67'
          and mb_id  = '01000009999'
        ;

        update gps_location
        set longitude =  126.43578553294424, latitude = 37.47278204430431, measure_time = now(), sensor_scan = null
        where wp_id = 'd044dcca224647f89d320d3951922e67'
          and mb_id  = '01058956530'
        ;

        update gps_location
        set longitude =  126.445678, latitude = 37.4695303, measure_time = now(), sensor_scan = null
        where wp_id = 'd044dcca224647f89d320d3951922e67'
          and mb_id  = '01030015525'
        ;

        update gps_location
        set longitude =  126.43638553294424, latitude = 37.47188204430431, measure_time = now(), sensor_scan = null
        where wp_id = 'd044dcca224647f89d320d3951922e67'
          and mb_id  = '01032318119'
        ;

        -- case when sensor_scan = 50000 then null else 50000 end
        update sensor_log_trace SLT
        set
            si_code = '10_50000',
            si_type = '위험지역',
            slt_in_datetime = date_sub(now(), interval 2 second),
            slt_out_datetime = case when slt_out_datetime is null then now() else null end,
            slt_datetime = now()
        where slt_idx = 247760
        ;

        update gps_location
        set longitude =  126.43598553294424 + cast( 0 + (RAND() * 0.001) as decimal(7,5) )
          , latitude = 37.47108204430431 + cast( 0 + (RAND() * 0.001) as decimal(7,5) )
          , measure_time = now()
          , sensor_scan = null
        where wp_id = 'd044dcca224647f89d320d3951922e67'
          and mb_id  = '01085611187'
        ;



        update worker_device_status WDS
        set upt_datetime = NOW()
        where exists (
                      select 1
                      from gps_location GL
                      where GL.mb_id = WDS.mb_id
                        and GL.wp_id = 'd044dcca224647f89d320d3951922e67'
                      limit 1
                  )
        ;

    END;

