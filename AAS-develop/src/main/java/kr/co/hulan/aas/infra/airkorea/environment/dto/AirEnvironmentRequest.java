package kr.co.hulan.aas.infra.airkorea.environment.dto;

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
@ApiModel(value="AirEnvironmentRequest", description="시도별 실시간 측정 정보 조회")
public class AirEnvironmentRequest {

  @ApiModelProperty(notes = "페이지 번호")
  private Integer pageNo = 1;
  @ApiModelProperty(notes = "페이지내 결과 수")
  private Integer numOfRows = 10;
  @ApiModelProperty(notes = "시도명. 전국, 서울, 부산, 대구, 인천, 광주, 대전, 울산, 경기, 강원, 충북, 충남, 전북, 전남, 경북, 경남, 제주, 세종")
  private String sidoName = "전국";

}
