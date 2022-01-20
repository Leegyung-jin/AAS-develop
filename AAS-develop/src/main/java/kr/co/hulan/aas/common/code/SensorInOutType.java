package kr.co.hulan.aas.common.code;

public enum SensorInOutType {

  IN(0, "접근"),
  OUT(1, "이탈"),
  DEVICE_IDLE(2, "단말 IDLE"),
  WORK_OFF(3, "외출/퇴근")
  ;

  private int code;
  private String name;
  SensorInOutType(int code, String name){
    this.code = code;
    this.name = name;
  }

  public int getCode() {
    return code;
  }
  public String getName(){
    return name;
  }
  public static SensorInOutType get(int code){
    for(SensorInOutType item : values()){
      if(code == item.getCode()){
        return item;
      }
    }
    return null;
  }
}
