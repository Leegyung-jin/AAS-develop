package kr.co.hulan.aas.infra.airkorea.station.dto;

import io.swagger.annotations.ApiModel;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="TmCoordinateItem", description="법정동명 기반 TM 좌표")
public class TmCoordinateItem {

  private String sidoName;
  private String sggName;
  private String umdName;
  private String tmX;
  private String tmY;

  public BigDecimal getDecimalTmX(){
    return new BigDecimal(tmX, MathContext.DECIMAL64).setScale(6, RoundingMode.HALF_EVEN);
  }

  public BigDecimal getDecimalTmY(){
    return new BigDecimal(tmY, MathContext.DECIMAL64).setScale(6, RoundingMode.HALF_EVEN);
  }

}
