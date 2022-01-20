package kr.co.hulan.aas.common.code;

public enum OtpUsage {
  LOGIN(1, "사용자 로그인"),
      ;

  private int code;
  private String name;
  OtpUsage(int code, String name){
    this.code = code;
    this.name = name;
  }
  public int getCode() {
    return code;
  }
  public String getName(){
    return name;
  }
  public static OtpUsage get(Integer code){
    if( code != null ){
      for(OtpUsage item : values()){
        if( item.getCode() == code ){
          return item;
        }
      }
    }
    return null;
  }
}
