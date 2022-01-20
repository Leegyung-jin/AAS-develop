package kr.co.hulan.aas.mvc.dao.mapper;

import java.util.List;
import kr.co.hulan.aas.mvc.api.openapi.vo.DailyWeatherForecastInfo;
import kr.co.hulan.aas.mvc.api.openapi.vo.HourWeatherForecastInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WeatherForecastDao {

  public List<String> findWeatherPointRelatedWorkplace();

  public List<DailyWeatherForecastInfo> findWeekWeatherForecast(@Param(value = "wpId") String wpId);

  public List<HourWeatherForecastInfo> findWeatherForecastPerHour(@Param(value = "wpId") String wpId, @Param(value = "forecastDay") String forecastDay);
}
