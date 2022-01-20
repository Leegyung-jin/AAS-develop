package kr.co.hulan.aas.infra.weather;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WeatherGridXY {

  private int nx;
  private int ny;

  @Override
  public String toString() {
    return "{ nx=" +  nx +", ny=" + ny + "}";
  }
}
