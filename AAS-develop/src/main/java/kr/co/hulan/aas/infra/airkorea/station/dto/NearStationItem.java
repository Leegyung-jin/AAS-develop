package kr.co.hulan.aas.infra.airkorea.station.dto;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import lombok.Data;

@Data
public class NearStationItem {

  private Double tm;
  private String addr;
  private String stationName;

  public BigDecimal getDistance(){
    return new BigDecimal(tm, MathContext.DECIMAL64).setScale(1, RoundingMode.HALF_EVEN);
  }

}
