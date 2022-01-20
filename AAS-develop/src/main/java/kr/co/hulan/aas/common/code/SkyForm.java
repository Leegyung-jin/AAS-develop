package kr.co.hulan.aas.common.code;

import org.apache.commons.lang3.StringUtils;

public enum SkyForm {
  SUNNY(1, "맑음"),
  MANY_CLOUDS(3, "구름많음"),
  CLOUDY(4, "흐림"),
  ;

  private int code;
  private String name;
  SkyForm(int code, String name){
    this.code = code;
    this.name = name;
  }

  public int getCode() {
    return code;
  }
  public String getName(){
    return name;
  }
  public String getForecastName(PrecipitationForm precipitation){
    if( precipitation == null || precipitation == PrecipitationForm.NONE ){
      return getName();
    }
    else if( this == SUNNY ) {
      return precipitation.getName();
    }
    else if( this == MANY_CLOUDS ) {
      return "구름많고 "+precipitation.getName();
    }
    else if( this == CLOUDY ) {
      return "흐리고 "+precipitation.getName();
    }
    return getName()+" "+precipitation.getName();
  }
  public static SkyForm get(Integer code){
    if( code != null ){
      for(SkyForm item : values()){
        if(code == item.getCode()){
          return item;
        }
      }
    }
    return null;
  }

  public static SkyForm get(String code){
    if( code != null ){
      for(SkyForm item : values()){
        if( StringUtils.equals(code , ""+item.getCode())
          || StringUtils.equals(code , "DB0"+item.getCode())  ){
          return item;
        }
      }
    }
    return null;
  }


}
