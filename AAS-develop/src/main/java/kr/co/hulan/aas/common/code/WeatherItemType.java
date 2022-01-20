package kr.co.hulan.aas.common.code;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

public enum WeatherItemType {
  TEMPERATURE("T1H", "기온", "℃"){
    public String getValueName(String value){ return value + getUnit(); }
  },
  HUMIDITY("REH", "습도", "%") {
    public String getValueName(String value){ return value + getUnit(); }
  },
  PRECIPITATION_FORM("PTY", "강수형태", ""){
    public String getValueName(String value){
      int ptyValue = NumberUtils.toInt(value);
      PrecipitationForm pfrom = PrecipitationForm.get(ptyValue);
      return pfrom != null ? pfrom.getName() : "";
    }
  },
  PRECIPITATION("RN1", "1시간 강수량", "mm"){
    public String getValueName(String value){ return value + getUnit(); }
  },
  WIND_SPEED("WSD", "풍속", "m/s"){
    public String getValueName(String value){ return value + getUnit(); }
  },
  WIND_DIRECTION("VEC", "풍향", "°"){
    public String getValueName(String value){ return value + getUnit(); }
  },
  SKY("SKY", "하늘상태", ""){
    public String getValueName(String value){
      int skyValue = NumberUtils.toInt(value);
      return skyValue == 4 ? "흐림" :
             skyValue == 3 ? "구름많음" : "맑음";
    }
  },
  RAINFALL("POP", "강수확률", "%"){
    public String getValueName(String value){ return value + getUnit(); }
  },
  PRECIPITATION_PCP("PCP", "1시간 강수량", ""){
    public String getValueName(String value){ return value + getUnit(); }
  },
  SNOW("SNO", "1시간 신적설", ""){
    public String getValueName(String value){ return value + getUnit(); }
  },
  TEMPERATURE_TMP("TMP", "1시간 기온", ""){
    public String getValueName(String value){ return value + getUnit(); }
  },
  ;

  private String code;
  private String name;
  private String unit;
  WeatherItemType(String code, String name, String unit){
    this.code = code;
    this.name = name;
    this.unit = unit;
  }
  public String getCode(){ return code; }
  public String getName(){ return name; }
  public String getUnit(){ return unit; }

  public static WeatherItemType get(String code){
    for(WeatherItemType item : values()){
      if( StringUtils.equals(code, item.getCode())){
        return item;
      }
    }
    return null;
  }
  public abstract String getValueName(String value);


}
