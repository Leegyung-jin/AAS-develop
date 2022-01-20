package kr.co.hulan.aas.common.code;

public enum CctvKind {
  NORMAL(0, "일반"),
  INTELLI_VIX(1,  "IntelliVix"),
  ;
  private int code;
  private String name;
  CctvKind(int code, String name){
    this.code = code;
    this.name = name;
  }
  public int getCode() {
    return code;
  }
  public String getName(){
    return name;
  }
  public static CctvKind get(int code){
    for(CctvKind item : values()){
      if( item.getCode() == code ){
        return item;
      }
    }
    return null;
  }
}
