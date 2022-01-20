package kr.co.hulan.aas.common.code;

import org.apache.commons.lang3.StringUtils;

public enum AdminMsgAlarmGrade {
  CAUTION(1, "주의"),
  ALERT(2,  "경계"),
  FATAL(3,  "심각"),
  ;

  private int code;
  private String name;
  AdminMsgAlarmGrade(int code, String name){
    this.code = code;
    this.name = name;
  }
  public int getCode() {
    return code;
  }
  public String getName(){
    return name;
  }
  public static AdminMsgAlarmGrade get(Integer code){
    if( code != null ){
      for(AdminMsgAlarmGrade item : values()){
        if( item.getCode() == code ){
          return item;
        }
      }
    }
    return null;
  }
}
