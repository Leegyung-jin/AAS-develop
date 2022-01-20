package kr.co.hulan.aas.infra.weather;


import java.util.Comparator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@Data
@AllArgsConstructor
public class RequestWeatherCommand {

  public static enum MODE {
    UltraSrtNcst,
    VilageFcst
  }

  private MODE mode = MODE.UltraSrtNcst;

  private String wpId;
  private WeatherGridXY point;

  private String address;
  private Double lat;
  private Double lon;

  public RequestWeatherCommand(MODE mode, String wpId){
    this.mode = mode;
    this.wpId = wpId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    RequestWeatherCommand that = (RequestWeatherCommand) o;

    return new EqualsBuilder()
        .append(wpId, that.wpId)
        .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
        .append(wpId)
        .toHashCode();
  }
}
