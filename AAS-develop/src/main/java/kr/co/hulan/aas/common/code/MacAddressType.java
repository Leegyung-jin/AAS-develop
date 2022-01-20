package kr.co.hulan.aas.common.code;

import kr.co.hulan.aas.common.validator.CodeForEnum;

public enum MacAddressType implements CodeForEnum {
  NORMAL(1, "일반"),
  LORA_GATEWAY(2,  "LoRa")
      ;
  private int code;
  private String name;
  MacAddressType(int code, String name){
    this.code = code;
    this.name = name;
  }
  public int getCode() {
    return code;
  }
  public String getName(){
    return name;
  }
  public static MacAddressType get(int code){
    for(MacAddressType item : values()){
      if( item.getCode() == code ){
        return item;
      }
    }
    return null;
  }
}
