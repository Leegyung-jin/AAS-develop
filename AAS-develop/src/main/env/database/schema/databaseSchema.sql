#################################################
## 2021-08-03
#################################################

create table admin_msg_info
(
    idx int auto_increment comment '일렬번호'
        primary key,
    mb_id varchar(50) null comment '현장관리자 아이디',
    wp_id varchar(50) null comment '현장 코드',
    coop_mb_id varchar(50) null comment '협력사 아이디',
    msg_type tinyint null comment '메시지 알림 유형
1: 위험지역 접근 알림.
2: 감시 센서 접근 알림.
3: 앱 기능 해제 알림.
',
    subject varchar(256) null comment '메시지 제목',
    msg varchar(4000) null comment '메시지 내용',
    is_send tinyint null comment 'push 발송 여부
0 : 발송
1 : 미 발송.
',
    upt_datetime datetime null comment '생성/수정 시간'
)
    comment '현장 관리자 알림 메시지 관리' engine=MyISAM charset=utf8;

create index mb_id_wp_id_coop_mb_id
    on admin_msg_info (mb_id, wp_id, coop_mb_id);

create index wp_id_upt_datetime
    on admin_msg_info (wp_id, upt_datetime);

create table admin_msg_type
(
    msg_type tinyint not null comment 'CCTV 번호'
        primary key,
    msg_name varchar(32) not null comment 'CCTV 명',
    description varchar(256) null comment 'CCTV 명',
    alarm_grade int not null comment '위험등급, 1: 주의 , 2: 경계, 3: 심각',
    create_date datetime default CURRENT_TIMESTAMP not null comment '생성일',
    creator varchar(20) not null comment '생성자 아이디(mb_id) ',
    update_date datetime default CURRENT_TIMESTAMP not null comment '수정일',
    updater varchar(20) not null comment '수정자 아이디(mb_id) '
)
    comment '알림메세지 타입 정보' charset=utf8;

create table air_environment
(
    station_name varchar(32) not null comment '측정소명'
        primary key,
    sido_name varchar(8) null comment '시도명',
    mang_name varchar(16) null comment '측정망 정보. 도시대기, 도로변대기, 국가배경농도, 교외대기, 항만',
    khai_value varchar(16) null comment '통합대기환경수치',
    khai_grade varchar(16) null comment '통합대기환경수치 지수 등급',
    pm25_value varchar(16) null comment '초미세먼지(PM2.5) 농도 (단위 : ㎍/㎥)',
    pm25_value24 varchar(16) null comment '초미세먼지(PM2.5) 24시간예측이동농도 (단위 : ㎍/㎥)',
    pm25_grade1h varchar(16) null comment '초미세먼지(PM2.5) 1시간 등급자료',
    pm25_grade varchar(16) null comment '초미세먼지(PM2.5) 24시간 등급자료',
    pm25_flag varchar(16) null comment '초미세먼지(PM2.5) 측정자료 상태 정보(점검및교정,장비점검,자료이상,통신장애)',
    pm10_value varchar(16) null comment '미세먼지(PM10) 농도 (단위 : ㎍/㎥)',
    pm10_value24 varchar(16) null comment '미세먼지(PM10) 24시간예측이동농도 (단위 : ㎍/㎥)',
    pm10_grade1h varchar(16) null comment '미세먼지(PM10) 1시간 등급자료',
    pm10_grade varchar(16) null comment '미세먼지(PM10) 24시간 등급자료',
    pm10_flag varchar(16) null comment '미세먼지(PM10) 측정자료 상태 정보(점검및교정,장비점검,자료이상,통신장애)',
    so2_value varchar(16) null comment '아황산가스 농도 (단위 : ppm)',
    so2_grade varchar(16) null comment '아황산가스 지수 등급',
    so2_flag varchar(16) null comment '아황산가스 측정자료 상태 정보(점검및교정,장비점검,자료이상,통신장애)',
    o3_value varchar(16) null comment '오존 농도 (단위 : ppm)',
    o3_grade varchar(16) null comment '오존 지수 등급',
    o3_flag varchar(16) null comment '오존 측정자료 상태 정보(점검및교정,장비점검,자료이상,통신장애)',
    co_value varchar(16) null comment '일산화탄소 농도 (단위 : ppm)',
    co_grade varchar(16) null comment '일산화탄소 지수 등급',
    co_flag varchar(16) null comment '일산화탄소 측정자료 상태 정보(점검및교정,장비점검,자료이상,통신장애)',
    no2_value varchar(16) null comment '이산화질소 농도 (단위 : ppm)',
    no2_grade varchar(16) null comment '이산화질소 지수 등급',
    no2_flag varchar(16) null comment '이산화질소 측정자료 상태 정보(점검및교정,장비점검,자료이상,통신장애)',
    data_time datetime not null comment '측정시간',
    create_date datetime default CURRENT_TIMESTAMP not null comment '생성시간'
)
    comment '측정소별 대기오염정보' charset=utf8mb4;

create table alarm_check_policy_info
(
    idx bigint auto_increment
        primary key,
    wp_id varchar(50) null comment '현장 id, null 일시 공통 반영',
    work_check tinyint null comment '출근 알림 감시 여부 (1 : 포함)',
    safe_check tinyint null comment '안전조회 알림 감시 여부 (1 : 포함)',
    alarm_type tinyint null comment '로컬 알림 d (1: 출 알림, 2: [일] 1회 알림)',
    report_type tinyint null comment '보고 형식 (1 : 1회 알림. , 2 : [일] 1회 알림.)',
    report_sub_type tinyint null comment '추가 알림 형식 (report interval 기준) 1 : 1회, 2: 계속. ',
    report_interval bigint null comment '보고 주기(ms) - 해당 주기 이상 미 동작 시 서버에 report 실시',
    alarm_interval bigint null comment '로컬 알림 주기(ms) - 해당 주기 이상 미동작 시 단말에서 로컬알림 발생',
    noti_allow_start varchar(6) null comment '알림 허용 시작 시간(내부 알림용, report와는 무관)\r\nHHmmss',
    noti_allow_end varchar(6) null comment '알림 허용 종료 시간(내부 알림용, report와는 무관)\r\nHHmmss\r\n',
    udp_datetime datetime null comment '변경일',
    updater varchar(50) null comment '변경자 ID'
)
    comment '알림 상태 조회 정책 관리' engine=MyISAM charset=utf8;

create index wp_id
    on alarm_check_policy_info (wp_id);

create table app_menu_visibility
(
    wp_id varchar(50) not null,
    mb_level tinyint not null,
    area_name varchar(50) not null,
    no tinyint not null comment '기능번호',
    title varchar(50) not null comment 'main_top title은 개행문자를 포함한 경우가 있으므로 주의',
    visibility tinyint(1) default 1 not null,
    display_order tinyint not null comment 'display 순서',
    primary key (wp_id, mb_level, area_name, no)
)
    charset=utf8;

create table app_version
(
    version_android varchar(10) not null,
    version_ios varchar(10) not null
)
    charset=utf8;

create table app_version2
(
    version_android varchar(10) not null,
    version_ios varchar(10) not null
)
    charset=utf8;

create table attendance_book
(
    working_day varchar(8) not null comment '출근일. YYYYMMDD ',
    wp_id varchar(50) not null comment '현장 아이디',
    mb_id varchar(20) not null comment '근로자 아이디',
    coop_mb_id varchar(20) null comment '출근당시 협력사 아이디',
    work_in_date datetime default CURRENT_TIMESTAMP not null comment '출근시간',
    work_in_method int null comment '출근방법. 1: app, 2: qrcode, 3: entrgate, 4: beacon, 5: geofence, 99: 기타',
    work_in_mac_addr varchar(32) null comment '장비 Mac address',
    work_in_device_nm varchar(16) null comment '장비명',
    work_in_si_idx varchar(16) null comment '출근센서 넘버',
    work_in_temperature varchar(8) null comment '체온',
    work_in_desc varchar(256) null comment '출근 비고',
    work_out_date datetime null comment '퇴근시간',
    work_out_method int null comment '퇴근방법. 1: app, 2: qrcode, 3: entrgate, 4: beacon, 5: geofence, 99: 기타',
    work_out_mac_addr varchar(32) null comment '장비 Mac address',
    work_out_device_nm varchar(16) null comment '장비명',
    work_out_si_idx varchar(16) null comment '퇴근센서 넘버',
    work_out_desc varchar(256) null comment '퇴근 비고',
    working_comment varchar(1024) null comment '비고',
    work_status int default 1 not null comment '1: 출근, 2: 퇴근',
    safety_edu_start datetime null comment '안전교육 참석 시작 시간',
    safety_edu_end datetime null comment '안전교육 참석 종료 시간',
    use_app int null comment '앱 사용 여부(앱상태로그 존재 여부)',
    create_date datetime default CURRENT_TIMESTAMP not null comment '생성시간',
    primary key (working_day, wp_id, mb_id)
)
    comment '현장별 출근일지' charset=utf8;

create table attendance_book_month_stat
(
    working_month varchar(6) not null comment '출근월. YYYYMM ',
    wp_id varchar(50) not null comment '현장 아이디',
    mb_id varchar(20) not null comment '근로자 아이디',
    coop_mb_id varchar(20) not null comment '협력사 아이디',
    working_day_count int default 0 not null comment '출역일수',
    safety_edu_count int default 0 not null comment '안전교육 참석일수',
    use_app_count int default 0 not null comment '앱활성 일수',
    create_date datetime default CURRENT_TIMESTAMP not null comment '생성일',
    primary key (working_month, wp_id, mb_id, coop_mb_id)
)
    comment '월별 사용자 출근 현황' charset=utf8;

create table attendance_log
(
    attd_no bigint auto_increment comment '현장 진입/이탈 로그번호'
        primary key,
    wp_id varchar(50) not null comment '현장 아이디',
    mb_id varchar(20) not null comment '근로자 아이디',
    coop_mb_id varchar(20) null comment '출근당시 협력사 아이디',
    work_date datetime default CURRENT_TIMESTAMP not null comment '진입/이탈 시간',
    work_method int null comment '출근방법. 1: app, 2: qrcode, 3: entrgate, 4: beacon, 5: geofence, 99: 기타',
    work_mac_addr varchar(32) null comment '장비 Mac address',
    work_device_nm varchar(16) null comment '장비명',
    work_si_idx varchar(16) null comment '출근센서 넘버',
    work_temperature varchar(8) null comment '체온',
    work_desc varchar(256) null comment '출근 비고',
    work_status int default 1 not null comment '1: 출근, 2: 퇴근',
    create_date datetime default CURRENT_TIMESTAMP not null comment '생성시간'
)
    comment '현장 진입/이탈 로그' charset=utf8;

create table building
(
    building_no bigint auto_increment comment '빌딩 넘버(SEQ)'
        primary key,
    wp_id varchar(50) not null comment '현장 아이디',
    building_name varchar(64) not null comment '빌딩명',
    floor_upstair int not null comment '지상 층수',
    floor_downstair int not null comment '지하 층수',
    pos_x int not null comment '도면 위치 X 좌표',
    pos_y int not null comment '도면 위치 Y 좌표',
    box_x int default 0 not null comment '건물정보란 도면 x 위치 ',
    box_y int default 0 not null comment '건물정보란 도면 y 위치',
    create_date datetime default CURRENT_TIMESTAMP not null comment '생성일',
    creator varchar(20) not null comment '생성자 아이디(mb_id) ',
    update_date datetime default CURRENT_TIMESTAMP not null comment '수정일',
    updater varchar(20) not null comment '수정자 아이디(mb_id) ',
    view_floor_file_name varchar(255) null comment '디폴트 층도면 파일명',
    view_floor_file_name_org varchar(255) null comment '디폴트 층도면 원본 파일명',
    view_floor_file_path varchar(255) null comment '디폴트 층도면 파일 저장 위치(상대경로)',
    view_floor_file_location int null comment '디폴트 층도면 파일 저장소. 0: local Storage ',
    cross_section_file_name varchar(255) null comment '단면도 파일명',
    cross_section_file_name_org varchar(255) null comment '단면도 원본 파일명',
    cross_section_file_path varchar(255) null comment '단면도 파일 저장 위치(상대경로)',
    cross_section_file_location int null comment '단면도 파일 저장소. 0: local Storage ',
    area_type int default 1 not null comment '지역 타입. 1: 빌딩, 2: 지상, 3: 지하',
    contain_roof int default 0 not null comment '옥상 포함 여부. 0: 없음, 1: 있음',
    contain_gangform int default 0 not null comment '갱폼 포함 여부. 0: 없음, 1: 있음'
)
    comment '현장 빌딩 정보';

create index idx_building_wp_id
    on building (wp_id);

create table building_floor
(
    building_no bigint not null comment '빌딩 넘버(SEQ)',
    floor int not null comment '빌딩 층',
    view_floor_file_name varchar(255) null comment '층도면 파일명',
    view_floor_file_name_org varchar(255) null comment '층도면 원본 파일명',
    view_floor_file_path varchar(255) null comment '층도면 파일 저장 위치(상대경로)',
    view_floor_file_location int null comment '층도면 파일 저장소. 0: local Storage ',
    update_date datetime default CURRENT_TIMESTAMP not null comment '수정일',
    updater varchar(20) not null comment '수정자 아이디(mb_id) ',
    cross_section_grid_x int default 0 not null comment '단면도 층 표시 x 축 좌표',
    cross_section_grid_y int default 0 not null comment '단면도 층 표시 y 축 좌표',
    box_x int default 10 not null comment '구획 정보 표시 x 위치 ',
    box_y int default 10 not null comment '구획 정보 표시 y 위치',
    floor_name varchar(16) default '' not null comment '층명',
    floor_type int default 1 not null comment '층타입. 1: 층, 1000: 옥상,  2000: 갱폼',
    activated int default 0 not null comment '층 작업진행 여부. 0: 미진행, 1: 진행',
    primary key (building_no, floor),
    constraint building_floor_ibfk_1
        foreign key (building_no) references building (building_no)
            on delete cascade
)
    comment '빌딩 층 정보';

create table con_company
(
    cc_id varchar(50) not null comment '건설사 고유코드'
        primary key,
    cc_name varchar(50) not null comment '건설사명',
    cc_num varchar(20) not null comment '사업자등록번호',
    cc_zip1 varchar(3) not null comment '우편번호 앞자리',
    cc_zip2 varchar(3) not null comment '우편번호 뒷자리',
    cc_addr1 varchar(255) not null comment '기본주소',
    cc_addr2 varchar(255) not null comment '상세주소',
    cc_addr3 varchar(255) not null comment '참고항목',
    cc_addr_jibeon varchar(1) not null comment '주소구분',
    cc_tel varchar(20) not null comment '전화번호',
    cc_memo text not null comment '메모',
    icon_file_name varchar(255) null comment '아이콘 파일명',
    icon_file_name_org varchar(255) null comment '아이콘 원본 파일명',
    icon_file_path varchar(255) null comment '아이콘 저장 위치(상대경로)',
    icon_file_location int null comment '아이콘 저장소. 0: local Storage ',
    bg_img_file_name varchar(255) null comment '백그라운드 이미지 파일명',
    bg_img_file_name_org varchar(255) null comment '백그라운드 이미지 원본 파일명',
    bg_img_file_path varchar(255) null comment '백그라운드 이미지 저장 위치(상대경로)',
    bg_img_file_location int null comment '백그라운드 이미지 저장소. 0: local Storage ',
    bg_color varchar(16) null comment '백그라운드 색',
    cc_datetime datetime not null comment '등록일시'
)
    comment '건설사 정보' charset=utf8;

create index cc_name
    on con_company (cc_name);

create table crash_log
(
    NID bigint auto_increment
        primary key,
    mb_id varchar(20) null,
    APP_VERSION varchar(20) null,
    OS_TYPE varchar(20) null,
    OS_VERSION varchar(20) null,
    DEVICE_MODEL varchar(64) null,
    CATEGORY varchar(255) null,
    STACK_TRACE mediumtext null,
    CREATE_DATE timestamp default CURRENT_TIMESTAMP null
)
    charset=utf8;

create index mb_id
    on crash_log (mb_id);

create index mb_id_CREATE_DATE
    on crash_log (mb_id, CREATE_DATE);

create table device_check_policy_info
(
    idx bigint auto_increment
        primary key,
    wp_id varchar(50) null comment '현장 id , null 일시 default 정책',
    ble_check tinyint null comment '블루투스 활성화 감시 여부
1 : 포함
',
    loc_check tinyint null comment '위치 활성화 감시 여부 (1 : 포함)',
    power_check tinyint null comment '단말 전원 ON/OFF',
    alarm_type tinyint null comment '로컬 알림 형식 (1: 1회 알림, 2: [일] 1회 알림)',
    report_type tinyint null comment '보고 형식 (1 : 1회 알림. , 2 : [일] 1회 알림.)',
    report_sub_type tinyint null comment '추가 알림 형식 (report interval 기준) 1 : 1회, 2: 계속. ',
    report_interval bigint null comment '보고 주기(ms) - 해당 주기 이상 미 동작 시 서버에 report 실시',
    alarm_interval bigint null comment '로컬 알림 주기(ms) - 해당 주기 이상 미동작 시 단말에서 로컬알림 발생',
    noti_allow_start varchar(6) null comment '알림 허용 시작 시간(내부 알림용, report와는 무관)
HHmmss',
    noti_allow_end varchar(6) null comment '알림 허용 종료 시간(내부 알림용, report와는 무관)
HHmmss
',
    udp_datetime datetime null comment '변경 시간',
    updater varchar(50) null comment '변경자 ID'
)
    comment '단말 감시 정책 테이블
' engine=MyISAM charset=utf8;

create index wp_id
    on device_check_policy_info (wp_id);

create table device_model_policy_info
(
    idx int unsigned auto_increment comment '일렬번호'
        primary key,
    device_model varchar(24) not null comment '단말모델',
    scan_interval int null comment '센서 스캔 주기(ms) - null 일시 현장의 스캔 주기 정책 반영',
    idle_interval int null comment '센서 스캔 idle 주기(ms) - null 일시 현장의 스캔 주기 정책 반영',
    report_interval int null comment '센서 스캔 결과 서버 report 주기(ms) - null 일시 현장의 스캔 주기 정책 반영',
    create_date datetime null comment '등록일',
    update_date datetime null comment '변경일',
    mb_id varchar(20) null comment '변경자 아이디',
    enable tinyint(1) unsigned zerofill default 0 null comment '사용여부 0 : 미사용, 1: 사용'
)
    charset=utf8;

create index device_model
    on device_model_policy_info (device_model, enable);

create table entr_gate_account
(
    account_id varchar(16) not null comment '연동 계정 아이디'
        primary key,
    account_pwd varchar(64) not null comment '연동 계정 패스워드',
    account_name varchar(32) not null comment '계정명',
    description varchar(1024) null comment '설명',
    status int default 0 not null comment '상태. 0: 미사용, 1: 사용',
    creator varchar(20) not null comment '생성자',
    create_date datetime default CURRENT_TIMESTAMP not null comment '생성일',
    updater varchar(20) not null comment '수정자',
    update_date datetime default CURRENT_TIMESTAMP not null comment '수정일'
)
    comment '출입게이트 이벤트 수신을 위한 연동 계정' charset=utf8;

create table equipment_code
(
    equipment_type tinyint not null
        primary key,
    equipment_name varchar(50) null,
    icon_type tinyint null
)
    charset=utf8;

create table falling_accident_recent
(
    mb_id varchar(20) not null
        primary key,
    wp_id varchar(50) not null,
    measure_time timestamp not null,
    dashboard_popup tinyint default 1 not null comment '안전모니터 팝업 표시 여부. 0.표시안함 1.해지시까지 표시(기본값)'
);

create table g5_auth
(
    mb_id varchar(20) default '' not null,
    au_menu varchar(20) default '' not null,
    au_auth set('r', 'w', 'd') default '' not null,
    primary key (mb_id, au_menu)
)
    charset=utf8;

create table g5_autosave
(
    as_id int auto_increment
        primary key,
    mb_id varchar(20) not null,
    as_uid bigint unsigned not null,
    as_subject varchar(180) not null,
    as_content text not null,
    as_datetime datetime not null,
    constraint as_uid
        unique (as_uid)
)
    charset=utf8;

create index mb_id
    on g5_autosave (mb_id);

create table g5_board
(
    bo_table varchar(50) default '' not null
        primary key,
    gr_id varchar(180) default '' not null,
    bo_subject varchar(180) default '' not null,
    bo_mobile_subject varchar(180) default '' not null,
    bo_device enum('both', 'pc', 'mobile') default 'both' not null,
    bo_admin varchar(180) default '' not null,
    bo_list_level tinyint default 0 not null,
    bo_read_level tinyint default 0 not null,
    bo_write_level tinyint default 0 not null,
    bo_reply_level tinyint default 0 not null,
    bo_comment_level tinyint default 0 not null,
    bo_upload_level tinyint default 0 not null,
    bo_download_level tinyint default 0 not null,
    bo_html_level tinyint default 0 not null,
    bo_link_level tinyint default 0 not null,
    bo_count_delete tinyint default 0 not null,
    bo_count_modify tinyint default 0 not null,
    bo_read_point int default 0 not null,
    bo_write_point int default 0 not null,
    bo_comment_point int default 0 not null,
    bo_download_point int default 0 not null,
    bo_use_category tinyint default 0 not null,
    bo_category_list text not null,
    bo_use_sideview tinyint default 0 not null,
    bo_use_file_content tinyint default 0 not null,
    bo_use_secret tinyint default 0 not null,
    bo_use_dhtml_editor tinyint default 0 not null,
    bo_use_rss_view tinyint default 0 not null,
    bo_use_good tinyint default 0 not null,
    bo_use_nogood tinyint default 0 not null,
    bo_use_name tinyint default 0 not null,
    bo_use_signature tinyint default 0 not null,
    bo_use_ip_view tinyint default 0 not null,
    bo_use_list_view tinyint default 0 not null,
    bo_use_list_file tinyint default 0 not null,
    bo_use_list_content tinyint default 0 not null,
    bo_table_width int default 0 not null,
    bo_subject_len int default 0 not null,
    bo_mobile_subject_len int default 0 not null,
    bo_page_rows int default 0 not null,
    bo_mobile_page_rows int default 0 not null,
    bo_new int default 0 not null,
    bo_hot int default 0 not null,
    bo_image_width int default 0 not null,
    bo_skin varchar(180) default '' not null,
    bo_mobile_skin varchar(180) default '' not null,
    bo_include_head varchar(180) default '' not null,
    bo_include_tail varchar(180) default '' not null,
    bo_content_head text not null,
    bo_mobile_content_head text not null,
    bo_content_tail text not null,
    bo_mobile_content_tail text not null,
    bo_insert_content text not null,
    bo_gallery_cols int default 0 not null,
    bo_gallery_width int default 0 not null,
    bo_gallery_height int default 0 not null,
    bo_mobile_gallery_width int default 0 not null,
    bo_mobile_gallery_height int default 0 not null,
    bo_upload_size int default 0 not null,
    bo_reply_order tinyint default 0 not null,
    bo_use_search tinyint default 0 not null,
    bo_order int default 0 not null,
    bo_count_write int default 0 not null,
    bo_count_comment int default 0 not null,
    bo_write_min int default 0 not null,
    bo_write_max int default 0 not null,
    bo_comment_min int default 0 not null,
    bo_comment_max int default 0 not null,
    bo_notice text not null,
    bo_upload_count tinyint default 0 not null,
    bo_use_email tinyint default 0 not null,
    bo_use_cert enum('', 'cert', 'adult', 'hp-cert', 'hp-adult') default '' not null,
    bo_use_sns tinyint default 0 not null,
    bo_use_captcha tinyint default 0 not null,
    bo_sort_field varchar(180) default '' not null,
    bo_1_subj varchar(180) default '' not null,
    bo_2_subj varchar(180) default '' not null,
    bo_3_subj varchar(180) default '' not null,
    bo_4_subj varchar(180) default '' not null,
    bo_5_subj varchar(180) default '' not null,
    bo_6_subj varchar(180) default '' not null,
    bo_7_subj varchar(180) default '' not null,
    bo_8_subj varchar(180) default '' not null,
    bo_9_subj varchar(180) default '' not null,
    bo_10_subj varchar(180) default '' not null,
    bo_11_subj varchar(180) default '' not null,
    bo_12_subj varchar(180) default '' not null,
    bo_13_subj varchar(180) default '' not null,
    bo_14_subj varchar(180) default '' not null,
    bo_15_subj varchar(180) default '' not null,
    bo_16_subj varchar(180) default '' not null,
    bo_17_subj varchar(180) default '' not null,
    bo_18_subj varchar(180) default '' not null,
    bo_19_subj varchar(180) default '' not null,
    bo_20_subj varchar(180) default '' not null,
    bo_1 varchar(180) default '' not null,
    bo_2 varchar(180) default '' not null,
    bo_3 varchar(180) default '' not null,
    bo_4 varchar(180) default '' not null,
    bo_5 varchar(180) default '' not null,
    bo_6 varchar(180) default '' not null,
    bo_7 varchar(180) default '' not null,
    bo_8 varchar(180) default '' not null,
    bo_9 varchar(180) default '' not null,
    bo_10 varchar(180) default '' not null,
    bo_11 varchar(180) default '' not null,
    bo_12 varchar(180) default '' not null,
    bo_13 varchar(180) default '' not null,
    bo_14 varchar(180) default '' not null,
    bo_15 varchar(180) default '' not null,
    bo_16 varchar(180) default '' not null,
    bo_17 varchar(180) default '' not null,
    bo_18 varchar(180) default '' not null,
    bo_19 varchar(180) default '' not null,
    bo_20 varchar(180) default '' not null
)
    charset=utf8;

create table g5_board_file
(
    bo_table varchar(50) default '' not null,
    wr_id int default 0 not null,
    bf_no int default 0 not null,
    bf_source varchar(180) default '' not null,
    bf_file varchar(180) default '' not null,
    bf_download int not null,
    bf_content text not null,
    bf_filesize int default 0 not null,
    bf_width int default 0 not null,
    bf_height smallint default 0 not null,
    bf_type tinyint default 0 not null,
    bf_datetime datetime default '0000-00-00 00:00:00' not null,
    primary key (bo_table, wr_id, bf_no)
)
    charset=utf8;

create table g5_board_good
(
    bg_id int auto_increment
        primary key,
    bo_table varchar(50) default '' not null,
    wr_id int default 0 not null,
    mb_id varchar(20) default '' not null,
    bg_flag varchar(180) default '' not null,
    bg_datetime datetime default '0000-00-00 00:00:00' not null,
    constraint fkey1
        unique (bo_table, wr_id, mb_id)
)
    charset=utf8;

create table g5_board_new
(
    bn_id int auto_increment
        primary key,
    bo_table varchar(50) default '' not null,
    wr_id int default 0 not null,
    wr_parent int default 0 not null,
    bn_datetime datetime default '0000-00-00 00:00:00' not null,
    mb_id varchar(20) default '' not null
)
    charset=utf8;

create index mb_id
    on g5_board_new (mb_id);

create table g5_cert_history
(
    cr_id int auto_increment
        primary key,
    mb_id varchar(20) default '' not null,
    cr_company varchar(180) default '' not null,
    cr_method varchar(180) default '' not null,
    cr_ip varchar(180) default '' not null,
    cr_date date default '0000-00-00' not null,
    cr_time time default '00:00:00' not null
)
    charset=utf8;

create index mb_id
    on g5_cert_history (mb_id);

create table g5_code
(
    co_id int unsigned auto_increment
        primary key,
    co_si varchar(20) default '' not null,
    co_gu varchar(20) default '' not null,
    constraint co_si
        unique (co_si, co_gu)
)
    charset=utf8;

create table g5_config
(
    cf_title varchar(180) default '' not null,
    cf_theme varchar(180) default '' not null,
    cf_admin varchar(180) default '' not null,
    cf_admin_email varchar(180) default '' not null,
    cf_admin_email_name varchar(180) default '' not null,
    cf_add_script text not null,
    cf_use_point tinyint default 0 not null,
    cf_point_term int default 0 not null,
    cf_use_copy_log tinyint default 0 not null,
    cf_use_email_certify tinyint default 0 not null,
    cf_login_point int default 0 not null,
    cf_cut_name tinyint default 0 not null,
    cf_nick_modify int default 0 not null,
    cf_new_skin varchar(180) default '' not null,
    cf_new_rows int default 0 not null,
    cf_search_skin varchar(180) default '' not null,
    cf_connect_skin varchar(180) default '' not null,
    cf_faq_skin varchar(180) default '' not null,
    cf_read_point int default 0 not null,
    cf_write_point int default 0 not null,
    cf_comment_point int default 0 not null,
    cf_download_point int default 0 not null,
    cf_write_pages int default 0 not null,
    cf_mobile_pages int default 0 not null,
    cf_link_target varchar(180) default '' not null,
    cf_delay_sec int default 0 not null,
    cf_filter text not null,
    cf_possible_ip text not null,
    cf_intercept_ip text not null,
    cf_analytics text not null,
    cf_add_meta text not null,
    cf_syndi_token varchar(180) not null,
    cf_syndi_except text not null,
    cf_member_skin varchar(180) default '' not null,
    cf_use_homepage tinyint default 0 not null,
    cf_req_homepage tinyint default 0 not null,
    cf_use_tel tinyint default 0 not null,
    cf_req_tel tinyint default 0 not null,
    cf_use_hp tinyint default 0 not null,
    cf_req_hp tinyint default 0 not null,
    cf_use_addr tinyint default 0 not null,
    cf_req_addr tinyint default 0 not null,
    cf_use_signature tinyint default 0 not null,
    cf_req_signature tinyint default 0 not null,
    cf_use_profile tinyint default 0 not null,
    cf_req_profile tinyint default 0 not null,
    cf_register_level tinyint default 0 not null,
    cf_register_point int default 0 not null,
    cf_icon_level tinyint default 0 not null,
    cf_use_recommend tinyint default 0 not null,
    cf_recommend_point int default 0 not null,
    cf_leave_day int default 0 not null,
    cf_search_part int default 0 not null,
    cf_email_use tinyint default 0 not null,
    cf_email_wr_super_admin tinyint default 0 not null,
    cf_email_wr_group_admin tinyint default 0 not null,
    cf_email_wr_board_admin tinyint default 0 not null,
    cf_email_wr_write tinyint default 0 not null,
    cf_email_wr_comment_all tinyint default 0 not null,
    cf_email_mb_super_admin tinyint default 0 not null,
    cf_email_mb_member tinyint default 0 not null,
    cf_email_po_super_admin tinyint default 0 not null,
    cf_prohibit_id text not null,
    cf_prohibit_email text not null,
    cf_new_del int default 0 not null,
    cf_memo_del int default 0 not null,
    cf_visit_del int default 0 not null,
    cf_popular_del int default 0 not null,
    cf_optimize_date date default '0000-00-00' not null,
    cf_use_member_icon tinyint default 0 not null,
    cf_member_icon_size int default 0 not null,
    cf_member_icon_width int default 0 not null,
    cf_member_icon_height int default 0 not null,
    cf_member_img_size int default 0 not null,
    cf_member_img_width int default 0 not null,
    cf_member_img_height int default 0 not null,
    cf_login_minutes int default 0 not null,
    cf_image_extension varchar(180) default '' not null,
    cf_flash_extension varchar(180) default '' not null,
    cf_movie_extension varchar(180) default '' not null,
    cf_formmail_is_member tinyint default 0 not null,
    cf_page_rows int default 0 not null,
    cf_mobile_page_rows int default 0 not null,
    cf_visit varchar(180) default '' not null,
    cf_max_po_id int default 0 not null,
    cf_stipulation text not null,
    cf_privacy text not null,
    cf_open_modify int default 0 not null,
    cf_memo_send_point int default 0 not null,
    cf_mobile_new_skin varchar(180) default '' not null,
    cf_mobile_search_skin varchar(180) default '' not null,
    cf_mobile_connect_skin varchar(180) default '' not null,
    cf_mobile_faq_skin varchar(180) default '' not null,
    cf_mobile_member_skin varchar(180) default '' not null,
    cf_captcha_mp3 varchar(180) default '' not null,
    cf_editor varchar(180) default '' not null,
    cf_cert_use tinyint default 0 not null,
    cf_cert_ipin varchar(180) default '' not null,
    cf_cert_hp varchar(180) default '' not null,
    cf_cert_kcb_cd varchar(180) default '' not null,
    cf_cert_kcp_cd varchar(180) default '' not null,
    cf_lg_mid varchar(180) default '' not null,
    cf_lg_mert_key varchar(180) default '' not null,
    cf_cert_limit int default 0 not null,
    cf_cert_req tinyint default 0 not null,
    cf_sms_use varchar(180) default '' not null,
    cf_sms_type varchar(10) default '' not null,
    cf_icode_id varchar(180) default '' not null,
    cf_icode_pw varchar(180) default '' not null,
    cf_icode_server_ip varchar(180) default '' not null,
    cf_icode_server_port varchar(180) default '' not null,
    cf_googl_shorturl_apikey varchar(180) default '' not null,
    cf_social_login_use tinyint default 0 not null,
    cf_social_servicelist varchar(180) default '' not null,
    cf_payco_clientid varchar(100) default '' not null,
    cf_payco_secret varchar(100) default '' not null,
    cf_facebook_appid varchar(180) not null,
    cf_facebook_secret varchar(180) not null,
    cf_twitter_key varchar(180) not null,
    cf_twitter_secret varchar(180) not null,
    cf_google_clientid varchar(100) default '' not null,
    cf_google_secret varchar(100) default '' not null,
    cf_naver_clientid varchar(100) default '' not null,
    cf_naver_secret varchar(100) default '' not null,
    cf_kakao_rest_key varchar(100) default '' not null,
    cf_kakao_client_secret varchar(100) default '' not null,
    cf_kakao_js_apikey varchar(180) not null,
    cf_captcha varchar(100) default '' not null,
    cf_recaptcha_site_key varchar(100) default '' not null,
    cf_recaptcha_secret_key varchar(100) default '' not null,
    cf_1_subj varchar(180) default '' not null,
    cf_2_subj varchar(180) default '' not null,
    cf_3_subj varchar(180) default '' not null,
    cf_4_subj varchar(180) default '' not null,
    cf_5_subj varchar(180) default '' not null,
    cf_6_subj varchar(180) default '' not null,
    cf_7_subj varchar(180) default '' not null,
    cf_8_subj varchar(180) default '' not null,
    cf_9_subj varchar(180) default '' not null,
    cf_10_subj varchar(180) default '' not null,
    cf_1 varchar(180) default '' not null,
    cf_2 varchar(180) default '' not null,
    cf_3 varchar(180) default '' not null,
    cf_4 varchar(180) default '' not null,
    cf_5 varchar(180) default '' not null,
    cf_6 varchar(180) default '' not null,
    cf_7 varchar(180) default '' not null,
    cf_8 varchar(180) default '' not null,
    cf_9 varchar(180) default '' not null,
    cf_10 varchar(180) default '' not null
)
    charset=utf8;

create table g5_content
(
    co_id varchar(20) default '' not null
        primary key,
    co_html tinyint default 0 not null,
    co_subject varchar(180) default '' not null,
    co_content longtext not null,
    co_mobile_content longtext not null,
    co_skin varchar(180) default '' not null,
    co_mobile_skin varchar(180) default '' not null,
    co_tag_filter_use tinyint default 0 not null,
    co_hit int default 0 not null,
    co_include_head varchar(180) not null,
    co_include_tail varchar(180) not null
)
    charset=utf8;

create table g5_faq
(
    fa_id int auto_increment
        primary key,
    fm_id int default 0 not null,
    fa_subject text not null,
    fa_content text not null,
    fa_order int default 0 not null
)
    charset=utf8;

create index fm_id
    on g5_faq (fm_id);

create table g5_faq_master
(
    fm_id int auto_increment
        primary key,
    fm_subject varchar(180) default '' not null,
    fm_head_html text not null,
    fm_tail_html text not null,
    fm_mobile_head_html text not null,
    fm_mobile_tail_html text not null,
    fm_order int default 0 not null
)
    charset=utf8;

create table g5_group
(
    gr_id varchar(10) default '' not null
        primary key,
    gr_subject varchar(180) default '' not null,
    gr_device enum('both', 'pc', 'mobile') default 'both' not null,
    gr_admin varchar(180) default '' not null,
    gr_use_access tinyint default 0 not null,
    gr_order int default 0 not null,
    gr_1_subj varchar(180) default '' not null,
    gr_2_subj varchar(180) default '' not null,
    gr_3_subj varchar(180) default '' not null,
    gr_4_subj varchar(180) default '' not null,
    gr_5_subj varchar(180) default '' not null,
    gr_6_subj varchar(180) default '' not null,
    gr_7_subj varchar(180) default '' not null,
    gr_8_subj varchar(180) default '' not null,
    gr_9_subj varchar(180) default '' not null,
    gr_10_subj varchar(180) default '' not null,
    gr_1 varchar(180) default '' not null,
    gr_2 varchar(180) default '' not null,
    gr_3 varchar(180) default '' not null,
    gr_4 varchar(180) default '' not null,
    gr_5 varchar(180) default '' not null,
    gr_6 varchar(180) default '' not null,
    gr_7 varchar(180) default '' not null,
    gr_8 varchar(180) default '' not null,
    gr_9 varchar(180) default '' not null,
    gr_10 varchar(180) default '' not null
)
    charset=utf8;

create table g5_group_member
(
    gm_id int auto_increment
        primary key,
    gr_id varchar(180) default '' not null,
    mb_id varchar(20) default '' not null,
    gm_datetime datetime default '0000-00-00 00:00:00' not null
)
    charset=utf8;

create index gr_id
    on g5_group_member (gr_id);

create index mb_id
    on g5_group_member (mb_id);

create table g5_login
(
    lo_ip varchar(180) default '' not null
        primary key,
    mb_id varchar(20) default '' not null,
    lo_datetime datetime default '0000-00-00 00:00:00' not null,
    lo_location text not null,
    lo_url text not null
)
    charset=utf8;

create table g5_mail
(
    ma_id int auto_increment
        primary key,
    ma_subject varchar(180) default '' not null,
    ma_content mediumtext not null,
    ma_time datetime default '0000-00-00 00:00:00' not null,
    ma_ip varchar(180) default '' not null,
    ma_last_option text not null
)
    charset=utf8;

create table g5_member_social_profiles
(
    mp_no int auto_increment,
    mb_id varchar(180) default '' not null,
    provider varchar(50) default '' not null,
    object_sha varchar(45) default '' not null,
    identifier varchar(180) default '' not null,
    profileurl varchar(180) default '' not null,
    photourl varchar(180) default '' not null,
    displayname varchar(150) default '' not null,
    description varchar(180) default '' not null,
    mp_register_day datetime default '0000-00-00 00:00:00' not null,
    mp_latest_day datetime default '0000-00-00 00:00:00' not null,
    constraint mp_no
        unique (mp_no)
)
    charset=utf8;

create index mb_id
    on g5_member_social_profiles (mb_id);

create index provider
    on g5_member_social_profiles (provider);

create table g5_memo
(
    me_id int default 0 not null
        primary key,
    me_recv_mb_id varchar(20) default '' not null,
    me_send_mb_id varchar(20) default '' not null,
    me_send_datetime datetime default '0000-00-00 00:00:00' not null,
    me_read_datetime datetime default '0000-00-00 00:00:00' not null,
    me_memo text not null
)
    charset=utf8;

create index me_recv_mb_id
    on g5_memo (me_recv_mb_id);

create table g5_menu
(
    me_id int auto_increment
        primary key,
    me_code varchar(180) default '' not null,
    me_name varchar(180) default '' not null,
    me_link varchar(180) default '' not null,
    me_target varchar(180) default '' not null,
    me_order int default 0 not null,
    me_use tinyint default 0 not null,
    me_mobile_use tinyint default 0 not null
)
    charset=utf8;

create table g5_new_win
(
    nw_id int auto_increment
        primary key,
    nw_division varchar(10) default 'both' not null,
    nw_device varchar(10) default 'both' not null,
    nw_begin_time datetime default '0000-00-00 00:00:00' not null,
    nw_end_time datetime default '0000-00-00 00:00:00' not null,
    nw_disable_hours int default 0 not null,
    nw_left int default 0 not null,
    nw_top int default 0 not null,
    nw_height int default 0 not null,
    nw_width int default 0 not null,
    nw_subject text not null,
    nw_content text not null,
    nw_content_html tinyint default 0 not null
)
    charset=utf8;

create table g5_point
(
    po_id int auto_increment
        primary key,
    mb_id varchar(20) default '' not null,
    po_datetime datetime default '0000-00-00 00:00:00' not null,
    po_content varchar(180) default '' not null,
    po_point int default 0 not null,
    po_use_point int default 0 not null,
    po_expired tinyint default 0 not null,
    po_expire_date date default '0000-00-00' not null,
    po_mb_point int default 0 not null,
    po_rel_table varchar(20) default '' not null,
    po_rel_id varchar(20) default '' not null,
    po_rel_action varchar(180) default '' not null
)
    charset=utf8;

create index index1
    on g5_point (mb_id, po_rel_table, po_rel_id, po_rel_action);

create index index2
    on g5_point (po_expire_date);

create table g5_poll
(
    po_id int auto_increment
        primary key,
    po_subject varchar(180) default '' not null,
    po_poll1 varchar(180) default '' not null,
    po_poll2 varchar(180) default '' not null,
    po_poll3 varchar(180) default '' not null,
    po_poll4 varchar(180) default '' not null,
    po_poll5 varchar(180) default '' not null,
    po_poll6 varchar(180) default '' not null,
    po_poll7 varchar(180) default '' not null,
    po_poll8 varchar(180) default '' not null,
    po_poll9 varchar(180) default '' not null,
    po_cnt1 int default 0 not null,
    po_cnt2 int default 0 not null,
    po_cnt3 int default 0 not null,
    po_cnt4 int default 0 not null,
    po_cnt5 int default 0 not null,
    po_cnt6 int default 0 not null,
    po_cnt7 int default 0 not null,
    po_cnt8 int default 0 not null,
    po_cnt9 int default 0 not null,
    po_etc varchar(180) default '' not null,
    po_level tinyint default 0 not null,
    po_point int default 0 not null,
    po_date date default '0000-00-00' not null,
    po_ips mediumtext not null,
    mb_ids text not null
)
    charset=utf8;

create table g5_poll_etc
(
    pc_id int default 0 not null
        primary key,
    po_id int default 0 not null,
    mb_id varchar(20) default '' not null,
    pc_name varchar(180) default '' not null,
    pc_idea varchar(180) default '' not null,
    pc_datetime datetime default '0000-00-00 00:00:00' not null
)
    charset=utf8;

create table g5_popular
(
    pp_id int auto_increment
        primary key,
    pp_word varchar(50) default '' not null,
    pp_date date default '0000-00-00' not null,
    pp_ip varchar(50) default '' not null,
    constraint index1
        unique (pp_date, pp_word, pp_ip)
)
    charset=utf8;

create table g5_qa_config
(
    qa_title varchar(180) default '' not null,
    qa_category varchar(180) default '' not null,
    qa_skin varchar(180) default '' not null,
    qa_mobile_skin varchar(180) default '' not null,
    qa_use_email tinyint default 0 not null,
    qa_req_email tinyint default 0 not null,
    qa_use_hp tinyint default 0 not null,
    qa_req_hp tinyint default 0 not null,
    qa_use_sms tinyint default 0 not null,
    qa_send_number varchar(180) default '0' not null,
    qa_admin_hp varchar(180) default '' not null,
    qa_admin_email varchar(180) default '' not null,
    qa_use_editor tinyint default 0 not null,
    qa_subject_len int default 0 not null,
    qa_mobile_subject_len int default 0 not null,
    qa_page_rows int default 0 not null,
    qa_mobile_page_rows int default 0 not null,
    qa_image_width int default 0 not null,
    qa_upload_size int default 0 not null,
    qa_insert_content text not null,
    qa_include_head varchar(180) default '' not null,
    qa_include_tail varchar(180) default '' not null,
    qa_content_head text not null,
    qa_content_tail text not null,
    qa_mobile_content_head text not null,
    qa_mobile_content_tail text not null,
    qa_1_subj varchar(180) default '' not null,
    qa_2_subj varchar(180) default '' not null,
    qa_3_subj varchar(180) default '' not null,
    qa_4_subj varchar(180) default '' not null,
    qa_5_subj varchar(180) default '' not null,
    qa_1 varchar(180) default '' not null,
    qa_2 varchar(180) default '' not null,
    qa_3 varchar(180) default '' not null,
    qa_4 varchar(180) default '' not null,
    qa_5 varchar(180) default '' not null
)
    charset=utf8;

create table g5_qa_content
(
    qa_id int auto_increment
        primary key,
    qa_num int default 0 not null,
    qa_parent int default 0 not null,
    qa_related int default 0 not null,
    mb_id varchar(20) default '' not null,
    qa_name varchar(180) default '' not null,
    qa_email varchar(180) default '' not null,
    qa_hp varchar(180) default '' not null,
    qa_type tinyint default 0 not null,
    qa_category varchar(180) default '' not null,
    qa_email_recv tinyint default 0 not null,
    qa_sms_recv tinyint default 0 not null,
    qa_html tinyint default 0 not null,
    qa_subject varchar(180) default '' not null,
    qa_content text not null,
    qa_status tinyint default 0 not null,
    qa_file1 varchar(180) default '' not null,
    qa_source1 varchar(180) default '' not null,
    qa_file2 varchar(180) default '' not null,
    qa_source2 varchar(180) default '' not null,
    qa_ip varchar(180) default '' not null,
    qa_datetime datetime default '0000-00-00 00:00:00' not null,
    qa_1 varchar(180) default '' not null,
    qa_2 varchar(180) default '' not null,
    qa_3 varchar(180) default '' not null,
    qa_4 varchar(180) default '' not null,
    qa_5 varchar(180) default '' not null
)
    charset=utf8;

create index qa_num_parent
    on g5_qa_content (qa_num, qa_parent);

create table g5_scrap
(
    ms_id int auto_increment
        primary key,
    mb_id varchar(20) default '' not null,
    bo_table varchar(20) default '' not null,
    wr_id varchar(15) default '' not null,
    ms_datetime datetime default '0000-00-00 00:00:00' not null
)
    charset=utf8;

create index mb_id
    on g5_scrap (mb_id);

create table g5_uniqid
(
    uq_id bigint unsigned not null
        primary key,
    uq_ip varchar(180) not null
)
    charset=utf8;

create table g5_visit
(
    vi_id int default 0 not null
        primary key,
    vi_ip varchar(180) default '' not null,
    vi_date date default '0000-00-00' not null,
    vi_time time default '00:00:00' not null,
    vi_referer text not null,
    vi_agent varchar(180) default '' not null,
    vi_browser varchar(180) default '' not null,
    vi_os varchar(180) default '' not null,
    vi_device varchar(180) default '' not null,
    constraint index1
        unique (vi_ip, vi_date)
)
    charset=utf8;

create index index2
    on g5_visit (vi_date);

create table g5_visit_sum
(
    vs_date date default '0000-00-00' not null
        primary key,
    vs_count int default 0 not null
)
    charset=utf8;

create index index1
    on g5_visit_sum (vs_count);

create table g5_write_
(
    wr_id int auto_increment
        primary key,
    wr_num int default 0 not null,
    wr_reply varchar(10) not null,
    wr_parent int default 0 not null,
    wr_is_comment tinyint default 0 not null,
    wr_comment int default 0 not null,
    wr_comment_reply varchar(5) not null,
    ca_name varchar(180) not null,
    wr_option set('html1', 'html2', 'secret', 'mail') not null,
    wr_subject varchar(180) not null,
    wr_content text not null,
    wr_link1 text not null,
    wr_link2 text not null,
    wr_link1_hit int default 0 not null,
    wr_link2_hit int default 0 not null,
    wr_hit int default 0 not null,
    wr_good int default 0 not null,
    wr_nogood int default 0 not null,
    mb_id varchar(20) not null,
    wr_password varchar(180) not null,
    wr_name varchar(180) not null,
    wr_email varchar(180) not null,
    wr_homepage varchar(180) not null,
    wr_datetime datetime default '0000-00-00 00:00:00' not null,
    wr_file tinyint default 0 not null,
    wr_last varchar(19) not null,
    wr_ip varchar(180) not null,
    wr_facebook_user varchar(180) not null,
    wr_twitter_user varchar(180) not null,
    wr_1 varchar(180) not null,
    wr_2 varchar(180) not null,
    wr_3 varchar(180) not null,
    wr_4 varchar(180) not null,
    wr_5 varchar(180) not null,
    wr_6 varchar(180) not null,
    wr_7 varchar(180) not null,
    wr_8 varchar(180) not null,
    wr_9 varchar(180) not null,
    wr_10 varchar(180) not null,
    wr_11 varchar(180) not null,
    wr_12 varchar(180) not null,
    wr_13 varchar(180) not null,
    wr_14 varchar(180) not null,
    wr_15 varchar(180) not null,
    wr_16 varchar(180) not null,
    wr_17 varchar(180) not null,
    wr_18 varchar(180) not null,
    wr_19 varchar(180) not null,
    wr_20 varchar(180) not null
)
    charset=utf8;

create index wr_is_comment
    on g5_write_ (wr_is_comment, wr_id);

create index wr_num_reply_parent
    on g5_write_ (wr_num, wr_reply, wr_parent);

create table g5_write_notice
(
    wr_id int auto_increment
        primary key,
    wr_num int default 0 not null,
    wr_reply varchar(10) not null,
    wr_parent int default 0 not null,
    wr_is_comment tinyint default 0 not null,
    wr_comment int default 0 not null,
    wr_comment_reply varchar(5) not null,
    ca_name varchar(180) not null,
    wr_option set('html1', 'html2', 'secret', 'mail') not null,
    wr_subject varchar(180) not null,
    wr_content text not null,
    wr_link1 text not null,
    wr_link2 text not null,
    wr_link1_hit int default 0 not null,
    wr_link2_hit int default 0 not null,
    wr_hit int default 0 not null,
    wr_good int default 0 not null,
    wr_nogood int default 0 not null,
    mb_id varchar(20) not null,
    wr_password varchar(180) not null,
    wr_name varchar(180) not null,
    wr_email varchar(180) not null,
    wr_homepage varchar(180) not null,
    wr_datetime datetime default '0000-00-00 00:00:00' not null,
    wr_file tinyint default 0 not null,
    wr_last varchar(19) not null,
    wr_ip varchar(180) not null,
    wr_facebook_user varchar(180) not null,
    wr_twitter_user varchar(180) not null,
    wr_1 varchar(180) not null,
    wr_2 varchar(180) not null comment '현장 ID',
    wr_3 varchar(180) not null comment '현장 이름',
    wr_4 varchar(180) not null,
    wr_5 varchar(180) not null,
    wr_6 varchar(180) not null,
    wr_7 varchar(180) not null,
    wr_8 varchar(180) not null,
    wr_9 varchar(180) not null,
    wr_10 varchar(180) not null,
    wr_11 varchar(180) not null,
    wr_12 varchar(180) not null,
    wr_13 varchar(180) not null,
    wr_14 varchar(180) not null,
    wr_15 varchar(180) not null,
    wr_16 varchar(180) not null,
    wr_17 varchar(180) not null,
    wr_18 varchar(180) not null,
    wr_19 varchar(180) not null,
    wr_20 varchar(180) not null
)
    charset=utf8;

create index wr_2
    on g5_write_notice (wr_2);

create index wr_is_comment
    on g5_write_notice (wr_is_comment, wr_id);

create index wr_num_reply_parent
    on g5_write_notice (wr_num, wr_reply, wr_parent);

create table g5_write_wpboard
(
    wr_id int auto_increment
        primary key,
    wr_num int default 0 not null,
    wr_reply varchar(10) not null,
    wr_parent int default 0 not null,
    wr_is_comment tinyint default 0 not null,
    wr_comment int default 0 not null,
    wr_comment_reply varchar(5) not null,
    ca_name varchar(180) not null,
    wr_option set('html1', 'html2', 'secret', 'mail') not null,
    wr_subject varchar(180) not null,
    wr_content text not null,
    wr_link1 text not null,
    wr_link2 text not null,
    wr_link1_hit int default 0 not null,
    wr_link2_hit int default 0 not null,
    wr_hit int default 0 not null,
    wr_good int default 0 not null,
    wr_nogood int default 0 not null,
    mb_id varchar(20) not null,
    wr_password varchar(180) not null,
    wr_name varchar(180) not null,
    wr_email varchar(180) not null,
    wr_homepage varchar(180) not null,
    wr_datetime datetime default '0000-00-00 00:00:00' not null,
    wr_file tinyint default 0 not null,
    wr_last varchar(19) not null,
    wr_ip varchar(180) not null,
    wr_facebook_user varchar(180) not null,
    wr_twitter_user varchar(180) not null,
    wr_1 varchar(180) not null,
    wr_2 varchar(180) not null,
    wr_3 varchar(180) not null,
    wr_4 varchar(180) not null,
    wr_5 varchar(180) not null,
    wr_6 varchar(180) not null,
    wr_7 varchar(180) not null,
    wr_8 varchar(180) not null,
    wr_9 varchar(180) not null,
    wr_10 varchar(180) not null,
    wr_11 text not null,
    wr_12 datetime not null,
    wr_13 varchar(180) not null,
    wr_14 varchar(180) not null,
    wr_15 varchar(180) not null,
    wr_16 varchar(180) not null,
    wr_17 varchar(180) not null,
    wr_18 varchar(180) not null,
    wr_19 varchar(180) not null,
    wr_20 varchar(180) not null
)
    charset=utf8;

create index wr_is_comment
    on g5_write_wpboard (wr_is_comment, wr_id);

create index wr_num_reply_parent
    on g5_write_wpboard (wr_num, wr_reply, wr_parent);

create table gas_category
(
    category tinyint not null comment '유해물질 카테고리번호'
        primary key,
    category_name varchar(16) not null comment '유해물질 카테고리명',
    min_measure int not null comment '측정 최소값',
    max_measure int not null comment '측정 최대값',
    unit varchar(8) not null comment '단위',
    ordering int default 0 not null comment '표출순서'
)
    comment '유해물질 카테고리 정보' charset=utf8;

create table gas_log
(
    mac_address varchar(50) not null,
    device_id varchar(50) null comment '디바이스 식별자',
    measure_time timestamp not null,
    temperature double not null,
    humidity double default 0 not null comment '습도 (0~100%)',
    o2 double not null,
    h2s double not null,
    co double not null,
    ch4 double not null,
    battery double default 100 not null comment '배터리 잔여량 (0~100%)',
    primary key (mac_address, measure_time)
);

create table gas_log_recent
(
    mac_address varchar(50) not null
        primary key,
    measure_time timestamp not null,
    temperature double not null,
    humidity double default 0 not null comment '습도 (0~100%)',
    o2 double not null,
    h2s double not null,
    co double not null,
    ch4 double not null,
    battery double default 100 not null comment '배터리 잔여량 (0~100%)',
    temperature_level tinyint default 0 not null comment '표시 레벨. 0.좋음 1.위험',
    o2_level tinyint default 0 not null comment '표시 레벨. 0.좋음 1.위험',
    h2s_level tinyint default 0 not null comment '표시 레벨. 0.좋음 1.위험',
    co_level tinyint default 0 not null comment '표시 레벨. 0.좋음 1.위험',
    ch4_level tinyint default 0 not null comment '표시 레벨. 0.좋음 1.위험',
    humidity_level tinyint default 0 not null comment '표시 레벨. 0.좋음 1.위험',
    hazard_phrase varchar(20) null comment '위험 항목 표시명',
    dashboard_popup tinyint default 1 not null comment '안전모니터 팝업 표시 여부. 0.표시안함 1.해지시까지 표시(기본값)'
);

create table gas_safe_range_policy
(
    policy_no int auto_increment comment '유해물질 측정 임계치 정책 넘버'
        primary key,
    policy_name varchar(64) not null comment '정책명',
    create_date datetime default CURRENT_TIMESTAMP not null comment '생성일',
    creator varchar(20) not null comment '생성자 아이디(mb_id) ',
    update_date datetime default CURRENT_TIMESTAMP not null comment '수정일',
    updater varchar(20) not null comment '수정자 아이디(mb_id) '
)
    comment '유해물질 측정 임계치 정책' charset=utf8;

create table gas_safe_range
(
    policy_no int default 1 not null comment '유해물질 측정 임계치 정책 넘버',
    category tinyint not null comment '1.온도 2.산소 3.황화수소 4.일산화탄소 5.메탄(탄산)',
    min double null,
    max double null,
    primary key (policy_no, category),
    constraint gas_safe_range_ibfk_1
        foreign key (policy_no) references gas_safe_range_policy (policy_no)
            on delete cascade
);

create table gps_check_policy_info
(
    idx int auto_increment
        primary key,
    wp_id varchar(50) null comment '현장 코드',
    gps_center_long double null comment '기준 longitude 좌표',
    gps_center_lat double null comment '기준 latitude 좌표',
    gps_dist_limit decimal(4) null comment '기준 좌표 에서 제한 거리 Km (해당 거리 이내의 측위 정보만 수집)',
    gps_round_long double null comment '거리 측정용 기준 좌표. longitude',
    gps_round_lat double null comment '거리 측정용 기준 좌표. latitude',
    gps_dist_limit_meter int null comment '기준 좌표 에서 제한 거리 m (해당 거리 이내의 측위 정보만 수집).향후 업데이트',
    report_interval bigint null comment '보고 주기(ms)
- 해당 주기 이상 미 동작시 서버에 report를 실시
',
    gps_interval bigint null comment 'GPS 스캔 주기(ms)
- 해당 주기 이상 미 동작 시 단말에서 로컬 알림 발생.
',
    allow_start varchar(6) null comment ' GPS  측위 시작 시간
HHmmss
',
    allow_end varchar(6) null comment '측위 종료 시간
HHmmss
',
    upd_datetime datetime null comment '변경일자',
    updater varchar(50) null comment '변경자 ID',
    constraint wp_id
        unique (wp_id)
)
    comment '현장 별 GPS 스캔 정책' charset=utf8;

create table gps_device_info
(
    idx int auto_increment
        primary key,
    wp_id varchar(50) not null comment '현장 코드',
    device_id varchar(50) not null comment '장비 모델 (유니크)',
    mac_address varchar(50) not null comment '장비 mac 주소',
    upd_datetime datetime not null comment '수정일',
    mb_id varchar(50) null comment '수정자',
    constraint wp_id_device_model
        unique (device_id)
)
    charset=utf8;

create table gps_location
(
    wp_id varchar(50) not null comment '현장 코드',
    coop_mb_id varchar(50) null comment '협력업체 아이디',
    mb_id varchar(50) null comment '근로자 아이디',
    device_id varchar(50) null comment 'GPS 디바이스 아이디',
    provider varchar(6) null comment 'GPS provider',
    longitude double null comment '경도',
    latitude double null comment '위도',
    altitude double null comment '고도',
    accuracy double null comment '정확도',
    speed double null comment '속도',
    bearing double null comment '방위각',
    battery double null comment '단말 배터리',
    sensor_scan int null comment '최종 스캔 비콘 minor 정보',
    measure_time datetime null comment '측위 시간',
    constraint mb_id_device_id
        unique (mb_id, device_id)
)
    charset=utf8;

create index wp_id_measure_time
    on gps_location (wp_id, measure_time);

create definer = kunsulup@`%` trigger gps_location_after_insert
    after insert
    on gps_location
    for each row
BEGIN

    INSERT INTO gps_location_hist
    (
        wp_id,
        coop_mb_id,
        mb_id,
        device_id,
        provider,
        longitude,
        latitude,
        altitude,
        accuracy,
        speed,
        bearing,
        battery,
        measure_time,
        sensor_scan
    )
    values
    (
        NEW.wp_id,
        NEW.coop_mb_id,
        NEW.mb_id,
        NEW.device_id,
        NEW.provider,
        NEW.longitude,
        NEW.latitude,
        NEW.altitude,
        NEW.accuracy,
        NEW.speed,
        NEW.bearing,
        NEW.battery,
        NEW.measure_time,
        NEW.sensor_scan
    );

END;

create definer = kunsulup@`%` trigger gps_location_after_update
    after update
    on gps_location
    for each row
BEGIN

    INSERT INTO gps_location_hist
    (
        wp_id,
        coop_mb_id,
        mb_id,
        device_id,
        provider,
        longitude,
        latitude,
        altitude,
        accuracy,
        speed,
        bearing,
        battery,
        measure_time,
        sensor_scan
    )
    values
    (
        NEW.wp_id,
        NEW.coop_mb_id,
        NEW.mb_id,
        NEW.device_id,
        NEW.provider,
        NEW.longitude,
        NEW.latitude,
        NEW.altitude,
        NEW.accuracy,
        NEW.speed,
        NEW.bearing,
        NEW.battery,
        NEW.measure_time,
        NEW.sensor_scan
    );
END;

create table gps_location_hist
(
    seq bigint auto_increment,
    wp_id varchar(50) null,
    coop_mb_id varchar(50) null,
    mb_id varchar(50) null,
    device_id varchar(50) null,
    provider varchar(6) null,
    longitude double null,
    latitude double null,
    altitude double null,
    accuracy double null,
    speed double null,
    bearing double null,
    battery double null,
    sensor_scan int null,
    measure_time datetime not null,
    primary key (seq, measure_time)
)
    charset=utf8;

create table hibernate_sequence
(
    next_val bigint null
)
    charset=latin1;

create table hulan_sequence
(
    seq_name varchar(8) not null comment '시퀀스명'
        primary key,
    next_val bigint default 0 not null comment '시퀀스 no',
    etc_1 varchar(16) null comment '기타'
)
    charset=utf8;

create table hulan_ui_component
(
    hcmpt_id varchar(16) not null comment '컴포넌트 아이디'
        primary key,
    hcmpt_name varchar(32) not null comment '컴포넌트명',
    description varchar(1024) null comment '설명',
    site_type int not null comment '사이트 유형. 1: IMOS, 2: HICC(통합관제)',
    ui_type int not null comment 'UI 유형. 1: 메인화면 유형, 2: 컴포넌트 유형',
    width int not null comment '컴포넌트의 x 길이 (1~4)',
    height int not null comment '컴포넌트의 y 길이 (1~3)',
    file_name varchar(255) null comment '대표 파일명',
    file_name_org varchar(255) null comment '대표 파일 원본명',
    file_path varchar(255) null comment '대표 파일 저장 위치(상대경로)',
    file_location int null comment '대표 파일 저장소. 0: local Storage ',
    status tinyint default 0 not null comment '상태. 0: 미사용, 1: 사용',
    create_date datetime default CURRENT_TIMESTAMP not null comment '생성일',
    creator varchar(20) not null comment '생성자 아이디(mb_id) ',
    update_date datetime default CURRENT_TIMESTAMP not null comment '수정일',
    updater varchar(20) not null comment '수정자 아이디(mb_id) '
)
    comment '통합 관제 모니터링 컴포넌트 정보' charset=utf8;

create table hicc_member_ui_component
(
    hmuc_no bigint auto_increment comment '빌딩 넘버(SEQ)'
        primary key,
    mb_id varchar(20) not null comment '컴포넌트 사용자',
    deploy_page int not null comment '배치된 페이지 ( 1, 2 )',
    pos_x int not null comment '컴포넌트의 시작 x 좌표',
    pos_y int not null comment '컴포넌트의 시작 y 좌표',
    hcmpt_id varchar(16) not null comment '컴포넌트 아이디',
    create_date datetime default CURRENT_TIMESTAMP not null comment '생성일',
    creator varchar(20) not null comment '생성자 아이디(mb_id) ',
    constraint uq_hicc_mb_ui_comp
        unique (mb_id, deploy_page, pos_x, pos_y),
    constraint fk_hicc_member_ui_comp
        foreign key (hcmpt_id) references hulan_ui_component (hcmpt_id)
            on delete cascade
)
    comment '통합 관제 사용자 배치 컴포넌트 정보' charset=utf8;

create table icon_code
(
    icon_type tinyint auto_increment comment '아이콘 유형'
        primary key,
    icon_name varchar(50) null comment '아이콘 명',
    icon_url varchar(128) null comment '아이콘 URL',
    expose_seq tinyint null comment '노출순서'
)
    charset=utf8;

create table manager_token
(
    mt_idx int auto_increment
        primary key,
    mb_id varchar(30) not null,
    mt_token varchar(255) not null,
    mt_yn tinyint not null,
    mt_datetime datetime not null
)
    charset=utf8;

create index mb_id
    on manager_token (mb_id);

create index mt_token
    on manager_token (mt_token);

create table member_device
(
    mb_no bigint not null comment '회원 번호'
        primary key,
    app_type tinyint null comment '앱 유형. 1.근로자 2.관리자',
    app_version varchar(12) null comment 'APP 버전',
    os_type varchar(16) null comment 'OS 유형. android/ios',
    os_version varchar(20) null comment 'OS 버전',
    device_model varchar(64) null comment '단말 모델',
    fcm_token varchar(255) null comment 'FCM 토큰',
    create_date datetime not null comment '정보 최초 생성 시간',
    update_date datetime not null comment '정보 최근 변경 시간'
)
    charset=utf8;

create table member_otp
(
    mb_id varchar(20) not null comment '사용자 아이디',
    otp_num int not null comment 'OTP 인증 번호',
    otp_usage int not null comment 'OTP 이용 유형. 1: 사용자 로그인',
    create_date datetime default CURRENT_TIMESTAMP not null comment '생성일',
    validate_date datetime null comment 'OTP 인증일',
    primary key (mb_id, create_date)
)
    comment '사용자 로그인 OTP 정보. 3개월 보관' charset=utf8mb4;

create table member_otp_phone
(
    mb_id varchar(20) not null comment '사용자 아이디',
    phone_no varchar(20) not null comment 'OTP 수신 단말 번호',
    uuid varchar(64) null comment 'UUID',
    create_date datetime default CURRENT_TIMESTAMP not null comment '생성일',
    creator varchar(20) not null comment '등록자 아이디',
    primary key (mb_id, phone_no)
)
    comment 'OTP 수신 단말번호' charset=utf8mb4;

create table oauth_access_token
(
    token_id varchar(256) null,
    token mediumblob null,
    authentication_id varchar(191) null,
    user_name varchar(256) null,
    client_id varchar(256) null,
    authentication mediumblob null,
    refresh_token varchar(256) null
)
    charset=latin1;

create table oauth_client_details
(
    client_id varchar(191) not null
        primary key,
    resource_ids varchar(256) null,
    client_secret varchar(256) null,
    scope varchar(256) null,
    authorized_grant_types varchar(256) null,
    web_server_redirect_uri varchar(256) null,
    authorities varchar(256) null,
    access_token_validity int null,
    refresh_token_validity int null,
    additional_information varchar(4096) null,
    autoapprove varchar(256) null
)
    charset=latin1;

create table oauth_refresh_token
(
    token_id varchar(256) null,
    token mediumblob null,
    authentication mediumblob null,
    reg_dt timestamp default CURRENT_TIMESTAMP not null
)
    charset=latin1;

create table ope_event_log
(
    oel_no bigint auto_increment comment 'OPE 이벤트 로그 번호'
        primary key,
    event_name varchar(64) not null comment '이벤트명',
    event_kind tinyint not null comment '이벤트 종류. 1: DB Event Scheduler',
    result tinyint not null comment '결과. 1: 성공, 0: 실패 ',
    result_desc varchar(256) not null comment '결과 상세',
    create_date datetime default CURRENT_TIMESTAMP not null comment '생성일'
)
    comment '시스템 운영을 위한 스케쥴러 등의 로그' charset=utf8mb4;

create table ordering_office
(
    office_no bigint auto_increment comment '발주처번호'
        primary key,
    office_name varchar(128) not null comment '발주처명',
    telephone varchar(32) not null comment '발주처 연락처',
    biznum varchar(10) not null comment '사업자번호',
    zonecode varchar(5) not null comment '새우편번호',
    bcode varchar(10) not null comment '법정동코드',
    address varchar(256) not null comment '기본주소',
    address_detail varchar(256) null comment '상세주소',
    sido varchar(32) null comment '시도명',
    sigungu varchar(32) null comment '시군구명',
    bname varchar(32) null comment '법정동/법정리 이름',
    icon_file_name varchar(255) null comment '아이콘 파일명',
    icon_file_name_org varchar(255) null comment '아이콘 원본 파일명',
    icon_file_path varchar(255) null comment '아이콘 저장 위치(상대경로)',
    icon_file_location int null comment '아이콘 저장소. 0: local Storage ',
    bg_img_file_name varchar(255) null comment '백그라운드 이미지 파일명',
    bg_img_file_name_org varchar(255) null comment '백그라운드 이미지 원본 파일명',
    bg_img_file_path varchar(255) null comment '백그라운드 이미지 저장 위치(상대경로)',
    bg_img_file_location int null comment '백그라운드 이미지 저장소. 0: local Storage ',
    bg_color varchar(16) null comment '백그라운드 색',
    create_date datetime default CURRENT_TIMESTAMP not null comment '생성일',
    creator varchar(20) not null comment '생성자',
    update_date datetime default CURRENT_TIMESTAMP not null comment '최종 수정일',
    updater varchar(20) not null comment '최종 수정자',
    constraint biznum
        unique (biznum)
)
    comment '발주처 정보' charset=utf8;

create table g5_member
(
    mb_no int auto_increment,
    mb_id varchar(20) default '' not null,
    mb_password varchar(180) default '' not null,
    mb_name varchar(180) default '' not null,
    mb_nick varchar(180) default '' not null,
    mb_nick_date date null,
    mb_email varchar(180) default '' not null,
    mb_homepage varchar(180) default '' not null,
    mb_level tinyint default 0 not null,
    mb_sex char default '' not null,
    mb_birth varchar(10) default '' not null,
    mb_tel varchar(20) default '' not null,
    mb_hp varchar(20) default '' not null,
    mb_certify varchar(20) default '' not null,
    mb_adult tinyint default 0 not null,
    mb_dupinfo varchar(180) default '' not null,
    mb_zip1 varchar(3) default '' not null,
    mb_zip2 varchar(3) default '' not null,
    mb_addr1 varchar(180) default '' not null,
    mb_addr2 varchar(180) default '' not null,
    mb_addr3 varchar(180) default '' not null,
    mb_addr_jibeon varchar(180) default '' not null,
    mb_signature text null,
    mb_recommend varchar(180) default '' not null,
    mb_point int default 0 not null,
    mb_today_login datetime null,
    mb_login_ip varchar(180) default '' not null,
    mb_datetime datetime null,
    mb_ip varchar(180) default '' not null,
    mb_leave_date varchar(8) default '' not null,
    mb_intercept_date varchar(8) default '' not null,
    mb_email_certify datetime null,
    mb_email_certify2 varchar(180) default '' not null,
    mb_memo text null,
    mb_lost_certify varchar(180) null,
    mb_mailling tinyint default 0 not null,
    mb_sms tinyint default 0 not null,
    mb_open tinyint default 0 not null,
    mb_open_date date null,
    mb_profile text null,
    mb_memo_call varchar(180) default '' not null,
    mb_1 varchar(180) default '' not null,
    mb_2 varchar(180) default '' not null,
    mb_3 varchar(180) default '' not null,
    mb_4 varchar(180) default '' not null,
    mb_5 varchar(180) default '' not null,
    mb_6 varchar(180) default '' not null,
    mb_7 varchar(180) default '' not null,
    mb_8 varchar(180) default '' not null,
    mb_9 varchar(180) default '' not null,
    mb_10 varchar(180) default '' not null,
    mb_11 varchar(180) default '' not null,
    mb_12 varchar(180) default '' not null,
    mb_13 varchar(180) default '' not null,
    mb_14 varchar(180) default '' not null,
    mb_15 varchar(180) default '' not null,
    mb_16 varchar(180) default '' not null,
    mb_17 varchar(180) default '' not null,
    mb_18 varchar(180) default '' not null,
    mb_19 varchar(180) default '' not null,
    mb_20 varchar(180) default '' not null,
    app_version varchar(6) null comment '사용중인 app_version null 일시 ios, 버전 기입시 aos로 구분',
    cc_id varchar(50) null comment '건설사 아이디 ( level 이 5: 건설사 관리자 인 경우 필수 )',
    work_section_a varchar(8) null comment '공종A 코드 (협력사 및 근로자용)',
    pwd_change_date datetime default CURRENT_TIMESTAMP not null comment '패스워드 변경일',
    attempt_login_count int default 0 not null comment '로그인 시도수(실패수)',
    office_no bigint null comment '발주처번호 ( level 이 6: 발주처 현장그룹 매니저, 7: 발주처 관리자인 경우 필수 )',
    primary key (mb_no, mb_id),
    constraint mb_id
        unique (mb_id),
    constraint fk_member_office_no
        foreign key (office_no) references ordering_office (office_no)
            on delete set null
)
    charset=utf8;

create table coop_worker
(
    worker_mb_id varchar(20) not null comment '근로자 아이디',
    coop_mb_id varchar(20) not null comment '협력사 아이디',
    create_datetime timestamp default CURRENT_TIMESTAMP null comment '등록일',
    update_datetime timestamp default CURRENT_TIMESTAMP null comment '변경일',
    primary key (worker_mb_id, coop_mb_id),
    constraint worker_mb_id
        unique (worker_mb_id),
    constraint member_mb_id_coop_worker_coop_mb_id_fk
        foreign key (coop_mb_id) references g5_member (mb_id)
            on delete cascade,
    constraint member_mb_id_coop_worker_mb_id_fk
        foreign key (worker_mb_id) references g5_member (mb_id)
            on delete cascade
)
    charset=utf8;

create index mb_20
    on g5_member (mb_20);

create index mb_datetime
    on g5_member (mb_datetime);

create index mb_today_login
    on g5_member (mb_today_login);

create definer = kunsulup@`%` trigger g5member_delete_trigger
    after delete
    on g5_member
    for each row
BEGIN
    IF ( OLD.mb_level = 4 ) THEN
        delete from construction_site_manager
        where mb_id = OLD.mb_id
        ;
    END IF;
END;

create table office_workplace_grp
(
    wp_grp_no bigint auto_increment comment '발주처 현장관리 그룹 번호'
        primary key,
    office_no bigint not null comment '발주처번호',
    office_grp_name varchar(128) not null comment '발주처 현장관리 그룹명',
    create_date datetime default CURRENT_TIMESTAMP not null comment '생성일',
    creator varchar(20) not null comment '생성자',
    update_date datetime default CURRENT_TIMESTAMP not null comment '최종 수정일',
    updater varchar(20) not null comment '최종 수정자',
    constraint office_workplace_grp_ibfk_1
        foreign key (office_no) references ordering_office (office_no)
            on delete cascade
)
    comment '발주처 현장관리 그룹 정보' charset=utf8;

create index office_no
    on office_workplace_grp (office_no);

create table office_workplace_manager
(
    wp_grp_no bigint not null comment '발주처 관리그룹 번호',
    mb_id varchar(20) not null comment '사용자 아이디',
    create_date datetime default CURRENT_TIMESTAMP not null comment '생성일',
    creator varchar(20) not null comment '생성자',
    primary key (wp_grp_no, mb_id),
    constraint office_workplace_manager_ibfk_1
        foreign key (wp_grp_no) references office_workplace_grp (wp_grp_no)
            on delete cascade
)
    comment '발주처 현장관리자 정보' charset=utf8;

create table push_log
(
    pl_idx int auto_increment
        primary key,
    url varchar(255) not null,
    token varchar(255) not null,
    title varchar(255) not null,
    content varchar(255) not null,
    sub_data varchar(255) not null,
    pl_datetime datetime not null,
    pl_response text not null
)
    charset=utf8;

create index pl_datetime
    on push_log (pl_datetime);

create index token
    on push_log (token);

create table recruit
(
    rc_idx int auto_increment
        primary key,
    rc_title varchar(100) not null,
    wp_id varchar(50) not null,
    wp_name varchar(100) not null,
    wpc_id varchar(50) not null,
    coop_mb_id varchar(20) not null,
    coop_mb_name varchar(50) not null,
    rc_work1 varchar(50) null,
    rc_work2 varchar(50) null,
    rc_work3 varchar(50) null,
    rc_pay_amount varchar(10) not null,
    rc_pay_unit varchar(5) not null,
    rc_tel varchar(20) not null,
    rc_content text not null,
    rc_datetime datetime not null,
    rc_updatetime datetime not null,
    work_section_a varchar(8) null comment '공종A',
    work_section_b varchar(8) null comment '공종B'
)
    charset=utf8;

create index wp_id_coop_mb_id
    on recruit (wp_id, coop_mb_id);

create table recruit_apply
(
    ra_idx int auto_increment
        primary key,
    rc_idx int not null,
    wp_id varchar(50) not null,
    wp_name varchar(100) not null,
    coop_mb_id varchar(20) not null,
    coop_mb_name varchar(50) not null,
    mb_id varchar(20) not null,
    mb_name varchar(20) not null,
    mb_birth varchar(20) not null,
    ra_datetime datetime not null,
    ra_status varchar(10) not null
)
    charset=utf8;

create index mb_id
    on recruit_apply (mb_id);

create index rc_idx_ra_datetime
    on recruit_apply (rc_idx, ra_datetime);

create index wp_id_mb_id
    on recruit_apply (wp_id, mb_id);

create table recruit_wish
(
    rw_idx int auto_increment
        primary key,
    rc_idx int not null,
    mb_id varchar(20) not null,
    rw_datetime datetime not null
)
    charset=utf8;

create index rc_idx_mb_id
    on recruit_wish (rc_idx, mb_id);

create table region_code
(
    r_idx int auto_increment
        primary key,
    region_type varchar(10) null,
    region_code varchar(10) null,
    sido_name varchar(10) null,
    sugungu_name varchar(20) null,
    dong_name varchar(20) null,
    region_x varchar(20) null,
    region_y varchar(20) null,
    `열 10` varchar(50) null,
    `열 15` varchar(50) null,
    `열 11` varchar(50) null,
    `열 12` varchar(50) null,
    `열 13` varchar(50) null,
    `열 14` varchar(50) null,
    latitude double null,
    longitude double null,
    loc_update varchar(50) null
)
    comment '공공기관 행정구역 코드표';

create table region_img_point
(
    sido varchar(32) not null comment '시도',
    region varchar(32) not null comment '지역명',
    x int null comment 'x 좌표',
    y int null comment 'y 좌표',
    primary key (sido, region)
)
    comment '통합 관제 시도이미지별 지역 좌표' charset=utf8;

create table region_sido
(
    sido_cd varchar(2) not null comment '시도 코드'
        primary key,
    sido_nm varchar(32) not null comment '시도명',
    sido_short_nm varchar(16) null comment '시도명(2자리명)',
    sido_ord int default 0 not null comment '정렬 순서',
    nx int default 0 not null comment '기상청 위경도 격자X',
    ny int default 0 not null comment '기상청 위경도 격자Y'
)
    comment '법정시군구 코드' charset=utf8;

create table region_sigungu
(
    sigungu_cd varchar(5) not null comment '시군구 코드'
        primary key,
    sido_cd varchar(2) not null comment '시도 코드',
    sigungu_nm varchar(32) null comment '시군구명',
    sigungu_nm_old varchar(32) null comment '예전 시군구명',
    sigungu_nm_full varchar(64) null comment '시도 시군구명',
    constraint region_sigungu_ibfk_1
        foreign key (sido_cd) references region_sido (sido_cd)
            on delete cascade
)
    comment '법정동코드' charset=utf8;

create index sido_cd
    on region_sigungu (sido_cd);

create table region_sigungu_point
(
    region_cd varchar(10) not null comment '지역코드'
        primary key,
    region_nm_full varchar(32) not null comment '지역명',
    x int null comment 'x 좌표',
    y int null comment 'y 좌표'
)
    comment '통합 관제 시도이미지별 지역 좌표' charset=utf8;

create table risk_accssmnt_item
(
    ra_item_no bigint auto_increment comment '위험요인 번호'
        primary key,
    section varchar(32) not null comment '공종',
    work_name varchar(64) not null comment '작업명',
    work_detail varchar(128) not null comment '작업내용',
    risk_factor varchar(128) not null comment '위험요인',
    occur_type varchar(8) not null comment '발생형태',
    safety_measure varchar(128) null comment '안전대책',
    create_date datetime default CURRENT_TIMESTAMP not null comment '생성일(작성일)',
    creator varchar(20) not null comment '생성자 아이디',
    update_date datetime default CURRENT_TIMESTAMP not null comment '최종 수정일',
    updater varchar(20) not null comment '최종 수정자 아이디',
    constraint section
        unique (section, work_name, work_detail, risk_factor)
)
    comment '위험성 평가 위험요인 정보' charset=utf8;

create table sensor_all_log
(
    sl_idx int auto_increment,
    sd_idx int null,
    sd_name varchar(100) null,
    si_code varchar(256) not null,
    si_type varchar(50) null,
    si_place1 varchar(50) null,
    si_place2 varchar(50) null,
    wp_id varchar(50) null,
    wp_name varchar(100) null,
    cc_id varchar(50) null,
    cc_name varchar(50) null,
    wpw_id varchar(50) null,
    coop_mb_id varchar(20) null,
    coop_mb_name varchar(50) null,
    mb_id varchar(20) null,
    mb_name varchar(50) null,
    sl_datetime datetime default '0000-00-00 00:00:00' not null,
    primary key (sl_idx, sl_datetime)
)
    charset=utf8;

create index mb_id
    on sensor_all_log (mb_id);

create table sensor_all_log_backup
(
    sl_idx int auto_increment
        primary key,
    sd_idx int null,
    sd_name varchar(100) null,
    si_code varchar(256) not null,
    si_type varchar(50) null,
    si_place1 varchar(50) null,
    si_place2 varchar(50) null,
    wp_id varchar(50) null,
    wp_name varchar(100) null,
    cc_id varchar(50) null,
    cc_name varchar(50) null,
    wpw_id varchar(50) null,
    coop_mb_id varchar(20) null,
    coop_mb_name varchar(50) null,
    mb_id varchar(20) null,
    mb_name varchar(50) null,
    sl_datetime datetime null
)
    charset=utf8;

create index mb_id
    on sensor_all_log_backup (mb_id);

create table sensor_district
(
    sd_idx int auto_increment
        primary key,
    wp_id varchar(50) not null,
    sd_name varchar(100) not null,
    sd_memo text not null,
    default_color varchar(8) null comment '구역표시 기본 색상',
    flash_color varchar(8) null comment '구역표시 점멸 색상'
)
    charset=utf8;

create index wp_id
    on sensor_district (wp_id);

create table sensor_info
(
    si_idx int auto_increment
        primary key,
    wp_id varchar(50) not null,
    wp_name varchar(100) not null,
    sd_idx int not null,
    sd_name varchar(100) not null,
    si_code varchar(100) not null,
    si_type varchar(20) not null,
    si_place1 varchar(50) not null,
    si_place2 varchar(50) not null,
    si_push tinyint not null,
    si_datetime datetime not null,
    uuid varchar(64) null comment 'ibeacon uuid',
    major smallint unsigned null comment 'ibeacon major',
    minor smallint unsigned null comment 'ibeacon minor'
)
    charset=utf8;

create table sensor_building_location
(
    si_idx int not null comment '센서 아이디'
        primary key,
    building_no bigint not null comment '빌딩 넘버(SEQ)',
    floor int not null comment '빌딩 층',
    grid_x int not null comment 'x 축 좌표',
    grid_y int not null comment 'y 축 좌표',
    create_date datetime default CURRENT_TIMESTAMP not null comment '생성일',
    creator varchar(20) not null comment '생성자 아이디(mb_id) ',
    update_date datetime default CURRENT_TIMESTAMP not null comment '수정일',
    updater varchar(20) not null comment '수정자 아이디(mb_id) ',
    constraint sensor_building_location_ibfk_1
        foreign key (building_no) references building (building_no)
            on delete cascade,
    constraint sensor_building_location_ibfk_2
        foreign key (si_idx) references sensor_info (si_idx)
            on delete cascade
)
    comment '센서 빌딩 위치 정보';

create index idx_sensor_building_location_1
    on sensor_building_location (building_no, floor);

create table sensor_detail_info
(
    si_idx int null comment 'sensor_info 일렬번호',
    sd_idx int null comment 'sensor_district 일렬번호',
    si_code varchar(64) null comment '센서명',
    uuid varchar(64) null comment 'ibeacon uuid',
    major int null comment 'ibeacon major',
    minor int null comment 'ibeacon minor',
    create_datetime datetime null comment '센서 정보 등록일',
    update_datetime timestamp null comment '센서 정보 수정일',
    constraint FK_sensor_detail_info_sensor_info
        foreign key (si_idx) references sensor_info (si_idx)
            on update cascade on delete cascade
)
    comment '센서 상세 정보 ' charset=utf8;

create index sd_idx
    on sensor_info (sd_idx);

create index si_code
    on sensor_info (si_code);

create index si_type
    on sensor_info (si_type);

create index wp_id
    on sensor_info (wp_id);

create table sensor_log
(
    sl_idx int auto_increment,
    sd_idx int not null,
    sd_name varchar(100) not null,
    si_code varchar(50) not null,
    si_type varchar(50) not null,
    si_place1 varchar(50) not null,
    si_place2 varchar(50) not null,
    wp_id varchar(50) not null,
    wp_name varchar(100) not null,
    cc_id varchar(50) not null,
    cc_name varchar(50) not null,
    wpw_id varchar(50) not null,
    coop_mb_id varchar(20) not null,
    coop_mb_name varchar(50) not null,
    mb_id varchar(20) not null,
    mb_name varchar(50) not null,
    sl_datetime datetime not null,
    uuid varchar(64) null comment 'sensor uuid',
    major int null comment 'sensor packet major',
    minor int null comment 'sensor packet minor',
    in_out_type int null comment '0 : sensor in, 1 : sensor out',
    scan_datetime datetime(6) null comment 'sensor 입.출 시간',
    distance decimal(10,2) null comment ' sensor 간의 거리',
    version varchar(8) null,
    primary key (sl_idx, sl_datetime)
)
    charset=utf8;

create index cc_name
    on sensor_log (cc_name);

create index coop_mb_name
    on sensor_log (coop_mb_name);

create index mb_id
    on sensor_log (mb_id);

create index mb_name
    on sensor_log (mb_name);

create index sd_idx_sl_datetime_mb_id
    on sensor_log (sd_idx, sl_datetime, mb_id);

create index wp_id_coop_mb_id
    on sensor_log (wp_id, coop_mb_id);

create index wp_id_mb_id
    on sensor_log (wp_id, mb_id);

create index wp_id_sd_idx
    on sensor_log (wp_id, sd_idx);

create index wp_id_si_type
    on sensor_log (wp_id, si_type);

create index wp_id_sl_datetime
    on sensor_log (wp_id, sl_datetime);

create index wp_name
    on sensor_log (wp_name);

create table sensor_log_equipment
(
    mode tinyint not null comment '센서 모드',
    name varchar(64) not null comment '(usim-mdn)',
    major int not null comment 'sensor packet major',
    minor int not null comment 'sensor packet minor',
    mb_id varchar(20) not null comment '접근 근로자 mb_id',
    version varchar(8) null comment '패킷 버전',
    distance decimal(10,2) null comment '거리(m)',
    sle_datetime datetime not null comment '이벤트 발생 시간',
    expire_datetime datetime not null comment '이벤트 만료 시간',
    primary key (mode, name)
)
    charset=utf8;

create table sensor_log_inout
(
    sli_idx int auto_increment
        primary key,
    sd_idx int not null,
    sd_name varchar(100) not null,
    si_code varchar(50) not null,
    si_type varchar(50) not null,
    si_place1 varchar(50) not null,
    si_place2 varchar(50) not null,
    wp_id varchar(50) not null,
    wp_name varchar(100) not null,
    cc_id varchar(50) not null,
    cc_name varchar(50) not null,
    wpw_id varchar(50) not null,
    coop_mb_id varchar(20) not null,
    coop_mb_name varchar(50) not null,
    mb_id varchar(20) not null,
    mb_name varchar(50) not null,
    sli_in_sd_idx int null,
    sli_in_sd_name varchar(50) null,
    sli_in_si_code varchar(50) null,
    sli_in_si_type varchar(20) null,
    sli_in_datetime datetime null,
    sli_out_sd_idx int null,
    sli_out_sd_name varchar(50) null,
    sli_out_si_code varchar(50) null,
    sli_out_si_type varchar(20) null,
    sli_out_datetime datetime null,
    sli_datetime datetime null,
    sli_middle_datetime text null
)
    charset=utf8;

create index cc_name
    on sensor_log_inout (cc_name);

create index coop_mb_name
    on sensor_log_inout (coop_mb_name);

create index mb_id
    on sensor_log_inout (mb_id);

create index mb_name
    on sensor_log_inout (mb_name);

create index wp_id
    on sensor_log_inout (wp_id);

create index wp_id_sli_datetime_sli_in_si_type
    on sensor_log_inout (wp_id, sli_datetime, sli_in_si_type, coop_mb_id);

create index wp_id_sli_in_si_type_coop_mb_id
    on sensor_log_inout (wp_id, sli_in_si_type, coop_mb_id);

create index wp_name
    on sensor_log_inout (wp_name);

create table sensor_log_recent
(
    wp_id varchar(50) not null comment '현장 ID',
    si_idx int not null comment 'sensor info 테이블 key',
    coop_mb_id varchar(20) not null comment '협력사 ID',
    mb_id varchar(20) not null comment '근로자 ID',
    in_out_type tinyint(1) not null comment '센서 접근/이탈 구분 (0 : 접근, 1: 이탈)',
    slr_datetime datetime not null comment '변경 시간',
    primary key (mb_id, slr_datetime)
)
    comment '최종 센서 접속 이력 관리';

create index idx_sensor_log_recent_si_idx_slr
    on sensor_log_recent (si_idx, slr_datetime);

create index idx_sensor_log_recent_wp_coop_mb
    on sensor_log_recent (wp_id, coop_mb_id, mb_id);

create table sensor_log_single
(
    sl_idx int auto_increment
        primary key,
    sd_idx int not null,
    sd_name varchar(100) not null,
    si_code varchar(50) not null,
    si_type varchar(50) not null,
    si_place1 varchar(50) not null,
    si_place2 varchar(50) not null,
    wp_id varchar(50) not null,
    wp_name varchar(100) not null,
    cc_id varchar(50) not null,
    cc_name varchar(50) not null,
    wpw_id varchar(50) not null,
    coop_mb_id varchar(20) not null,
    coop_mb_name varchar(50) not null,
    mb_id varchar(20) not null,
    mb_name varchar(50) not null,
    sl_datetime datetime not null
)
    charset=utf8;

create index mb_id_sl_datetime
    on sensor_log_single (mb_id, sl_datetime);

create index wp_id_coop_mb_id_mb_id_si_type_sl_datetime
    on sensor_log_single (wp_id, coop_mb_id, mb_id, si_type, sl_datetime);

create index wp_id_sd_idx_sl_datetime
    on sensor_log_single (wp_id, sd_idx, sl_datetime);

create index wp_id_si_type_sl_datetime
    on sensor_log_single (wp_id, si_type, sl_datetime);

create table sensor_log_trace
(
    slt_idx int auto_increment,
    sd_idx int not null,
    sd_name varchar(100) not null,
    si_code varchar(50) not null,
    si_type varchar(50) not null,
    si_place1 varchar(50) not null,
    si_place2 varchar(50) not null,
    wp_id varchar(50) not null,
    wp_name varchar(100) not null,
    cc_id varchar(50) not null,
    cc_name varchar(50) not null,
    wpw_id varchar(50) not null,
    coop_mb_id varchar(20) not null,
    coop_mb_name varchar(50) not null,
    mb_id varchar(20) not null,
    mb_name varchar(50) not null,
    slt_in_sd_idx int null,
    slt_in_sd_name varchar(50) null,
    slt_in_si_code varchar(50) null,
    slt_in_si_type varchar(20) null,
    slt_in_datetime datetime null,
    slt_out_sd_idx int null,
    slt_out_sd_name varchar(50) null,
    slt_out_si_code varchar(50) null,
    slt_out_si_type varchar(20) null,
    slt_out_datetime datetime null,
    slt_datetime datetime not null,
    primary key (slt_idx, slt_datetime)
)
    charset=utf8;

create index si_code_wp_id_cc_id_wpw_id_mb_id_sli_in_datetime
    on sensor_log_trace (si_code, wp_id, cc_id, wpw_id, mb_id, slt_in_datetime);

create index slt_in_datetime
    on sensor_log_trace (slt_in_datetime, slt_out_datetime);

create index wp_id_slt_datetime
    on sensor_log_trace (wp_id, mb_id, slt_in_datetime);

create table sensor_policy_info
(
    sp_idx int unsigned auto_increment comment '일렬번호'
        primary key,
    si_type varchar(20) not null comment '센서 유형',
    minor_min int not null comment 'minor identifier range min value',
    minor_max int not null comment 'minor identifier range max value',
    scan_interval int not null comment '센서 스캔 주기(ms)',
    idle_interval int not null comment '센서 스캔 idle 주기(ms)',
    report_interval int not null comment '센서 스캔 결과 서버 report 주기(ms)',
    wp_id varchar(50) null comment '현장코드 (현장 코드가 없을 시 default 설정)',
    wp_name varchar(100) null comment '현장명',
    create_date datetime null comment '등록일',
    update_date datetime null comment '변경일',
    mb_id varchar(20) null comment '변경자 아이디',
    constraint UQIDX_SI_TYPE_WP_ID
        unique (si_type, wp_id)
)
    charset=utf8;

create index wp_id
    on sensor_policy_info (wp_id);

create table sensor_type
(
    si_type varchar(20) not null comment '센서 타입'
        primary key,
    default_color varchar(8) not null comment '센서 기본 색상',
    flash_color varchar(8) not null comment '센서 점멸 색상',
    support_flash tinyint default 0 not null comment '센서 점멸 지원여부. 0: 미지원, 1: 지원',
    create_date datetime default CURRENT_TIMESTAMP not null comment '생성일',
    creator varchar(20) not null comment '생성자 아이디(mb_id) ',
    update_date datetime default CURRENT_TIMESTAMP not null comment '수정일',
    updater varchar(20) not null comment '수정자 아이디(mb_id) '
)
    comment '센서 타입 정보' charset=utf8;

create table tracker_assign_log
(
    tal_no bigint auto_increment comment '트랙커 할당/회수 번호'
        primary key,
    tracker_id varchar(16) not null comment '트랙커 아이디',
    wp_id varchar(50) not null comment '할당 현장',
    coop_mb_id varchar(20) not null comment '할당시 근로자 소속 협력사',
    mb_id varchar(20) not null comment '트랙커 할당받은 근로자',
    assign_date datetime not null comment '할당일',
    assigner varchar(20) not null comment '할당자',
    collect_date datetime null comment '회수일',
    collector varchar(20) null comment '회수자'
)
    comment '트랙커 할당/회수 로그 정보';

create index idx_tracker_assign_log_tracker_id
    on tracker_assign_log (tracker_id);

create table ui_component
(
    cmpt_id varchar(16) not null comment '컴포넌트 아이디'
        primary key,
    cmpt_name varchar(32) not null comment '컴포넌트명',
    description varchar(1024) not null comment '설명',
    ui_type int not null comment 'UI 유형. 1: 메인화면 유형, 2: 컴포넌트 유형',
    width int not null comment '컴포넌트의 x 길이 (1~4)',
    height int not null comment '컴포넌트의 y 길이 (1~3)',
    file_name varchar(255) null comment '대표 파일명',
    file_name_org varchar(255) null comment '대표 파일 원본명',
    file_path varchar(255) null comment '대표 파일 저장 위치(상대경로)',
    file_location int null comment '대표 파일 저장소. 0: local Storage ',
    status tinyint default 0 not null comment '상태. 0: 미사용, 1: 사용',
    create_date datetime default CURRENT_TIMESTAMP not null comment '생성일',
    creator varchar(20) not null comment '생성자 아이디(mb_id) ',
    update_date datetime default CURRENT_TIMESTAMP not null comment '수정일',
    updater varchar(20) not null comment '수정자 아이디(mb_id) '
)
    comment '모니터링 컴포넌트 정보' charset=utf8;

create table warn_push_password
(
    password varchar(50) not null
        primary key
)
    charset=utf8;

create table work
(
    idx int auto_increment
        primary key,
    work1 varchar(100) not null,
    work2 varchar(100) not null,
    work3 varchar(100) not null
)
    charset=utf8;

create index work1
    on work (work1);

create table work_device_info
(
    idx int auto_increment comment '일렬번호'
        primary key,
    wp_id varchar(50) not null comment '현장코드',
    device_type tinyint(1) null comment '디바이스 구분 (0 : 출입게이트, QR 코드 리더기, 1: 출입 표출 디바이스, 2 : 고정형 GPS, 3 : CCTV)',
    device_id varchar(50) null comment '디바이스 식별자',
    mac_address_type tinyint(1) null comment 'mac_address 유/무선 구분 (0 : 유선, 1: 무선)',
    mac_address varchar(24) not null comment 'mac_address 정보',
    link_url varchar(128) null comment '기본 link url',
    ai_link_url varchar(128) null comment 'ai 영상 link url',
    update_datetime datetime null,
    updater varchar(20) null,
    constraint wp_id_mac_address
        unique (wp_id, mac_address)
)
    comment '현장 디바이스 관리 ' charset=utf8;

create table image_analytic_info
(
    wp_id varchar(50) not null comment '현장 코드',
    mac_address varchar(24) not null comment 'CCTV mac_address',
    event_type tinyint(1) not null comment '이벤트 구분 ( 1 : 안전모, 2: 화재 )',
    event_status tinyint(1) not null comment '이벤트 상태 (0 : 해제, 1: 위험)',
    file_path varchar(128) null comment '이미지 파일 path',
    file_name varchar(128) null comment '이미지 파일 경로',
    org_file_name varchar(128) null comment '원본파일명',
    event_datetime datetime not null comment '이벤트 발생 시간',
    event_view tinyint default 1 not null comment '이벤트 보기 설정 ON/OFF. 0 : OFF, 1: ON',
    primary key (wp_id, mac_address, event_type),
    constraint FK_image_analytic_info_work_device_info
        foreign key (wp_id, mac_address) references work_device_info (wp_id, mac_address)
            on update cascade on delete cascade
)
    charset=utf8;

create table work_equipment_info
(
    idx int auto_increment
        primary key,
    wp_id varchar(50) not null,
    coop_mb_id varchar(50) not null,
    mb_id varchar(50) not null,
    equipment_type tinyint null,
    equipment_no varchar(12) null,
    device_id varchar(50) null,
    mac_address varchar(50) null,
    ope_type tinyint default 0 not null comment '운행 유형. 0: 상시, 1: 기간제',
    ope_start date null comment '운행 시작일 ( 기간제일 경우 )',
    ope_end date null comment '운행 종료일 ( 기간제일 경우 )',
    `desc` varchar(50) null comment '설명',
    rtc_url varchar(128) null comment 'RTC play 정보',
    upd_datetime datetime null,
    updater varchar(50) null,
    constraint wp_id_device_model
        unique (wp_id, device_id, mb_id)
)
    charset=utf8;

create index mac_address
    on work_equipment_info (mac_address);

create index wp_id_mb_id
    on work_equipment_info (wp_id, mb_id);

create table work_equipment_info_copy
(
    idx int not null
        primary key,
    wp_id varchar(50) not null,
    coop_mb_id varchar(50) not null,
    mb_id varchar(50) not null,
    equipment_type tinyint null,
    equipment_no varchar(12) null,
    device_id varchar(50) null,
    mac_address varchar(50) null,
    `desc` varchar(50) null comment '설명',
    rtc_url varchar(128) null comment 'RTC play 정보',
    upd_datetime datetime null,
    updater varchar(50) null,
    constraint wp_id_device_model
        unique (wp_id, device_id)
)
    charset=utf8;

create index mac_address
    on work_equipment_info_copy (mac_address);

create index wp_id_mb_id
    on work_equipment_info_copy (wp_id, mb_id);

create table work_geofence_detail
(
    wp_id varchar(50) not null comment '현장 코드',
    wp_seq tinyint default 0 not null comment 'geo fence 멀티 현장 일시 순차 반영',
    seq tinyint not null comment 'geo fence drawing 순서',
    latitude double null comment '경도',
    longitude double null comment '위도',
    upd_datetime datetime null comment '최종 변경 일시',
    updater varchar(50) null comment '변경자',
    primary key (wp_id, wp_seq, seq)
)
    comment '현장별 geo fence 정보' charset=utf8;

create table work_geofence_info
(
    wp_id varchar(50) not null comment '현장 코드',
    wp_seq tinyint default 0 not null comment '현장 layout 순서(main 0 - 순차 증가)',
    seq tinyint not null comment 'geo fence drawing 순서',
    latitude double null comment '경도',
    longitude double null comment '위도',
    upd_datetime datetime null comment '최종 변경 일시',
    updater varchar(50) null comment '변경자',
    primary key (wp_id, wp_seq, seq)
)
    comment '현장별 geo fence 정보' charset=utf8;

create table work_geofence_info_copy
(
    wp_id varchar(50) not null comment '현장 코드',
    wp_seq tinyint default 0 not null comment '현장 layout 순서(main 0 - 순차 증가)',
    seq tinyint not null comment 'geo fence drawing 순서',
    latitude double null comment '경도',
    longitude double null comment '위도',
    upd_datetime datetime null comment '최종 변경 일시',
    updater varchar(50) null comment '변경자',
    primary key (wp_id, wp_seq, seq)
)
    comment '현장별 geo fence 정보' charset=utf8;

create table work_geofence_limit
(
    wp_id varchar(50) not null comment '현장 코드',
    seq tinyint not null comment 'geo fence drawing 순서',
    latitude double null comment '경도',
    longitude double null comment '위도',
    upd_datetime datetime null comment '최종 변경 일시',
    updater varchar(50) null comment '변경자',
    primary key (wp_id, seq)
)
    comment '현장별 geo fence 정보' engine=MyISAM charset=utf8;

create table work_place
(
    wp_idx int auto_increment,
    wp_id varchar(50) not null
        primary key,
    wp_cate varchar(50) not null,
    cc_id varchar(50) not null,
    cc_name varchar(50) not null,
    man_mb_id varchar(20) not null,
    man_mb_name varchar(20) not null,
    wp_name varchar(100) not null,
    wp_sido varchar(30) not null,
    wp_gugun varchar(30) not null,
    wp_addr varchar(255) not null,
    wp_tel varchar(20) not null,
    wp_start_date date null comment '착공일',
    wp_end_date date null,
    wp_edutime_start time null,
    wp_edutime_end time null,
    wp_end_status tinyint not null,
    wp_memo text not null,
    wp_datetime datetime not null,
    view_map_file_name varchar(255) null comment '조감도 파일명',
    view_map_file_name_org varchar(255) null comment '조감도 원본 파일명',
    view_map_file_path varchar(255) null comment '조감도 저장 위치(상대경로)',
    view_map_file_location int null comment '조감도 저장소. 0: local Storage ',
    link_cctv varchar(255) null comment 'CCTV 링크 정보',
    link_mesurement varchar(255) null comment '전자계측 링크 정보',
    link_wearable_1 varchar(255) null comment '넥밴드 #1 링크 정보',
    link_wearable_1_status tinyint null comment 'wearable#1 상태. 0:off, 1:on',
    link_wearable_2 varchar(255) null comment '넥밴드 #2 링크 정보',
    link_wearable_2_status tinyint null comment 'wearable#2 상태. 0:off, 1:on',
    default_coop_mb_id varchar(20) null comment '디폴트 협력사',
    gas_policy_no int default 1 not null comment '유해물질 측정 임계치 정책 넘버',
    station_name varchar(32) null comment '측정소(미세먼지)',
    activation_geofence_longitude double null comment '현장 Activation Geofence longitude',
    activation_geofence_latitude double null comment '현장 Activation Geofence latitude',
    activation_geofence_radius int null comment '현장 Activation Geofence 반경',
    uninjury_record_date datetime default CURRENT_TIMESTAMP null comment '무재해 시작일',
    office_no bigint default 1 null comment '발주사 번호',
    constraint wp_idx
        unique (wp_idx),
    constraint fk_workplace_office
        foreign key (office_no) references ordering_office (office_no)
            on delete set null
)
    charset=utf8;

create table bookmark_ble
(
    mb_id varchar(20) not null comment '사용자 아이디',
    wp_id varchar(50) not null comment '현장 코드',
    building_no bigint not null comment '건물 번호',
    floor int not null comment '건물 층 번호',
    update_date datetime default CURRENT_TIMESTAMP not null comment '최종 수정일',
    primary key (mb_id, wp_id, building_no, floor),
    constraint bookmark_ble_ibfk_1
        foreign key (mb_id) references g5_member (mb_id)
            on delete cascade,
    constraint bookmark_ble_ibfk_2
        foreign key (wp_id) references work_place (wp_id)
            on delete cascade,
    constraint bookmark_ble_ibfk_3
        foreign key (building_no, floor) references building_floor (building_no, floor)
            on delete cascade
)
    comment '현장(BLE) bookmark 정보' charset=utf8;

create index fk_bookmark_ble_building
    on bookmark_ble (building_no, floor);

create index fk_bookmark_ble_wp
    on bookmark_ble (wp_id);

create table construction_site
(
    wp_id varchar(50) not null comment '현장 아이디',
    cc_id varchar(50) not null comment '건설사 아이디',
    create_date datetime default CURRENT_TIMESTAMP not null comment '생성일',
    creator varchar(20) not null comment '생성자',
    primary key (wp_id, cc_id),
    constraint construction_site_ibfk_1
        foreign key (wp_id) references work_place (wp_id)
            on delete cascade,
    constraint construction_site_ibfk_2
        foreign key (cc_id) references con_company (cc_id)
            on delete cascade
)
    comment '건설사 현장 편입 정보' charset=utf8;

create index cc_id
    on construction_site (cc_id);

create table construction_site_manager
(
    wp_id varchar(50) not null comment '현장 아이디',
    cc_id varchar(50) not null comment '건설사 아이디',
    mb_id varchar(20) not null comment '사용자 아이디',
    create_date datetime default CURRENT_TIMESTAMP not null comment '생성일',
    creator varchar(20) not null comment '생성자',
    primary key (wp_id, cc_id, mb_id),
    constraint construction_site_manager_ibfk_1
        foreign key (wp_id, cc_id) references construction_site (wp_id, cc_id)
            on delete cascade
)
    comment '건설 현장 관리자' charset=utf8;

create table entr_gate_workplace
(
    wp_id varchar(50) not null comment '현장 아이디'
        primary key,
    account_id varchar(16) not null comment '연동 계정 아이디',
    mapping_cd varchar(32) not null comment '현장 맵핑 아이디',
    status int default 1 not null comment '상태. 0: 미사용, 1: 사용',
    gate_type int null comment '출입게이트 유형. 1: 안면인식, 2: QR Reader',
    admin_url varchar(128) null comment '관리자 사이트 URL',
    admin_id varchar(16) null comment '관리자 사이트 계정',
    admin_pwd varchar(64) null comment '관리자 사이트 비번',
    creator varchar(20) not null comment '생성자',
    create_date datetime default CURRENT_TIMESTAMP not null comment '생성일',
    updater varchar(20) not null comment '수정자',
    update_date datetime default CURRENT_TIMESTAMP not null comment '수정일',
    constraint uq_entr_gate_workplace_ac
        unique (account_id, mapping_cd),
    constraint fk_entr_gate_workplace_id
        foreign key (account_id) references entr_gate_account (account_id)
            on delete cascade,
    constraint fk_entr_gate_workplace_wp
        foreign key (wp_id) references work_place (wp_id)
            on delete cascade
)
    comment '출입게이트 맵핑 현장' charset=utf8;

create table imos_member_ui_component
(
    imuc_no bigint auto_increment comment '넘버(SEQ)'
        primary key,
    wp_id varchar(50) not null comment '현장 아이디',
    mb_id varchar(20) not null comment '컴포넌트 사용자',
    deploy_page int not null comment '배치된 페이지 ( 1, 2 )',
    pos_x int not null comment '컴포넌트의 시작 x 좌표',
    pos_y int not null comment '컴포넌트의 시작 y 좌표',
    hcmpt_id varchar(16) not null comment '컴포넌트 아이디',
    create_date datetime default CURRENT_TIMESTAMP not null comment '생성일',
    constraint uq_imos_mb_uicomp
        unique (wp_id, mb_id, deploy_page, pos_x, pos_y),
    constraint fk_imos_mb_uicomp_cmpt
        foreign key (hcmpt_id) references hulan_ui_component (hcmpt_id)
            on delete cascade,
    constraint fk_imos_mb_uicomp_wp
        foreign key (wp_id) references work_place (wp_id)
            on delete cascade
)
    comment 'IMOS 사용자 배치 컴포넌트 정보' charset=utf8;

create table risk_accssmnt_inspect
(
    rai_no bigint auto_increment comment '위험성 평가 번호'
        primary key,
    wp_id varchar(50) not null comment '현장 아이디',
    coop_mb_id varchar(20) not null comment '협력사 아이디',
    start_date date not null comment '점검기간 시작일',
    end_date date not null comment '점검기간 종료일',
    man_mb_id varchar(20) not null comment '결재자 아이디(현장관리자)',
    status int default 0 not null comment '결재상태. 0:대기, 1: 승인요청, 2: 승인(완료), 3: 반려',
    rai_apprvl_no bigint null comment '최종 결재 이력 번호',
    create_date datetime default CURRENT_TIMESTAMP not null comment '생성일(작성일)',
    update_date datetime default CURRENT_TIMESTAMP not null comment '최종 수정일',
    constraint fk_risk_acc_ins_wp
        foreign key (wp_id) references work_place (wp_id)
            on delete cascade
)
    comment '위험성 평가 안전점검 정보' charset=utf8;

create table risk_accssmnt_inspect_apprvl
(
    rai_apprvl_no bigint auto_increment comment '위험성 평가 일일점검 번호'
        primary key,
    rai_no bigint not null comment '위험성 평가 번호',
    action int default 0 not null comment '행위. 1: 승인요청, 2: 승인(완료), 3: 반려',
    comment varchar(256) null comment '비고',
    create_date datetime default CURRENT_TIMESTAMP not null comment '처리일( 승인 요청/승인/반려일 )',
    creator varchar(20) not null comment '처리자 아이디',
    constraint fk_risk_acc_ins_appr_rai
        foreign key (rai_no) references risk_accssmnt_inspect (rai_no)
            on delete cascade
)
    comment '위험성 평가 안전점검 결재 이력' charset=utf8;

alter table risk_accssmnt_inspect
    add constraint fk_risk_acc_ins_appr
        foreign key (rai_apprvl_no) references risk_accssmnt_inspect_apprvl (rai_apprvl_no)
            on delete set null;

create table risk_accssmnt_inspect_item
(
    rai_item_no bigint auto_increment comment '위험성 평가 항목 번호'
        primary key,
    rai_no bigint not null comment '위험성 평가 번호',
    location varchar(32) not null comment '위치',
    section varchar(32) not null comment '공종',
    work_name varchar(64) not null comment '작업명',
    work_detail varchar(128) not null comment '작업내용',
    risk_factor varchar(128) not null comment '위험요인',
    occur_type varchar(8) not null comment '발생형태',
    safety_measure varchar(128) null comment '안전대책',
    result int not null comment '점검결과. 0: 미조치 1: 완료[유지], 2: 불량, 3: N/A',
    create_date datetime default CURRENT_TIMESTAMP not null comment '생성일',
    constraint fk_risk_acc_ins_itm_rai
        foreign key (rai_no) references risk_accssmnt_inspect (rai_no)
            on delete cascade
)
    comment '안전 점수 현황' charset=utf8;

create table tracker
(
    tracker_id varchar(16) not null comment '트랙커 아이디'
        primary key,
    device_model varchar(64) not null comment '트랙커 디바이스 모델명',
    wp_id varchar(50) null comment '할당 현장',
    create_date datetime default CURRENT_TIMESTAMP not null comment '생성일',
    creator varchar(20) not null comment '생성자 아이디(mb_id) ',
    update_date datetime default CURRENT_TIMESTAMP not null comment '수정일',
    updater varchar(20) not null comment '수정자 아이디(mb_id)',
    constraint tracker_ibfk_1
        foreign key (wp_id) references work_place (wp_id)
            on delete set null
)
    comment '트랙커 정보' charset=utf8;

create index fk_tracker_wp_id
    on tracker (wp_id);

create table tracker_assign
(
    tracker_id varchar(16) not null comment '트랙커 아이디'
        primary key,
    wp_id varchar(50) not null comment '할당 현장',
    coop_mb_id varchar(20) not null comment '협력사 아이디',
    mb_id varchar(20) not null comment '근로자 아이디',
    tal_no bigint not null comment '관련 트랙커 할당 로그 번호',
    constraint uq_tracker_mb_id
        unique (mb_id),
    constraint tracker_assign_ibfk_1
        foreign key (tracker_id) references tracker (tracker_id)
            on delete cascade,
    constraint tracker_assign_ibfk_2
        foreign key (wp_id) references work_place (wp_id)
            on delete cascade,
    constraint tracker_assign_ibfk_3
        foreign key (mb_id) references g5_member (mb_id)
            on delete cascade,
    constraint tracker_assign_ibfk_4
        foreign key (tal_no) references tracker_assign_log (tal_no)
            on delete cascade
)
    comment '트랙커 할당 정보' charset=utf8;

create index fk_tracker_assign_tal_no
    on tracker_assign (tal_no);

create index idx_tracker_assign_wp_id_coop_mb_id_mb_id
    on tracker_assign (wp_id, coop_mb_id, mb_id);

create table work_geofence_group
(
    wp_id varchar(50) not null comment '현장 코드',
    wp_seq int not null comment '현장 순서',
    fence_name varchar(32) null comment 'fence 명',
    center_latitude double null comment '중심 latitude',
    center_longitude double null comment '중심 longitude',
    create_date datetime default CURRENT_TIMESTAMP not null comment '생성일',
    creator varchar(20) not null comment '생성자',
    update_date datetime default CURRENT_TIMESTAMP not null comment '최종 수정일',
    updater varchar(20) not null comment '최종 수정자',
    primary key (wp_id, wp_seq),
    constraint work_geofence_group_ibfk_1
        foreign key (wp_id) references work_place (wp_id)
            on delete cascade
)
    comment '현장별 fence 정보' charset=utf8;

create table bookmark_gps
(
    mb_id varchar(20) not null comment '사용자 아이디',
    wp_id varchar(50) not null comment '현장 코드',
    wp_seq int not null comment 'fence 순번',
    update_date datetime default CURRENT_TIMESTAMP not null comment '최종 수정일',
    primary key (mb_id, wp_id, wp_seq),
    constraint bookmark_gps_ibfk_1
        foreign key (mb_id) references g5_member (mb_id)
            on delete cascade,
    constraint bookmark_gps_ibfk_2
        foreign key (wp_id) references work_place (wp_id)
            on delete cascade,
    constraint bookmark_gps_ibfk_3
        foreign key (wp_id, wp_seq) references work_geofence_group (wp_id, wp_seq)
            on delete cascade
)
    comment '현장(GPS) bookmark 정보' charset=utf8;

create index fk_bookmark_gps_fence
    on bookmark_gps (wp_id, wp_seq);

create index cc_id_wp_name
    on work_place (cc_id, wp_name);

create index man_mb_id
    on work_place (man_mb_id);

create definer = kunsulup@`%` trigger work_place_after_delete
    after delete
    on work_place
    for each row
BEGIN
    DELETE FROM app_menu_visibility WHERE wp_id = OLD.wp_id;

END;

create definer = kunsulup@`%` trigger work_place_after_insert
    after insert
    on work_place
    for each row
BEGIN

    insert into app_menu_visibility
    select NEW.wp_id, mb_level, area_name, no, title, visibility, display_order from app_menu_visibility where wp_id = 'default';

END;

create table work_place_address
(
    wp_id varchar(50) not null comment '현장 아이디'
        primary key,
    zonecode varchar(5) not null comment '새우편번호',
    address varchar(256) not null comment '기본주소',
    road_address varchar(256) null comment '도로명주소',
    jibun_address varchar(256) null comment '지번주소',
    building_name varchar(64) null comment '건물명',
    sido varchar(32) not null comment '시도명',
    sigungu varchar(32) not null comment '시군구명',
    sigungu_code varchar(5) not null comment '시군구코드',
    roadname_code varchar(10) null comment '도로명코드',
    roadname varchar(32) null comment '도로명',
    bname varchar(32) null comment '법정동/법정리 이름',
    bname1 varchar(32) null comment '법정리의 읍/면 이름',
    bname2 varchar(32) null comment '법정동/법정리 이름',
    bcode varchar(10) not null comment '법정동코드',
    hname varchar(32) null comment '행정동 이름',
    create_date datetime default CURRENT_TIMESTAMP not null comment '생성시간'
)
    comment '현장의 상세 주소 정보' charset=utf8mb4;

create table work_place_cctv
(
    cctv_no bigint auto_increment comment 'CCTV 번호'
        primary key,
    cctv_name varchar(128) not null comment 'CCTV 명',
    cctv_url varchar(512) not null comment 'CCTV URL',
    description varchar(1024) null comment '설명',
    wp_id varchar(50) not null comment '현장 아이디',
    status int default 1 not null comment 'CCTV 사용 여부. 0: 미사용, 1: 사용',
    ptz_status int default 0 not null comment 'CCTV PTZ 사용 여부. 0: 미사용, 1: 사용',
    create_date datetime default CURRENT_TIMESTAMP not null comment '생성일',
    creator varchar(20) not null comment '생성자 아이디(mb_id) ',
    update_date datetime default CURRENT_TIMESTAMP not null comment '수정일',
    updater varchar(20) not null comment '수정자 아이디(mb_id) ',
    constraint fk_work_place_cctv_wp
        foreign key (wp_id) references work_place (wp_id)
            on delete cascade
)
    comment '현장의 상세 주소 정보' charset=utf8;

create table work_place_coop
(
    wpc_id varchar(50) not null
        primary key,
    wp_id varchar(50) not null,
    wp_name varchar(50) not null,
    cc_id varchar(50) null comment '건설사 아이디',
    coop_mb_id varchar(20) not null,
    coop_mb_name varchar(20) not null,
    work_section_a varchar(8) null comment '공종A 코드',
    wpc_work varchar(255) not null,
    wpc_datetime datetime not null,
    constraint fk_work_place_coop_cc_id
        foreign key (cc_id) references con_company (cc_id)
            on delete set null
)
    charset=utf8;

create index wp_id
    on work_place_coop (wp_id);

create index wp_id_2
    on work_place_coop (wp_id, cc_id);

create index wp_id_coop_mb_id
    on work_place_coop (wp_id, coop_mb_id);

create index wpc_id
    on work_place_coop (wp_id, wpc_id);

create definer = kunsulup@`%` trigger work_place_coop_before_insert
    before insert
    on work_place_coop
    for each row
BEGIN
    IF ( NEW.cc_id is null ) THEN
        SET NEW.cc_id = (
            select cc_id from work_place where wp_id = NEW.wp_id
        );
    end if;

END;

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

create table work_place_monitor_config
(
    wp_id varchar(50) not null comment '현장 아이디'
        primary key,
    support_ble tinyint default 0 not null comment 'BLE 모니터링 지원 여부. 0: 미노출, 1: 노출',
    support_gps tinyint default 0 not null comment 'GPS 모니터링 지원. 0: 미노출, 1: 노출',
    support_3d tinyint default 0 not null comment '3D 모니터링 지원. 0: 미노출, 1: 노출',
    current_worker_gps tinyint default 1 not null comment 'GPS 현재인원 노출 여부. 0:미노출, 1:노출',
    current_worker_ble tinyint default 1 not null comment 'BLE 현재인원 노출 여부  0:미노출, 1:노출',
    environment_dust tinyint default 1 not null comment '미세먼지 상황 노출 여부. 0:미노출, 1:노출',
    environment_noise tinyint default 1 not null comment '미세먼지 상황 노출 여부. 0:미노출, 1:노출',
    environment_gas tinyint default 0 not null comment '유해물질 노출 여부. 0: 미노출, 1: 노출',
    create_date datetime default CURRENT_TIMESTAMP not null comment '생성일',
    creator varchar(20) not null comment '생성자 아이디(mb_id) ',
    update_date datetime default CURRENT_TIMESTAMP not null comment '수정일',
    updater varchar(20) not null comment '수정자 아이디(mb_id) ',
    constraint work_place_monitor_config_ibfk_1
        foreign key (wp_id) references work_place (wp_id)
            on delete cascade
)
    comment '센서 타입 정보' charset=utf8;

create table work_place_safety_score
(
    safety_date varchar(8) not null comment '안전점수 대상일',
    wp_id varchar(50) not null comment '현장 아이디',
    score int default 0 not null comment '안전점수. ( 0 ~ 100 )',
    create_date datetime default CURRENT_TIMESTAMP not null comment '생성일',
    creator varchar(20) not null comment '생성자',
    update_date datetime default CURRENT_TIMESTAMP not null comment '최종 수정일',
    updater varchar(20) not null comment '최종 수정자',
    primary key (safety_date, wp_id),
    constraint fk_work_place_safety_score_wp
        foreign key (wp_id) references work_place (wp_id)
            on delete cascade
)
    comment '안전 점수 현황' charset=utf8;

create table work_place_ui_component
(
    wp_id varchar(50) not null comment '현장 아이디',
    location int not null comment '컴포넌트 위치',
    creator varchar(20) not null comment '사용자 아이디',
    cmpt_id varchar(16) not null comment '컴포넌트 아이디',
    create_date datetime default CURRENT_TIMESTAMP not null comment '생성일',
    primary key (wp_id, location, creator),
    constraint fk_work_place_ui_component_cmpt
        foreign key (cmpt_id) references ui_component (cmpt_id)
            on delete cascade,
    constraint fk_work_place_ui_component_wp
        foreign key (wp_id) references work_place (wp_id)
            on delete cascade
)
    comment '모니터링 컴포넌트 정보' charset=utf8;

create table work_place_ui_component_new
(
    wpuc_no bigint auto_increment comment '넘버(SEQ)'
        primary key,
    wp_id varchar(50) not null comment '현장 아이디',
    mb_id varchar(20) not null comment '컴포넌트 사용자',
    deploy_page int not null comment '배치된 페이지 ( 1, 2 )',
    pos_x int not null comment '컴포넌트의 시작 x 좌표',
    pos_y int not null comment '컴포넌트의 시작 y 좌표',
    cmpt_id varchar(16) not null comment '컴포넌트 아이디',
    create_date datetime default CURRENT_TIMESTAMP not null comment '생성일',
    constraint uq_wp_mb_uicomp
        unique (wp_id, mb_id, deploy_page, pos_x, pos_y),
    constraint fk_wp_mb_uicomp_cmpt
        foreign key (cmpt_id) references ui_component (cmpt_id)
            on delete cascade,
    constraint fk_wp_mb_uicomp_wp
        foreign key (wp_id) references work_place (wp_id)
            on delete cascade
)
    comment '모니터링 컴포넌트 정보' charset=utf8;

create table work_place_weather
(
    wp_id varchar(50) not null comment '현장 아이디'
        primary key,
    base_date date null comment '측정일',
    base_time time null comment '측정시간',
    nx int not null comment '예보지점 X좌표',
    ny int not null comment '예보지점 Y좌표',
    humidity varchar(16) null comment '습도',
    humidity_unit varchar(8) default '%' not null comment '습도 단위',
    wind_direction varchar(16) null comment '풍향',
    wind_speed varchar(16) null comment '풍속',
    wind_speed_unit varchar(8) default 'm/s' not null comment '풍속 단위',
    precipitation_form varchar(16) null comment '강수 형태 코드',
    precipitation_form_name varchar(16) null comment '강수 형태명',
    precipitation varchar(16) null comment '1시간 강수량',
    precipitation_unit varchar(8) default 'mm' not null comment '강수량 단위',
    sky_form varchar(16) null comment '하늘 형태 코드',
    sky_form_name varchar(16) null comment '하늘 형태명',
    rainfall varchar(16) null comment '강수확률',
    rainfall_unit varchar(8) default '%' not null comment '강수확률 단위',
    temperature varchar(16) null comment '온도',
    temperature_unit varchar(8) default '℃' not null comment '온도 단위',
    update_date datetime default CURRENT_TIMESTAMP not null comment '최종 수정일',
    constraint work_place_weather_ibfk_1
        foreign key (wp_id) references work_place (wp_id)
            on delete cascade
)
    comment '현장별 날씨 정보' charset=utf8;

create table work_section
(
    section_cd varchar(8) not null comment '공종코드'
        primary key,
    section_name varchar(64) not null comment '공종명',
    description varchar(256) null comment '공종설명',
    parent_section_cd varchar(8) null comment '상위공종코드',
    create_date datetime default CURRENT_TIMESTAMP not null comment '생성일',
    creator varchar(20) not null comment '생성자 아이디(mb_id) ',
    update_date datetime default CURRENT_TIMESTAMP not null comment '수정일',
    updater varchar(20) not null comment '수정자 아이디(mb_id) ',
    constraint work_section_ibfk_1
        foreign key (parent_section_cd) references work_section (section_cd)
            on delete set null
)
    comment '공종 정보';

create table work_place_worker
(
    wpw_id varchar(50) not null
        primary key,
    wp_id varchar(50) not null,
    wp_name varchar(50) not null,
    wpc_id varchar(50) not null,
    coop_mb_id varchar(50) not null,
    coop_mb_name varchar(50) not null,
    wpc_work varchar(50) not null,
    worker_mb_id varchar(20) not null,
    worker_mb_name varchar(50) not null,
    wpw_out tinyint not null,
    wpw_out_memo varchar(255) not null,
    wpw_bp tinyint not null,
    wpw_oper tinyint not null,
    wpw_dis1 tinyint not null,
    wpw_dis2 tinyint not null,
    wpw_dis3 tinyint not null,
    wpw_dis4 tinyint not null,
    wpw_show tinyint not null,
    wpw_status tinyint not null,
    wpw_datetime datetime default CURRENT_TIMESTAMP not null,
    work_section_b varchar(8) collate utf8mb4_general_ci null comment '공종B 코드 (근로자용)',
    constraint uq_work_place_worker_wp_mb
        unique (wp_id, worker_mb_id),
    constraint FK_work_section_b
        foreign key (work_section_b) references work_section (section_cd)
            on delete set null
)
    charset=utf8;

create index idx_work_place_worker_wp_coop_mb
    on work_place_worker (wp_id, coop_mb_id, worker_mb_id);

create index worker_mb_id
    on work_place_worker (worker_mb_id);

create index wp_id
    on work_place_worker (wp_id);

create index wp_id_coop_mb_id
    on work_place_worker (wp_id, coop_mb_id);

create index wp_id_worker_mb_id_wpw_out
    on work_place_worker (wp_id, worker_mb_id, wpw_out);

create index parent_section_cd
    on work_section (parent_section_cd);

create table work_tag_info
(
    idx int auto_increment comment '일렬번호'
        primary key,
    wp_id varchar(50) null comment '현장코드',
    worker_tag varchar(16) null comment '근로자 정보 tag',
    update_datetime datetime not null comment '변경일',
    updator varchar(50) default '' not null comment '변경자 ID',
    constraint wp_id
        unique (wp_id)
)
    comment '현장 근로자 tag 관리' charset=utf8;

create table work_temp
(
    idx int auto_increment
        primary key,
    work1 varchar(100) not null,
    work2 varchar(100) not null,
    work3 varchar(100) not null
)
    charset=utf8;

create table worker_check
(
    wc_idx int auto_increment
        primary key,
    wp_id varchar(50) not null,
    wp_name varchar(50) not null,
    coop_mb_id varchar(50) not null,
    coop_mb_name varchar(50) not null,
    wpc_id varchar(50) not null,
    wc_type int(1) unsigned zerofill default 0 not null,
    mb_id varchar(20) not null,
    mb_name varchar(20) not null,
    worker_mb_id varchar(20) not null,
    worker_mb_name varchar(20) not null,
    wc_memo text null,
    wc_datetime datetime not null
)
    charset=utf8;

create index wp_id_coop_mb_id
    on worker_check (wp_id, coop_mb_id);

create index wp_id_mb_id
    on worker_check (wp_id, mb_id);

create index wp_id_worker_mb_id
    on worker_check (wp_id, worker_mb_id);

create table worker_device_status
(
    mb_id varchar(20) not null comment '근로자 아이디'
        primary key,
    app_version varchar(8) not null comment '앱 버전',
    os_type varchar(8) not null comment 'ios/android ',
    os_version varchar(8) not null comment 'OS 버전',
    device_model varchar(32) not null comment '단말 모델',
    battery decimal(4) not null comment '배터리 잔여량',
    charge_check tinyint null comment '배터리 충전여부 미충전 : 0 , 충전 : 1, USB충전 : 2, AC충전 : 3, 무선 충전 : 4',
    ble_check tinyint not null comment '블루투스 활성화 체크 OFF : 0 , ON : 1',
    loc_check tinyint not null comment '위치 활성화 체크 OFF : 0 , ON : 1',
    si_code varchar(1024) null comment '센서명( 최종 1개)',
    upt_datetime datetime default CURRENT_TIMESTAMP not null
);

create table worker_event_hist
(
    idx int auto_increment comment '일렬번호'
        primary key,
    wp_id varchar(50) not null comment '현장코드',
    coop_mb_id varchar(50) not null comment '협력사 아이디',
    mb_id varchar(50) not null comment '근로자 아이디',
    event_type tinyint not null comment '이벤트 구분',
    gate_id varchar(24) null comment '출입 게이트 식별 정보',
    create_datetime datetime not null comment '등록일 ',
    update_datetime datetime not null comment '변경일'
)
    comment '현장 근로자 출입 이벤트 이력 관리 테이블' charset=utf8;

create table worker_get_off
(
    wg_idx int auto_increment
        primary key,
    wp_id varchar(50) not null comment '현장 ID',
    worker_mb_id varchar(20) not null comment '근로자 ID',
    memo text not null comment '사유',
    mb_id varchar(20) not null comment '처리자',
    create_date datetime not null comment '퇴근 처리 일자 (센서 정보 수집 무시 일자)',
    constraint wp_id_worker_mb_id
        unique (wp_id, worker_mb_id)
);

create index wp_id_worker_mb_id_create_date
    on worker_get_off (wp_id, worker_mb_id, create_date);

create table worker_msg_info
(
    idx int auto_increment comment '일렬번호'
        primary key,
    mb_id varchar(50) null comment '현장관리자 아이디',
    wp_id varchar(50) null comment '현장 코드',
    coop_mb_id varchar(50) null comment '협력사 아이디',
    msg_type tinyint null comment '메시지 알림 유형\r\n1: 위험지역 접근 알림.\r\n2: 감시 센서 접근 알림.\r\n3: 앱 기능 해제 알림.\r\n',
    subject varchar(256) null comment '메시지 제목',
    msg varchar(4000) null comment '메시지 내용',
    is_send tinyint null comment 'push 발송 여부\r\n0 : 발송 \r\n1 : 미 발송.\r\n',
    upt_datetime datetime null comment '생성/수정 시간'
)
    comment '근로자 알림 메시지 관리' engine=MyISAM charset=utf8;

create index mb_id_wp_id_coop_mb_id
    on worker_msg_info (mb_id, wp_id, coop_mb_id, msg_type);

create index upt_datetime
    on worker_msg_info (upt_datetime);

create table worker_qr_enter
(
    wp_id varchar(50) not null comment '현장 아이디',
    coop_mb_id varchar(20) not null comment '협력사 아이디',
    mb_id varchar(20) not null comment '사용자 아이디',
    enter_day date not null comment '출근일',
    create_date datetime default CURRENT_TIMESTAMP not null comment '생성일',
    update_date datetime default CURRENT_TIMESTAMP not null comment '최종 수정일',
    primary key (wp_id, coop_mb_id, mb_id, enter_day),
    constraint worker_qr_enter_ibfk_1
        foreign key (mb_id) references g5_member (mb_id)
            on delete cascade,
    constraint worker_qr_enter_ibfk_2
        foreign key (wp_id) references work_place (wp_id)
            on delete cascade
)
    comment 'QR 출근 이력' charset=utf8;

create index fk_worker_qr_enter_mb
    on worker_qr_enter (mb_id);

create table worker_warn
(
    ww_idx int auto_increment
        primary key,
    wp_id varchar(50) not null,
    wp_name varchar(50) not null,
    coop_mb_id varchar(50) not null,
    coop_mb_name varchar(50) not null,
    mb_id varchar(20) not null,
    mb_name varchar(20) not null,
    worker_mb_id varchar(20) not null,
    worker_mb_name varchar(20) not null,
    ww_content text not null,
    ww_datetime datetime not null
)
    charset=utf8;

create index worker_mb_id_ww_datetime
    on worker_warn (worker_mb_id, ww_datetime);

create index wp_id_coop_mb_name
    on worker_warn (wp_id, coop_mb_name);

create table worker_work_print
(
    wwp_idx int auto_increment
        primary key,
    cc_id varchar(50) not null,
    cc_name varchar(50) not null,
    wp_id varchar(50) not null,
    wp_name varchar(50) not null,
    coop_mb_id varchar(50) not null,
    coop_mb_name varchar(50) not null,
    wwp_data text null,
    wwp_status tinyint null,
    wwp_date date not null,
    wwp_updatetime datetime null
)
    charset=utf8;

create index coop_mb_id
    on worker_work_print (coop_mb_id);

create index coop_mb_name
    on worker_work_print (coop_mb_name);

create index wp_id_coop_mb_id_wwp_date
    on worker_work_print (wp_id, coop_mb_id, wwp_date);

create index wwp_date
    on worker_work_print (wwp_date);

