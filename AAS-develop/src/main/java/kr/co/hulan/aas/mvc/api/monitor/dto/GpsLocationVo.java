package kr.co.hulan.aas.mvc.api.monitor.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="GpsLocationVo", description="GPS 정보")
public class GpsLocationVo {
  @ApiModelProperty(notes = "경도")
  private Double longitude;
  @ApiModelProperty(notes = "위도")
  private Double latitude;
  @ApiModelProperty(notes = "고도")
  private Double altitude;
  @ApiModelProperty(notes = "정확도")
  private Double accuracy;
  @ApiModelProperty(notes = "속도")
  private Double speed;
  @ApiModelProperty(notes = "방위")
  private Double bearing;
  @ApiModelProperty(notes = "배터리 잔량")
  private Double battery;
  @ApiModelProperty(notes = "측정 시간")
  private java.util.Date measureTime;
}
