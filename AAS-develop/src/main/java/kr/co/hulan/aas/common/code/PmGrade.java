package kr.co.hulan.aas.common.code;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

public enum PmGrade {

  GOOD(1, "좋음"),
  NORMAL(2, "보통"),
  BAD(3, "나쁨"),
  VERY_BAD(4,"매우 나쁨")
      ;

  private int code;
  private String name;
  PmGrade(int code, String name){
    this.code = code;
    this.name = name;
  }

  public int getCode() {
    return code;
  }
  public String getName(){
    return name;
  }
  public static PmGrade get(Integer code){
    if( code != null ){
      for(PmGrade item : values()){
        if(code == item.getCode()){
          return item;
        }
      }
    }
    return null;
  }

  public static PmGrade get(String code){
    if(StringUtils.isNotBlank(code) ){
      for(PmGrade item : values()){
        if(item.getCode() == NumberUtils.toInt(code, 0)){
          return item;
        }
      }
    }
    return null;
  }

}
