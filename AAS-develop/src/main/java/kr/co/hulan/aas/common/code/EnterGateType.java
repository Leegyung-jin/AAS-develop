package kr.co.hulan.aas.common.code;

import kr.co.hulan.aas.common.validator.CodeForEnum;

public enum EnterGateType implements CodeForEnum {
  FACE_RECOGNITION(1, "안면인식"),
  QR_READER(2, "QR Reader")
  ;
  private int code;
  private String name;

  EnterGateType(int code, String name){
    this.code = code;
    this.name = name;

  }
  public int getCode() {
    return code;
  }
  public String getName(){
    return name;
  }
  public static EnterGateType get(Integer code){
    if( code != null ){
      for(EnterGateType item : values()){
        if( item.getCode() == code ){
          return item;
        }
      }
    }
    return null;
  }
}
