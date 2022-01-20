DROP TRIGGER IF EXISTS worker_device_status_after_update;

create definer = kunsulup@`%` trigger worker_device_status_after_update
    after update
    on worker_device_status
    for each row
BEGIN
    IF NEW.upt_datetime > OLD.upt_datetime then
        insert into worker_device_status_hist (mb_id, app_version, os_type, os_version, device_model, battery, charge_check, ble_check, loc_check, si_code, upt_datetime )
        values( NEW.mb_id, NEW.app_version, NEW.os_type, NEW.os_version, NEW.device_model, NEW.battery, NEW.charge_check, NEW.ble_check, NEW.loc_check, NEW.si_code, NEW.upt_datetime )
        ;
    END IF;
END;

