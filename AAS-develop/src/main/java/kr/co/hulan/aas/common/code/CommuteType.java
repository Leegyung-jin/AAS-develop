package kr.co.hulan.aas.common.code;

import org.apache.commons.lang3.StringUtils;

public enum CommuteType {
  QR_ENTER("qr", "QR 출근"),
  APP_ENTER("app",  "APP(비콘) 출근"),
      ;
  private String code;
  private String name;
  CommuteType(String code, String name){
    this.code = code;
    this.name = name;
  }
  public String getCode() {
    return code;
  }
  public String getName(){
    return name;
  }
  public static CommuteType get(String code){
    if( StringUtils.isNotBlank(code)){
      for(CommuteType item : values()){
        if( StringUtils.equals(item.getCode() , code ) ){
          return item;
        }
      }
    }
    return null;
  }
}
