package kr.co.hulan.aas.infra.weather;

import java.util.Date;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.data.geo.Point;

@Data
public class WeatherCacheInfo {

  private WeatherInfo weatherInfo;
  private Date cacheDate;

  public WeatherCacheInfo(WeatherInfo weatherInfo){
    this.weatherInfo = weatherInfo;
    this.cacheDate = new Date();
  }

  public boolean isExpired(long limitTime){
    return System.currentTimeMillis() - cacheDate.getTime() > limitTime;
  }

}
