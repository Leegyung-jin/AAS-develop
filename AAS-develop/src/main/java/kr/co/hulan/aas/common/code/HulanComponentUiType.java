package kr.co.hulan.aas.common.code;

import kr.co.hulan.aas.common.validator.CodeForEnum;

public enum HulanComponentUiType implements CodeForEnum {
  MAIN_UI(1, "메인화면 유형"),
  COMPONENT_UI(2,  "컴포넌트 유형")
  ;
  private int code;
  private String name;
  HulanComponentUiType(int code, String name){
    this.code = code;
    this.name = name;
  }
  public int getCode() {
    return code;
  }
  public String getName(){
    return name;
  }
  public static HulanComponentUiType get(int code){
    for(HulanComponentUiType item : values()){
      if( item.getCode() == code ){
        return item;
      }
    }
    return null;
  }
}
