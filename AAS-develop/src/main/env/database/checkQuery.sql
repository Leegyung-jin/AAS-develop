
-- -----------------------------------------
-- 금일 센서 접근 사용자 App 버전별 현황
-- -----------------------------------------
select
    EX.app_version, count( distinct mb_id )
from (
         SELECT
             t.slt_idx, t.wp_id, si.si_idx, t.coop_mb_id, t.mb_id, IF(t.slt_out_datetime IS NULL, 0, 1) as in_out_type, t.slt_datetime
              ,gm.mb_name, gm.app_version
         FROM sensor_log_trace t
                  LEFT JOIN sensor_info si ON t.wp_id = si.wp_id and t.si_code = si.si_code
                  LEFT JOIN g5_member gm ON gm.mb_id = t.mb_id
         WHERE t.wp_id = '55402b34777a4413b85b662f1d305241'
           AND t.slt_datetime >= DATE_FORMAT(NOW() , '%Y-%m-%d 00:00:00')
         -- AND ( gm.app_version is null or gm.app_version != '3.3.4' )
     ) EX
group by app_version
order by app_version
;



-- ############################################################
-- 출력인원 점검 ( 하남감일 : 55402b34777a4413b85b662f1d305241 )
-- ############################################################

-- --------------------------------------------------------
-- 총 출력인원
-- --------------------------------------------------------
select count(distinct WPW.worker_mb_id) as cnt
FROM work_place_worker WPW
         INNER JOIN work_place WP ON WPW.wp_id = WP.wp_id
where WPW.wpw_out = 0
  AND WPW.wp_id = '55402b34777a4413b85b662f1d305241'
  AND EXISTS(
        select 1
        from sensor_log_inout SLI
        where SLI.wp_id = WPW.wp_id
          and SLI.coop_mb_id = WPW.coop_mb_id
          and SLI.mb_id = WPW.worker_mb_id
          AND SLI.si_type = '출입용'
          AND SLI.sli_in_datetime >= DATE(NOW())
          AND SLI.sli_in_datetime < DATE(DATE_ADD(NOW(), INTERVAL 1 DAY))
        LIMIT 1
    )
;

-- --------------------------------------------------------
-- 현재 작업자
-- --------------------------------------------------------
SELECT
    COUNT(distinct WPW.worker_mb_id) AS total_cnt
FROM work_place_worker WPW
         INNER JOIN sensor_log_recent SLR ON WPW.worker_mb_id = SLR.mb_id AND WPW.coop_mb_id = SLR.coop_mb_id AND WPW.wp_id = SLR.wp_id
         INNER JOIN sensor_building_location SBL ON SBL.si_idx = SLR.si_idx
         LEFT JOIN worker_device_status WDS ON WDS.mb_id = SLR.mb_id
WHERE WPW.wp_id = '55402b34777a4413b85b662f1d305241'
  AND SLR.slr_datetime >=  DATE(NOW())
  AND (
        WDS.upt_datetime IS NULL
        OR
        (
                WDS.upt_datetime IS NOT NULL
                AND WDS.upt_datetime > date_sub( NOW(), interval 22 minute )
                AND WDS.ble_check = 1
                AND WDS.loc_check = 1
            )
    )
  AND SLR.in_out_type IN ( 0, 1 )
;

-- --------------------------------------------------------
-- 전체 출력 인원 현재 작업자 단말 상태 현황 파악
-- --------------------------------------------------------
select
    RES.WORK_STATUS, count(*)  as cnt
from (
         select
             SITU.worker_mb_id,
             CASE
                 WHEN SITU.upt_datetime IS NULL THEN '1[X] 단말 상태 로그 없음'
                 WHEN SITU.upt_datetime < date(now()) THEN '2[X] 단말 상태 금일 로그 없음'
                 WHEN SITU.upt_datetime <= date_sub( NOW(), interval 22 minute ) THEN '3[X] DEVICE IDLE'
                 WHEN SITU.si_idx IS NULL THEN '4[X] 센서 감지 이력 없음'
                 WHEN SITU.location IS NULL THEN '5[X] 위치 등록 안된 센서'
                 WHEN SITU.ble_check != 1 THEN '6[X] BLE OFF'
                 WHEN SITU.loc_check != 1 THEN '7[X] GPS OFF'
                 WHEN SITU.in_out_type = 2 THEN '8[X] 외출'
                 WHEN SITU.in_out_type = 3 THEN '9[X] 퇴근'
                 ELSE '[O] 정상'
                 END as WORK_STATUS
         from (
                  select WPW.worker_mb_id, WPW.worker_mb_name, SBL.si_idx as location, SLR.si_idx, SLR.in_out_type, WDI.ble_check, WDI.loc_check, WDI.upt_datetime, G5M.app_version
                  FROM work_place_worker WPW
                           INNER JOIN work_place WP ON WPW.wp_id = WP.wp_id
                           LEFT JOIN sensor_log_recent SLR ON SLR.mb_id = WPW.worker_mb_id
                           LEFT JOIN worker_device_status WDI ON WDI.mb_id = WPW.worker_mb_id
                           LEFT JOIN g5_member G5M ON G5M.mb_id = WPW.worker_mb_id
                           LEFT JOIN sensor_building_location SBL ON SBL.si_idx = SLR.si_idx
                  where WPW.wpw_out = 0
                    AND WPW.wp_id = '13c8a3bfe64841dfb190cbde8893114b'
                    AND EXISTS(
                          select 1
                          from sensor_log_inout SLI
                          where SLI.wp_id = WPW.wp_id
                            and SLI.coop_mb_id = WPW.coop_mb_id
                            and SLI.mb_id = WPW.worker_mb_id
                            AND SLI.si_type = '출입용'
                            AND SLI.sli_in_datetime >= DATE(NOW())
                            AND SLI.sli_in_datetime < DATE(DATE_ADD(NOW(), INTERVAL 1 DAY))
                          LIMIT 1
                      )
              ) SITU
     ) RES
group by res.WORK_STATUS
;

select *
from (
         select
             SITU.worker_mb_id,
             SITU.worker_mb_name,
             SITU.coop_mb_name,
             SITU.upt_datetime,
             CASE
                 WHEN SITU.upt_datetime IS NULL THEN '10[X] 단말 상태 로그 없음'
                 WHEN SITU.upt_datetime < date(now()) THEN '11[X] 단말 상태 금일 로그 없음'
                 WHEN SITU.upt_datetime <= date_sub( NOW(), interval 22 minute ) THEN '12[X] DEVICE IDLE'
                 WHEN SITU.ble_check != 1 THEN '13[X] BLE OFF'
                 WHEN SITU.loc_check != 1 THEN '14[X] GPS OFF'
                 WHEN SITU.si_idx IS NULL THEN '20[X] 센서 감지 이력 없음'
                 WHEN SITU.slr_datetime < date(now()) THEN '21[X] 금일 센서 감지 이력 없음'
                 WHEN SITU.location IS NULL THEN '22[X] 위치 등록 안된 센서'
                 WHEN SITU.in_out_type = 2 THEN '23[X] 외출'
                 WHEN SITU.in_out_type = 3 THEN '24[X] 퇴근'
                 ELSE '[O] 정상'
                 END as WORK_STATUS
         from (
                  select
                      WPW.worker_mb_id, G5M.mb_name as worker_mb_name, COOP_G5M.mb_name as coop_mb_name,
                      SBL.si_idx as location, SLR.slr_datetime, SLR.si_idx, SLR.in_out_type, WDI.ble_check, WDI.loc_check, WDI.upt_datetime, G5M.app_version
                  FROM work_place_worker WPW
                           INNER JOIN work_place WP ON WPW.wp_id = WP.wp_id
                           LEFT JOIN sensor_log_recent SLR ON SLR.mb_id = WPW.worker_mb_id
                      AND SLR.slr_datetime >= date(now())
                           LEFT JOIN worker_device_status WDI ON WDI.mb_id = WPW.worker_mb_id
                           LEFT JOIN g5_member G5M ON G5M.mb_id = WPW.worker_mb_id
                           LEFT JOIN g5_member COOP_G5M ON COOP_G5M.mb_id = WPW.coop_mb_id
                           LEFT JOIN sensor_building_location SBL ON SBL.si_idx = SLR.si_idx
                  where WPW.wpw_out = 0
                    AND WPW.wp_id = '13c8a3bfe64841dfb190cbde8893114b'
                    AND EXISTS(
                          select 1
                          from sensor_log_inout SLI
                          where SLI.wp_id = WPW.wp_id
                            and SLI.coop_mb_id = WPW.coop_mb_id
                            and SLI.mb_id = WPW.worker_mb_id
                            AND SLI.si_type = '출입용'
                            AND SLI.sli_in_datetime >= DATE(NOW())
                            AND SLI.sli_in_datetime < DATE(DATE_ADD(NOW(), INTERVAL 1 DAY))
                          LIMIT 1
                      )
              ) SITU
     ) RES
where RES.WORK_STATUS != '[O] 정상'
order by RES.coop_mb_name
;

-- -------------------------------------
-- 근로자 접근 센서 중 위치 등록 안된 센서
-- -------------------------------------
select SI.*, count(*) as cnt
FROM work_place_worker WPW
         INNER JOIN work_place WP ON WPW.wp_id = WP.wp_id
         LEFT JOIN sensor_log_recent SLR ON SLR.mb_id = WPW.worker_mb_id
         LEFT JOIN worker_device_status WDI ON WDI.mb_id = WPW.worker_mb_id
         LEFT JOIN g5_member G5M ON G5M.mb_id = WPW.worker_mb_id
         LEFT JOIN sensor_building_location SBL ON SBL.si_idx = SLR.si_idx
         LEFT JOIN sensor_info SI ON SI.si_idx = SLR.si_idx
where WPW.wpw_out = 0
  AND WPW.wp_id = '55402b34777a4413b85b662f1d305241'
  AND EXISTS(
        select 1
        from sensor_log_inout SLI
        where SLI.wp_id = WPW.wp_id
          and SLI.coop_mb_id = WPW.coop_mb_id
          and SLI.mb_id = WPW.worker_mb_id
          AND SLI.si_type = '출입용'
          AND SLI.sli_in_datetime >= DATE(NOW())
          AND SLI.sli_in_datetime < DATE(DATE_ADD(NOW(), INTERVAL 1 DAY))
        LIMIT 1
    )
  AND SBL.si_idx IS NULL
group by SI.si_idx
;


-- -----------------------------------------
-- 현장 전체 외출/퇴근 시키기
-- -----------------------------------------
update sensor_log_recent slr left join sensor_info si on slr.si_idx = si.si_idx
set in_out_type = 3
where slr.slr_datetime > date(now())
  and slr.wp_id = '55402b34777a4413b85b662f1d305241'
;


-- -----------------------------------------
-- BLE 전체 출근인원,  작업 인원 근로자
-- -----------------------------------------
-- 전체 출근인원
select
    total_worker_count,
    qr_enter_count,
    0 as tracker_enter_count,
    ( total_worker_count - qr_enter_count ) as app_enter_count
from (
         select
             IFNULL( count(distinct WPW.worker_mb_id), 0) as total_worker_count,
             IFNULL( count(distinct WQE.mb_id), 0) AS qr_enter_count
         FROM work_place_worker WPW
                  INNER JOIN work_place WP ON WPW.wp_id = WP.wp_id
                  LEFT JOIN worker_qr_enter WQE ON WQE.wp_id = WPW.wp_id
             and WQE.coop_mb_id = WPW.coop_mb_id
             AND WQE.mb_id = WPW.worker_mb_id
             AND WQE.enter_day = DATE(NOW())
         where WPW.wpw_out = 0
           AND WPW.wp_id = 'd2a3577f61af4fe4b36cc767bb6d0bbc'
           AND EXISTS(
                 select 1
                 from sensor_log_inout SLI
                 where SLI.wp_id = WPW.wp_id
                   and SLI.mb_id = WPW.worker_mb_id
                   AND SLI.si_type = '출입용'
                   AND SLI.sli_in_datetime >= DATE(NOW())
                   AND SLI.sli_in_datetime < DATE(DATE_ADD(NOW(), INTERVAL 1 DAY))
                LIMIT 1
            )
     ) WORK_STATUS
;

-- 작업 인원 근로자
SELECT
    COUNT(distinct WPW.worker_mb_id) AS total_cnt
FROM work_place_worker WPW
         LEFT JOIN worker_device_status WDS ON WDS.mb_id = WPW.worker_mb_id
WHERE WPW.wp_id = 'd2a3577f61af4fe4b36cc767bb6d0bbc'
  AND EXISTS(
        SELECT 1
        FROM sensor_log_recent SLR
                 INNER JOIN sensor_building_location SBL ON SBL.si_idx = SLR.si_idx
        WHERE WPW.worker_mb_id = SLR.mb_id AND WPW.wp_id = SLR.wp_id
          AND SLR.slr_datetime >= DATE(NOW())
          AND SLR.in_out_type IN ( 0, 1 )
        LIMIT 1
    )
  AND (
        WDS.upt_datetime IS NULL
        OR
        (
                WDS.upt_datetime IS NOT NULL
                AND WDS.upt_datetime > date_sub( NOW(), interval 22 minute )
                AND WDS.ble_check = 1
                AND WDS.loc_check = 1
            )
    )
;

-- 출역인원과 게이트인원 차이 비교 ( BLE )
select F1.worker_mb_id, ADB.mb_id
from (
         select distinct WPW.worker_mb_id
         FROM work_place_worker WPW
                  INNER JOIN work_place WP ON WPW.wp_id = WP.wp_id
                  LEFT JOIN worker_qr_enter WQE ON WQE.wp_id = WPW.wp_id
             and WQE.coop_mb_id = WPW.coop_mb_id
             AND WQE.mb_id = WPW.worker_mb_id
             AND WQE.enter_day = DATE(NOW())
         where WPW.wpw_out = 0
           AND WPW.wp_id = 'd2a3577f61af4fe4b36cc767bb6d0bbc'
           AND EXISTS(
                 select 1
                 from sensor_log_inout SLI
                 where SLI.wp_id = WPW.wp_id
                   and SLI.mb_id = WPW.worker_mb_id
                   AND SLI.si_type = '출입용'
                   AND SLI.sli_in_datetime >= DATE(NOW())
                   AND SLI.sli_in_datetime < DATE(DATE_ADD(NOW(), INTERVAL 1 DAY))
                 LIMIT 1
             )
     ) F1
         left join attendance_book ADB ON ADB.mb_id = F1.worker_mb_id AND ADB.working_day = DATE_FORMAT(NOW(),'%Y%m%d')
where ADB.mb_id is null
;

-- 출역인원과 게이트인원 차이 비교 ( GPS )
select
    F1.worker_mb_id,
    F1.coop_mb_name,
    G5M.mb_name,
    ADB.mb_id
from (
         select
             WPW.worker_mb_id,
             WPW.coop_mb_name
         FROM work_place_worker WPW
                  INNER JOIN work_place WP ON WPW.wp_id = WP.wp_id
         where WPW.wpw_out = 0
           AND WPW.wp_id = '2b625ebb1dec4474b28e3f9bdb9b72ca'
           AND EXISTS(
                 select 1
                 from sensor_log_inout SLI
                 where SLI.wp_id = WPW.wp_id
                   and SLI.mb_id = WPW.worker_mb_id
                   AND SLI.si_type = '출입용'
                   AND SLI.sli_in_datetime >= DATE(NOW())
                   AND SLI.sli_in_datetime < DATE(DATE_ADD(NOW(), INTERVAL 1 DAY))
                 LIMIT 1
             )
     ) F1
         left join attendance_book ADB ON ADB.mb_id = F1.worker_mb_id AND ADB.working_day = date_format(now(), '%Y%m%d')
         left join g5_member G5M ON G5M.mb_id = F1.worker_mb_id
where ADB.mb_id is null
;


-- 작업인원 차이 ( 잔류 - BLE )
select
    RES1.worker_mb_id
from (
         SELECT
             WPW.worker_mb_id
         FROM work_place_worker WPW
                  LEFT JOIN worker_device_status WDS ON WDS.mb_id = WPW.worker_mb_id
         WHERE WPW.wp_id = 'd2a3577f61af4fe4b36cc767bb6d0bbc'
           AND EXISTS(
                 SELECT 1
                 FROM sensor_log_recent SLR
                          INNER JOIN sensor_building_location SBL ON SBL.si_idx = SLR.si_idx
                 WHERE WPW.worker_mb_id = SLR.mb_id AND WPW.wp_id = SLR.wp_id
                   AND SLR.slr_datetime >= DATE(NOW())
                   AND SLR.in_out_type IN ( 0, 1 )
                 LIMIT 1
             )
           AND (
                 WDS.upt_datetime IS NULL
                 OR
                 (
                         WDS.upt_datetime IS NOT NULL
                         AND WDS.upt_datetime > date_sub( NOW(), interval 22 minute )
                         AND WDS.ble_check = 1
                         AND WDS.loc_check = 1
                     )
             )

     ) RES1
         left join (
    select
        FLT1.worker_mb_id
    from (
             select
                 WPW.wp_id,
                 WPW.worker_mb_id,
                 ATBK.work_status,
                 case when WPMC.support_gps = 1 then 0 when WPMC.support_ble = 1 then 1 else 0 end as need_device_check,
                 case
                     when WPMC.support_gps = 1 then
                         IFNULL((
                                    SELECT 1
                                    FROM gps_location GL
                                    WHERE WPW.worker_mb_id = GL.mb_id AND WPW.wp_id = GL.wp_id
                                      and GL.mb_id is not null
                                      AND GL.measure_time >= DATE_ADD(NOW(), INTERVAL -10 MINUTE)
                                    LIMIT 1
                                ), 0)
                     when WPMC.support_ble = 1 then
                         IFNULL((
                                    SELECT 1
                                    FROM sensor_log_recent SLR
                                             INNER JOIN sensor_building_location SBL ON SBL.si_idx = SLR.si_idx
                                    WHERE WPW.worker_mb_id = SLR.mb_id AND WPW.wp_id = SLR.wp_id
                                      AND SLR.slr_datetime  >= DATE(NOW())
                                      AND SLR.in_out_type IN ( 0, 1 )
                                    LIMIT 1
                                ), 0)
                     else 0
                     end as active_worker,
                 case when
                              WDS.upt_datetime IS NOT NULL
                              AND WDS.upt_datetime > date_sub(NOW(), interval 22 minute)
                              AND WDS.ble_check = 1
                              AND WDS.loc_check = 1
                          then 1 else 0
                     end as use_app_worker
             from work_place WP
                      inner join work_place_monitor_config WPMC ON WPMC.wp_id = WP.wp_id
                      inner join attendance_book ATBK ON ATBK.wp_id = WP.wp_id
                      inner join work_place_worker WPW ON ATBK.wp_id = WPW.wp_id AND ATBK.mb_id = WPW.worker_mb_id
                      LEFT JOIN worker_device_status WDS ON WDS.mb_id = WPW.worker_mb_id
             where WP.wp_end_status = 0
               AND WP.wp_id = 'd2a3577f61af4fe4b36cc767bb6d0bbc'
               AND ATBK.working_day = DATE_FORMAT(NOW(), '%Y%m%d')
               AND ATBK.work_in_method in ( 2, 3 )
               AND WPW.wpw_out = 0
         ) FLT1
    where case when FLT1.need_device_check = 1 then FLT1.use_app_worker * FLT1.active_worker else FLT1.active_worker end = 1
) RES2 ON RES1.worker_mb_id = RES2.worker_mb_id
where RES2.worker_mb_id IS NULL
;

-- 작업인원 차이 ( 잔류 - GPS )  ( sensor_log_in_out 차이에 의해 차이가 날 수 있다 )
select
    RES1.worker_mb_id
from (
         SELECT
             WPW.worker_mb_id
         FROM work_place_worker WPW
                  INNER JOIN gps_location GL ON WPW.worker_mb_id = GL.mb_id AND WPW.wp_id = GL.wp_id
         WHERE WPW.wp_id = '2b625ebb1dec4474b28e3f9bdb9b72ca'
           AND WPW.wpw_out = 0
           and GL.mb_id is not null
           AND GL.measure_time >= DATE_ADD(NOW(), INTERVAL -10 MINUTE)
     ) RES1
         left join (
    select
        FLT1.worker_mb_id
    from (
             select
                 WPW.wp_id,
                 WPW.worker_mb_id,
                 ATBK.work_status,
                 case when WPMC.support_gps = 1 then 0 when WPMC.support_ble = 1 then 1 else 0 end as need_device_check,
                 case
                     when WPMC.support_gps = 1 then
                         IFNULL((
                                    SELECT 1
                                    FROM gps_location GL
                                    WHERE WPW.worker_mb_id = GL.mb_id AND WPW.wp_id = GL.wp_id
                                      and GL.mb_id is not null
                                      AND GL.measure_time >= DATE_ADD(NOW(), INTERVAL -10 MINUTE)
                                    LIMIT 1
                                ), 0)
                     when WPMC.support_ble = 1 then
                         IFNULL((
                                    SELECT 1
                                    FROM sensor_log_recent SLR
                                             INNER JOIN sensor_building_location SBL ON SBL.si_idx = SLR.si_idx
                                    WHERE WPW.worker_mb_id = SLR.mb_id AND WPW.wp_id = SLR.wp_id
                                      AND SLR.slr_datetime  >= DATE(NOW())
                                      AND SLR.in_out_type IN ( 0, 1 )
                                    LIMIT 1
                                ), 0)
                     else 0
                     end as active_worker,
                 case when
                              WDS.upt_datetime IS NOT NULL
                              AND WDS.upt_datetime > date_sub(NOW(), interval 22 minute)
                              AND WDS.ble_check = 1
                              AND WDS.loc_check = 1
                          then 1 else 0
                     end as use_app_worker
             from work_place WP
                      inner join work_place_monitor_config WPMC ON WPMC.wp_id = WP.wp_id
                      inner join attendance_book ATBK ON ATBK.wp_id = WP.wp_id
                      inner join work_place_worker WPW ON ATBK.wp_id = WPW.wp_id AND ATBK.mb_id = WPW.worker_mb_id
                      LEFT JOIN worker_device_status WDS ON WDS.mb_id = WPW.worker_mb_id
             where WP.wp_end_status = 0
               AND WP.wp_id = '2b625ebb1dec4474b28e3f9bdb9b72ca'
               AND ATBK.working_day = DATE_FORMAT(NOW(), '%Y%m%d')
               AND ATBK.work_in_method in ( 2, 3 )
               AND WPW.wpw_out = 0
         ) FLT1
    where case when FLT1.need_device_check = 1 then FLT1.use_app_worker * FLT1.active_worker else FLT1.active_worker end = 1
) RES2 ON RES1.worker_mb_id = RES2.worker_mb_id
where RES2.worker_mb_id IS NULL
;


-- 근로자 출역 관리 종료 ( BLE )
select
    count(worker_mb_id) as total,
    sum( case when in_out_type IN ( 0, 1 ) and not_active_device = 0 then 1 else 0 end ) as active_cnt,
    sum( case when in_out_type NOT IN ( 0, 1 ) or not_active_device = 1 then 1 else 0 end ) as not_active,
    sum( case when not_active_device = 1 then 1 else 0 end ) as U_device_not_active,
    sum( case when in_out_type IN ( 2, 3 ) then 1 else 0 end ) as U_sensor_2_3_not_active,
    sum( case when in_out_type = -1  then 1 else 0 end ) as U_sensor_not_exists,
    sum( case when in_out_type IN ( 0, 1 ) and not_active_device = 1 then 1 else 0 end ) as device_not_active,
    sum( case when in_out_type IN ( 2, 3 ) and not_active_device = 1 then 1 else 0 end ) as device_not_active2,
    sum( case when in_out_type NOT IN ( 0, 1, 2, 3 ) and not_active_device = 1 then 1 else 0 end ) as device_not_active3,
    sum( case when in_out_type = 2 and not_active_device = 0 then 1 else 0 end ) as sensor_not_active_2,
    sum( case when in_out_type = 3 and not_active_device = 0 then 1 else 0 end ) as sensor_not_active_3,
    sum( case when in_out_type = -1 and not_active_device = 0 then 1 else 0 end ) as sensor_not_exists,
    sum( case when in_out_type NOT IN (-1, 0, 1, 2, 3 ) and not_active_device = 0 then 1 else 0 end ) as sensor_not_active_u
from (
         select WPW.worker_mb_id,
                IFNULL((
                           SELECT SLR.in_out_type
                           FROM sensor_log_recent SLR
                                    INNER JOIN sensor_building_location SBL ON SBL.si_idx = SLR.si_idx
                           WHERE WPW.worker_mb_id = SLR.mb_id AND WPW.wp_id = SLR.wp_id
                             AND SLR.slr_datetime >= DATE(NOW())
                           LIMIT 1
                       ), -1) as in_out_type,
                case when
                             WDS.upt_datetime IS NOT NULL
                             AND WDS.upt_datetime > date_sub( NOW(), interval 22 minute )
                             AND WDS.ble_check = 1
                             AND WDS.loc_check = 1
                         then 0 else 1 end as not_active_device
         FROM work_place_worker WPW
                  INNER JOIN work_place WP ON WPW.wp_id = WP.wp_id
                  LEFT JOIN worker_device_status WDS ON WDS.mb_id = WPW.worker_mb_id
         where WPW.wpw_out = 0
           AND WPW.wp_id = 'd2a3577f61af4fe4b36cc767bb6d0bbc'
           AND EXISTS(
                 select 1
                 from sensor_log_inout SLI
                 where SLI.wp_id = WPW.wp_id
                   and SLI.mb_id = WPW.worker_mb_id
                   AND SLI.si_type = '출입용'
                   AND SLI.sli_in_datetime >= DATE(NOW())
                   AND SLI.sli_in_datetime < DATE(DATE_ADD(NOW(), INTERVAL 1 DAY))
                 LIMIT 1
             )
     ) RES1
;
-- 근로자 출역 관리 종료 ( GPS )

select
    count(worker_mb_id) as total,
    sum( case when in_out_type IN ( 0, 1 ) and not_active_device = 0 then 1 else 0 end ) as active_cnt,
    sum( case when in_out_type NOT IN ( 0, 1 ) or not_active_device = 1 then 1 else 0 end ) as not_active,
    sum( case when not_active_device = 1 then 1 else 0 end ) as U_device_not_active,
    sum( case when in_out_type IN ( 2, 3 ) then 1 else 0 end ) as U_sensor_2_3_not_active,
    sum( case when in_out_type = -1  then 1 else 0 end ) as U_sensor_not_exists,
    sum( case when in_out_type IN ( 0, 1 ) and not_active_device = 1 then 1 else 0 end ) as device_not_active,
    sum( case when in_out_type IN ( 2, 3 ) and not_active_device = 1 then 1 else 0 end ) as device_not_active2,
    sum( case when in_out_type NOT IN ( 0, 1, 2, 3 ) and not_active_device = 1 then 1 else 0 end ) as device_not_active3,
    sum( case when in_out_type = 2 and not_active_device = 0 then 1 else 0 end ) as sensor_not_active_2,
    sum( case when in_out_type = 3 and not_active_device = 0 then 1 else 0 end ) as sensor_not_active_3,
    sum( case when in_out_type = -1 and not_active_device = 0 then 1 else 0 end ) as sensor_not_exists,
    sum( case when in_out_type NOT IN (-1, 0, 1, 2, 3 ) and not_active_device = 0 then 1 else 0 end ) as sensor_not_active_u
from (
         select WPW.worker_mb_id,
                IFNULL((
                           SELECT SLR.in_out_type
                           FROM sensor_log_recent SLR
                                    INNER JOIN sensor_building_location SBL ON SBL.si_idx = SLR.si_idx
                           WHERE WPW.worker_mb_id = SLR.mb_id AND WPW.wp_id = SLR.wp_id
                             AND SLR.slr_datetime >= DATE(NOW())
                           LIMIT 1
                       ), -1) as in_out_type,
                case when
                             WDS.upt_datetime IS NOT NULL
                             AND WDS.upt_datetime > date_sub( NOW(), interval 22 minute )
                             AND WDS.ble_check = 1
                             AND WDS.loc_check = 1
                         then 0 else 1 end as not_active_device
         FROM work_place_worker WPW
                  INNER JOIN work_place WP ON WPW.wp_id = WP.wp_id
                  LEFT JOIN worker_device_status WDS ON WDS.mb_id = WPW.worker_mb_id
         where WPW.wpw_out = 0
           AND WPW.wp_id = '2b625ebb1dec4474b28e3f9bdb9b72ca'
           AND EXISTS(
                 select 1
                 from sensor_log_inout SLI
                 where SLI.wp_id = WPW.wp_id
                   and SLI.mb_id = WPW.worker_mb_id
                   AND SLI.si_type = '출입용'
                   AND SLI.sli_in_datetime >= DATE(NOW())
                   AND SLI.sli_in_datetime < DATE(DATE_ADD(NOW(), INTERVAL 1 DAY))
                 LIMIT 1
             )
     ) RES1
;


########################################
## 출근부 업데이트 ( 안전조회 참가 현황 등 ) -  2019-10-01  이후부터 생성.
########################################
insert into attendance_book (
    working_day, wp_id, mb_id, coop_mb_id,
    work_in_date, work_in_method, work_in_mac_addr, work_in_device_nm, work_in_si_idx, work_in_desc,
    work_out_date, work_out_method, work_out_mac_addr, work_out_device_nm, work_out_si_idx, work_out_desc,
    working_comment, work_status, safety_edu_start, safety_edu_end, use_app, create_date)
select
    RES.working_day,
    RES.wp_id,
    RES.worker_mb_id,
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
    RES.safety_edu_start,
    RES.safety_edu_end,
    RES.use_app,
    NOW()
from (
         select
             FILT2.working_day,
             FILT2.wp_id,
             FILT2.worker_mb_id,
             FILT2.coop_mb_id,
             FILT2.work_start as work_in_date,
             4 as work_in_method,
             null as work_in_mac_addr,
             null as work_in_device_nm,
             null as work_in_si_idx,
             'system based sensor log' as work_in_desc,
             case
                 when FILT2.work_end = 0 then FILT2.work_start
                 else GREATEST( date_add(FILT2.work_start, interval 1 second) , FILT2.work_end)
                 end as work_out_date,
             4 as work_out_method,
             null as work_out_mac_addr,
             null as work_out_device_nm,
             null as work_out_si_idx,
             'system based sensor log' as work_out_desc,
             'system create based sensor log' as working_comment,
             2 as work_status,
             case
                 when FILT2.edu_start > now() then null
                 else FILT2.edu_start
                 end as safety_edu_start,
             case
                 when FILT2.edu_start > now() then null
                 when FILT2.edu_end = 0 then date_add(FILT2.edu_start, interval 1 second)
                 else GREATEST( date_add(FILT2.edu_start, interval 1 second), FILT2.edu_end)
                 end as safety_edu_end,
             case
                 when FILT2.edu_start > now() then 0
                 else 1
                 end as use_app
         from (
                  select
                      FILT1.working_day,
                      FILT1.wp_id,
                      FILT1.worker_mb_id,
                      FILT1.coop_mb_id,
                      MIN(FILT1.sli_in_datetime)                  as work_start,
                      MAX(IFNULL(FILT1.sli_out_datetime, 0))      as work_end,
                      MIN(FILT1.edu_start)                  as edu_start,
                      MAX(IFNULL(FILT1.edu_end, 0))      as edu_end
                  from (
                           select
                               DATE_FORMAT( DATE(SLI.sli_in_datetime), '%Y%m%d') as working_day,
                               WPW.wp_id,
                               WPW.worker_mb_id,
                               WPW.coop_mb_id,
                               SLI.sli_in_datetime,
                               SLI.sli_out_datetime,
                               IFNULL( (
                                           case when si_type = '안전조회' then SLI.sli_in_datetime else null end
                                           ),
                                       date_add(now(), interval 1 year )
                                   ) as edu_start,
                               IFNULL( (
                                           case when si_type = '안전조회' then SLI.sli_out_datetime else null end
                                           ),
                                       0
                                   ) as edu_end
                           from work_place_worker WPW
                                    inner join work_place WP ON WP.wp_id = WPW.wp_id
                                    inner join sensor_log_inout SLI ON WPW.wp_id = SLI.wp_id AND WPW.worker_mb_id = SLI.mb_id
                           WHERE WP.wp_end_status = 0
                             AND WPW.wpw_out = 0
                             AND SLI.sli_in_datetime >= DATE( DATE_SUB(DATE_ADD(LAST_DAY(NOW()), INTERVAL 1 DAY), INTERVAL 24 MONTH) )
                             AND SLI.sli_in_datetime < DATE( NOW())
                       ) FILT1
                  group by FILT1.working_day, FILT1.wp_id, FILT1.worker_mb_id
              ) FILT2
     ) RES
ON DUPLICATE KEY UPDATE
                     work_status = 2,
                     safety_edu_start = RES.safety_edu_start,
                     safety_edu_end = RES.safety_edu_end,
                     use_app = RES.use_app,
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


update attendance_book
set use_app = case when safety_edu_start is not null then 1 else 0 end
where use_app is null
;


insert into attendance_book_month_stat(working_month, wp_id, mb_id, coop_mb_id, working_day_count, safety_edu_count, use_app_count, create_date)
select
    RES.month,
    RES.wp_id,
    RES.mb_id,
    RES.coop_mb_id,
    RES.working_day_count,
    RES.safety_edu_count,
    RES.use_app_count,
    now()
from (
         select
             FILT2.month,
             FILT2.wp_id,
             FILT2.mb_id,
             FILT2.coop_mb_id,
             SUM( FILT2.attendance ) as working_day_count,
             SUM( FILT2.safety ) as safety_edu_count,
             SUM( FILT2.use_app ) as use_app_count
         from (
                  select
                      DATE_FORMAT( date(ATB.working_day), '%Y%m') as month,
                      ATB.wp_id,
                      ATB.mb_id,
                      ATB.coop_mb_id,
                      1 as attendance,
                      case when ATB.safety_edu_start IS NOT NULL then 1 else 0 end as safety,
                      ATB.use_app
                  from attendance_book ATB
                  where ATB.coop_mb_id IS NOT NULL
              ) FILT2
         group by FILT2.month, FILT2.wp_id, FILT2.mb_id, FILT2.coop_mb_id
     ) RES
ON DUPLICATE KEY UPDATE
                     working_day_count = RES.working_day_count,
                     safety_edu_count = RES.safety_edu_count,
                     use_app_count = RES.use_app_count
;



######################################
## 최근 일주일 이내 현장 센서별 센싱 수
######################################

select
    SI.si_idx, SI.si_code, SI.sd_name, SI.si_place1, SI.si_place2, SI.building_name, SI.floor_name,
    IFNULL( DET.DETECT_CNT, 0 ) as DETECT_CNT
FROM (
         select
             SI.si_idx, SI.si_code, SI.sd_name, SI.si_place1, SI.si_place2, BD.building_name, BDF.floor_name
         from sensor_info SI
                  left join sensor_building_location sbl on SI.si_idx = sbl.si_idx
                  left join building_floor BDF on BDF.building_no = sbl.building_no and BDF.floor = sbl.floor
                  left join building BD on BD.building_no = sbl.building_no
         where SI.wp_id = 'd2a3577f61af4fe4b36cc767bb6d0bbc'
     ) SI
         left join (
    select
        SI.si_code, count(*) as DETECT_CNT
    from sensor_info SI
             left join sensor_log_trace SLT ON SI.si_code = SLT.si_code AND SI.wp_id = SLT.wp_id
    where SLT.slt_in_datetime >= date_sub( date(now()), interval 7 day )
      and SI.wp_id = 'd2a3577f61af4fe4b36cc767bb6d0bbc'
    group by SI.si_code
) DET ON SI.si_code = DET.si_code
order by SI.si_code
;

######################################
## 테이블명 용량 체크
######################################
SELECT table_name AS 'TableName',
       ROUND(SUM(data_length+index_length)/(1024*1024), 2) AS 'All(MB)',
       ROUND(data_length/(1024*1024), 2) AS 'Data(MB)',
       ROUND(index_length/(1024*1024), 2) AS 'Index(MB)'
FROM information_schema.tables
where table_schema = 'hulan'
GROUP BY table_name
ORDER BY data_length DESC;

######################################
## 파티션 테이블 확인
######################################
select *
from information_schema.PARTITIONS
where TABLE_SCHEMA = 'hulan'
  and PARTITION_NAME is not null;


######################################
## 유해물질 데이터 추출
######################################

select
    WDI.device_id, GL.*
from work_device_info WDI
         inner join gas_log GL ON GL.mac_address = WDI.mac_address
where WDI.wp_id = '13c8a3bfe64841dfb190cbde8893114b'
  and WDI.mac_address = 'A8-40-41-20-C5-50-41-50-59'
  and GL.measure_time >= STR_TO_DATE( '2021-12-09 18:00:00', '%Y-%m-%d %H:%i:%s')
  and GL.measure_time <= STR_TO_DATE( '2021-12-10 06:00:00', '%Y-%m-%d %H:%i:%s')
;




-- 앱종료 알림 근로자 리스트 검색
select
    G5M.mb_id,
    G5M.mb_name,
    G5MCOOP.mb_name as coop_mb_name,
    AMI.subject,
    AMI.msg,
    AMI.upt_datetime
from admin_msg_info AMI
         inner join g5_member G5M ON G5M.mb_id = AMI.mb_id
         inner join work_place_worker WPW ON WPW.wp_id = AMI.wp_id AND WPW.worker_mb_id = AMI.mb_id
         inner join g5_member G5MCOOP ON G5MCOOP.mb_id = WPW.coop_mb_id
where AMI.wp_id = '9305f8013d3c43f0af9a41b469be5f96'
  AND msg_type = 120
  and AMI.upt_datetime >= DATE('20211025')
  and AMI.upt_datetime < DATE('20211030')
order by AMI.upt_datetime
;


-- 종료 처리 사유
select
    RESULT_RES.*,
    case
        when RESULT_RES.close_reason IN ( 11, 12 ) then '단말 상태 로그 없음. 앱 종료'
        when RESULT_RES.close_reason =  13 then 'APP 종료 내지는 음영구역. 앱 종료'
        when RESULT_RES.close_reason IN  ( 14, 15 ) then 'BLE/GPS 사용 미설정. 앱 상태 오류'
        when RESULT_RES.close_reason IN ( 21, 22 ) then 'GPS 로그 미수신 혹은 만료'
        when RESULT_RES.close_reason IN ( 31, 32 ) then '센서 감지 이력 없음. 센서 미감지'
        when RESULT_RES.close_reason =  33 then '외출/퇴근'
        when RESULT_RES.close_reason =  34 then '미등록 센서에 감지. 센서 미감지'
        else ''
        end as reason_name
from (
         select
             RES.wp_name,
             G5MCOOP.mb_name as coop_mb_name,
             RES.worker_mb_id,
             G5M.mb_name as worker_mb_name,
             (select section_name from work_section where section_cd = WPC.work_section_a) as work_section_name_a,
             (select section_name from work_section where section_cd = RES.work_section_b) as work_section_name_b,
             RES.measure_time,
             greatest(
                     COALESCE(
                             CASE
                                 WHEN need_device_check = 1 and RES.upt_datetime IS NULL THEN null
                                 WHEN need_device_check = 1 and RES.upt_datetime < date(now()) THEN null
                                 WHEN need_device_check = 1 and RES.upt_datetime <= date_sub( NOW(), interval 22 minute ) THEN RES.upt_datetime
                                 WHEN need_device_check = 1 and RES.ble_check != 1 THEN RES.upt_datetime
                                 WHEN need_device_check = 1 and RES.loc_check != 1 THEN RES.upt_datetime
                                 ELSE RES.close_time
                                 END, 0 )
                 ,RES.measure_time
                 ) as close_time,
             CASE
                 WHEN need_device_check = 1 and RES.upt_datetime IS NULL THEN 11
                 WHEN need_device_check = 1 and RES.upt_datetime < date(now()) THEN 12
                 WHEN need_device_check = 1 and RES.upt_datetime <= date_sub( NOW(), interval 22 minute ) THEN 13
                 WHEN need_device_check = 1 and RES.ble_check != 1 THEN 14
                 WHEN need_device_check = 1 and RES.loc_check != 1 THEN 15
                 ELSE RES.close_reason
                 END as close_reason

         FROM (
                  select
                      WP.wp_id,
                      WP.wp_name,
                      WPW.coop_mb_id,
                      WPW.worker_mb_id,
                      WPW.work_section_b,
                      ATBK.work_in_temperature as temperature,
                      case
                          when ATBK.mb_id is null then 4 else ATBK.work_in_method
                          end as commute_type,
                      case
                          when ATBK.mb_id is null then
                              (
                                  select
                                      MIN(sli_in_datetime)
                                  from sensor_log_inout SLI
                                  where SLI.wp_id = WPW.wp_id
                                    AND SLI.mb_id = WPW.worker_mb_id
                                    AND SLI.si_type = '출입용'
                                    AND SLI.sli_in_datetime >= DATE(NOW())
                                    AND SLI.sli_in_datetime <  DATE(DATE_ADD(NOW(), INTERVAL 1 DAY))
                              )
                          else ATBK.work_in_date
                          end as measure_time,
                      case
                          when WPMC.support_gps = 1 then 0
                          when WPMC.support_ble = 1 then 1
                          else 0
                          end as need_device_check,
                      case
                          when WPMC.support_gps = 1 then
                              IFNULL((
                                         SELECT 1
                                         FROM gps_location GL
                                         WHERE WPW.worker_mb_id = GL.mb_id AND WPW.wp_id = GL.wp_id
                                           and GL.mb_id is not null
                                           AND GL.measure_time >= DATE_ADD(NOW(), INTERVAL -10 MINUTE)
                                         LIMIT 1
                                     ), 0)
                          when WPMC.support_ble = 1 then
                              IFNULL((
                                         SELECT 1
                                         FROM sensor_log_recent SLR
                                                  INNER JOIN sensor_building_location SBL ON SBL.si_idx = SLR.si_idx
                                         WHERE WPW.worker_mb_id = SLR.mb_id AND WPW.wp_id = SLR.wp_id
                                           AND SLR.slr_datetime  >= DATE(NOW())
                                           AND SLR.in_out_type IN ( 0, 1 )
                                         LIMIT 1
                                     ), 0)
                          else 0
                          end as active_worker,
                      case
                          when
                              (
                                      WDS.upt_datetime IS NOT NULL
                                      AND WDS.upt_datetime > date_sub( NOW(), interval 22 minute )
                                      AND WDS.ble_check = 1
                                      AND WDS.loc_check = 1
                                  )
                              then 1 else 0
                          end as use_app_worker,
                      case
                          when WPMC.support_gps = 1 then
                              IFNULL((
                                         SELECT
                                             CASE
                                                 WHEN GL.measure_time IS NULL THEN 21
                                                 WHEN GL.measure_time < DATE_ADD(NOW(), INTERVAL -10 MINUTE) THEN 22
                                                 ELSE 1
                                                 END
                                         FROM gps_location GL
                                         WHERE WPW.worker_mb_id = GL.mb_id AND WPW.wp_id = GL.wp_id
                                         LIMIT 1
                                     ), 21)
                          when WPMC.support_ble = 1 then
                              IFNULL((
                                         SELECT
                                             CASE
                                                 WHEN SLR.si_idx IS NULL THEN 31
                                                 WHEN SLR.slr_datetime < date(now()) THEN 32
                                                 WHEN SLR.in_out_type IN ( 2, 3 ) THEN 33
                                                 WHEN SBL.building_no IS NULL THEN 34
                                                 ELSE 1
                                                 END
                                         FROM sensor_log_recent SLR
                                                  LEFT JOIN sensor_building_location SBL ON SBL.si_idx = SLR.si_idx
                                         WHERE WPW.worker_mb_id = SLR.mb_id AND WPW.wp_id = SLR.wp_id
                                         LIMIT 1
                                     ), 31 )
                          else 0
                          end as close_reason,
                      case
                          when WPMC.support_gps = 1 then
                              IFNULL((
                                         SELECT
                                             GL.measure_time
                                         FROM gps_location GL
                                         WHERE WPW.worker_mb_id = GL.mb_id AND WPW.wp_id = GL.wp_id
                                           AND GL.measure_time >= DATE(NOW())
                                         LIMIT 1
                                     ), WDS.upt_datetime)
                          when WPMC.support_ble = 1 then
                              IFNULL((
                                         SELECT
                                             SLR.slr_datetime
                                         FROM sensor_log_recent SLR
                                         WHERE WPW.worker_mb_id = SLR.mb_id AND WPW.wp_id = SLR.wp_id
                                           AND SLR.slr_datetime >= DATE(NOW())
                                         LIMIT 1
                                     ), WDS.upt_datetime)
                          else null
                          end as close_time,
                      WDS.upt_datetime,
                      WDS.ble_check,
                      WDS.loc_check
                  from work_place_worker WPW
                           inner join work_place_monitor_config WPMC ON WPMC.wp_id = WPW.wp_id
                           inner join work_place WP ON WP.wp_id = WPW.wp_id
                           left join attendance_book ATBK ON ATBK.wp_id = WPW.wp_id
                      AND ATBK.mb_id = WPW.worker_mb_id
                      AND ATBK.working_day = DATE_FORMAT(NOW(), '%Y%m%d')
                           LEFT JOIN worker_device_status WDS ON WDS.mb_id = WPW.worker_mb_id
                  where WP.wp_end_status = 0
                    AND WP.wp_id = '13c8a3bfe64841dfb190cbde8893114b'
                    AND WPW.wpw_out = 0
                    AND (
                          ATBK.mb_id IS NOT NULL
                          OR
                          EXISTS(
                                  select 1
                                  from sensor_log_inout SLI
                                  where SLI.wp_id = WPW.wp_id
                                    and SLI.mb_id = WPW.worker_mb_id
                                    AND SLI.si_type = '출입용'
                                    AND SLI.sli_in_datetime >= DATE(NOW())
                                    AND SLI.sli_in_datetime < DATE(DATE_ADD(NOW(), INTERVAL 1 DAY))
                                  LIMIT 1
                              )
                      )
              ) RES
                  inner join work_place_coop WPC ON WPC.wp_id = RES.wp_id and WPC.coop_mb_id = RES.coop_mb_id
                  inner join g5_member G5M ON G5M.mb_id = RES.worker_mb_id
                  inner join g5_member G5MCOOP ON G5MCOOP.mb_id = RES.coop_mb_id
         where (case when RES.need_device_check = 1 then RES.use_app_worker * RES.active_worker else RES.active_worker end) = 0
     ) RESULT_RES
;

######################################
## 출역인원 중 앱 미사용자 리스트
######################################
select
    working_day,
    ( select mb_name from g5_member G5M where G5M.mb_id = ADB.coop_mb_id ) as coop_mb_name,
    mb_id,
    ( select mb_name from g5_member G5M where G5M.mb_id = ADB.mb_id ) as mb_name,
    case when use_app = 1 then '사용' else '미사용' end
from attendance_book ADB
where ADB.working_day = '20211220'
  and ADB.mb_level = 2
  and ADB.wp_id = '13c8a3bfe64841dfb190cbde8893114b'
order by coop_mb_name, mb_name
;

######################################
## 기울기 센서 데이터 추출
######################################
select
    TL.*
from tilt_log TL
         inner join work_device_info WDI ON TL.mac_address = WDI.mac_address
where WDI.wp_id = '44f3743c161449fb9d78b159c3e62ca3'
  AND TL.mac_address = '11-01-01-01-00-00-01-02'
  AND TL.measure_time >= DATE('20211201')
order by TL.measure_time desc
;

######################################
## 안전고리 데이터 추출
######################################
select
    mb_id, event_date,
    case
        when event_type = 1 then
            case when pairing_status = 1 then '연결' else '연결해제' end
        when event_type = 2 then
            case when district_enter_status = 1 then '감지구역 진입' else '감지구역 이탈' end
        when event_type = 3 then
            case when lock_status = 1 then '안전고리 체결' else '안전고리 해제' end
        else 'Unknown Event'
        end as event_type,
    mac_address
from safety_hook_event_log SHEL
where SHEL.wp_id = '0e3384bdf0734a269adc34393739c4ad'
  and event_date >= DATE('20211201')
order by event_date desc
;