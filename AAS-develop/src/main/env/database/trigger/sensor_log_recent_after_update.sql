DROP TRIGGER IF EXISTS sensor_log_recent_after_update;

create definer = kunsulup@`%` trigger sensor_log_recent_after_update
    after update
    on sensor_log_recent
    for each row
BEGIN
    IF NEW.slr_datetime > OLD.slr_datetime then
        insert into sensor_log_recent_hist (wp_id, si_idx, coop_mb_id, mb_id, in_out_type, slr_datetime )
        values( NEW.wp_id, NEW.si_idx, NEW.coop_mb_id, NEW.mb_id, NEW.in_out_type, NEW.slr_datetime )
        ;
    END IF;
END;
