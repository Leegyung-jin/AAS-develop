package kr.co.hulan.aas.common.code;

import org.apache.commons.lang3.StringUtils;

public enum AdminMsgType {
  BUILDING("100", "위험지역 접근"),
  GROUND("120",  "앱기능 해제")
  ;

  private String code;
  private String name;
  AdminMsgType(String code, String name){
    this.code = code;
    this.name = name;
  }
  public String getCode() {
    return code;
  }
  public String getName(){
    return name;
  }
  public static AdminMsgType get(int code){
    return get(""+code);
  }
  public static AdminMsgType get(String code){
    for(AdminMsgType item : values()){
      if( StringUtils.equals(item.getCode(), code) ){
        return item;
      }
    }
    return null;
  }
}
