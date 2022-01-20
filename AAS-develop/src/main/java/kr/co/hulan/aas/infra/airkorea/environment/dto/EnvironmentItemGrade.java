package kr.co.hulan.aas.infra.airkorea.environment.dto;

import org.apache.commons.lang3.StringUtils;

public enum EnvironmentItemGrade {
  GOOD(1, "좋음"),
  NORMAL(2,  "보틍"),
  BAD(2,  "보틍"),
  VERY_BAD(3, "매우 나쁨")
  ;
  private int code;
  private String name;
  EnvironmentItemGrade(int code, String name){
    this.code = code;
    this.name = name;
  }
  public int getCode() {
    return code;
  }
  public String getName(){
    return name;
  }
  public static EnvironmentItemGrade get(Integer code){
    if( code != null ){
      for(EnvironmentItemGrade item : values()){
        if( item.getCode() == code ){
          return item;
        }
      }
    }
    return null;
  }
  public static EnvironmentItemGrade get(String code){
    if(StringUtils.isNotBlank(code)){
      for(EnvironmentItemGrade item : values()){
        if( StringUtils.equals(""+item.getCode(), code )){
          return item;
        }
      }
    }
    return null;
  }
}
