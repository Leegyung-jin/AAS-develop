package kr.co.hulan.aas.mvc.model.domain.weather;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeatherForecastByRegionKey implements Serializable {
  private String wfrCd;
  private String forecastDay;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    WeatherForecastByRegionKey that = (WeatherForecastByRegionKey) o;

    return new EqualsBuilder()
        .append(wfrCd, that.wfrCd)
        .append(forecastDay, that.forecastDay)
        .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
        .append(wfrCd)
        .append(forecastDay)
        .toHashCode();
  }
}
