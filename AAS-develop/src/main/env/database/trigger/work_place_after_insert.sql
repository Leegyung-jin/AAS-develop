DROP TRIGGER IF EXISTS work_place_after_insert;

create definer = kunsulup@`%` trigger work_place_after_insert
    after insert
    on work_place
    for each row
BEGIN

    insert into app_menu_visibility
    select NEW.wp_id, mb_level, area_name, no, title, visibility, display_order from app_menu_visibility where wp_id = 'default';

END;

