package kr.co.hulan.aas.mvc.api.building.controller.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="UpdateSensorBuildingLocationRequest", description="센서 빌딩 정보 등록/수정 요청")
public class UpdateSensorBuildingLocationRequest {

  @NotNull
  @ApiModelProperty(notes = "센서 아이디", required = true)
  private Integer siIdx;

  @NotNull
  @ApiModelProperty(notes = "빌딩 넘버(SEQ)", required = true)
  private Long buildingNo;

  @NotNull
  @ApiModelProperty(notes = "빌딩 층", required = true)
  private Integer floor;

  @NotNull
  @ApiModelProperty(notes = "x 축 좌표", required = true)
  private Integer gridX;

  @NotNull
  @ApiModelProperty(notes = "y 축 좌표", required = true)
  private Integer gridY;
}
