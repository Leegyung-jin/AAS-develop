CREATE TABLE region_sigungu_point (
    region_cd               varchar(10) not null primary key comment '지역코드',
    region_nm_full          varchar(32) not null comment '지역명',
    x                       integer null comment 'x 좌표',
    y                       integer null comment 'y 좌표'
) ENGINE=InnoDB  charset=utf8 COMMENT '통합 관제 시도이미지별 지역 좌표';

CREATE TABLE region_img_point (
    sido                    varchar(32) not null comment '시도',
    region                  varchar(32) not null comment '지역명',
    x                       integer null comment 'x 좌표',
    y                       integer null comment 'y 좌표',
    primary key ( sido, region )
) ENGINE=InnoDB  charset=utf8 COMMENT '통합 관제 시도이미지별 지역 좌표 임시테이블';

truncate table region_img_point;
truncate table region_sigungu_point;

insert into region_img_point(sido, region, x, y) values( '강원', '고성', 58, 22 );
insert into region_img_point(sido, region, x, y) values( '강원', '철원', 21, 26 );
insert into region_img_point(sido, region, x, y) values( '강원', '화천', 31, 31 );
insert into region_img_point(sido, region, x, y) values( '강원', '양구', 44, 30 );
insert into region_img_point(sido, region, x, y) values( '강원', '속초', 63, 32 );
insert into region_img_point(sido, region, x, y) values( '강원', '인제', 53, 35 );
insert into region_img_point(sido, region, x, y) values( '강원', '양양', 65, 40 );
insert into region_img_point(sido, region, x, y) values( '강원', '춘천', 34, 43 );
insert into region_img_point(sido, region, x, y) values( '강원', '홍천', 44, 50 );
insert into region_img_point(sido, region, x, y) values( '강원', '강릉', 73, 54 );
insert into region_img_point(sido, region, x, y) values( '강원', '횡성', 45, 61 );
insert into region_img_point(sido, region, x, y) values( '강원', '평창', 58, 60 );
insert into region_img_point(sido, region, x, y) values( '강원', '동해', 80, 64 );
insert into region_img_point(sido, region, x, y) values( '강원', '원주', 37, 70 );
insert into region_img_point(sido, region, x, y) values( '강원', '정선', 67, 69 );
insert into region_img_point(sido, region, x, y) values( '강원', '삼척', 83, 74 );
insert into region_img_point(sido, region, x, y) values( '강원', '영월', 59, 78 );
insert into region_img_point(sido, region, x, y) values( '강원', '태백', 76, 79 );
insert into region_img_point(sido, region, x, y) values( '경기', '연천', 44, 15 );
insert into region_img_point(sido, region, x, y) values( '경기', '포천', 55, 23 );
-- insert into region_img_point(sido, region, x, y) values( '경기', '|', 42, 27 );
insert into region_img_point(sido, region, x, y) values( '경기', '동두천', 46, 27 );
insert into region_img_point(sido, region, x, y) values( '경기', '파주', 33, 29 );
insert into region_img_point(sido, region, x, y) values( '경기', '양주', 43, 32 );
insert into region_img_point(sido, region, x, y) values( '경기', '가평', 63, 32 );
insert into region_img_point(sido, region, x, y) values( '경기', '김포', 24, 41 );
insert into region_img_point(sido, region, x, y) values( '경기', '고양', 35, 42 );
insert into region_img_point(sido, region, x, y) values( '경기', '의정부', 45, 38 );
-- insert into region_img_point(sido, region, x, y) values( '경기', '|', 51, 42 );
insert into region_img_point(sido, region, x, y) values( '경기', '남양주', 55, 42 );
insert into region_img_point(sido, region, x, y) values( '경기', '구리', 48, 46 );
insert into region_img_point(sido, region, x, y) values( '경기', '양평', 70, 53 );
insert into region_img_point(sido, region, x, y) values( '경기', '부천', 32, 52 );
insert into region_img_point(sido, region, x, y) values( '경기', '하남', 52, 52 );
insert into region_img_point(sido, region, x, y) values( '경기', '과천', 43, 55 );
insert into region_img_point(sido, region, x, y) values( '경기', '광명', 36, 56 );
insert into region_img_point(sido, region, x, y) values( '경기', '시흥', 31, 59 );
insert into region_img_point(sido, region, x, y) values( '경기', '안양', 39, 59 );
insert into region_img_point(sido, region, x, y) values( '경기', '성남', 48, 59 );
insert into region_img_point(sido, region, x, y) values( '경기', '광주', 56, 59 );
insert into region_img_point(sido, region, x, y) values( '경기', '군포', 38, 62 );
insert into region_img_point(sido, region, x, y) values( '경기', '의왕', 44, 62 );
insert into region_img_point(sido, region, x, y) values( '경기', '안산', 32, 65 );
insert into region_img_point(sido, region, x, y) values( '경기', '여주', 74, 66 );
insert into region_img_point(sido, region, x, y) values( '경기', '수원', 43, 68 );
insert into region_img_point(sido, region, x, y) values( '경기', '용인', 54, 70 );
insert into region_img_point(sido, region, x, y) values( '경기', '이천', 64, 69 );
insert into region_img_point(sido, region, x, y) values( '경기', '화성', 37, 74 );
insert into region_img_point(sido, region, x, y) values( '경기', '오산', 45, 74 );
insert into region_img_point(sido, region, x, y) values( '경기', '안성', 57, 82 );
insert into region_img_point(sido, region, x, y) values( '경기', '평택', 42, 84 );
insert into region_img_point(sido, region, x, y) values( '경남', '거창', 27, 24 );
insert into region_img_point(sido, region, x, y) values( '경남', '함양', 19, 35 );
insert into region_img_point(sido, region, x, y) values( '경남', '합천', 39, 34 );
insert into region_img_point(sido, region, x, y) values( '경남', '창녕', 55, 37 );
insert into region_img_point(sido, region, x, y) values( '경남', '밀양', 69, 38 );
insert into region_img_point(sido, region, x, y) values( '경남', '의령', 45, 43 );
insert into region_img_point(sido, region, x, y) values( '경남', '양산', 81, 42 );
insert into region_img_point(sido, region, x, y) values( '경남', '산청', 26, 45 );
insert into region_img_point(sido, region, x, y) values( '경남', '함안', 52, 49 );
insert into region_img_point(sido, region, x, y) values( '경남', '김해', 72, 50 );
insert into region_img_point(sido, region, x, y) values( '경남', '진주', 38, 54 );
insert into region_img_point(sido, region, x, y) values( '경남', '창원', 61, 54 );
insert into region_img_point(sido, region, x, y) values( '경남', '하동', 22, 59 );
insert into region_img_point(sido, region, x, y) values( '경남', '사천', 34, 64 );
insert into region_img_point(sido, region, x, y) values( '경남', '고성', 47, 65 );
insert into region_img_point(sido, region, x, y) values( '경남', '통영', 52, 74 );
insert into region_img_point(sido, region, x, y) values( '경남', '거제', 63, 73 );
insert into region_img_point(sido, region, x, y) values( '경남', '남해', 30, 78 );
insert into region_img_point(sido, region, x, y) values( '경북', '봉화', 49, 24 );
insert into region_img_point(sido, region, x, y) values( '경북', '울진', 65, 24 );
insert into region_img_point(sido, region, x, y) values( '경북', '영주', 37, 27 );
insert into region_img_point(sido, region, x, y) values( '경북', '문경', 22, 35 );
insert into region_img_point(sido, region, x, y) values( '경북', '영양', 58, 34 );
insert into region_img_point(sido, region, x, y) values( '경북', '울릉도', 83, 32 );
insert into region_img_point(sido, region, x, y) values( '경북', '예천', 33, 37 );
insert into region_img_point(sido, region, x, y) values( '경북', '안동', 46, 39 );
insert into region_img_point(sido, region, x, y) values( '경북', '영덕', 66, 44 );
insert into region_img_point(sido, region, x, y) values( '경북', '상주', 20, 47 );
insert into region_img_point(sido, region, x, y) values( '경북', '의성', 39, 50 );
insert into region_img_point(sido, region, x, y) values( '경북', '청송', 57, 49 );
insert into region_img_point(sido, region, x, y) values( '경북', '구미', 31, 56 );
insert into region_img_point(sido, region, x, y) values( '경북', '군위', 42, 59 );
insert into region_img_point(sido, region, x, y) values( '경북', '포항', 63, 58 );
insert into region_img_point(sido, region, x, y) values( '경북', '김천', 20, 63 );
insert into region_img_point(sido, region, x, y) values( '경북', '영천', 51, 64 );
insert into region_img_point(sido, region, x, y) values( '경북', '칠곡', 34, 65 );
insert into region_img_point(sido, region, x, y) values( '경북', '성주', 26, 70 );
insert into region_img_point(sido, region, x, y) values( '경북', '경산', 46, 72 );
insert into region_img_point(sido, region, x, y) values( '경북', '경주', 63, 73 );
insert into region_img_point(sido, region, x, y) values( '경북', '고령', 28, 78 );
insert into region_img_point(sido, region, x, y) values( '경북', '청도', 46, 81 );
insert into region_img_point(sido, region, x, y) values( '광주', '북구', 70, 39 );
insert into region_img_point(sido, region, x, y) values( '광주', '광산구', 32, 47 );
insert into region_img_point(sido, region, x, y) values( '광주', '서구', 54, 54 );
insert into region_img_point(sido, region, x, y) values( '광주', '동구', 76, 61 );
insert into region_img_point(sido, region, x, y) values( '광주', '남구', 56, 68 );
insert into region_img_point(sido, region, x, y) values( '대구', '동구', 69, 23 );
insert into region_img_point(sido, region, x, y) values( '대구', '북구', 51, 25 );
insert into region_img_point(sido, region, x, y) values( '대구', '달성군', 31, 34 );
insert into region_img_point(sido, region, x, y) values( '대구', '서구', 47, 36 );
insert into region_img_point(sido, region, x, y) values( '대구', '중구', 55, 38 );
insert into region_img_point(sido, region, x, y) values( '대구', '달서구', 43, 44 );
insert into region_img_point(sido, region, x, y) values( '대구', '남구', 53, 44 );
insert into region_img_point(sido, region, x, y) values( '대구', '수성구', 65, 44 );
-- insert into region_img_point(sido, region, x, y) values( '대구', '달성군', 38, 63 );
insert into region_img_point(sido, region, x, y) values( '대전', '대덕구', 59, 31 );
insert into region_img_point(sido, region, x, y) values( '대전', '유성구', 32, 37 );
insert into region_img_point(sido, region, x, y) values( '대전', '동구', 69, 53 );
insert into region_img_point(sido, region, x, y) values( '대전', '서구', 36, 63 );
insert into region_img_point(sido, region, x, y) values( '대전', '중구', 51, 63 );
insert into region_img_point(sido, region, x, y) values( '부산', '기장군', 75, 27 );
insert into region_img_point(sido, region, x, y) values( '부산', '금정구', 57, 34 );
insert into region_img_point(sido, region, x, y) values( '부산', '북구', 46, 39 );
insert into region_img_point(sido, region, x, y) values( '부산', '동래구', 56, 44 );
-- insert into region_img_point(sido, region, x, y) values( '부산', '|', 64, 48 );
insert into region_img_point(sido, region, x, y) values( '부산', '해운대구', 69, 48 );
insert into region_img_point(sido, region, x, y) values( '부산', '연제구', 56, 50 );
insert into region_img_point(sido, region, x, y) values( '부산', '부산진구', 50, 53 );
insert into region_img_point(sido, region, x, y) values( '부산', '사상구', 40, 55 );
insert into region_img_point(sido, region, x, y) values( '부산', '수영구', 62, 55 );
insert into region_img_point(sido, region, x, y) values( '부산', '동구', 50, 59 );
insert into region_img_point(sido, region, x, y) values( '부산', '서구', 44, 61 );
insert into region_img_point(sido, region, x, y) values( '부산', '남구', 59, 61 );
insert into region_img_point(sido, region, x, y) values( '부산', '중구', 48, 66 );
insert into region_img_point(sido, region, x, y) values( '부산', '사하구', 39, 69 );
insert into region_img_point(sido, region, x, y) values( '부산', '영도구', 54, 71 );
insert into region_img_point(sido, region, x, y) values( '부산', '강서구', 25, 62 );
insert into region_img_point(sido, region, x, y) values( '서울', '도봉구', 59, 23 );
insert into region_img_point(sido, region, x, y) values( '서울', '강북구', 54, 30 );
insert into region_img_point(sido, region, x, y) values( '서울', '노원구', 68, 29 );
insert into region_img_point(sido, region, x, y) values( '서울', '성동구', 62, 51 );
insert into region_img_point(sido, region, x, y) values( '서울', '광진구', 71, 52 );
insert into region_img_point(sido, region, x, y) values( '서울', '은평구', 40, 36 );
insert into region_img_point(sido, region, x, y) values( '서울', '성북구', 57, 40 );
insert into region_img_point(sido, region, x, y) values( '서울', '종로구', 48, 42 );
insert into region_img_point(sido, region, x, y) values( '서울', '중랑구', 72, 41 );
insert into region_img_point(sido, region, x, y) values( '서울', '서대문구', 41, 46 );
insert into region_img_point(sido, region, x, y) values( '서울', '동대문구', 63, 44 );
-- insert into region_img_point(sido, region, x, y) values( '서울', ',', 68, 44 );
-- insert into region_img_point(sido, region, x, y) values( '서울', '[', 15, 49 );
insert into region_img_point(sido, region, x, y) values( '서울', '강서구', 19, 49 );
insert into region_img_point(sido, region, x, y) values( '서울', '중구', 52, 49 );
insert into region_img_point(sido, region, x, y) values( '서울', '마포구', 36, 51 );
insert into region_img_point(sido, region, x, y) values( '서울', '강동구', 83, 51 );
insert into region_img_point(sido, region, x, y) values( '서울', '용산구', 49, 56 );
insert into region_img_point(sido, region, x, y) values( '서울', '양천구', 25, 59 );
insert into region_img_point(sido, region, x, y) values( '서울', '영등포구', 36, 59 );
insert into region_img_point(sido, region, x, y) values( '서울', '송파구', 76, 63 );
insert into region_img_point(sido, region, x, y) values( '서울', '구로구', 25, 66 );
insert into region_img_point(sido, region, x, y) values( '서울', '동작구', 44, 64 );
insert into region_img_point(sido, region, x, y) values( '서울', '강남구', 66, 66 );
insert into region_img_point(sido, region, x, y) values( '서울', '서초구', 55, 70 );
insert into region_img_point(sido, region, x, y) values( '서울', '관악구', 42, 72 );
insert into region_img_point(sido, region, x, y) values( '서울', '금천구', 33, 74 );
insert into region_img_point(sido, region, x, y) values( '세종특별자치시', '소정면', 28, 11 );
insert into region_img_point(sido, region, x, y) values( '세종특별자치시', '전의면', 31, 25 );
insert into region_img_point(sido, region, x, y) values( '세종특별자치시', '전동면', 44, 28 );
insert into region_img_point(sido, region, x, y) values( '세종특별자치시', '조치원읍', 55, 40 );
insert into region_img_point(sido, region, x, y) values( '세종특별자치시', '연서면', 42, 46 );
insert into region_img_point(sido, region, x, y) values( '세종특별자치시', '연동면', 60, 55 );
insert into region_img_point(sido, region, x, y) values( '세종특별자치시', '연기면', 51, 60 );
insert into region_img_point(sido, region, x, y) values( '세종특별자치시', '부강면', 72, 60 );
insert into region_img_point(sido, region, x, y) values( '세종특별자치시', '장군면', 35, 67 );
insert into region_img_point(sido, region, x, y) values( '세종특별자치시', '금남면', 56, 80 );
insert into region_img_point(sido, region, x, y) values( '울산', '북구', 73, 33 );
insert into region_img_point(sido, region, x, y) values( '울산', '중구', 61, 41 );
insert into region_img_point(sido, region, x, y) values( '울산', '울주군', 36, 44 );
insert into region_img_point(sido, region, x, y) values( '울산', '동구', 80, 50 );
insert into region_img_point(sido, region, x, y) values( '울산', '남구', 66, 51 );
insert into region_img_point(sido, region, x, y) values( '인천', '강화군', 48, 32 );
insert into region_img_point(sido, region, x, y) values( '인천', '서구', 70, 51 );
insert into region_img_point(sido, region, x, y) values( '인천', '계양구', 83, 53 );
insert into region_img_point(sido, region, x, y) values( '인천', '중구', 47, 62 );
insert into region_img_point(sido, region, x, y) values( '인천', '옹진군', 17, 66 );
insert into region_img_point(sido, region, x, y) values( '인천', '부평구', 80, 62 );
insert into region_img_point(sido, region, x, y) values( '인천', '동구', 70, 64 );
-- insert into region_img_point(sido, region, x, y) values( '인천', '중구', 65, 68 );
insert into region_img_point(sido, region, x, y) values( '인천', '미추홀구', 72, 69 );
-- insert into region_img_point(sido, region, x, y) values( '인천', '구', 77, 69 );
insert into region_img_point(sido, region, x, y) values( '인천', '남동구', 83, 72 );
insert into region_img_point(sido, region, x, y) values( '인천', '연수구', 71, 75 );
insert into region_img_point(sido, region, x, y) values( '전남', '장성', 45, 22 );
insert into region_img_point(sido, region, x, y) values( '전남', '영광', 31, 25 );
insert into region_img_point(sido, region, x, y) values( '전남', '담양', 56, 23 );
insert into region_img_point(sido, region, x, y) values( '전남', '구례', 79, 26 );
insert into region_img_point(sido, region, x, y) values( '전남', '곡성', 69, 28 );
insert into region_img_point(sido, region, x, y) values( '전남', '함평', 35, 34 );
insert into region_img_point(sido, region, x, y) values( '전남', '화순', 57, 39 );
insert into region_img_point(sido, region, x, y) values( '전남', '광양', 86, 39 );
insert into region_img_point(sido, region, x, y) values( '전남', '나주', 44, 41 );
insert into region_img_point(sido, region, x, y) values( '전남', '순천', 73, 41 );
insert into region_img_point(sido, region, x, y) values( '전남', '무안', 31, 45 );
insert into region_img_point(sido, region, x, y) values( '전남', '신안', 13, 47 );
insert into region_img_point(sido, region, x, y) values( '전남', '목포', 28, 51 );
insert into region_img_point(sido, region, x, y) values( '전남', '보성', 64, 50 );
insert into region_img_point(sido, region, x, y) values( '전남', '영암', 39, 52 );
insert into region_img_point(sido, region, x, y) values( '전남', '여수', 86, 53 );
insert into region_img_point(sido, region, x, y) values( '전남', '장흥', 53, 57 );
insert into region_img_point(sido, region, x, y) values( '전남', '강진', 46, 61 );
insert into region_img_point(sido, region, x, y) values( '전남', '고흥', 70, 63 );
insert into region_img_point(sido, region, x, y) values( '전남', '해남', 36, 65 );
insert into region_img_point(sido, region, x, y) values( '전남', '진도', 22, 72 );
insert into region_img_point(sido, region, x, y) values( '전남', '완도', 45, 77 );
insert into region_img_point(sido, region, x, y) values( '전북', '익산', 41, 27 );
insert into region_img_point(sido, region, x, y) values( '전북', '군산', 26, 31 );
insert into region_img_point(sido, region, x, y) values( '전북', '완주', 53, 31 );
insert into region_img_point(sido, region, x, y) values( '전북', '무주', 82, 33 );
insert into region_img_point(sido, region, x, y) values( '전북', '김제', 34, 42 );
insert into region_img_point(sido, region, x, y) values( '전북', '전주', 48, 41 );
insert into region_img_point(sido, region, x, y) values( '전북', '진안', 65, 41 );
insert into region_img_point(sido, region, x, y) values( '전북', '부안', 20, 52 );
insert into region_img_point(sido, region, x, y) values( '전북', '장수', 72, 53 );
insert into region_img_point(sido, region, x, y) values( '전북', '정읍', 35, 57 );
insert into region_img_point(sido, region, x, y) values( '전북', '임실', 54, 58 );
insert into region_img_point(sido, region, x, y) values( '전북', '고창', 18, 68 );
insert into region_img_point(sido, region, x, y) values( '전북', '순창', 46, 71 );
insert into region_img_point(sido, region, x, y) values( '전북', '남원', 66, 70 );
insert into region_img_point(sido, region, x, y) values( '제주', '제주시', 49, 41 );
insert into region_img_point(sido, region, x, y) values( '제주', '서귀포', 53, 57 );
insert into region_img_point(sido, region, x, y) values( '충남', '당진', 38, 21 );
insert into region_img_point(sido, region, x, y) values( '충남', '아산', 55, 28 );
insert into region_img_point(sido, region, x, y) values( '충남', '천안', 68, 28 );
insert into region_img_point(sido, region, x, y) values( '충남', '태안', 16, 31 );
insert into region_img_point(sido, region, x, y) values( '충남', '서산', 29, 30 );
insert into region_img_point(sido, region, x, y) values( '충남', '예산', 46, 37 );
insert into region_img_point(sido, region, x, y) values( '충남', '홍성', 37, 45 );
insert into region_img_point(sido, region, x, y) values( '충남', '공주', 60, 49 );
insert into region_img_point(sido, region, x, y) values( '충남', '청양', 48, 53 );
insert into region_img_point(sido, region, x, y) values( '충남', '보령', 35, 60 );
insert into region_img_point(sido, region, x, y) values( '충남', '계룡', 69, 62 );
insert into region_img_point(sido, region, x, y) values( '충남', '부여', 49, 66 );
insert into region_img_point(sido, region, x, y) values( '충남', '논산', 65, 70 );
insert into region_img_point(sido, region, x, y) values( '충남', '금산', 82, 74 );
insert into region_img_point(sido, region, x, y) values( '충남', '서천', 41, 76 );
insert into region_img_point(sido, region, x, y) values( '충북', '제천', 60, 22 );
insert into region_img_point(sido, region, x, y) values( '충북', '충주', 45, 24 );
insert into region_img_point(sido, region, x, y) values( '충북', '단양', 73, 25 );
insert into region_img_point(sido, region, x, y) values( '충북', '음성', 28, 28 );
insert into region_img_point(sido, region, x, y) values( '충북', '진천', 20, 34 );
insert into region_img_point(sido, region, x, y) values( '충북', '증평', 28, 39 );
insert into region_img_point(sido, region, x, y) values( '충북', '괴산', 42, 40 );
insert into region_img_point(sido, region, x, y) values( '충북', '청주', 22, 49 );
insert into region_img_point(sido, region, x, y) values( '충북', '보은', 36, 58 );
insert into region_img_point(sido, region, x, y) values( '충북', '옥천', 32, 69 );
insert into region_img_point(sido, region, x, y) values( '충북', '영동', 41, 79 );



-- 우선 시군구에 따라 넣는다.
insert into region_sigungu_point (region_cd, region_nm_full, x, y)
select
    RSG.sigungu_cd, concat(TRL.sido, ' ', TRL.region), TRL.x, TRL.y
from region_sigungu RSG
         inner join region_sido RS ON RS.sido_cd = RSG.sido_cd
         inner join region_img_point TRL ON TRL.sido = RS.sido_short_nm
where RSG.sigungu_nm_full like concat(TRL.sido, ' ', TRL.region, '%')
;

-- 세종시는 따로 넣어준다.
insert into region_sigungu_point (region_cd, region_nm_full, x, y)
select
    case
        when TRL.region = '조치원읍' then '361102'
        when TRL.region = '연기면' then '3611031'
        when TRL.region = '연동면' then '3611032'
        when TRL.region = '부강면' then '3611033'
        when TRL.region = '금남면' then '3611034'
        when TRL.region = '장군면' then '3611035'
        when TRL.region = '연서면' then '3611036'
        when TRL.region = '전의면' then '3611037'
        when TRL.region = '전동면' then '3611038'
        when TRL.region = '소정면' then '3611039'
        else '361101'
        end,
    concat(TRL.sido, ' ', TRL.region), TRL.x, TRL.y
from region_img_point TRL
where sido = '세종특별자치시'
;

insert into region_sigungu_point (region_cd, region_nm_full, x, y)
select '361101', '세종특별자치시', avg(x) as x, avg(y) as y
from region_sigungu_point RSP
where RSP.region_cd IN (
   '3611031', '3611034'
);
;


-- 중복 제거
insert into region_sigungu_point (region_cd, region_nm_full, x, y)
select region_cd, region_nm_full, x, y
from (
         select SUBSTR( RSP.region_cd, 1 , 4) as region_cd, region_nm_full, x, y
         from region_sigungu_point RSP
         where exists (
                       select region_nm_full
                       from region_sigungu_point RSP2
                       where RSP2.region_nm_full = RSP.region_nm_full
                       group by region_nm_full
                       having count(*) > 1
                   )
     ) FILT1
group by region_cd, region_nm_full, x, y
;


delete from region_sigungu_point
where region_cd in (
                    '41111',
                    '41113',
                    '41115',
                    '41117',
                    '41131',
                    '41133',
                    '41135',
                    '41171',
                    '41173',
                    '41271',
                    '41273',
                    '41281',
                    '41285',
                    '41287',
                    '41461',
                    '41463',
                    '41465',
                    '43111',
                    '43112',
                    '43113',
                    '43114',
                    '44131',
                    '44133',
                    '45111',
                    '45113',
                    '47111',
                    '47113',
                    '48121',
                    '48123',
                    '48125',
                    '48127',
                    '48129'
    )
;
