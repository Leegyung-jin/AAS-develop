package kr.co.hulan.aas.common.code;


import kr.co.hulan.aas.common.validator.CodeForEnum;

public enum NetworkVideoRecoderType implements CodeForEnum {

  NORMAL(1, "일반"),
  INTELLIVIX(2, "지능형(IntelliVix)")
  ;

  private int code;
  private String name;
  NetworkVideoRecoderType(int code, String name){
    this.code = code;
    this.name = name;
  }

  public int getCode() {
    return code;
  }
  public String getName(){
    return name;
  }
  public static NetworkVideoRecoderType get(Integer code){
    if( code != null ){
      for(NetworkVideoRecoderType item : values()){
        if(code == item.getCode()){
          return item;
        }
      }
    }
    return null;
  }

}
