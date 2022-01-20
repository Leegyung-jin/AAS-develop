package kr.co.hulan.aas.mvc.dao.repository;

import kr.co.hulan.aas.mvc.model.domain.weather.WeatherRegion;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WeatherRegionRepository extends JpaRepository<WeatherRegion, String> {

  // List<WeatherForecastRegion> findAll();
}
