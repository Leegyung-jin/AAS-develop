package kr.co.hulan.aas.common.code;

import kr.co.hulan.aas.common.validator.CodeForEnum;

public enum HulanComponentSiteType implements CodeForEnum {
  IMOS( 1, "IMOS"),
  HICC( 2, "통합관제"),
  ;

  private int code;
  private String name;
  HulanComponentSiteType(int code, String name){
    this.code = code;
    this.name = name;
  }

  public int getCode() {
    return code;
  }
  public String getName(){
    return name;
  }
  public static HulanComponentSiteType get(Integer code){
    if( code != null ){
      for(HulanComponentSiteType item : values()){
        if(code == item.getCode()){
          return item;
        }
      }
    }
    return null;
  }
}
