package kr.co.hulan.aas.mvc.dao.repository;

import kr.co.hulan.aas.mvc.model.domain.weather.WeatherForecastByPoint;
import kr.co.hulan.aas.mvc.model.domain.weather.WeatherForecastByPointKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeatherForecastByPointRepository extends
    JpaRepository<WeatherForecastByPoint, WeatherForecastByPointKey> {

}
