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
public class WeatherForecastByGridKey implements Serializable {
  private Integer nx;
  private Integer ny;
  private String forecastDay;
  private Integer forecastHour;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    WeatherForecastByGridKey that = (WeatherForecastByGridKey) o;

    return new EqualsBuilder()
        .append(nx, that.nx)
        .append(ny, that.ny)
        .append(forecastDay, that.forecastDay)
        .append(forecastHour, that.forecastHour)
        .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
        .append(nx)
        .append(ny)
        .append(forecastDay)
        .append(forecastHour)
        .toHashCode();
  }
}
