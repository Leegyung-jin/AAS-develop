create definer = kunsulup@`%` event test_windgauge_data on schedule
    every '1' MINUTE
        starts '2021-01-01 08:05:30'
    enable
    comment '1분 마다 풍속계 데이터 갱신'
    do
    BEGIN

        -- ================================================================
        -- 현장 : (BLE) 하남감일 70d532fe1e9d430ea0170702abfabcee
        -- ================================================================

        DECLARE wind_gauge_mac varchar(32);
        DECLARE wind_speed decimal(3,1);
        DECLARE wind_speed_max decimal(3,1);

        SET wind_gauge_mac = 'A8-40-41-20-5B-14-41-50-16';

        SET wind_speed = cast( 15 + (RAND() * 10) as decimal(3,1) );
        SET wind_speed_max = wind_speed + 2;

        INSERT INTO wind_speed_log(mac_address, measure_time, display_name, speed_avg, speed_max)
        values(wind_gauge_mac, NOW(), '풍속 #1',  wind_speed, wind_speed_max );

        INSERT INTO wind_speed_recent(mac_address, measure_time, display_name, recently_avg, recently_max, latest_avg, latest_max)
        SELECT
            WSL.mac_address, NOW(), '풍속 #1', wind_speed, wind_speed_max, AVG( speed_avg ) as speed_avg, MAX(speed_avg) as speed_max
        FROM wind_speed_log WSL
        WHERE WSL.mac_address = wind_gauge_mac
          AND WSL.measure_time >= DATE_SUB(NOW(), interval 10 minute )
        ON DUPLICATE KEY UPDATE
            recently_avg = VALUES(recently_avg),
            recently_max = VALUES(recently_max),
            latest_avg = VALUES(latest_avg),
            latest_max = VALUES(latest_max),
            measure_time = NOW()
        ;

    END;

