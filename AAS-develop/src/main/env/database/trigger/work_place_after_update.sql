DROP TRIGGER IF EXISTS work_place_after_update;

create definer = kunsulup@`%` trigger work_place_after_update
    after update
    on work_place
    for each row
BEGIN

    --
END;
