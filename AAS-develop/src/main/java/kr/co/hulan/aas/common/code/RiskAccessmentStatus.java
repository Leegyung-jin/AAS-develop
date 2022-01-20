package kr.co.hulan.aas.common.code;

public enum RiskAccessmentStatus {

  READY(0, "대기"),
  REQUEST_APPROVAL(1, "승인요청"),
  APPROVAL(2, "승인(완료)"),
  REJECT(3,"반려")
  ;

  private int code;
  private String name;
  RiskAccessmentStatus(int code, String name){
    this.code = code;
    this.name = name;
  }

  public int getCode() {
    return code;
  }
  public String getName(){
    return name;
  }
  public static RiskAccessmentStatus get(Integer code){
    if( code != null ){
      for(RiskAccessmentStatus item : values()){
        if(code == item.getCode()){
          return item;
        }
      }
    }
    return null;
  }
}
