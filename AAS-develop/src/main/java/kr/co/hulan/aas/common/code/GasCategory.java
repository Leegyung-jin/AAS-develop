package kr.co.hulan.aas.common.code;

public enum GasCategory {
  ENTRANCE_GATE(1, "온도"),
  ENTRANCE_DEVICE(2,  "산소"),
  FIXED_GPS(3,  "황화수소"),
  VIDEO_ANALYSIS_EQUIP(4,  "일산화탄소"),
  HAZARD_SENSOR(5,  "메탄(탄산)"),
  ;
  private int code;
  private String name;
  GasCategory(int code, String name){
    this.code = code;
    this.name = name;
  }
  public int getCode() {
    return code;
  }
  public String getName(){
    return name;
  }
  public static GasCategory get(int code){
    for(GasCategory item : values()){
      if( item.getCode() == code ){
        return item;
      }
    }
    return null;
  }
}
