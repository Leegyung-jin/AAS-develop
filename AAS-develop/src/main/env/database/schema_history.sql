#####################################
## 통합 관제  ( QA 상용 미적용 )
#####################################
-- 1. 발주사 및 건설사 계정 필요

-- --------------------------------------------------------------------------------------
-- create table
-- --------------------------------------------------------------------------------------

CREATE TABLE ordering_office (
    office_no	        bigint	        NOT NULL	AUTO_INCREMENT PRIMARY KEY  COMMENT '발주처번호',
    office_name	        varchar(128)    NOT NULL	COMMENT '발주처명',
    telephone	        varchar(32)	    NOT NULL	COMMENT '발주처 연락처',
    biznum               varchar(10)	    NOT NULL	COMMENT '사업자번호',
    zonecode             varchar(5) not null comment '새우편번호',
    bcode                varchar(10) not null comment '법정동코드',
    address              varchar(256) not null comment '기본주소',
    address_detail       varchar(256) null comment '상세주소',
    sido                 varchar(32) null comment '시도명',
    sigungu              varchar(32) null comment '시군구명',
    bname                varchar(32) null comment '법정동/법정리 이름',
    icon_file_name       varchar(255) null comment '아이콘 파일명',
    icon_file_name_org   varchar(255) null comment '아이콘 원본 파일명',
    icon_file_path       varchar(255) null comment '아이콘 저장 위치(상대경로)',
    icon_file_location   integer null comment '아이콘 저장소. 0: local Storage ',
    bg_img_file_name     varchar(255) null comment '백그라운드 이미지 파일명',
    bg_img_file_name_org varchar(255) null comment '백그라운드 이미지 원본 파일명',
    bg_img_file_path     varchar(255) null comment '백그라운드 이미지 저장 위치(상대경로)',
    bg_img_file_location integer null comment '백그라운드 이미지 저장소. 0: local Storage ',
    bg_color             varchar(16) null comment '백그라운드 색',
    create_date          datetime default current_timestamp not null comment '생성일',
    creator              varchar(20) not null comment '생성자',
    update_date          datetime default current_timestamp not null comment '최종 수정일',
    updater              varchar(20) not null comment '최종 수정자',
    unique(biznum)
) ENGINE=InnoDB  charset=utf8 COMMENT '발주처 정보';

CREATE TABLE office_workplace_grp (
    wp_grp_no           bigint	        NOT NULL AUTO_INCREMENT PRIMARY KEY	COMMENT '발주처 현장관리 그룹 번호',
    office_no	        bigint	        NOT NULL	COMMENT '발주처번호',
    office_grp_name	    varchar(128)    NOT NULL	COMMENT '발주처 현장관리 그룹명',
    create_date         datetime default current_timestamp not null comment '생성일',
    creator             varchar(20) not null comment '생성자',
    update_date         datetime default current_timestamp not null comment '최종 수정일',
    updater             varchar(20) not null comment '최종 수정자',
    foreign key (office_no) references ordering_office(office_no) on delete cascade
) ENGINE=InnoDB  charset=utf8 COMMENT '발주처 현장관리 그룹 정보';

CREATE TABLE office_workplace_manager (
    wp_grp_no	       bigint	NOT NULL	COMMENT '발주처 관리그룹 번호',
    mb_id	            varchar(20)	NOT NULL	COMMENT '사용자 아이디',
    create_date         datetime default current_timestamp not null comment '생성일',
    creator             varchar(20) not null comment '생성자',
    primary key (wp_grp_no, mb_id),
    foreign key (wp_grp_no) references office_workplace_grp(wp_grp_no) on delete cascade
) ENGINE=InnoDB  charset=utf8 COMMENT '발주처 현장관리자 정보';

alter table g5_member
    add office_no	        bigint	        NULL  COMMENT '발주처번호 ( level 이 6: 발주처 현장그룹 매니저, 7: 발주처 관리자인 경우 필수 )',
    modify cc_id            varchar(50) null comment '건설사 아이디 ( level 이 5: 건설사 관리자 인 경우 필수 )',
    add index(cc_id),
    add CONSTRAINT fk_member_office_no foreign key (office_no) references ordering_office(office_no) on delete set null
;

-- --------------------------------------------------------------------------------------
-- create data and migration
-- --------------------------------------------------------------------------------------

insert into ordering_office (office_no, office_name, telephone, biznum, zonecode, bcode, address, address_detail, sido, sigungu, bname, create_date, creator, update_date, updater)
values(1, '휴랜 발주사', '032-719-8962', '7618701070', '22013', '2818510600', '인천 연수구 인천타워대로 99 (애니오션빌딩)', '애니오션빌딩 8층', '인천', '연수구', '송도동',  now(), 'admin', now(), 'admin');

insert into office_workplace_grp (wp_grp_no, office_no, office_grp_name, create_date, creator, update_date, updater)
values(1, 1, '휴랜 현장그룹', now(), 'admin', now(), 'admin');

alter table work_place
    add wp_start_date   date null comment '착공일' after wp_tel,
    add uninjury_record_date datetime null default now() comment '무재해 시작일',
    add wp_grp_no	    bigint	NULL default 1 COMMENT '발주처 현장그룹 번호',
    add CONSTRAINT fk_workplace_wp_grp_no foreign key (wp_grp_no) references office_workplace_grp(wp_grp_no) on delete set null
;

INSERT INTO g5_member (mb_id, mb_password, mb_name, mb_nick, mb_nick_date, mb_email, mb_homepage, mb_level, mb_sex, mb_birth, mb_tel, mb_hp, mb_certify, mb_adult, mb_dupinfo, mb_zip1, mb_zip2, mb_addr1, mb_addr2, mb_addr3, mb_addr_jibeon, mb_signature, mb_recommend, mb_point, mb_today_login, mb_login_ip, mb_datetime, mb_ip, mb_leave_date, mb_intercept_date, mb_email_certify, mb_email_certify2, mb_memo, mb_lost_certify, mb_mailling, mb_sms, mb_open, mb_open_date, mb_profile, mb_memo_call, mb_1, mb_2, mb_3, mb_4, mb_5, mb_6, mb_7, mb_8, mb_9, mb_10, mb_11, mb_12, mb_13, mb_14, mb_15, mb_16, mb_17, mb_18, mb_19, mb_20, app_version, cc_id, work_section_a, pwd_change_date, attempt_login_count, office_no)
VALUES
('hulanoffice', '*BDBEFEEEFEA8C7F2540F1A22795C989DA5562AB1', '휴랜 발주처 관리자', '휴랜 발주처 관리자', '2019-10-08', '', '', 7, '', '', '0327198962', '', '', 0, '', '220', '07', '인천 연수구 인천타워대로 323', '', ' (송도동, 송도 센트로드)', 'R', '', '', 0, '2021-04-29 13:32:06', '106.101.0.52', '2018-10-15 14:25:27', '183.102.228.181', '', '', '2018-10-15 14:25:27', '', '메모', '', 0, 0, 0, '2019-10-08', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '3.3.2', null, null, '2021-04-14 07:19:40', 0, 1),
('hulangroup', '*BDBEFEEEFEA8C7F2540F1A22795C989DA5562AB1', '휴랜 현장그룹 매니저', '휴랜 현장그룹 매니저', '2019-10-08', '', '', 6, '', '', '0327198962', '', '', 0, '', '220', '07', '인천 연수구 인천타워대로 323', '', ' (송도동, 송도 센트로드)', 'R', '', '', 0, '2021-04-29 13:32:06', '106.101.0.52', '2018-10-15 14:25:27', '183.102.228.181', '', '', '2018-10-15 14:25:27', '', '메모', '', 0, 0, 0, '2019-10-08', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '3.3.2', null, null, '2021-04-14 07:19:40', 0, 1)
;

insert into office_workplace_manager (wp_grp_no, mb_id, creator)
values(1, 'hulangroup', 'admin');

-- --------------------------------------------------------------------------------------
-- 원복
-- --------------------------------------------------------------------------------------
ALTER TABLE work_place DROP FOREIGN KEY fk_workplace_wp_grp_no;
ALTER TABLE work_place DROP column wp_grp_no;

ALTER TABLE g5_member DROP FOREIGN KEY fk_member_office_no;
ALTER TABLE g5_member DROP column office_no;

DROP TABLE IF EXISTS office_group_manager;
DROP TABLE IF EXISTS office_workplace_manager;
DROP TABLE IF EXISTS office_workplace_grp;
DROP TABLE IF EXISTS ordering_office;

delete from g5_member where mb_id in ('hulanoffice','hulangroup');

-- QA 적용 여기까지 함 -----

-- 2. UI 구성정보 ( 아이콘, 배경색, 배경이미지, UI 컴포넌트 구성 등 )
-- 발주사/건설사의 경우 아이콘/배경색/배경이미지 지정. 어드민은 디폴트 값 이용.

alter table con_company
    add     icon_file_name       varchar(255) null comment '아이콘 파일명' after cc_memo,
    add     icon_file_name_org   varchar(255) null comment '아이콘 원본 파일명' after icon_file_name,
    add     icon_file_path       varchar(255) null comment '아이콘 저장 위치(상대경로)' after icon_file_name_org,
    add     icon_file_location   integer null comment '아이콘 저장소. 0: local Storage ' after icon_file_path,
    add     bg_img_file_name     varchar(255) null comment '백그라운드 이미지 파일명' after icon_file_location,
    add     bg_img_file_name_org varchar(255) null comment '백그라운드 이미지 원본 파일명' after bg_img_file_name,
    add     bg_img_file_path     varchar(255) null comment '백그라운드 이미지 저장 위치(상대경로)' after bg_img_file_name_org,
    add     bg_img_file_location integer null comment '백그라운드 이미지 저장소. 0: local Storage ' after bg_img_file_path,
    add     bg_color             varchar(16) null comment '백그라운드 색' after bg_img_file_location
;

create table hulan_ui_component (
    hcmpt_id                 varchar(16) not null primary key comment '컴포넌트 아이디',
    hcmpt_name               varchar(32) not null comment '컴포넌트명',
    description             varchar(1024) null comment '설명',
    ui_type                 integer not null comment 'UI 유형. 1: 메인화면 유형, 2: 컴포넌트 유형',
    width                   integer not null comment '컴포넌트의 x 길이 (1~4)',
    height                  integer not null comment '컴포넌트의 y 길이 (1~3)',
    file_name               varchar(255) null comment '대표 파일명',
    file_name_org           varchar(255) null comment '대표 파일 원본명',
    file_path               varchar(255) null comment '대표 파일 저장 위치(상대경로)',
    file_location           integer null comment '대표 파일 저장소. 0: local Storage ',
    status                  tinyint not null default 0 comment '상태. 0: 미사용, 1: 사용',
    create_date             datetime default current_timestamp not null comment '생성일',
    creator                 varchar(20) not null comment '생성자 아이디(mb_id) ',
    update_date             datetime default current_timestamp not null comment '수정일',
    updater                 varchar(20) not null comment '수정자 아이디(mb_id) '
) ENGINE=InnoDB  charset=utf8 COMMENT '통합 관제 모니터링 컴포넌트 정보';

create table hicc_member_ui_component (
    hmuc_no                 bigint auto_increment primary key comment '빌딩 넘버(SEQ)',
    mb_id                   varchar(20) not null comment '컴포넌트 사용자',
    deploy_page             integer not null comment '배치된 페이지 ( 1, 2 )',
    pos_x                   integer not null comment '컴포넌트의 시작 x 좌표',
    pos_y                   integer not null comment '컴포넌트의 시작 y 좌표',
    hcmpt_id                varchar(16) not null comment '컴포넌트 아이디',
    create_date             datetime default current_timestamp not null comment '생성일',
    creator                 varchar(20) not null comment '생성자 아이디(mb_id) ',
    constraint uq_hicc_mb_ui_comp unique( mb_id, deploy_page, pos_x, pos_y ),
    constraint member_hicc_ui_comp foreign key (hcmpt_id) references hicc_ui_component(hcmpt_id) on delete cascade
) ENGINE=InnoDB  charset=utf8 COMMENT '통합 관제 사용자 배치 컴포넌트 정보';

INSERT INTO hicc_ui_component (hcmpt_id, hcmpt_name, description, ui_type, width, height, file_name, file_name_org, file_path, file_location, status, create_date, creator, update_date, updater)
VALUES
('MAIN1', '메인화면', '통합관제 메인 화면1', 1, 3, 2, '20210302163505122_DfLRXodZE8.png', '컴포넌트_근로자출력관리.png', 'uicomponent/WORK_LOG/', 0, 0, now(), 'admin', now(), 'admin'),
('SAFETY_STATUS', '안전 점수 현황', '통합관제 안전 점수 현황 컴포넌트', 0, 3, 1, '20210302163505122_DfLRXodZE8.png', '컴포넌트_근로자출력관리.png', 'uicomponent/WORK_LOG/', 0, 0, now(), 'admin', now(), 'admin'),
('RISK_ASSMN', '위험성 평가', '통합관제 위험성 평가 컴포넌트', 0, 1, 1, '20210302163505122_DfLRXodZE8.png', '컴포넌트_근로자출력관리.png', 'uicomponent/WORK_LOG/', 0, 0, now(), 'admin', now(), 'admin'),
('COOP_MGR', '협력사 관리', '통합관제 협력사 관리 컴포넌트', 0, 1, 1, '20210302163505122_DfLRXodZE8.png', '컴포넌트_근로자출력관리.png', 'uicomponent/WORK_LOG/', 0, 0, now(), 'admin', now(), 'admin'),
('WORKER_LOG', '근로자 출력인원', '통합관제 근로자 출력인원 컴포넌트', 0, 1, 1, '20210302163505122_DfLRXodZE8.png', '컴포넌트_근로자출력관리.png', 'uicomponent/WORK_LOG/', 0, 0, now(), 'admin', now(), 'admin')
;


-- 현장 주소 넣기 ( 시군구 코드 파악 )
insert into work_place_address (wp_id, zonecode, address, road_address, jibun_address, building_name, sido, sigungu, sigungu_code, roadname_code, roadname, bname, bname1, bname2, bcode, hname, create_date)
select
    WP.wp_id,
    '00000' as zonecode,
    concat( WP.wp_sido, ' ', WP.wp_gugun, ' ', WP.wp_addr ) as address,
    null as road_address,
    concat( WP.wp_sido, ' ', WP.wp_gugun, ' ', WP.wp_addr )  as jibun_address,
    null as building_name,
    WP.wp_sido as sido,
    WP.wp_gugun as sigungu,
    IFNULL((
               select RSG.sigungu_cd
               from region_sigungu RSG
                        inner join region_sido RS on RS.sido_cd = RSG.sido_cd
               where RSG.sigungu_nm = WP.wp_gugun
                 and ( RS.sido_nm = WP.wp_sido or RS.sido_short_nm = WP.wp_sido )
               LIMIT 1
           ), '') as sigungu_code,
    null as roadname_code,
    null as roadname,
    null as bname,
    null as bname1,
    null as bname2,
    null as bcode,
    null as hname,
    now()
from work_place WP
         left join work_place_address WPA ON WPA.wp_id = WP.wp_id
where WPA.wp_id is null
;

CREATE TABLE work_place_safety_score (
    safety_date           varchar(8) not null comment '안전점수 대상일',
    wp_id                varchar(50) not null comment '현장 아이디',
    score                integer not null default 0 comment '안전점수. ( 0 ~ 100 )',
    create_date          datetime default current_timestamp not null comment '생성일',
    creator              varchar(20) not null comment '생성자',
    update_date          datetime default current_timestamp not null comment '최종 수정일',
    updater              varchar(20) not null comment '최종 수정자',
    primary key(safety_date, wp_id),
    constraint fk_work_place_safety_score_wp foreign key(wp_id) references work_place(wp_id) on delete cascade
) ENGINE=InnoDB  charset=utf8 COMMENT '안전 점수 현황';


alter table attendance_book
    add safety_edu_start   datetime    null comment '안전교육 참석 시작 시간' after work_status,
    add safety_edu_end     datetime    null comment '안전교육 참석 종료 시간' after safety_edu_start,
    add use_app            integer     null comment '앱 사용 여부(앱상태로그 존재 여부)'  after safety_edu_end
;

CREATE TABLE attendance_book_month_stat (
    working_month           varchar(6)  not null comment '출근월. YYYYMM ',
    wp_id                   varchar(50) not null comment '현장 아이디',
    mb_id                   varchar(20) not null comment '근로자 아이디',
    working_day_count       integer not null default 0 comment '출역일수',
    safety_edu_count        integer not null default 0 comment '안전교육 참석일수',
    use_app_count           integer not null default 0 comment '앱활성 일수',
    create_date             datetime default current_timestamp not null comment '생성일',
    primary key (working_month, wp_id, mb_id)
) ENGINE=InnoDB  charset=utf8 COMMENT '안전 점수 현황';


-- --------------------------------------------------------------------------------------
-- 위험성 평가
-- --------------------------------------------------------------------------------------

-- 위험성 평가 삭제

alter table risk_accssmnt_inspect drop foreign key fk_risk_acc_ins_appr;
drop table risk_accssmnt_inspect_item;
drop table risk_accssmnt_inspect_apprvl;
drop table risk_accssmnt_inspect;
drop table risk_accssmnt_item;

-- 공종 - 작업명 - 작업내용 - 위험요인 및 발생형태 - 개선대책
-- ==> 공정의 하위공정이 불분명

CREATE TABLE risk_accssmnt_item (
    ra_item_no              bigint primary key auto_increment comment '위험요인 번호',
    section                 varchar(32) not null comment '공종',
    work_name               varchar(64) not null comment '작업명',
    work_detail             varchar(128) not null comment '작업내용',
    risk_factor             varchar(128) not null comment '위험요인',
    occur_type              varchar(8) not null comment '발생형태',
    safety_measure          varchar(128) null comment '안전대책',
    create_date             datetime default current_timestamp not null comment '생성일(작성일)',
    creator                 varchar(20) not null comment '생성자 아이디',
    update_date             datetime default current_timestamp not null comment '최종 수정일',
    updater                 varchar(20) not null comment '최종 수정자 아이디',
    unique key (section, work_name, work_detail, risk_factor)
) ENGINE=InnoDB  charset=utf8 COMMENT '위험성 평가 위험요인 정보';


-- 위험성 평가 안전점검 테이블
-- 결재번호(PK) 점검일 현장 협력사 점검기간 결재자 (현장관리자) 결재상태(대기, 승인요청, 승인, 반려) 비고 등록일자 승인요청일 승인일 반려일

CREATE TABLE risk_accssmnt_inspect (
    rai_no                  bigint primary key auto_increment comment '위험성 평가 번호',
    wp_id                   varchar(50) not null comment '현장 아이디',
    coop_mb_id              varchar(20) not null comment '협력사 아이디',
    start_date              date not null comment '점검기간 시작일',
    end_date                date not null comment '점검기간 종료일',
    man_mb_id               varchar(20) not null comment '결재자 아이디(현장관리자)',
    status                  integer not null default 0 comment '결재상태. 0:대기, 1: 승인요청, 2: 승인(완료), 3: 반려',
    rai_apprvl_no           bigint null comment '최종 결재 이력 번호',
    create_date             datetime default current_timestamp not null comment '생성일(작성일)',
    update_date             datetime default current_timestamp not null comment '최종 수정일',
    constraint fk_risk_acc_ins_wp foreign key (wp_id) references work_place(wp_id) on delete cascade
) ENGINE=InnoDB  charset=utf8 COMMENT '위험성 평가 안전점검 정보';

CREATE TABLE risk_accssmnt_inspect_apprvl (
    rai_apprvl_no           bigint primary key auto_increment comment '위험성 평가 일일점검 번호',
    rai_no                  bigint not null comment '위험성 평가 번호',
    action                  integer not null default 0 comment '행위. 1: 승인요청, 2: 승인(완료), 3: 반려',
    comment                 varchar(256) null comment '비고',
    create_date             datetime default current_timestamp not null comment '처리일( 승인 요청/승인/반려일 )',
    creator                 varchar(20) not null comment '처리자 아이디',
    constraint fk_risk_acc_ins_appr_rai foreign key (rai_no) references risk_accssmnt_inspect(rai_no) on delete cascade
) ENGINE=InnoDB  charset=utf8 COMMENT '위험성 평가 안전점검 결재 이력';

CREATE TABLE risk_accssmnt_inspect_item (
    rai_item_no             bigint primary key auto_increment comment '위험성 평가 항목 번호',
    rai_no                  bigint not null comment '위험성 평가 번호',
    location                varchar(32) not null comment '위치',
    section                 varchar(32) not null comment '공종',
    work_name               varchar(64) not null comment '작업명',
    work_detail             varchar(128) not null comment '작업내용',
    risk_factor             varchar(128) not null comment '위험요인',
    occur_type              varchar(8) not null comment '발생형태',
    safety_measure          varchar(128) null comment '안전대책',
    result                  integer not null comment '점검결과. 0: 미조치 1: 완료[유지], 2: 불량, 3: N/A',
    create_date             datetime default current_timestamp not null comment '생성일',
    constraint fk_risk_acc_ins_itm_rai foreign key (rai_no) references risk_accssmnt_inspect(rai_no) on delete cascade
) ENGINE=InnoDB  charset=utf8 COMMENT '안전 점수 현황';

alter table risk_accssmnt_inspect
    add constraint fk_risk_acc_ins_appr foreign key (rai_apprvl_no) references risk_accssmnt_inspect_apprvl(rai_apprvl_no) on delete set null
;



-- --------------------------------------------------
-- TEST
-- ---------------------------------------------------- --------------------------------------------------
update ordering_office  -- con_company
set icon_file_name = 'logo_hulan.3e7bf959.jpg',
    icon_file_name_org = 'logo_hulan.3e7bf959.jpg',
    icon_file_location = 0,
    icon_file_path = 'img/',
    bg_img_file_name = 'logo_hulan.3e7bf959.jpg',
    bg_img_file_name_org = 'logo_hulan.3e7bf959.jpg',
    bg_img_file_location = 0,
    bg_img_file_path = 'img/',
    bg_color = 'yellow'
where office_no = 1
;

insert into ordering_office (office_no, office_name, telephone, biznum, zonecode, bcode, address, address_detail, sido, sigungu, bname, create_date, creator, update_date, updater)
values(1, '휴랜 발주사', '032-719-8962', '7618701070', '22013', '2818510600', '인천 연수구 인천타워대로 99 (애니오션빌딩)', '애니오션빌딩 8층', '인천', '연수구', '송도동',  now(), 'admin', now(), 'admin');

insert into office_workplace_grp (wp_grp_no, office_no, office_grp_name, create_date, creator, update_date, updater)
values(1, 1, '휴랜 현장그룹', now(), 'admin', now(), 'admin');

INSERT INTO g5_member (mb_id, mb_password, mb_name, mb_nick, mb_nick_date, mb_email, mb_homepage, mb_level, mb_sex, mb_birth, mb_tel, mb_hp, mb_certify, mb_adult, mb_dupinfo, mb_zip1, mb_zip2, mb_addr1, mb_addr2, mb_addr3, mb_addr_jibeon, mb_signature, mb_recommend, mb_point, mb_today_login, mb_login_ip, mb_datetime, mb_ip, mb_leave_date, mb_intercept_date, mb_email_certify, mb_email_certify2, mb_memo, mb_lost_certify, mb_mailling, mb_sms, mb_open, mb_open_date, mb_profile, mb_memo_call, mb_1, mb_2, mb_3, mb_4, mb_5, mb_6, mb_7, mb_8, mb_9, mb_10, mb_11, mb_12, mb_13, mb_14, mb_15, mb_16, mb_17, mb_18, mb_19, mb_20, app_version, cc_id, work_section_a, pwd_change_date, attempt_login_count, office_no)
VALUES
('qaofc', '*89C6B530AA78695E257E55D63C00A6EC9AD3E977', 'QA 발주사 관리자', 'QA 발주처 관리자', '2019-10-08', '', '', 7, '', '', '0327198962_1', '', '', 0, '', '220', '07', '인천 연수구 인천타워대로 323', '', ' (송도동, 송도 센트로드)', 'R', '', '', 0, '2021-04-29 13:32:06', '106.101.0.52', '2018-10-15 14:25:27', '183.102.228.181', '', '', '2018-10-15 14:25:27', '', '메모', '', 0, 0, 0, '2019-10-08', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '3.3.2', null, null, '2021-04-14 07:19:40', 0, 3),
('qaofcgrp', '*89C6B530AA78695E257E55D63C00A6EC9AD3E977', 'QA 발주사 현장그룹 매니저', 'QA 발주사 현장그룹 매니저', '2019-10-08', '', '', 6, '', '', '0327198962_2', '', '', 0, '', '220', '07', '인천 연수구 인천타워대로 323', '', ' (송도동, 송도 센트로드)', 'R', '', '', 0, '2021-04-29 13:32:06', '106.101.0.52', '2018-10-15 14:25:27', '183.102.228.181', '', '', '2018-10-15 14:25:27', '', '메모', '', 0, 0, 0, '2019-10-08', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '', '3.3.2', null, null, '2021-04-14 07:19:40', 0, 3)
;


insert into office_workplace_manager (wp_grp_no, mb_id, creator)
values(5, 'qaofcgrp', 'admin');


insert into work_place_safety_score(safety_date, wp_id, score, create_date, creator, update_date, updater)
select RES.score_date, RES.wp_id, RES.score, now(), 'admin', now(), 'admin'
from (

         select
             DATE_FORMAT(date_target.score_date, '%Y%m%d') as score_date,
             WP.wp_id,
             ROUND(80 + (RAND() * 20), 1) as score
         from work_place WP, (
             select curdate() - INTERVAL (a.a) DAY as score_date
             from (select 0 as a union all select 1 union all select 2 union all select 3 union all select 4 union all select 5 union all select 6) as a
         ) date_target
         where WP.cc_id IN (
             'de1465121c6c48b8b05c09dfc5b29462'
             )
     ) RES
where not exists(
        select 1
        from work_place_safety_score WPSS_V
        where WPSS_V.wp_id = RES.wp_id
          and WPSS_V.safety_date = RES.score_date
        limit 1
    )
;

##
-- 출근기록 ( 성격은 현재는 일자별 출력인원 테이블이 될 듯... ) 을 위해 trigger 를 달고자 함...
-- sensor_log_inout 에 insert 시 attendance_book 에 출근 처리 ( 존재하면 상태만 출근으로 업데이트 )
-- worker_qr_enter 에 insert 시 attendance_book 에 출근 처리 ( 존재하면 상태만 출근으로 업데이트 ).
-- sensor_log_recent 의 in_out_type 값 3 으로 업데이트시 퇴근 처리



insert into risk_accssmnt_item (section, work_name, work_detail, risk_factor, occur_type, safety_measure, create_date, creator, update_date, updater)
values
( '굴착', '굴착공사', '굴착장비 반입', '굴삭기 운전원의 운전미숙에 의한 전도, 충돌 재해', '충돌', '굴삭기 운전원의 자격유무, 경험 정도 등을 사전에 확인 실시', now(), 'admin', now(), 'admin' ),
( '파일', '파일공사', '기초파일 천공', '지게차 운전원의 조작 미숙에 의한 충돌 협착', '협착', '지게차 운전원 자격 여부 확인', now(), 'admin', now(), 'admin' ),
( '비계', '비계공사', '운반', '파이프의 한쪽만을 어깨에 메고 끌고가다 허리 재해', '요통', '파이프의 중심을 메고 운반하도록 작업전에 작업방법 주지', now(), 'admin', now(), 'admin' )
;


CREATE TABLE temp_region_loc (
    sido                    varchar(32) not null comment '시도',
    region                  varchar(32) not null comment '지역명',
    x                       integer null comment 'x 좌표',
    y                       integer null comment 'y 좌표',
    primary key ( sido, region )
) ENGINE=InnoDB  charset=utf8 COMMENT '좌표';


#####################################
## 통합 관제  ( QA 상용 미적용 )
#####################################
alter table region_sido
    add    nx int default 0 not null comment '기상청 위경도 격자X',
    add    ny int default 0 not null comment '기상청 위경도 격자Y'
;


INSERT INTO region_sido (sido_cd, sido_nm, sido_short_nm, sido_ord, nx, ny)
VALUES
('11', '서울특별시', '서울', 1, 60, 127),
('26', '부산광역시', '부산', 4, 98, 76),
('27', '대구광역시', '대구', 7, 89, 90),
('28', '인천광역시', '인천', 3, 55, 124),
('29', '광주광역시', '광주', 13, 58, 74),
('30', '대전광역시', '대전', 11, 67, 100),
('31', '울산광역시', '울산', 6, 102, 84),
('36', '세종특별자치시', '세종', 17, 66, 103),
('41', '경기도', '경기', 2, 60, 120),
('42', '강원도', '강원', 12, 73, 134),
('43', '충청북도', '충북', 10, 69, 107),
('44', '충청남도', '충남', 9, 68, 100),
('45', '전라북도', '전북', 15, 63, 89),
('46', '전라남도', '전남', 14, 51, 67),
('47', '경상북도', '경북', 8, 89, 91),
('48', '경상남도', '경남', 5, 91, 77),
('50', '제주특별자치도', '제주', 16, 52, 38)
ON DUPLICATE KEY UPDATE
                     sido_nm = VALUES(sido_nm),
                     sido_short_nm = VALUES(sido_short_nm),
                     sido_ord = VALUES(sido_ord),
                     nx = VALUES(nx),
                     ny = VALUES(ny)
;

create table sido_weather_atmosphere (
                                         sido_cd                 varchar(2) not null primary key comment '시도 코드',
                                         humidity                varchar(16) null comment '습도(%)',
                                         wind_direction          varchar(16) null comment '풍향(0~360)',
                                         wind_speed              varchar(16) null comment '풍속(m/s)',
                                         precipitation_form      varchar(16) null comment '강수 형태 코드',
                                         precipitation_form_name varchar(16) null comment '강수 형태명',
                                         precipitation           varchar(16) null comment '1시간 강수량(mm)',
                                         sky_form                varchar(16) null comment '하늘 형태 코드',
                                         sky_form_name           varchar(16) null comment '하늘 형태명',
                                         rainfall                varchar(16) null comment '강수확률(%)',
                                         temperature             varchar(16) null comment '온도(℃)',
                                         pm25_value              varchar(16) null comment '초미세먼지(PM2.5) 농도 (단위 : ㎍/㎥)',
                                         pm25_grade              varchar(16) null comment '초미세먼지 등급. 1:좋음, 2:보통, 3:나쁨, 4:매우나쁨',
                                         weather_ultra_update    datetime null comment '초단기실황 업데이트 시간',
                                         weather_vil_update      datetime null comment '동네예보 업데이트 시간',
                                         atm_update              datetime null comment '대기오염 업데이트 시간',
                                         update_date             datetime null comment '최종 수정일'
) ENGINE=InnoDB  charset=utf8 comment '시도별 날씨 및 대기오염 수치 정보';

insert into sido_weather_atmosphere(sido_cd, pm25_value, pm25_grade, atm_update, update_date)
select
    ATM_RAW.sido_cd,
    ATM_RAW.pm25_value,
    case
        when ATM_RAW.pm25_value > 75 then 4
        when ATM_RAW.pm25_value > 35 then 3
        when ATM_RAW.pm25_value > 15 then 2
        else 1
        end as pm25_grade,
    NOW(),
    NOW()
from (
         select
             RS.sido_cd,
             cast( avg( cast( AE.pm25_value as unsigned )) as decimal(3,0)) as pm25_value
         from region_sido RS
                  inner join air_environment AE ON RS.sido_short_nm = AE.sido_name
         where AE.mang_name = '도시대기'
           and AE.pm25_value REGEXP ('^[0-9]+$')
           and AE.create_date >= date_sub( now(), interval 2 hour )
         group by RS.sido_cd
     ) ATM_RAW
;

##############################
-- 통합관제 메인 변경
##############################
-- 권역 필요...  ( 통합 관제 정보 테이블 필요 ) > 어드민은 무조건 디폴트.  발주사와 건설사는 fk 로 참고하게.
create table hicc_info (
                           hicc_no                 bigint primary key auto_increment comment '통합관제 넘버',
                           hicc_name               varchar(32) not null comment '통합관제 관리명',
                           description             varchar(256) null comment '설명',
                           title                   varchar(32) not null comment '통합관제 노출 타이틀',
                           map_file_name           varchar(255) null comment '권역표시 지도 파일명',
                           map_file_name_org       varchar(255) null comment '권역표시 지도 원본 파일명',
                           map_file_path           varchar(255) null comment '권역표시 지도 저장 위치(상대경로)',
                           map_file_location       int null comment '권역표시 지도 저장소. 0: local Storage ',
                           icon_file_name          varchar(255) null comment '아이콘 파일명',
                           icon_file_name_org      varchar(255) null comment '아이콘 원본 파일명',
                           icon_file_path          varchar(255) null comment '아이콘 저장 위치(상대경로)',
                           icon_file_location      int null comment '아이콘 저장소. 0: local Storage ',
                           bg_img_file_name        varchar(255) null comment '백그라운드 이미지 파일명',
                           bg_img_file_name_org    varchar(255) null comment '백그라운드 이미지 원본 파일명',
                           bg_img_file_path        varchar(255) null comment '백그라운드 이미지 저장 위치(상대경로)',
                           bg_img_file_location    int null comment '백그라운드 이미지 저장소. 0: local Storage ',
                           bg_color                varchar(16) null comment '백그라운드 색',
                           create_date             datetime default current_timestamp not null comment '생성일(작성일)',
                           creator                 varchar(20) not null comment '생성자 아이디',
                           update_date             datetime default current_timestamp not null comment '최종 수정일',
                           updater                 varchar(20) not null comment '최종 수정자 아이디'
) ENGINE=InnoDB  charset=utf8 comment '통합관제 정보';

create table hicc_integ_group (
                                  hrg_no                  bigint primary key auto_increment comment '통합관제 권역 넘버',
                                  hrg_name                varchar(32) not null comment '권역명',
                                  hicc_no                 bigint not null comment '통합관제 넘버',
                                  order_seq               integer not null default 0 comment '정렬 순서',
                                  create_date             datetime default current_timestamp not null comment '생성일(작성일)',
                                  creator                 varchar(20) not null comment '생성자 아이디',
                                  update_date             datetime default current_timestamp not null comment '최종 수정일',
                                  updater                 varchar(20) not null comment '최종 수정자 아이디',
                                  constraint hicc_integ_group_hicc foreign key (hicc_no) references hicc_info(hicc_no) on delete cascade
) ENGINE=InnoDB  charset=utf8 comment '통합관제 권역 정보';

create table hicc_region (
                             hicc_no                 bigint not null comment '통합관제 넘버',
                             sido_cd                 varchar(2) not null comment '시도 코드',
                             hrg_no                  bigint not null comment '통합관제 권역 넘버',
                             create_date             datetime default current_timestamp not null comment '생성일(작성일)',
                             creator                 varchar(20) not null comment '생성자 아이디',
                             primary key (hicc_no, sido_cd ),
                             constraint hicc_region_hicc foreign key (hicc_no) references hicc_info(hicc_no) on delete cascade,
                             constraint hicc_region_hrg foreign key (hrg_no) references hicc_integ_group(hrg_no) on delete cascade,
                             constraint hicc_region_sido foreign key (sido_cd) references region_sido(sido_cd) on delete cascade
) ENGINE=InnoDB  charset=utf8 comment '통합관제 권역별 지역 정보';

alter table con_company
    add hicc_no bigint null comment '통합관제 넘버' after cc_memo
;

alter table ordering_office
    add hicc_no bigint null comment '통합관제 넘버' after bname
;



-- 현장 관제 정보 생성
alter table hicc_info auto_increment = 3;

insert into hicc_info (
    hicc_no, hicc_name, description, title,
    map_file_name, map_file_name_org, map_file_path, map_file_location,
    icon_file_name, icon_file_name_org, icon_file_path, icon_file_location,
    bg_img_file_name, bg_img_file_name_org, bg_img_file_path, bg_img_file_location,
    bg_color, create_date, creator, update_date, updater)
values
(1, '디폴트 통합관제 설정 정보', '디폴트 통합관제 설정 정보입니다.', '전국 통합 관제 시스템',
 'map.png', 'map.png', 'image/imos-c/', 0,
 'hulan_logo.png', 'hulan_logo.png', 'image/imos-c/', 0,
 'default_background.png', 'default_background.png', 'image/imos-c/', 0,
 'black', now(), 'admin', now(), 'admin'),
(2, '한라 통합관제 설정 정보', '한라 통합관제 설정 정보입니다.', '한라 전국 통합 관제 시스템',
 'map.png', 'map.png', 'image/imos-c/', 0,
 'logo-halla.png', 'logo-halla.png', 'image/imos-c/', 0,
 'default_background.png', 'default_background.png', 'image/imos-c/', 0,
 'black', now(), 'admin', now(), 'admin')
;

-- 현장별 권역 생성
alter table hicc_integ_group auto_increment = 7;

insert into hicc_integ_group(hrg_no, hrg_name, hicc_no, order_seq, create_date, creator, update_date, updater)
values
(1, '수도권', 1, 1, now(),'admin', now(), 'admin'),
(2, '강원/경상권', 1, 2, now(), 'admin', now(), 'admin'),
(3, '충청/전라/제주권', 1, 3, now(), 'admin', now(), 'admin'),
(4, '수도권', 2, 1, now(), 'admin', now(), 'admin'),
(5, '강원/경상권', 2, 2, now(), 'admin', now(), 'admin'),
(6, '충청/전라/제주권', 2, 3, now(), 'admin', now(), 'admin')
;

-- 권역별 지역 설정
insert into hicc_region(hrg_no, sido_cd, hicc_no, create_date, creator)
values
(1, 11, 1, now(), 'admin'),
(1, 28, 1, now(), 'admin'),
(1, 41, 1, now(), 'admin'),
(2, 26, 1, now(), 'admin'),
(2, 27, 1, now(), 'admin'),
(2, 31, 1, now(), 'admin'),
(2, 42, 1, now(), 'admin'),
(2, 47, 1, now(), 'admin'),
(2, 48, 1, now(), 'admin'),
(3, 29, 1, now(), 'admin'),
(3, 30, 1, now(), 'admin'),
(3, 36, 1, now(), 'admin'),
(3, 43, 1, now(), 'admin'),
(3, 44, 1, now(), 'admin'),
(3, 45, 1, now(), 'admin'),
(3, 46, 1, now(), 'admin'),
(3, 50, 1, now(), 'admin'),
(4, 11, 2, now(), 'admin'),
(4, 28, 2, now(), 'admin'),
(4, 41, 2, now(), 'admin'),
(5, 26, 2, now(), 'admin'),
(5, 27, 2, now(), 'admin'),
(5, 31, 2, now(), 'admin'),
(5, 42, 2, now(), 'admin'),
(5, 47, 2, now(), 'admin'),
(5, 48, 2, now(), 'admin'),
(6, 29, 2, now(), 'admin'),
(6, 30, 2, now(), 'admin'),
(6, 36, 2, now(), 'admin'),
(6, 43, 2, now(), 'admin'),
(6, 44, 2, now(), 'admin'),
(6, 45, 2, now(), 'admin'),
(6, 46, 2, now(), 'admin'),
(6, 50, 2, now(), 'admin')
;


##############################
-- IMOS Component 변경 적용시
##############################

truncate table imos_member_ui_component;

insert into imos_member_ui_component (wp_id, mb_id, deploy_page, pos_x, pos_y, hcmpt_id, create_date)
select
    wp_id, creator, 1 as page, 1 as pos_x, 1 as pos_y, 'I_MAIN', now()
from work_place_ui_component
group by wp_id, creator
;

insert into imos_member_ui_component (wp_id, mb_id, deploy_page, pos_x, pos_y, hcmpt_id, create_date)
select
    wp_id, creator, 1,
    case when location > 3  then 4 else location end as pos_x,
    case when location > 3  then location - 3 else 3 end as pos_y,
    cmpt_id, now()
from work_place_ui_component
;

##############################
-- HICC Component 관련 custom data 저장
##############################
alter table hicc_member_ui_component
    add custom_data	        varchar(1024)  NULL  COMMENT '지정 컴포넌트 관련 맞춤 데이터 JSON' after hcmpt_id
;

alter table imos_member_ui_component
    add custom_data	        varchar(1024)  NULL  COMMENT '지정 컴포넌트 관련 맞춤 데이터 JSON' after hcmpt_id
;


####################################
-- Component 사용가능 사용자 등급 지정
####################################
CREATE TABLE member_level (
                              mb_level            tinyint not null comment '사용자 레벨(등급)',
                              mb_level_name          varchar(32) not null comment '사용자 레벨(등급)명',
                              description         varchar(1024) not null comment '설명',
                              create_date         datetime default current_timestamp not null comment '생성일',
                              creator             varchar(20) not null comment '생성자',
                              update_date         datetime default current_timestamp not null comment '수정일',
                              updater             varchar(20) not null comment '수정자',
                              primary key (mb_level)
) ENGINE=InnoDB  charset=utf8 COMMENT '계정 레벨(등급)';

insert into member_level (mb_level, mb_level_name, description, create_date, creator, update_date, updater)
values
(10, '최고 관리자', '최고 관리자', now(), 'admin', now(), 'admin'),
(7, '발주사 관리자', '발주사', now(), 'admin', now(), 'admin'),
(6, '발주사 현장그룹 매니저', '발주사 현장그룹 매니저', now(), 'admin', now(), 'admin'),
(5, '건설사 관리자', '건설사 관리자', now(), 'admin', now(), 'admin'),
(4, '현장 관리자', '현장 관리자', now(), 'admin', now(), 'admin'),
(3, '협력사 관리자', '협력사 관리자', now(), 'admin', now(), 'admin'),
(2, '근로자', '근로자', now(), 'admin', now(), 'admin')
;

CREATE TABLE hulan_ui_component_level (
                                          hcmpt_id            varchar(16) not null comment '컴포넌트 아이디',
                                          mb_level            tinyint not null comment '사용자 레벨(등급)',
                                          create_date         datetime default current_timestamp not null comment '생성일',
                                          creator             varchar(20) not null comment '생성자',
                                          primary key (hcmpt_id, mb_level),
                                          constraint fk_hu_ui_comp_lv_hcmpt foreign key (hcmpt_id) references hulan_ui_component(hcmpt_id) on delete cascade,
                                          constraint fk_hu_ui_comp_lv_level foreign key (mb_level) references member_level(mb_level) on delete cascade
) ENGINE=InnoDB  charset=utf8 COMMENT 'UI 컴포넌트 지원 계정 레벨(등급)';

insert into hulan_ui_component_level(hcmpt_id, mb_level, create_date, creator)
select
    HUC.hcmpt_id,
    MBLV.mb_level,
    now() as create_date,
    'admin' as creator
from hulan_ui_component HUC
   , member_level MBLV
where MBLV.mb_level not in ( 2 )
;


##################################################
-- 스마트 출입관리 시스템 QRCode 모든 계정 허용
##################################################
alter table attendance_book
    add mb_level   tinyint  NULL  COMMENT '출입자 계정 레벨(등급)' after mb_id
;

update attendance_book AB
    INNER JOIN g5_member G5M ON G5M.mb_id = AB.mb_id
set AB.mb_level = G5M.mb_level
where AB.mb_level IS NULL
;

##################################################
-- 기울기 센서
##################################################
create table tilt_log
(
    mac_address varchar(50) not null comment '장비 구분자',
    measure_time timestamp not null comment '데이터 수집 시간',
    display_name varchar(50) not null comment '데이터 수집 시 등록된 디바이스 이름',
    tilt_x double not null comment 'X축 기울기',
    tilt_y double not null comment 'Y축 기울기',
    tilt_z double not null comment 'Z축 기울기',
    primary key (mac_address, measure_time)
) ENGINE=InnoDB  charset=utf8 COMMENT '기울기 센서 로그';

create table tilt_log_recent
(
    mac_address varchar(50) not null primary key comment '장비 구분자',
    measure_time timestamp not null comment '데이터 수집 시간',
    display_name varchar(50) not null comment '데이터 수집 시 등록된 디바이스 이름',
    tilt_x double not null comment 'X축 기울기',
    tilt_y double not null comment 'Y축 기울기',
    tilt_z double not null comment 'Z축 기울기',
    dashboard_popup tinyint not null default '1' comment '안전모니터 팝업 표시 여부. 0.표시안함 1.해지시까지 표시(기본값)'
) ENGINE=InnoDB  charset=utf8 COMMENT '기울기 센서 최근 로그';

##################################################
-- 안전고리 정보
##################################################

create table safety_hook_state
(
    mb_id                       varchar(20) primary key comment '근로자 아이디',
    mac_address                 varchar(24) not null comment 'mac_address 정보',
    pairing_status              tinyint not null default 0 comment '안전고리 디바이스 페어링 상태. 0: 미연결, 1: 연결',
    pairing_date                datetime null default now() comment '페어링 시간',
    district_enter_status       tinyint not null default 0 comment '작업장소 진입 상태. 0: 이탈, 1: 진입',
    district_enter_date         datetime null default now() comment '작업장소 진입 시간',
    lock_status                 tinyint null default 0 comment '안전고리 잠금 상태. 0: 해제, 1: 잠금',
    lock_date                   datetime null default now() comment '잠금 시간',
    update_date                 datetime not null default now() comment '최종 수정일'
) ENGINE=InnoDB  charset=utf8 COMMENT '안전고리 체결 상태';

create table safety_hook_event_log
(
    mb_id                       varchar(20) comment '근로자 아이디',
    event_date                  datetime not null default now() comment '이벤트 시간',
    event_type                  tinyint not null default 0 comment '이벤트 타입. 1: 페어링, 2: 작업장소 진입/이탈, 3: 안전고리 잠금/해제',
    mac_address                 varchar(24) not null comment 'mac_address 정보',
    pairing_status              tinyint null default 0 comment '안전고리 디바이스 페어링 상태. 0: 미연결, 1: 연결',
    district_enter_status       tinyint null default 0 comment '작업장소 진입 상태. 0: 이탈, 1: 진입',
    lock_status                 tinyint null default 0 comment '안전고리 잠금 상태. 0: 해제, 1: 잠금',
    log_data                    text not null comment '이벤트 JSON 데이터',
    create_date                 datetime not null default now() comment '생성 시간',
    primary key (mb_id, event_date)
) ENGINE=InnoDB  charset=utf8 COMMENT '안전고리 이벤트 로그'
    PARTITION BY RANGE ( TO_DAYS(event_date) )(
        PARTITION safety_hook_event_log_p202110 VALUES LESS THAN ( TO_DAYS('2021-11-01') ),
        PARTITION safety_hook_event_log_p202111 VALUES LESS THAN ( TO_DAYS('2021-12-01') ),
        PARTITION safety_hook_event_log_p202112 VALUES LESS THAN ( TO_DAYS('2022-01-01') ),
        PARTITION safety_hook_event_log_p202202 VALUES LESS THAN ( TO_DAYS('2022-02-01') )
        );

-- safety_hook_event_log_partition.sql

##################################################
-- 개구부 센서 데이터
##################################################
drop table magnetic_open_log;
create table magnetic_open_log
(
    mac_address varchar(50) not null comment '장비 구분자',
    measure_time timestamp not null comment '개구부 상태 변경 시간',
    display_name varchar(50) not null comment '데이터 수집 시 등록된 디바이스 이름',
    status tinyint not null comment '개구부 열림 상태. 0.닫힘 1.열림',
    primary key (mac_address, measure_time)
) ENGINE=InnoDB  charset=utf8 COMMENT '개구부 로그';

drop table magnetic_open_recent;
create table magnetic_open_recent
(
    mac_address varchar(50) not null primary key comment '장비 구분자',
    measure_time timestamp not null comment '개구부 상태 변경 시간',
    display_name varchar(50) not null comment '데이터 수집 시 등록된 디바이스 이름',
    status tinyint not null comment '개구부 열림 상태. 0.닫힘 1.열림',
    dashboard_popup tinyint not null default '1' comment '안전모니터 팝업 표시 여부. 0.표시안함 1.해지시까지 표시(기본값)'
) ENGINE=InnoDB  charset=utf8 COMMENT '개구부 현재 상태';

##################################################
-- 출입게이트 정보 변경
##################################################

alter table entr_gate_account drop column default_api_type;

alter table entr_gate_workplace
    drop column api_type,
    drop column member_api_url,
    add gate_type  tinyint  not null default 2 COMMENT '출입게이트 유형. 1: 안면인식, 2: QR Reader' after mapping_cd
;

update entr_gate_workplace EGW
    inner join work_place WP ON EGW.wp_id = WP.wp_id
set EGW.gate_type = case when WP.wp_idx = EGW.mapping_cd then 2 else 1 end
;

-- attendance_book_midnight_job.sql 변경 ( mb_level 업데이트 )

update attendance_book AB
    INNER JOIN g5_member G5M ON G5M.mb_id = AB.mb_id
set AB.mb_level = G5M.mb_level
where AB.mb_level IS NULL
;

-- 패치 후 실행하여야 함
update imos_member_ui_component IMUC
    inner join entr_gate_workplace EGW ON EGW.wp_id = IMUC.wp_id
    inner join work_place WP ON IMUC.wp_id = WP.wp_id
set     IMUC.hcmpt_id = 'QR_GATE'
where IMUC.hcmpt_id = 'ENTER_GATE'
  and WP.wp_idx = EGW.mapping_cd
;

##################################################
-- 통합 관제 정보 관리
##################################################

alter table hicc_info
    drop column title,
    drop column description
;

alter table ordering_office
    drop column icon_file_name,
    drop column icon_file_name_org,
    drop column icon_file_path,
    drop column icon_file_location,
    drop column bg_img_file_name,
    drop column bg_img_file_name_org,
    drop column bg_img_file_path,
    drop column bg_img_file_location,
    drop column bg_color
;

alter table con_company
    drop column icon_file_name,
    drop column icon_file_name_org,
    drop column icon_file_path,
    drop column icon_file_location,
    drop column bg_img_file_name,
    drop column bg_img_file_name_org,
    drop column bg_img_file_path,
    drop column bg_img_file_location,
    drop column bg_color
;



alter table region_sigungu
    add wfr_cd varchar(8) null comment '기상청 지점코드'
;

alter table work_place_monitor_config
    add nx                      int null comment '기상청 지역격자 X좌표' after kalman_filter,
    add ny                      int null comment '기상청 지역격자 Y좌표' after nx
;

update work_place_monitor_config WPA
    inner join work_place_weather WPW on WPA.wp_id = WPW.wp_id
set WPA.nx = WPW.nx , WPA.ny = WPW.ny
WHERE WPA.nx is null
;


update region_sigungu RS
    inner join (
        select
            RES.sigungu_cd,
            RES.sido_short_nm,
            RES.sigungu_nm,
            RES.wfr_name,
            case when RES.wfp_cd is null then
                     (
                         select group_concat(WFP.wfp_cd separator '|' )
                         from weather_point WFP
                                  inner join weather_region WR ON WR.wfr_cd = WFP.wfr_cd
                         where  (
                                     WR.wfr_name like concat('%', RES.sido_short_nm, '%' )
                                 OR
                                     WR.wfr_name like concat('%', RES.sido_nm, '%' )
                             )
                           and RES.sido_nm like concat(WFP.dosi_name, '%' )
                     )
                 else RES.wfp_cd
                end as wfp_cd
        from (
                 select RS.sido_nm, RS.sido_short_nm, RSG.sigungu_cd, RSG.sigungu_nm,
                        WFP.wfr_cd, WFP.wfr_name, WFP.dosi_name, WFP.wfp_cd
                 from region_sigungu RSG
                          inner join region_sido RS ON RS.sido_cd = RSG.sido_cd
                          left join (
                     select
                         WR.wfr_cd, WR.wfr_name, WFP.wfp_cd, WFP.dosi_name
                     from weather_point WFP
                              inner join weather_region WR ON WR.wfr_cd = WFP.wfr_cd
                 ) WFP ON RSG.sigungu_nm like concat(WFP.dosi_name, '%' )
                     and (
                                      WFP.wfr_name like concat('%', RS.sido_short_nm, '%' )
                                  OR
                                      WFP.wfr_name like concat('%', RS.sido_nm, '%' )
                              )
             ) RES
    ) RS2 ON RS.sigungu_cd = RS2.sigungu_cd
set RS.wfp_cd = RS2.wfp_cd
where RS.wfp_cd is null
;



-- 지역 ( 3일 이후 )
create table weather_forecast_by_region
(
    wfr_cd                  varchar(8)  not null comment '기상청 지역 코드',
    forecast_day            varchar(8)  not null comment '날씨예보일',
    am_rainfall             varchar(16) null comment '오전 강수확률. 오후 강수확률이 없다면 해당일 강수확률',
    pm_rainfall             varchar(16) null comment '오후 강수확률',
    am_wf_form_name         varchar(32) null comment '오전 날씨형태. 오후 날씨형태가 없다면 해당일 날씨형태',
    pm_wf_form_name         varchar(32) null comment '오후 날씨형태',
    am_sky_form             varchar(16) null comment '오전 하늘형태 DB01: 맑음, DB03: 구름많음, DB04: 흐림',
    pm_sky_form             varchar(16) null comment '오후 하늘형태 DB01: 맑음, DB03: 구름많음, DB04: 흐림',
    am_precipitation_form   varchar(16) null comment '오전 강수형태 0: 강수없음, 1: 비, 2: 비/눈, 3: 눈: 4: 소나기, 5: 빗방울, 6: 빗방울눈날림, 7: 눈날림',
    pm_precipitation_form   varchar(16) null comment '오후 강수형태 0: 강수없음, 1: 비, 2: 비/눈, 3: 눈: 4: 소나기, 5: 빗방울, 6: 빗방울눈날림, 7: 눈날림',
    create_date             datetime not null comment '생성일',
    update_date             datetime not null comment '최종수정일',
    primary key (wfr_cd, forecast_day)
) ENGINE=InnoDB  charset=utf8 COMMENT '3일 이후 일별 지역 날씨 예보 정보';

-- 지점 ( 3일 이후는 최고/최저 기온만.. )
create table weather_forecast_by_point
(
    wfp_cd                  varchar(8)  not null comment '기상청 지점 코드',
    forecast_day            varchar(8)  not null comment '날씨예보일',
    am_rainfall             varchar(16) null comment '오전 강수확률. 오후 강수확률이 없다면 해당일 강수확률 (3일 이내만) ',
    pm_rainfall             varchar(16) null comment '오후 강수확률 (3일 이내만)',
    am_wf_form_name         varchar(32) null comment '오전 날씨형태. 오후 날씨형태가 없다면 해당일 날씨형태 (3일 이내만). 하늘형태+강수형태 조합',
    pm_wf_form_name         varchar(32) null comment '오후 날씨형태 (3일 이내만)',
    am_sky_form             varchar(16) null comment '오전 하늘형태 (3일 이내만). DB01: 맑음, DB03: 구름많음, DB04: 흐림',
    pm_sky_form             varchar(16) null comment '오후 하늘형태 (3일 이내만). DB01: 맑음, DB03: 구름많음, DB04: 흐림',
    am_precipitation_form   varchar(16) null comment '오전 강수형태 (3일 이내만). 0: 강수없음, 1: 비, 2: 비/눈, 3: 눈: 4: 소나기, 5: 빗방울, 6: 빗방울눈날림, 7: 눈날림',
    pm_precipitation_form   varchar(16) null comment '오후 강수형태 (3일 이내만). 0: 강수없음, 1: 비, 2: 비/눈, 3: 눈: 4: 소나기, 5: 빗방울, 6: 빗방울눈날림, 7: 눈날림',
    temperature_low         varchar(16) not null comment '예상 최저기온',
    temperature_high        varchar(16) not null comment '예상 최고기온',
    create_date             datetime not null comment '생성일',
    update_date             datetime not null comment '최종수정일',
    primary key (wfp_cd, forecast_day)
) ENGINE=InnoDB  charset=utf8 COMMENT '일별 예보지점 날씨 예보 정보';

create table weather_forecast_by_grid
(
    nx                      integer     not null comment '예보위치 X좌표',
    ny                      integer     not null comment '예보위치 Y좌표',
    forecast_day            varchar(8)  not null comment '날씨예보일',
    forecast_hour           integer     not null comment '날씨예보시간',
    rainfall                varchar(16) null comment '강수확률(%)',
    precipitation_form      varchar(16) null comment '강수형태. 0: 강수없음, 1: 비, 2: 비/눈, 3: 눈: 4: 소나기, 5: 빗방울, 6: 빗방울눈날림, 7: 눈날림',
    precipitation           varchar(16) null comment '1시간 강수량 범주',
    humidity                varchar(16) null comment '습도(%)',
    amount_snow             varchar(16) null comment '1시간 적설량 범주',
    sky_form                varchar(16) null comment '하늘상태 코드. 1: 맑음, 3: 구름많음, 4: 흐림',
    temperature             varchar(16) null comment '1시간 기온(℃)',
    wind_direction          varchar(16) null comment '풍향 (degree)',
    wind_speed              varchar(16) null comment '풍속 (m/s)',
    create_date             datetime not null comment '생성일',
    update_date             datetime not null comment '생성일',
    primary key (nx, ny, forecast_day, forecast_hour)
) ENGINE=InnoDB  charset=utf8 COMMENT '날씨 3일 이내 시간별 예보위치 날씨 정보';

##############################
-- 풍속계
##############################

create table wind_speed_log
(
    mac_address varchar(50) not null comment '장비 구분자',
    measure_time timestamp not null comment '데이터 수집 시간. 매분 단위로 저장',
    display_name varchar(50) not null comment '데이터 수집 시 등록된 디바이스 이름',
    speed_avg double not null comment '1분 풍속 평균값',
    speed_max double not null comment '1분 풍속 최대값',
    primary key (mac_address, measure_time)
) ENGINE=InnoDB  charset=utf8 COMMENT '풍속 수집 로그';

create table wind_speed_recent
(
    mac_address varchar(50) not null comment '장비 구분자'  primary key,
    measure_time timestamp not null comment '데이터 수집 시간. 매분 업데이트',
    display_name varchar(50) not null comment '데이터 수집 시 등록된 디바이스 이름',
    recently_avg double not null comment '최근 10분간의 평균 풍속',
    recently_max double not null comment '최근 10분간의 최대 풍속',
    latest_avg double not null comment '1분 평균 풍속.',
    latest_max double not null comment '1분 최대 풍속',
    dashboard_popup tinyint default 1 not null comment '안전모니터 팝업 표시 여부. 0.표시안함 1.해지시까지 표시(기본값)'
) ENGINE=InnoDB  charset=utf8 COMMENT '풍속계 최근 수집 정보';

##############################
-- 체결고리 변경
##############################
alter table safety_hook_event_log
    add wp_id                varchar(50) null comment '현장 아이디' after lock_status;
;

alter table safety_hook_state
    add wp_id                varchar(50) null comment '현장 아이디' after lock_date;
;

##############################
-- 디바이스 관리 변경
##############################

alter table work_device_info
    change mac_address_type mac_address_type tinyint not null default 1 comment '통신방식. 1: 일반, 2: LoRa',
    add use_sensory_temp    tinyint not null default 0 comment '체감온도 사용여부. 0: 미사용, 1: 사용. 디바이스 구분이 풍속계일때 사용' after ai_link_url,
    add ref_device          int null comment '연관 디바이스. 예) 체감온도 사용시에는 온습도계 지정' after use_sensory_temp
;

update work_device_info
set mac_address_type = 1
where mac_address_type = 0
;


##############################
-- 현장 공지사항
##############################
create table imos_notice (
                             imnt_no              bigint primary key auto_increment comment '현장관제 공지번호',
                             subject              varchar(128) not null comment '제목',
                             content              text null comment '내용',
                             noti_all_flag        tinyint not null default 0 comment '전체 공지 여부. 0: 선택공지, 1: 전체공지',
                             importance           tinyint not null default 1 comment '중요도. 1: 1단계, 2: 2단계, 3: 3단계',
                             create_date          datetime default current_timestamp not null comment '생성일',
                             creator              varchar(20) not null comment '생성자',
                             update_date          datetime default current_timestamp not null comment '최종 수정일',
                             updater              varchar(20) not null comment '최종 수정자'
) ENGINE=InnoDB  charset=utf8 COMMENT '현장관제 공지 사항';

create table imos_notice_workplace (
                                       imnt_no             bigint not null comment '현장관제 공지번호',
                                       wp_id               varchar(50) not null comment '현장 아이디',
                                       create_date         datetime not null default now() comment '생성일',
                                       creator             varchar(20) not null comment '생성자',
                                       primary key (imnt_no, wp_id),
                                       constraint fk_imos_notice_workplace_imnt foreign key(imnt_no) references imos_notice(imnt_no) on delete cascade,
                                       constraint fk_imos_notice_workplace_wp foreign key(wp_id) references work_place(wp_id) on delete cascade
) ENGINE=InnoDB  charset=utf8 COMMENT '현장관제 공지 대상 정보';

create table imos_notice_file (
                                  file_no             bigint primary key auto_increment comment '파일 번호',
                                  imnt_no             bigint not null comment '현장관제 공지번호',
                                  file_name           varchar(255) null comment '파일명',
                                  file_name_org       varchar(255) null comment '원본 파일명',
                                  file_path           varchar(255) null comment '저장 위치(상대경로)',
                                  file_location       int null comment '저장소. 0: local Storage ',
                                  create_date         datetime not null default now() comment '생성일',
                                  creator             varchar(20) not null comment '생성자',
                                  constraint fk_imos_notice_file_imnt foreign key(imnt_no) references imos_notice(imnt_no) on delete cascade
) ENGINE=InnoDB  charset=utf8 COMMENT '현장관제 공지 파일';


##################################################
-- IntelliVix CCTV 추가 ( 통합관제용 )
##################################################
create table network_video_recoder
(
    nvr_no              bigint primary key auto_increment comment 'NVR 번호(seq)',
    nvr_name            varchar(64) not null comment 'NVR명',
    nvr_type            tinyint not null comment 'NVR 유형. 1: 일반, 2: IntelliVix',
    description         varchar(1024) not null comment 'NVR 설명',
    id                  varchar(16) not null comment 'NVR 연동 계정',
    pw                  varchar(16) not null comment 'NVR 연동 계정 패스워드',
    ip                  varchar(32) not null comment 'NVR 연동 IP',
    port                integer not null default 7681 comment 'NVR 연동 Port',
    status              tinyint not null default 0 comment 'NVR 사용여부. 0: 사용안함, 1: 사용',
    wp_id               varchar(50) null comment '현장 아이디',
    create_date         datetime default current_timestamp not null comment '생성일',
    creator             varchar(20) not null comment '생성자',
    update_date         datetime default current_timestamp not null comment '최종 수정일',
    updater             varchar(20) not null comment '최종 수정자',
    constraint fk_nvr_wp foreign key(wp_id) references work_place(wp_id) on delete set null
) ENGINE=InnoDB  charset=utf8 COMMENT 'NVR 정보';


create table network_video_recoder_ch
(
    gid                 varchar(40) not null primary key comment 'NVR 채널 아이디',
    nvr_no              bigint not null comment 'NVR 번호',
    uid                 integer not null comment 'NVR 에 따른 채널(정수형) 아이디',
    name                varchar(128) null comment '채널명',
    size_x              integer null comment '영상원본 해상도 X',
    size_y              integer null comment '영상원본 해상도 Y',
    ptz_control_auth    integer null comment 'PTZ 제어권 소유 여부. 0: 없음, 1: 있음',
    is_ptz              integer null comment 'ptz 카메라 여부. 0: 고정형 카메라, 1: ptz 카메라',
    id                  varchar(16) not null comment 'CCTV 계정',
    pw                  varchar(16) not null comment 'CCTV 계정 패스워드',
    ip                  varchar(32) not null comment 'CCTV IP',
    port                integer not null default 7681 comment 'CCTV Port',
    url                 varchar(128) not null comment 'CCTV 채널 URL',
    create_date         datetime default current_timestamp not null comment '생성일',
    creator             varchar(20) not null comment '생성자',
    update_date         datetime default current_timestamp not null comment '최종 수정일',
    updater             varchar(20) not null comment '최종 수정자',
    constraint fk_nvr_ch_nvr foreign key(nvr_no) references network_video_recoder(nvr_no) on delete cascade
) ENGINE=InnoDB  charset=utf8 COMMENT 'intelliVIX NVR 채널 정보';

create table nvr_event_log
(
    elog_no             bigint not null auto_increment comment '이벤트 로그 넘버',
    elog_id             varchar(64) not null comment '이벤트 아이디',
    tm                  datetime not null comment '로그 발생일',
    nvr_no              bigint not null comment 'NVR 번호',
    uid                 integer not null comment 'NVR 에 따른 채널(정수형) 아이디',
    gid                 varchar(40) not null comment 'NVR 채널 아이디',
    id                  bigint not null comment '관련 아이디(이벤트 유형에 따라 의미하는 바가 다르다)',
    type                varchar(2) not null comment '로그 유형',
    stat                varchar(2) not null comment '이벤트 상태. 1: 시작, 2: 계속, 4: 종료',
    pattr               varchar(8) null comment '보행자속성. 16진수표현. 보행자속성코드표 참고',
    ezn                 varchar(64) null comment '이벤트 존 이름',
    recv_host           varchar(64) null comment '이벤트 수신 Hostname',
    create_date         datetime default current_timestamp not null comment '생성일',
    primary key ( elog_no, tm ),
    index(elog_id)
) ENGINE=InnoDB  charset=utf8 COMMENT 'NVR 이벤트 발생 정보'
    PARTITION BY RANGE ( TO_DAYS(tm) )(
        PARTITION network_video_recoder_event_p202112 VALUES LESS THAN ( TO_DAYS('2022-01-01') ),
        PARTITION network_video_recoder_event_p202201 VALUES LESS THAN ( TO_DAYS('2022-02-01') ),
        PARTITION network_video_recoder_event_p202202 VALUES LESS THAN ( TO_DAYS('2022-03-01') ),
        PARTITION network_video_recoder_event_p202203 VALUES LESS THAN ( TO_DAYS('2022-04-01') ),
        PARTITION network_video_recoder_event_p202204 VALUES LESS THAN ( TO_DAYS('2022-05-01') ),
        PARTITION network_video_recoder_event_p202205 VALUES LESS THAN ( TO_DAYS('2022-06-01') ),
        PARTITION network_video_recoder_event_p202206 VALUES LESS THAN ( TO_DAYS('2022-07-01') )
        );


create table nvr_event
(
    event_no                bigint not null auto_increment primary key comment '이벤트 번호',
    elog_id                 varchar(64) not null comment '이벤트 아이디',
    start_elog_no           bigint not null comment '시작 이벤트 로그 넘버',
    start_tm                datetime not null comment '이벤트 발생일',
    end_elog_no             bigint null comment '종료 이벤트 로그 넘버',
    end_tm                  datetime null comment '이벤트 종료일',
    event_duration          bigint null comment '이벤트 유지시간 (second)',
    wp_id                   varchar(50) not null comment '현장 아이디',
    nvr_no                  bigint not null comment 'NVR 번호',
    nvr_name                varchar(64) not null comment 'NVR명',
    gid                     varchar(40) not null comment 'NVR 채널 아이디',
    name                    varchar(128) not null comment 'NVR 채널명',
    id                      bigint not null comment '관련 아이디(이벤트 유형에 따라 의미하는 바가 다르다)',
    type                    varchar(2) not null comment '이벤트 유형',
    stat                    varchar(2) not null comment '이벤트 상태. 1: 시작, 2: 계속, 4: 종료',
    pattr                   varchar(8) null comment '보행자속성. 16진수표현. 보행자속성코드표 참고',
    ezn                     varchar(64) null comment '이벤트 존 이름',
    recv_host               varchar(64) null comment '이벤트 수신 Hostname',
    action_status           integer not null default 0 comment '조치여부. 0: 미조치, 1: 조치완료',
    action_method           integer null comment '조치방법. 1: 확인 완료, 2: 감지오류',
    action_end_date         datetime null comment '조치완료 처리일',
    action_end_treator      varchar(20) null comment '조치완료 처리자',
    memo                    varchar(1024) null comment '메모',
    create_date             datetime default current_timestamp not null comment '생성일',
    update_date             datetime default current_timestamp not null comment '최종 수정일',
    updater                 varchar(20) null comment '최종 수정자',
    unique ( elog_id ),
    index( nvr_no, start_tm  ),
    index( gid, start_tm )
) ENGINE=InnoDB  charset=utf8 COMMENT 'NVR 이벤트 정보';

create table nvr_event_file
(
    file_no             bigint not null auto_increment primary key comment '이벤트 파일 번호',
    event_no            bigint not null comment '이벤트 번호',
    elog_no             bigint not null comment '이벤트 로그 넘버',
    stat                varchar(2) not null comment '이벤트 상태. 1: 시작, 2: 계속, 4: 종료',
    recv_host           varchar(64) null comment '이벤트 수신 Hostname',
    file_name           varchar(255) not null comment '파일명',
    file_path           varchar(255) not null comment '파일 저장 위치(상대경로)',
    file_location       integer not null comment '파일 저장소. 0: local Storage ',
    create_date         datetime default current_timestamp not null comment '생성일',
    index( event_no ),
    constraint fk_nvr_event_file_no foreign key(event_no) references nvr_event(event_no) on delete cascade
) ENGINE=InnoDB  charset=utf8 COMMENT 'NVR 이벤트 파일';

##################################################
-- 통합관제 버튼 추가
##################################################
create table hicc_main_btn
(
    hbtn_no             bigint not null auto_increment primary key comment '통합관제 버튼 관리번호',
    hicc_no             bigint not null comment '통합관제 관리번호',
    hbtn_name           varchar(16) not null comment '버튼명',
    hbtn_link_url       varchar(256) not null comment '링크 URL',
    create_date         datetime default current_timestamp not null comment '생성일',
    creator             varchar(20) not null comment '생성자',
    update_date         datetime default current_timestamp not null comment '최종 수정일',
    updater             varchar(20) not null comment '최종 수정자',
    constraint fk_hicc_main_btn_hicc foreign key(hicc_no) references hicc_info(hicc_no) on delete cascade
) ENGINE=InnoDB  charset=utf8 COMMENT '통합관제 메인 화면 링크 버튼 정보';


alter table work_place_cctv
    add cctv_kind integer not null default 0 comment 'CCTV 유형. 0:일반, 1: intelliVIX' after cctv_name,
    add gid       varchar(40) null comment '채널 아이디(cctv 아이디)' after ptz_status,
    add constraint uq_work_place_cctv_gid unique(gid)
;

alter table work_place_monitor_config
    add nvr_event integer not null default 0 comment 'NVR 이벤트 발송(팝업 표시) 여부. 0: 미표시, 1: 표시' after kalman_filter
;

##############################
-- 최근 센서 변경 이력 history 테이블
##############################
create table sensor_log_recent_hist (
                                        slr_no              bigint not null auto_increment comment '최근 센서 변경 이력 번호',
                                        wp_id               varchar(50) not null comment '현장 ID',
                                        si_idx              int not null comment 'sensor info 테이블 key',
                                        coop_mb_id          varchar(20) not null comment '협력사 ID',
                                        mb_id               varchar(20) not null comment '근로자 ID',
                                        in_out_type         tinyint(1) not null comment '센서 접근/이탈 구분 (0 : 접근, 1: 이탈)',
                                        slr_datetime        datetime not null comment '변경 시간',
                                        create_date         datetime not null default now() comment '변경 시간',
                                        primary key (slr_no, create_date)
) ENGINE=InnoDB  charset=utf8 COMMENT '최근 센서 변경 이력'
    PARTITION BY RANGE ( TO_DAYS(create_date) )(
        PARTITION sensor_log_recent_hist_p20211229 VALUES LESS THAN ( TO_DAYS('2021-12-30') ),
        PARTITION sensor_log_recent_hist_p20211230 VALUES LESS THAN ( TO_DAYS('2021-12-31') ),
        PARTITION sensor_log_recent_hist_p20211231 VALUES LESS THAN ( TO_DAYS('2022-01-01') ),
        PARTITION sensor_log_recent_hist_p20220101 VALUES LESS THAN ( TO_DAYS('2022-01-02') ),
        PARTITION sensor_log_recent_hist_p20220102 VALUES LESS THAN ( TO_DAYS('2022-01-03') ),
        PARTITION sensor_log_recent_hist_p20220103 VALUES LESS THAN ( TO_DAYS('2022-01-04') ),
        PARTITION sensor_log_recent_hist_p20220104 VALUES LESS THAN ( TO_DAYS('2022-01-05') ),
        PARTITION sensor_log_recent_hist_p20220105 VALUES LESS THAN ( TO_DAYS('2022-01-06') ),
        PARTITION sensor_log_recent_hist_p20220106 VALUES LESS THAN ( TO_DAYS('2022-01-07') ),
        PARTITION sensor_log_recent_hist_p20220107 VALUES LESS THAN ( TO_DAYS('2022-01-08') ),
        PARTITION sensor_log_recent_hist_p20220108 VALUES LESS THAN ( TO_DAYS('2022-01-09') ),
        PARTITION sensor_log_recent_hist_p20220109 VALUES LESS THAN ( TO_DAYS('2022-01-10') ),
        PARTITION sensor_log_recent_hist_p20220110 VALUES LESS THAN ( TO_DAYS('2022-01-11') ),
        PARTITION sensor_log_recent_hist_p20220111 VALUES LESS THAN ( TO_DAYS('2022-01-12') )
        );


create table worker_device_status_hist (
                                           wds_no              bigint not null auto_increment comment '단말 상태 변경 이력 번호',
                                           mb_id               varchar(20) not null comment '근로자 아이디',
                                           app_version         varchar(8) not null comment '앱 버전',
                                           os_type             varchar(8) not null comment 'ios/android ',
                                           os_version          varchar(8) not null comment 'OS 버전',
                                           device_model        varchar(32) not null comment '단말 모델',
                                           battery             decimal(4) not null comment '배터리 잔여량',
                                           charge_check        tinyint null comment '배터리 충전여부 미충전 : 0 , 충전 : 1, USB충전 : 2, AC충전 : 3, 무선 충전 : 4',
                                           ble_check           tinyint not null comment '블루투스 활성화 체크 OFF : 0 , ON : 1',
                                           loc_check           tinyint not null comment '위치 활성화 체크 OFF : 0 , ON : 1',
                                           si_code             varchar(48) null comment '센서명( 최종 1개)',
                                           upt_datetime        datetime default current_timestamp() not null,
                                           create_date         datetime not null default now() comment '변경 시간',
                                           primary key (wds_no, create_date)
) ENGINE=InnoDB  charset=utf8mb4 COMMENT '단말 상태 변경 이력'
    PARTITION BY RANGE ( TO_DAYS(create_date) )(
        PARTITION worker_device_status_hist_p20211229 VALUES LESS THAN ( TO_DAYS('2021-12-30') ),
        PARTITION worker_device_status_hist_p20211230 VALUES LESS THAN ( TO_DAYS('2021-12-31') ),
        PARTITION worker_device_status_hist_p20211231 VALUES LESS THAN ( TO_DAYS('2022-01-01') ),
        PARTITION worker_device_status_hist_p20220101 VALUES LESS THAN ( TO_DAYS('2022-01-02') ),
        PARTITION worker_device_status_hist_p20220102 VALUES LESS THAN ( TO_DAYS('2022-01-03') ),
        PARTITION worker_device_status_hist_p20220103 VALUES LESS THAN ( TO_DAYS('2022-01-04') ),
        PARTITION worker_device_status_hist_p20220104 VALUES LESS THAN ( TO_DAYS('2022-01-05') ),
        PARTITION worker_device_status_hist_p20220105 VALUES LESS THAN ( TO_DAYS('2022-01-06') ),
        PARTITION worker_device_status_hist_p20220106 VALUES LESS THAN ( TO_DAYS('2022-01-07') ),
        PARTITION worker_device_status_hist_p20220107 VALUES LESS THAN ( TO_DAYS('2022-01-08') ),
        PARTITION worker_device_status_hist_p20220108 VALUES LESS THAN ( TO_DAYS('2022-01-09') ),
        PARTITION worker_device_status_hist_p20220109 VALUES LESS THAN ( TO_DAYS('2022-01-10') ),
        PARTITION worker_device_status_hist_p20220110 VALUES LESS THAN ( TO_DAYS('2022-01-11') ),
        PARTITION worker_device_status_hist_p20220111 VALUES LESS THAN ( TO_DAYS('2022-01-12') )
        );



##################################################
-- 현장 - 건설사 1:N 분리 작업
##################################################
alter table attendance_book
    add cc_id varchar(50) null comment '건설사 아이디' after mb_level
;

alter table attendance_log
    add cc_id varchar(50) null comment '건설사 아이디' after mb_Id
;

update g5_member G5M
set cc_id = mb_12
where G5M.mb_level = 4
  and cc_id is null
;

alter table work_place_coop
    add cc_id varchar(50) null comment '건설사 아이디' after wp_name,
    add index idx_work_place_coop_wp_cc (wp_id, cc_id),
    add constraint fk_work_place_coop_cc  foreign key (cc_id) references con_company (cc_id)  on delete cascade
;

update work_place_coop WPC
    inner join work_place WP ON WP.wp_id = WPC.wp_id
    inner join con_company CC on WP.cc_id = CC.cc_id
set WPC.cc_id = CC.cc_id
where WPC.cc_id is null
;

/* 향후에는 필수여야 한다. (  편입 정보가 불완전하면 삭제? )
delete WPC from work_place_coop WPC
left join work_place WP ON WP.wp_id = WPC.wp_id
left join con_company CC on WP.cc_id = CC.cc_id
where CC.cc_id is null
;

alter table work_place_coop
    modify cc_id varchar(50) not null comment '건설사 아이디'
;
 */

update attendance_book ADB
    inner join work_place_coop WPC ON WPC.wp_id = ADB.wp_id AND WPC.coop_mb_id = ADB.coop_mb_id
set ADB.cc_id = WPC.cc_id
where WPC.coop_mb_id IS NOT NULL
;


create table construction_site (
                                   wp_id varchar(50) not null comment '현장 아이디',
                                   cc_id varchar(50) not null comment '건설사 아이디',
                                   create_date datetime default CURRENT_TIMESTAMP not null comment '생성일',
                                   creator varchar(20) not null comment '생성자',
                                   primary key (wp_id, cc_id),
                                   constraint fk_construction_site_wp foreign key (wp_id) references work_place (wp_id) on delete cascade,
                                   constraint fk_construction_site_cc foreign key (cc_id) references con_company (cc_id) on delete cascade
)  ENGINE=InnoDB  charset=utf8 COMMENT '건설사 현장 편입 정보';

create table construction_site_manager (
                                           wp_id varchar(50) not null comment '현장 아이디',
                                           mb_id varchar(20) not null comment '현장 관리자 아이디',
                                           cc_id varchar(50) not null comment '건설사 아이디',
                                           create_date datetime default CURRENT_TIMESTAMP not null comment '생성일',
                                           creator varchar(20) not null comment '생성자',
                                           primary key (wp_id, mb_id),
                                           unique(mb_id),
                                           index idx_construction_site_manager_cc(cc_id),
                                           constraint fk_construction_site_manager_mb foreign key (mb_id) references g5_member (mb_id) on delete cascade,
                                           constraint fk_construction_site_manager_wp_cc  foreign key (wp_id, cc_id) references construction_site (wp_id, cc_id)  on delete cascade
) ENGINE=InnoDB  charset=utf8 COMMENT '건설현장 현장관리자';

insert into construction_site (wp_id, cc_id, create_date, creator)
select
    WP.wp_id,
    WP.cc_id,
    WP.wp_datetime, 'admin'
from work_place WP
         inner join con_company CC on WP.cc_id = CC.cc_id
;

insert into construction_site_manager(wp_id, cc_id, mb_id, create_date, creator)
select
    CS.wp_id, CS.cc_id, WP.man_mb_id, NOW(), 'admin'
from work_place WP
         inner join g5_member GM ON GM.mb_id = WP.man_mb_id
         inner join construction_site CS on WP.wp_id = CS.wp_id and CS.cc_id = WP.cc_id
where WP.man_mb_id IS NOT NULL
;

##################################################
-- 장비 타입 추가
##################################################
insert into equipment_code(equipment_type, equipment_name, icon_type)
values
( 15, '오거크레인', 17 ),
( 16, 'SGR천공기', 17 ),
( 17, '펌프카', 17 ),
( 18, '타워크레인', 17 ),
( 19, '공기압축기', 17 )
;

##################################################
-- 풍속계 임계치 관리 추가
##################################################

drop table wind_speed_range_policy;
create table wind_speed_range_policy
(
    policy_no int auto_increment primary key comment '풍속 임계치 정책 구분자',
    policy_name varchar(100) not null comment '정책명',
    creator integer comment '정보 등록 사용자',
    updater integer comment '정보 수정 사용자',
    create_date datetime not null comment '정보 최초 생성 시간',
    update_date datetime not null comment '정보 최근 변경 시간'
) ENGINE=InnoDB  charset=utf8 COMMENT '풍속 임계치 정책';

drop table wind_speed_range;
create table wind_speed_range
(
    idx int auto_increment primary key comment '풍속 구간 별 임계치 정책 구분자',
    policy_no int not null comment '풍속 임계치 정책 구분자',
    display_name varchar(50) not null comment '풍속 구간 설명 문구',
    range_type          tinyint not null default 1 comment '풍속 구간 유형. 1: 일반, 2: 경계, 3: 중지',
    speed_avg_more_than double not null comment '10분 평균 풍속 최소값 (이상)',
    speed_avg_less_than double not null comment '10분 평균 풍속 최대값 (미만)',
    speed_max_more_than double not null comment '10분 최대 풍속 최소값 (이상)',
    speed_max_less_than double not null comment '10분 최대 풍속 최대값 (미만)',
    alert tinyint not null default 0 comment '경고 여부. 0: 미경고, 1: 경고',
    status_message text not null comment '풍속 상태 메시지 - IMOS 표시 용도'
);

ALTER TABLE wind_speed_range_policy AUTO_INCREMENT=3;

insert into wind_speed_range_policy(policy_no, policy_name, creator, updater, create_date, update_date)
values
( 1, '디폴트 풍속 임계치 정책', 1, 1, now(), now() ),
( 2, '인천공항 T2 장기주차장 풍속계 풍속 임계치 정책',  1, 1, now(), now() )
;

insert into wind_speed_range(policy_no, display_name, range_type, speed_avg_more_than, speed_avg_less_than, speed_max_more_than, speed_max_less_than, alert, status_message)
values
(  1, '풍속 1단계', 1, 0, 2, 0, 3, 0, '바람이 매우 약하게 불고 있습니다' ),
(  1, '풍속 2단계', 1, 2, 5, 3, 8, 0,  '바람이 약하게 불고 있습니다' ),
(  1, '풍속 3단계', 1, 5, 9, 8, 13, 0,  '바람이 다소 불고 있습니다' ),
(  1, '풍속 4단계', 1, 9, 13, 13, 19, 0,  '바람이 다소 강하게 불고 있습니다' ),
(  1, '폭풍주의보', 2, 13, 18, 19, 25, 1,  '폭풍주의보가 발생하였습니다' ),
(  1, '폭풍경보', 3, 18, 100, 26, 100, 1, '폭풍경보가 발생하였습니다' ),

(  2, '풍속 1단계', 1, 0, 2, 0, 3, 0, '바람이 매우 약하게 불고 있습니다' ),
(  2, '풍속 2단계', 1, 2, 5, 3, 8, 0, '바람이 약하게 불고 있습니다' ),
(  2, '풍속 3단계', 1, 5, 10, 8, 10, 0, '바람이 다소 불고 있습니다' ),
(  2, '경계단계', 2, 10, 15, 10, 15, 1, '타워크레인 등 대형 건설기계의 설치, 해체작업을 중단해 주시기 바랍니다'),
(  2, '중지단계', 3, 15, 100, 15, 100, 1, '타워크레인을 사용한 양중작업, 고소작업대를 사용한 고소작업 등을 중단해 주시기 바랍니다' )
;

alter table work_place add column wind_speed_policy_no int comment '풍속 임계치 정책 구분자' after gas_policy_no;

update work_place
set wind_speed_policy_no = 1
where wind_speed_policy_no is null
;

update work_place
set wind_speed_policy_no = 2
where wp_id = 'e3a13f5fdbaf469eb48e1097d82e9c60'
;



##################################################
-- 소음 측정 데이터
##################################################

create table noise_meter_log_recent (
                                        mac_address varchar(50) not null primary key comment '소음측정기 Mac 주소',
                                        measure_time datetime not null comment '수집시간.',
                                        display_name varchar(50) not null comment '소음측정기명',
                                        noise double not null comment '측정수치. 단위 dB',
                                        noise_avg double not null comment '최근 5분간 평균 수치. 단위 dB',
                                        dashboard_popup tinyint default 1 not null comment '현장관제 팝업 표시 여부. 0.표시안함 1.해지시까지 표시(기본값)'
) ENGINE=InnoDB  charset=utf8 COMMENT '소음측정기 최근 수치';

create table noise_meter_log (
                                 mac_address varchar(50) not null comment '소음측정기 Mac 주소',
                                 measure_time datetime not null comment '수집시간.',
                                 display_name varchar(50) not null comment '소음측정기명',
                                 noise double not null comment '측정수치. 단위 dB',
                                 noise_avg double not null comment '최근 5분간 평균 수치. 단위 dB',
                                 primary key (mac_address, measure_time)
) ENGINE=InnoDB  charset=utf8 COMMENT '소음측정기 측정 수치 로그'
    PARTITION BY RANGE ( TO_DAYS(measure_time) )(
        PARTITION noise_meter_log_p202201 VALUES LESS THAN ( TO_DAYS('2022-02-01') ),
        PARTITION noise_meter_log_p202202 VALUES LESS THAN ( TO_DAYS('2022-03-01') ),
        PARTITION noise_meter_log_p202203 VALUES LESS THAN ( TO_DAYS('2022-04-01') ),
        PARTITION noise_meter_log_p202204 VALUES LESS THAN ( TO_DAYS('2022-05-01') ),
        PARTITION noise_meter_log_p202205 VALUES LESS THAN ( TO_DAYS('2022-06-01') ),
        PARTITION noise_meter_log_p202206 VALUES LESS THAN ( TO_DAYS('2022-07-01') )
        );


##################################################
-- 탈퇴 이력 테이블 ( 30일이 경과한 데이터는 삭제하자 )
##################################################


create table withdraw_member
(
    mb_no int ,
    mb_id varchar(20)  null,
    mb_password varchar(180)  null,
    mb_name varchar(180)  null,
    mb_nick varchar(180)  null,
    mb_nick_date date null,
    mb_email varchar(180)  null,
    mb_homepage varchar(180)  null,
    mb_level tinyint null,
    mb_sex char  null,
    mb_birth varchar(10)  null,
    mb_tel varchar(20)  null,
    mb_hp varchar(20)  null,
    mb_certify varchar(20)  null,
    mb_adult tinyint null,
    mb_dupinfo varchar(180)  null,
    mb_zip1 varchar(3)  null,
    mb_zip2 varchar(3)  null,
    mb_addr1 varchar(180)  null,
    mb_addr2 varchar(180)  null,
    mb_addr3 varchar(180)  null,
    mb_addr_jibeon varchar(180)  null,
    mb_signature text null,
    mb_recommend varchar(180)  null,
    mb_point int null,
    mb_today_login datetime null,
    mb_login_ip varchar(180)  null,
    mb_datetime datetime null,
    mb_ip varchar(180)  null,
    mb_leave_date varchar(8)  null,
    mb_intercept_date varchar(8)  null,
    mb_email_certify datetime null,
    mb_email_certify2 varchar(180)  null,
    mb_memo text null,
    mb_lost_certify varchar(180) null,
    mb_mailling tinyint null,
    mb_sms tinyint null,
    mb_open tinyint null,
    mb_open_date date null,
    mb_profile text null,
    mb_memo_call varchar(180)  null,
    mb_1 varchar(180)  null,
    mb_2 varchar(180)  null,
    mb_3 varchar(180)  null,
    mb_4 varchar(180)  null,
    mb_5 varchar(180)  null,
    mb_6 varchar(180)  null,
    mb_7 varchar(180)  null,
    mb_8 varchar(180)  null,
    mb_9 varchar(180)  null,
    mb_10 varchar(180)  null,
    mb_11 varchar(180)  null,
    mb_12 varchar(180)  null,
    mb_13 varchar(180)  null,
    mb_14 varchar(180)  null,
    mb_15 varchar(180)  null,
    mb_16 varchar(180)  null,
    mb_17 varchar(180)  null,
    mb_18 varchar(180)  null,
    mb_19 varchar(180)  null,
    mb_20 varchar(180)  null,
    app_version varchar(6) null comment '사용중인 app_version null 일시 ios, 버전 기입시 aos로 구분',
    cc_id varchar(50) null comment '건설사 아이디 ( level 이 5: 건설사 관리자 인 경우 필수 )',
    work_section_a varchar(8) null comment '공종A 코드 (협력사 및 근로자용)',
    pwd_change_date datetime null comment '패스워드 변경일',
    attempt_login_count int null comment '로그인 시도수(실패수)',
    office_no bigint null comment '발주처번호 ( level 이 6: 발주처 현장그룹 매니저, 7: 발주처 관리자인 경우 필수 )',
    withdraw_date datetime not null default CURRENT_TIMESTAMP comment '탈퇴일',
    withdraw_type tinyint not null default 0 comment '탈퇴 유형. 1: [App]근로자 본인 탈퇴, 2:[Adm]관리자에 의한 강제 탈퇴',
    withdraw_desc varchar(128) null comment '탈퇴 관련 비고',
    primary key (mb_no, withdraw_date)
) ENGINE=InnoDB charset=utf8 comment '탈퇴 이력 테이블'
;