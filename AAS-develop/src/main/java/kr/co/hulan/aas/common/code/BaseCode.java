package kr.co.hulan.aas.common.code;

public enum BaseCode implements I18nEnum {
  SUCCESS(0, "Success"),
  ERR_ETC_EXCEPTION(10000, "기타 오류"),
  ERR_ARG_IS_WRONG(10001, "Request 파라미터 오류"),
  ERR_SQL_EXCEPTION(10002, "SQL 오류"),
  ERR_API_EXCEPTION(10003, "API 통신 오류"),
  ERR_TOKEN_EXCEPTION(10004, "TOKEN 정보가 없습니다."),
  ERR_GRADE_EXCEPTION(10005, "해당 API 권한이 없습니다."),
  ERR_OTP_SELECT_PHONE(20101, "OTP 수신단말을 선택하여주세요"),
  ERR_OTP_FIRED(20102, "OTP 전송하였습니다."),
  ERR_DETAIL_EXCEPTION(30002, "상세 내용이 없습니다. Key 값을 확인해 주세요."),
  ERR_NONE_ALERT_EXCEPTION(40000, "alert 을 노출하지 않는 오류입니다."),
  ERR_OAUTH_INVALID_GRANT(40001, "OAuth2 인증 에러"),
  ERR_OAUTH_ACCOUNT_LOCKED(40002, "계정이 잠겼습니다"),
  ERR_OAUTH_DISABLED_USER(40003, "유효하지 않은 사용자입니다"),
  ERR_OAUTH_CREDENTIAL_EXPIRED(40004, "비밀번호 유효기간이 만료되었습니다"),
  ERR_OAUTH_USER_NOT_FOUND(40005, "아이디/비밀번호가 일치하지 않습니다."),
  ERR_OAUTH_BAD_CREDENTIALS(40006, "아이디/비밀번호가 일치하지 않습니다."),
  ERR_INCORRECT_PASSWORD(40007, "비밀번호가 일치하지 않습니다."),
  ERR_NOT_EXISTS_USER(40008, "존재하지 않는 사용자입니다."),
  ERR_NOT_AUTHORIZED(40301, "권한이 없습니다."),
  ;


  private int code;
  private String message;

  BaseCode(int code, String msg) {
    this.code = code;
    this.message = msg;
  }

  public int code() {
    return this.code;
  }

  public String message() {
    return this.message;
  }

}
