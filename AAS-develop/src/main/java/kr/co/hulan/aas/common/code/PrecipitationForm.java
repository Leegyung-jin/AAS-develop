package kr.co.hulan.aas.common.code;

import org.apache.commons.lang3.StringUtils;

public enum PrecipitationForm {
  NONE(0, "없음"),
  RAIN(1, "비"),
  RAIN_SNOW(2, "비/눈"),
  SNOW(3, "눈"),
  SHOWER(4, "소나기"),
  RAINDROP(5, "빗방울"),
  RAINDROP_SNOWDRIFTING(6, "빗방울/눈날림"),
  SNOWDRIFTING(7, "눈날림")
  ;

  private int code;
  private String name;
  PrecipitationForm(int code, String name){
    this.code = code;
    this.name = name;
  }

  public int getCode() {
    return code;
  }
  public String getName(){
    return name;
  }
  public static PrecipitationForm get(Integer code){
    if( code != null ){
      for(PrecipitationForm item : values()){
        if(code == item.getCode()){
          return item;
        }
      }
    }
    return null;
  }
  public static PrecipitationForm get(String code){
    if( code != null ){
      for(PrecipitationForm item : values()){
        if( StringUtils.equals(code , ""+item.getCode())){
          return item;
        }
      }
    }
    return null;
  }
}
