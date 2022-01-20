package kr.co.hulan.aas.infra.airkorea.station.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="NearStationRequest", description="근접측정소 목록 조회")
public class NearStationRequest {

  @ApiModelProperty(notes = "TM_X 좌표")
  private BigDecimal tmX;
  @ApiModelProperty(notes = "TM_Y 좌표")
  private BigDecimal tmY;

}
