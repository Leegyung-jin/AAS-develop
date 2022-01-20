##############################
-- 출근기록
##############################
-- sensor_log_inout 에 insert 시 attendance_book 에 출근 처리 ( 존재하면 상태만 출근으로 업데이트 )
-- worker_qr_enter 에 insert 시 attendance_book 에 출근 처리 ( 존재하면 상태만 출근으로 업데이트 ).
-- sensor_log_recent 의 in_out_type 값 3 으로 업데이트시 퇴근 처리

##################################################
-- 권한관리
##################################################

drop table authority_mb;
drop table authority_level;
drop table member_authority;

CREATE TABLE member_authority (
    authority_id   VARCHAR(32)   NOT NULL COMMENT '권한ID',
    authority_name VARCHAR(32) NOT NULL COMMENT '권한명',
    description varchar(1024) NOT NULL COMMENT '설명',
    create_date datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성일',
    creator varchar(20) NOT NULL COMMENT '생성자',
    update_date datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '수정일',
    updater varchar(20) NOT NULL COMMENT '수정자',
    PRIMARY KEY(authority_id)
) ENGINE=InnoDB  charset=utf8 COMMENT '권한';

CREATE TABLE authority_level (
    mb_level   tinyint NOT NULL COMMENT '사용자 등급',
    authority_id   VARCHAR(32) NOT NULL COMMENT '사용자 권한 ID',
    description varchar(1024) NOT NULL COMMENT '설명',
    update_date datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '갱신일',
    updater varchar(20) NOT NULL COMMENT '갱신자',
    PRIMARY KEY(mb_level, authority_id),
    CONSTRAINT FK_authority_level_mb_level FOREIGN KEY (mb_level) REFERENCES member_level (mb_level) on delete cascade,
    CONSTRAINT FK_authority_level_authority_id FOREIGN KEY (authority_id) REFERENCES member_authority (authority_id) on delete cascade
) ENGINE=InnoDB  charset=utf8 COMMENT '계정 레벨(등급)별 권한';


CREATE TABLE authority_mb (
    mb_id   VARCHAR(20)   NOT NULL COMMENT '사용자 ID',
    authority_id   VARCHAR(32)   NOT NULL COMMENT '사용자 권한 ID',
    description varchar(1024) NOT NULL COMMENT '설명',
    update_date datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '갱신일',
    updater varchar(20) NOT NULL COMMENT '갱신자',
    PRIMARY KEY(mb_id, authority_id),
    CONSTRAINT FK_authority_mb_mb_id FOREIGN KEY (mb_id) REFERENCES g5_member (mb_id) on delete cascade,
    CONSTRAINT FK_authority_mb_authority_id FOREIGN KEY (authority_id) REFERENCES member_authority (authority_id) on delete cascade
) ENGINE=InnoDB  charset=utf8 COMMENT '사용자별 권한';

##################################################
-- 소음 수치 임계치 정책 설계 필요
##################################################

create table noise_level_range_policy (
    policy_no int auto_increment primary key comment '소음 수치 임계치 정책 관리번호',
    policy_name varchar(64) not null comment '정책명',
    create_date datetime default CURRENT_TIMESTAMP not null comment '생성일',
    creator varchar(20) not null comment '생성자 아이디(mb_id) ',
    update_date datetime default CURRENT_TIMESTAMP not null comment '수정일',
    updater varchar(20) not null comment '수정자 아이디(mb_id) '
) ENGINE=InnoDB charset=utf8 comment '소음 수치 임계치 정책'
;

create table noise_level (
    policy_no int comment '소음 수치 임계치 정책 관리번호',
    nlevel_no int not null comment '임계치 레벨 코드. ( 1 ~ 10 )',
    nlevel_name varchar(64) not null comment '임계치 레벨명',
    create_date datetime default CURRENT_TIMESTAMP not null comment '생성일',
    creator varchar(20) not null comment '생성자 아이디(mb_id) ',
    update_date datetime default CURRENT_TIMESTAMP not null comment '수정일',
    updater varchar(20) not null comment '수정자 아이디(mb_id) ',
    primary key(policy_no, nlevel_no),
    constraint fk_noise_level_policy foreign key (policy_no) references noise_level_range_policy (policy_no) on delete cascade
) ENGINE=InnoDB charset=utf8 comment '소음 수치 레벨'
;


-- 시간대별로 다른 임계 수치 구간 필요
-- alert 등급. 몇단계로 나눌지 데이터 필요
-- ==> 시간대별 alert 등급 수치 필요
create table noise_level_range (
    range_no int auto_increment primary key comment '소음 수치 임계치 구간 관리번호',
    range_name varchar(64) not null comment '소음 수치 임계치 구간명',
    time_range tinyint not null comment '소음수치 시간대. 1: 아침/저녁(05:00~07:00, 18:00~22:00), 2: 주간(07:00~18:00), 3: 야간(22:00~05:00)',
    policy_no int not null comment '소음 수치 임계치 정책 관리번호',
    min integer not null comment '임계 구간 최소 수치(이상). 단위 dB',
    alert tinyint not null default 0 comment '현장관제 경고 여부. 0: 미경고, 1: 경고',
    status_message varchar(128) null comment '소음 수치 상태 메세지',
    constraint fk_noise_level_range_policy foreign key (policy_no) references noise_level_range_policy (policy_no) on delete cascade
)  ENGINE=InnoDB charset=utf8 comment '소음 수치 임계치 정책 구간'
;

##################################################
-- 현장별 링크 정보 추가
##################################################
create table work_place_ext_link (
    wp_id                varchar(50) not null primary key comment '현장 아이디',
    link_name            varchar(32) not null comment '링크 표시명',
    link_kind            tinyint not null default 1 comment '링크 종류. 1: 공정율 이미지',
    link_content         tinyint not null default 1 comment '링크 컨텐츠 유형. 1: 이미지, 2: Site Url',
    link_url             varchar(512) not null comment '링크 URL',
    create_date          datetime default current_timestamp not null comment '생성일',
    creator              varchar(20) not null comment '생성자',
    update_date          datetime default current_timestamp not null comment '최종 수정일',
    updater              varchar(20) not null comment '최종 수정자',
    constraint fk_work_place_ext_link_wp foreign key (wp_id) references work_place (wp_id) on delete cascade
)  ENGINE=InnoDB charset=utf8 comment '현장 링크 정보'
;