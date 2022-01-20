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
public class WeatherForecastByPointKey implements Serializable {
  private String wfpCd;
  private String forecastDay;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    WeatherForecastByPointKey that = (WeatherForecastByPointKey) o;

    return new EqualsBuilder()
        .append(wfpCd, that.wfpCd)
        .append(forecastDay, that.forecastDay)
        .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
        .append(wfpCd)
        .append(forecastDay)
        .toHashCode();
  }
}
