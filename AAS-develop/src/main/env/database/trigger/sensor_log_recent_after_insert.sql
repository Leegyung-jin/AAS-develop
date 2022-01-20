DROP TRIGGER IF EXISTS sensor_log_recent_after_insert;

create definer = kunsulup@`%` trigger sensor_log_recent_after_insert
    after insert
    on sensor_log_recent
    for each row
BEGIN
    insert into sensor_log_recent_hist (wp_id, si_idx, coop_mb_id, mb_id, in_out_type, slr_datetime )
    values( NEW.wp_id, NEW.si_idx, NEW.coop_mb_id, NEW.mb_id, NEW.in_out_type, NEW.slr_datetime )
    ;
END;

