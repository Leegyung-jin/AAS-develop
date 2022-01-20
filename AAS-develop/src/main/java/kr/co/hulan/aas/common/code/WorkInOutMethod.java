package kr.co.hulan.aas.common.code;

public enum WorkInOutMethod {
  MANAGER(1, "MANAGER"),
  QRCODE(2,  "Qrcode"),
  ENTER_GATE(3,  "안면인식"),
  BEACON(4,  "출입센서"),
  GEOFENCE(5,  "Geofence"),
  POLIGON(6,  "Poligon"),
  ETC(99,  "기타"),
      ;
  private int code;
  private String name;

  WorkInOutMethod(int code, String name){
    this.code = code;
    this.name = name;

  }
  public int getCode() {
    return code;
  }
  public String getName(){
    return name;
  }
  public static WorkInOutMethod get(Integer code){
    if( code != null ){
      for(WorkInOutMethod item : values()){
        if( item.getCode() == code ){
          return item;
        }
      }
    }
    return null;
  }
}
