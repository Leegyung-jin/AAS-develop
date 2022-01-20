package kr.co.hulan.aas.common.code;

public enum WindSpeedRangeType {
  NORMAL(1, "일반"),
  CAUTION(2, "경계"),
  STOP(3, "중지"),
  ;

  private int code;
  private String name;
  WindSpeedRangeType(int code, String name){
    this.code = code;
    this.name = name;
  }

  public int getCode() {
    return code;
  }
  public String getName(){
    return name;
  }
  public static WindSpeedRangeType get(Integer code){
    if( code != null ){
      for(WindSpeedRangeType item : values()){
        if(code == item.getCode()){
          return item;
        }
      }
    }
    return null;
  }
}
