package kr.co.hulan.aas.mvc.api.building.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@ApiModel(value="SensorBuildingSituationDto", description="센서 빌딩 위치 현황 정보 정보")
@AllArgsConstructor
@NoArgsConstructor
public class SensorBuildingSituationDto {

  @ApiModelProperty(notes = "현장 아이디")
  private String wpId;

  @ApiModelProperty(notes = "현장명")
  private String wpName;

  @ApiModelProperty(notes = "빌딩 넘버(SEQ)")
  private Long buildingNo;

  @ApiModelProperty(notes = "빌딩명")
  private String buildingName;

  @ApiModelProperty(notes = "빌딩 층")
  private Integer floor;

  @ApiModelProperty(notes = "층명")
  private String floorName;

  @ApiModelProperty(notes = "층 내 할당된 센서 수")
  private Integer sensorCnt;


}
