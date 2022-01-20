package kr.co.hulan.aas.infra.airkorea.station.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="AirEnvironmentRequest", description="법정동명 기반 TM 좌표 조회")
public class TmCoordinateRequest {

  @ApiModelProperty(notes = "페이지 번호")
  private Integer pageNo = 1;
  @ApiModelProperty(notes = "페이지내 결과 수")
  private Integer numOfRows = 10;
  @ApiModelProperty(notes = "읍면동명")
  private String umdName;
}
