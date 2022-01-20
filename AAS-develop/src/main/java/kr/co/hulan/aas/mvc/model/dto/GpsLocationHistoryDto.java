package kr.co.hulan.aas.mvc.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@ApiModel(value="GpsLocationHistoryDto", description="GPS 위치 이력 정보")
@AllArgsConstructor
@NoArgsConstructor
public class GpsLocationHistoryDto {

  @ApiModelProperty(notes = "GPS 위치 이력 넘버")
  private Long seq;
  @ApiModelProperty(notes = "현장 아이디")
  private String wpId;
  @ApiModelProperty(notes = "협력사 아이디")
  private String coopMbId;
  @ApiModelProperty(notes = "근로자 아이디")
  private String mbId;
  @ApiModelProperty(notes = "단말 아이디")
  private String deviceId;
  @ApiModelProperty(notes = "제공수단")
  private String provider;
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
  @ApiModelProperty(notes = "최근 센서")
  private Integer sensorScan;
  @ApiModelProperty(notes = "측정 시간")
  private java.util.Date measureTime;
  @ApiModelProperty(notes = "아이콘 URL")
  private String iconUrl;
}
