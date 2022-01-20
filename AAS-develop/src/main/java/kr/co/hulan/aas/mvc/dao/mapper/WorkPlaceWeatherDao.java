package kr.co.hulan.aas.mvc.dao.mapper;

import kr.co.hulan.aas.infra.weather.WeatherInfo;
import kr.co.hulan.aas.mvc.api.hicc.vo.main.HiccWeatherInfoVo;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkPlaceWeatherDao {

  public WeatherInfo findWeather(String wpId);

  public HiccWeatherInfoVo findHiccWeatherInfo(String wpId);
}
