package kr.co.hulan.aas.common.code;

import org.apache.commons.lang3.StringUtils;

public enum UiComponentCode {
  ENTER_GATE("ENTER_GATE", "출근 시스템"),
  CCTV("CCTV",  "CCTV"),
  TILT_SENSOR("TILT_SENSOR", "기울기 센서"),
  ;

  private String code;
  private String name;
  UiComponentCode(String code, String name){
    this.code = code;
    this.name = name;
  }
  public String getCode() {
    return code;
  }
  public String getName(){
    return name;
  }
  public static UiComponentCode get(String code){
    if( StringUtils.isNotBlank(code)){
      for(UiComponentCode item : values()){
        if( StringUtils.equals(item.getCode() , code) ){
          return item;
        }
      }
    }
    return null;
  }
}
