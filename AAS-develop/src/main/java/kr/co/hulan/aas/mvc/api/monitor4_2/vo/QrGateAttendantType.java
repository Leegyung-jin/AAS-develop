package kr.co.hulan.aas.mvc.api.monitor4_2.vo;

import kr.co.hulan.aas.common.validator.CodeForEnum;

public enum QrGateAttendantType implements CodeForEnum {

  QR_WORKER(1, "근로자-QR"),  // QR Reader 이용 출입 근로자
  ETC_WORKER(2,  "근로자-기타"),  // QR Reader 이용하지 않은 근로자
  QR_MANAGER(3,  "관리자"), // QR Reader 이용 출입 관리자
  ;
  private int code;
  private String name;
  QrGateAttendantType(int code, String name){
    this.code = code;
    this.name = name;
  }
  public int getCode() {
    return code;
  }
  public String getName(){
    return name;
  }
  public static QrGateAttendantType get(Integer code){
    if( code != null ){
      for(QrGateAttendantType item : values()){
        if( code == item.getCode() ){
          return item;
        }
      }
    }
    return null;
  }
}
