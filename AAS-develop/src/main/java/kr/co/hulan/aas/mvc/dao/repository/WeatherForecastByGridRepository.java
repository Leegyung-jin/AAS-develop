package kr.co.hulan.aas.mvc.dao.repository;

import kr.co.hulan.aas.mvc.model.domain.weather.WeatherForecastByGrid;
import kr.co.hulan.aas.mvc.model.domain.weather.WeatherForecastByGridKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeatherForecastByGridRepository extends
    JpaRepository<WeatherForecastByGrid, WeatherForecastByGridKey> {

}
