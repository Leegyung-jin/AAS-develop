Spring boot Security Oauth2 & API
==========================================
##### 최초 작성일 : 2019-07-01
##### 최초 작성자 : 이병무

version
------
- spring boot : 2.1.6.RELEASE
- java : 1.8
- spring-security-oauth2-autoconfigure : 2.1.4.RELEASE
- mybatis : 3.4.5
- mybatis-spring : 1.3.1
- mysql : 8.5.23

schema
------
```
create table oauth_client_details (
  client_id VARCHAR(191) PRIMARY KEY,
  resource_ids VARCHAR(256),
  client_secret VARCHAR(256),
  scope VARCHAR(256),
  authorized_grant_types VARCHAR(256),
  web_server_redirect_uri VARCHAR(256),
  authorities VARCHAR(256),
  access_token_validity INTEGER,
  refresh_token_validity INTEGER,
  additional_information VARCHAR(4096),
  autoapprove VARCHAR(256)
);

# DB로 토큰을 저장하여 사용할 경우 생성(AuthorizationServerConfiguration.java)
create table oauth_access_token (
  token_id VARCHAR(256),
  token mediumblob,
  authentication_id VARCHAR(191),
  user_name VARCHAR(256),
  client_id VARCHAR(256),
  authentication mediumblob,
  refresh_token VARCHAR(256)
);
# DB로 토큰을 저장하여 사용할 경우 생성(AuthorizationServerConfiguration.java)
# 만료된 토큰을 삭제하기 위해서 reg_dt 생성
create table oauth_refresh_token (
  token_id VARCHAR(256),
  token mediumblob,
  authentication mediumblob,
  reg_dt DATETIME DEFAULT CURRENT_TIMESTAMP
);
```

data
------
##### 패스워드는 PasswordEncodeTest 로 확인
- pass-admin = *6E08455D8EC382D58829487654FEDA76ABAA5E3A
```
INSERT INTO oauth_client_details (client_id, client_secret, scope, authorized_grant_types, access_token_validity, refresh_token_validity)
VALUES ('hulan', '*6E08455D8EC382D58829487654FEDA76ABAA5E3A', 'admin', 'authorization_code,password,refresh_token', 36000, 2592000);
```

OAuth
-----
**1.Tomcat Run**
##### 아래 local 테스트 url는 포트를 8100으로 설정하여 테스트하였다.

**2. 로그인 (token 발급)**
```
curl -u hulan:pass-admin -X POST http://localhost:8100/aas/oauth/token -d grant_type=password -d username=admin -d password=lan0628
```

**3. 토큰으로 회원 정보 확인**
```
curl -u hulan:pass-admin -X POST http://localhost:8100/oauth/check_token -d token=[access_token]
```

**4. refresh_token 을 통한 token 재발급**
```
curl -u hulan:pass-admin -X POST http://localhost:8100/oauth/token -d grant_type=refresh_token -d refresh_token=[refresh_token]
```

참고
----
- DB는 Password 는 mysql 암호화로 되어 있어서 java 에서 동일하게 암호화 할 수 있도록 변경함.
- CustomRevokeTokenEndpoint 로 OAuth 토큰 삭제(로그아웃) 기능 추가.

확인필요
--------
oauth_client_details > client_secret
- 환경별(?)(admin 사용자, partner 사용자, 일반 web 사용자 등) 인증 key 값 정의

oauth_client_details > access_token_validity,  refresh_token_validity 
- 인증 key 값 별로 토큰 유효시간 정의

문제 발견(2019.06.12)
- 하나의 계정으로 각각 다른 브라우저로 로그인 할 경우 동일한 access_token 이 응답된다....이렇게 되면 refresh_token 으로 access_token 을 갱신 할 경우 문제가 발생하는데... 어떻게해야 각각 다른 access_token을 생성 할 것인가....
- 해결 완료 (CustomAuthenticationKeyGenerator 추가 & DataSourceConfig.tokenStore 수정) - 원복 할 일 있을 시 참고 


JPA Metamodel Intellij 적용 참고
----
https://intellij-support.jetbrains.com/hc/en-us/community/posts/206842215-Hibernate-JPA-2-Metamodel-generation

