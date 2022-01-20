DROP TRIGGER IF EXISTS work_place_after_delete;

create definer = kunsulup@`%` trigger work_place_after_delete
    after delete
    on work_place
    for each row
BEGIN
    DELETE FROM app_menu_visibility WHERE wp_id = OLD.wp_id;

END;

