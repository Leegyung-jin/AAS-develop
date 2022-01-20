
INSERT INTO g5_member (
    mb_id, mb_password, mb_name, mb_nick, mb_nick_date,
    mb_email, mb_homepage, mb_level, mb_sex, mb_birth,
    mb_tel, mb_hp, mb_certify, mb_adult, mb_dupinfo,
    mb_zip1, mb_zip2, mb_addr1, mb_addr2, mb_addr3,
    mb_addr_jibeon, mb_signature, mb_recommend, mb_point, mb_today_login,
    mb_login_ip, mb_datetime, mb_ip, mb_leave_date, mb_intercept_date,
    mb_email_certify, mb_email_certify2, mb_memo, mb_lost_certify, mb_mailling,
    mb_sms, mb_open, mb_open_date, mb_profile, mb_memo_call,
    mb_1, mb_2, mb_3, mb_4, mb_5, mb_6, mb_7, mb_8, mb_9, mb_10, mb_11, mb_12, mb_13, mb_14, mb_15, mb_16, mb_17, mb_18, mb_19, mb_20)
VALUES (
           'admin', '*BDBEFEEEFEA8C7F2540F1A22795C989DA5562AB1', '휴랜', '휴랜', NOW(),
           '', '', 10, '', '',
           '010-4444-5889', '', '', 0, '',
           '067', '66', '서울 서초구 송동길 18', '11000', ' (우면동)',
           'R', '', '', 0, '2019-10-07 17:42:00',
           '211.196.191.85', '2018-10-15 14:25:27', '183.102.228.181', '', '',
           '2018-10-15 14:25:27', '', '메모', '', 0,
           0, 0, NOW(), '', '',
           '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', 'ezxx5qTYPPs:APA91bEcorwKCxLt1MnPWP_uJ766jm29SmQ2LD7Qg3S_do64seG8j3D6NF-wtj8i6RUUec1rj_Z9aEdt0cEai-rua8uapC6I42mKrweBGsw-p-rStOyaXa29kOy0q3uvgmleGhM_mNvk');



insert into hulan_sequence ( seq_name, next_val, etc_1 )
    value( 'mb_key', 0, DATE_FORMAT(NOW(), '%y-%m%d'));


INSERT INTO oauth_client_details (client_id, client_secret, scope, authorized_grant_types, access_token_validity, refresh_token_validity)
VALUES ('hulan', '*6E08455D8EC382D58829487654FEDA76ABAA5E3A', 'admin', 'authorization_code,password,refresh_token', 36000, 2592000);


-- 장비 타입 추가
insert into icon_code (icon_name, icon_url) value ( '백호', 'http://kun.hulan.co.kr/static/image/new_icon/pork.png' );
insert into icon_code (icon_name, icon_url) value ( '페이로더', 'http://kun.hulan.co.kr/static/image/new_icon/payloader.gif' );
insert into icon_code (icon_name, icon_url) value ( '살수차(물차)', 'http://kun.hulan.co.kr/static/image/new_icon/sprintkr.gif' );
insert into icon_code (icon_name, icon_url) value ( '플랜트', 'http://kun.hulan.co.kr/static/image/new_icon/plant.gif' );
insert into icon_code (icon_name, icon_url) value ( '크레인', 'http://kun.hulan.co.kr/static/image/new_icon/crane.gif' );
insert into icon_code (icon_name, icon_url) value ( '천공기', 'http://kun.hulan.co.kr/static/image/new_icon/boring-machine.gif' );


insert into gas_safe_range_policy ( policy_no, policy_name, create_date, creator, update_date, updater)
values
(1, '디폴트 유해물질 측정 임계치', now(), 'admin', now(), 'admin'),
(2, '하남감일 유해물질 측정 임계치', now(), 'admin', now(), 'admin');
;

insert into gas_category (category, category_name, min_measure, max_measure, unit, ordering)
values
(1, '온도', -50, 100, '℃' , 5),
(2, '산소', 0, 100, '%' , 1),
(3, '황화수소', 0, 1500, 'ppm' , 2 ),
(4, '일산화탄소', 0, 30, 'ppm', 3 ),
(5, '메탄', 0, 100, '%' , 4),
(6, '습도', 0, 100, '%' , 6)
;


INSERT INTO ui_component (cmpt_id, cmpt_name, description, file_name, file_name_org, file_path, file_location, status, create_date, creator, update_date, updater)
VALUES
('DANGER_WORKER', '위험근로자', '위험지역 접근/고위험/추락감지 근로자 모니터링 컴포넌트', '20210302163430969_GDCdGVUKeq.png', '컴포넌트_근로자정보.png', 'uicomponent/DANGER_WORKER/', 0, 1, now(), 'admin', now(), 'admin'),
('EQUIP_MGR', '장비 및 차량', '장비 및 차량 현황 모니터링 및 관리 컴포넌트', '20210302163440043_AutMm51mG4.png', '컴포넌트_장비및차량.png', 'uicomponent/EQUIP_MGR/', 0, 1, now(), 'admin', now(), 'admin'),
('HAZARD', '유해물질', '유해물질 모니터링 컴포넌트', '20210302163448792_Apkw0Dh7UX.png', '컴포넌트_유해물질.png', 'uicomponent/HAZARD/', 0, 1, now(), 'admin', now(), 'admin'),
('NOTICE', '알림 및 공지', '관리자 알림 내역 및 공지사항 컴포넌트', '20210302163455233_HMcFvxpBBM.png', '컴포넌트_알림및공지.png', 'uicomponent/NOTICE/', 0, 1, now(), 'admin', now(), 'admin'),
('WORK_LOG', '근로자 출력관리', '전체 출력인원 및 협력사별 출력인원 모니터링 컴포넌트', '20210302163505122_DfLRXodZE8.png', '컴포넌트_근로자출력관리.png', 'uicomponent/WORK_LOG/', 0, 1, now(), 'admin', now(), 'admin'),
('CCTV', 'CCTV', 'CCTV  Component', '20210302163535893_jgrRVDoYw8.png', '컴포넌트_cctv.png', 'uicomponent/CCTV/', 0, 1, now(), 'admin', now(), 'admin')
;

insert admin_msg_type (msg_type, msg_name, description, alarm_grade, create_date, creator, update_date, updater)
values
( 100, '근로자 위험지역 접근', '위헙지역 접근시 발생', 1, now(), 'admin', now(), 'admin' ),
( 110, '근로자 위험지역 접근', '과거 위험지역 접근 코드', 1, now(), 'admin', now(), 'admin' ),
( 120, '앱 필수기능 해제', 'BLE/GPS OFF 혹은 단말앱 종료시 발생', 1, now(), 'admin', now(), 'admin' )
;


insert into entr_gate_account ( account_id, account_pwd, account_name, description, status, creator, updater )
values
('hulan', concat('*', upper(convert(sha1(unhex(sha1('hulanpwd'))) USING utf8mb4))), 'HULAN', 'HULAN 출입게이트 연동', 1, 'admin', 'admin' )
;
-- sample
-- insert into entr_gate_workplace ( wp_id, account_id, mapping_cd, status, api_type, creator, updater ) values ('d044dcca224647f89d320d3951922e67', 'hulan',   'HUW0000001', 1, 1, 'admin', 'admin' );

INSERT INTO ordering_office (office_name, telephone, biznum, zonecode, bcode, address, address_detail, sido, sigungu, bname, icon_file_name, icon_file_name_org, icon_file_path, icon_file_location, bg_img_file_name, bg_img_file_name_org, bg_img_file_path, bg_img_file_location, bg_color, create_date, creator, update_date, updater)
VALUES ('휴랜 발주사', '032-719-8962', '7618701070', '22013', '2818510600', '인천 연수구 인천타워대로 99 (애니오션빌딩)', '애니오션빌딩 8층', '인천', '연수구', '송도동', '20210819103055097_Wv5qLaKalh.png', 'hulan_logo.png', 'office/1/', 0, null, null, null, null, 'yellow', now(), 'admin', now(), 'admin');

INSERT INTO hulan_ui_component (hcmpt_id, hcmpt_name, description, site_type, ui_type, width, height, file_name, file_name_org, file_path, file_location, status, create_date, creator, update_date, updater)
VALUES
('MAIN1', '메인화면', '통합관제 메인 화면1', 2, 1, 3, 2, 'mainComp.png', '메인컴포넌트.png', 'image/comp/', 0, 1, now(), 'admin', now(), 'admin'),
('SAFETY_STATUS', '안전 점수 현황', '통합관제 안전 점수 현황 컴포넌트', 2, 2, 3, 1, 'safetyComp.png', '컴포넌트_안전점수현황.png', 'image/comp/', 0, 1, now(), 'admin', now(), 'admin'),
('RISK_ASSMN', '위험성 평가', '통합관제 위험성 평가 컴포넌트', 2, 2, 1, 1, 'riskAccessmentComp.png', '컴포넌트_위험성평가.png', 'image/comp/', 0, 1, now(), 'admin', now(), 'admin'),
('COOP_MGR', '협력사 관리', '통합관제 협력사 관리 컴포넌트', 2, 2, 1, 1, 'coopMgrComp.png', '컴포넌트_협력사관리.png', 'image/comp/', 0, 1, now(), 'admin', now(), 'admin'),
('WORKER_LOG', '근로자 출력인원', '통합관제 근로자 출력인원 컴포넌트', 2, 2, 1, 1, 'attendanceComp.png', '컴포넌트_월간출역인원.png', 'image/comp/', 0, 1, now(), 'admin', now(), 'admin')
;

INSERT INTO hulan_ui_component (hcmpt_id, hcmpt_name, description, site_type, ui_type, width, height, file_name, file_name_org, file_path, file_location, status, create_date, creator, update_date, updater)
VALUES
('I_MAIN', 'IMOS 메인화면', 'IMOS 메인 화면', 1, 1, 3, 2, 'imainComp.png', '컴포넌트_메인.png', 'image/comp/', 0, 1, now(), 'admin', now(), 'admin')
;