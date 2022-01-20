package kr.co.hulan.aas.mvc.api.bls.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@ApiModel(value="FloorSituationDto", description="현장 빌딩 내 층별 상황 정보")
@AllArgsConstructor
@NoArgsConstructor
public class FloorSituationDto {

  @ApiModelProperty(notes = "빌딩 넘버(SEQ)")
  private Long buildingNo;

  @ApiModelProperty(notes = "빌딩명")
  private String buildingName;

  @ApiModelProperty(notes = "빌딩 층")
  private Integer floor;

  @ApiModelProperty(notes = "빌딩 층명")
  private String floorName;

  @ApiModelProperty(notes = "근로자 수")
  private Integer workerCount;
}
