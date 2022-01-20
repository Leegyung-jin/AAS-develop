package kr.co.hulan.aas.common.code;

import kr.co.hulan.aas.common.validator.CodeForEnum;

public enum NvrEventActionMethod implements CodeForEnum {

  CONFIRM_CHECK(1, "확인 완료"),
  SENSE_ERROR(2, "감지오류")
  ;
  private int code;
  private String name;
  NvrEventActionMethod(int code, String name){
    this.code = code;
    this.name = name;
  }

  public int getCode() {
    return code;
  }
  public String getName(){
    return name;
  }
  public static NvrEventActionMethod get(Integer code){
    if( code != null ){
      for(NvrEventActionMethod item : values()){
        if(code == item.getCode()){
          return item;
        }
      }
    }
    return null;
  }
}
