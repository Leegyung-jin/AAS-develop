package kr.co.hulan.aas.mvc.dao.repository;

import kr.co.hulan.aas.mvc.model.domain.weather.WeatherForecastByRegion;
import kr.co.hulan.aas.mvc.model.domain.weather.WeatherForecastByRegionKey;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeatherForecastByRegionRepository extends JpaRepository<WeatherForecastByRegion, WeatherForecastByRegionKey> {

}
