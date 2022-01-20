DROP TRIGGER IF EXISTS work_place_coop_before_update;

create definer = kunsulup@`%` trigger work_place_coop_before_update
    before update
    on work_place_coop
    for each row
BEGIN
    IF ( NEW.cc_id is null ) THEN
        SET NEW.cc_id = (
            select cc_id from work_place where wp_id = NEW.wp_id
        );
    end if;

END;
