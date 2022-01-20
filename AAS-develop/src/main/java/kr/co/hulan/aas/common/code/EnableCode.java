package kr.co.hulan.aas.common.code;

import kr.co.hulan.aas.common.validator.CodeForEnum;

public enum EnableCode implements CodeForEnum {

  DISABLED(0, "미사용"),
  ENABLED(1,  "사용"),
  ;
  private int code;
  private String name;
  EnableCode(int code, String name){
    this.code = code;
    this.name = name;
  }
  public int getCode() {
    return code;
  }
  public String getName(){
    return name;
  }
  public static EnableCode get(Integer code){
    if( code != null ){
      for(EnableCode item : values()){
        if( item.getCode() == code ){
          return item;
        }
      }
    }
    return null;
  }
}
