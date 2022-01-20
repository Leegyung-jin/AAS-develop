package kr.co.hulan.aas.common.code;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

public enum WeatherForecastForm {
  SUNNY(SkyForm.SUNNY, PrecipitationForm.NONE, "맑음", "01"),
  RAIN(SkyForm.SUNNY, PrecipitationForm.RAIN, "비", "05"),
  RAIN_SNOW(SkyForm.SUNNY, PrecipitationForm.RAIN_SNOW, "비/눈", "06"),
  SNOW(SkyForm.SUNNY, PrecipitationForm.SNOW, "눈", "08"),
  SHOWER(SkyForm.SUNNY, PrecipitationForm.SHOWER, "소나기", "09"),
  RAINDROP(SkyForm.SUNNY, PrecipitationForm.RAINDROP, "빗방울", "10"),
  RAINDROP_SNOWDRIFTING(SkyForm.SUNNY, PrecipitationForm.RAIN_SNOW, "빗방울/눈날림", "11"),
  SNOWDRIFTING(SkyForm.SUNNY, PrecipitationForm.RAIN_SNOW, "눈날림", "12"),

  MANY_CLOUDS(SkyForm.MANY_CLOUDS, PrecipitationForm.NONE, "구름많음", "03"),
  MANY_CLOUDS_RAIN(SkyForm.MANY_CLOUDS, PrecipitationForm.RAIN, "구름많고 비", "05"),
  MANY_CLOUDS_RAIN_SNOW(SkyForm.MANY_CLOUDS, PrecipitationForm.RAIN_SNOW, "구름많고 비/눈", "06"),
  MANY_CLOUDS_SNOW(SkyForm.MANY_CLOUDS, PrecipitationForm.SNOW, "구름많고 눈", "08"),
  MANY_CLOUDS_SHOWER(SkyForm.MANY_CLOUDS, PrecipitationForm.SHOWER, "구름많고 소나기", "09"),
  MANY_CLOUDS_RAINDROP(SkyForm.MANY_CLOUDS, PrecipitationForm.RAINDROP, "구름많고 빗방울", "10"),
  MANY_CLOUDS_RAINDROP_SNOWDRIFTING(SkyForm.MANY_CLOUDS, PrecipitationForm.RAIN_SNOW, "구름많고 빗방울/눈날림", "11"),
  MANY_CLOUDS_SNOWDRIFTING(SkyForm.MANY_CLOUDS, PrecipitationForm.RAIN_SNOW, "구름많고 눈날림", "12"),

  CLOUDY(SkyForm.CLOUDY, PrecipitationForm.NONE, "흐림", "04"),
  CLOUDY_RAIN(SkyForm.CLOUDY, PrecipitationForm.RAIN, "흐리고 비", "05"),
  CLOUDY_RAIN_SNOW(SkyForm.CLOUDY, PrecipitationForm.RAIN_SNOW, "흐리고 비/눈", "06"),
  CLOUDY_SNOW(SkyForm.CLOUDY, PrecipitationForm.SNOW, "흐리고 눈", "08"),
  CLOUDY_SHOWER(SkyForm.CLOUDY, PrecipitationForm.SHOWER, "흐리고 소나기", "09"),
  CLOUDY_RAINDROP(SkyForm.CLOUDY, PrecipitationForm.RAINDROP, "흐리고 빗방울", "10"),
  CLOUDY_RAINDROP_SNOWDRIFTING(SkyForm.CLOUDY, PrecipitationForm.RAIN_SNOW, "흐리고 빗방울/눈날림", "11"),
  CLOUDY_SNOWDRIFTING(SkyForm.CLOUDY, PrecipitationForm.RAIN_SNOW, "흐리고 눈날림", "12"),
  ;

  private SkyForm skyForm;
  private PrecipitationForm precipitationForm;
  private String name;
  private Integer code;
  private String weatherIconCode;
  WeatherForecastForm(SkyForm skyForm, PrecipitationForm precipitationForm, String name, String weatherIconCode){
    this.skyForm = skyForm;
    this.precipitationForm = precipitationForm;
    this.code = skyForm.getCode()*10 + precipitationForm.getCode();
    this.name = name;
    this.weatherIconCode = weatherIconCode;
  }
  public Integer getCode(){
    return code;
  }
  public String getName(){
    return name;
  }
  public String getWeatherIconCode(){
    return weatherIconCode;
  }
  public SkyForm getSkyForm(){
    return skyForm;
  }
  public PrecipitationForm getPrecipitationForm(){
    return precipitationForm;
  }
  public static WeatherForecastForm get(Integer code){
    if( code != null ){
      for(WeatherForecastForm item : values()){
        if(code == item.getCode()){
          return item;
        }
      }
    }
    return null;
  }

  public static WeatherForecastForm get(Integer skyFormCd, Integer precipitationFormCd){
    if( skyFormCd != null && precipitationFormCd != null  ){
      int code = skyFormCd*10 + precipitationFormCd;
      for(WeatherForecastForm item : values()){
        if(code == item.getCode()){
          return item;
        }
      }
    }
    return null;
  }

  public static WeatherForecastForm get(String skyFormCd, String precipitationFormCd){
    if( StringUtils.isNotBlank(skyFormCd)  && StringUtils.isNotBlank(precipitationFormCd)   ){
      SkyForm skyform = SkyForm.get(skyFormCd);
      int skyFormCode = skyform != null ? skyform.getCode() : NumberUtils.toInt(skyFormCd, 1);
      int code = skyFormCode*10 + NumberUtils.toInt(precipitationFormCd);
      for(WeatherForecastForm item : values()){
        if(code == item.getCode()){
          return item;
        }
      }
    }
    return null;
  }

  public static WeatherForecastForm getByName(String name){
    if(StringUtils.isNotBlank(name) ){
      for(WeatherForecastForm item : values()){
        if( StringUtils.equals(name, item.getName())){
          return item;
        }
      }
    }
    return null;
  }

}
