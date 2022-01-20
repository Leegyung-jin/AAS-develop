package kr.co.hulan.aas.mvc.api.bls.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@ApiModel(value="FloorSituations", description="현장 빌딩 층 상황")
@AllArgsConstructor
@NoArgsConstructor
public class FloorSituations {

  @ApiModelProperty(notes = "빌딩 넘버(SEQ)")
  private Long buildingNo;

  @ApiModelProperty(notes = "빌딩명")
  private String buildingName;

  @ApiModelProperty(notes = "빌딩 층")
  private Integer floor;

  @ApiModelProperty(notes = "빌딩 층명")
  private String floorName;

  @ApiModelProperty(notes = "빌딩 층 타입. 1: 층, 1000: 옥상, 2000: 갱폼 ")
  private Integer floorType;

  @ApiModelProperty(notes = "단면도 층/구획 표시 x 축 좌표 ")
  private Integer crossSectionGridX;

  @ApiModelProperty(notes = "단면도 층/구획 표시 y 축 좌표")
  private Integer crossSectionGridY;

  @ApiModelProperty(notes = "단면도 층/구획 정보 표시 x 축 좌표 ")
  private Integer boxX;

  @ApiModelProperty(notes = "단면도 층/구획 정보 표시 y 축 좌표")
  private Integer boxY;

  @ApiModelProperty(notes = "근로자 수")
  private Integer workerCount;

  @ApiModelProperty(notes = "위험 지역 접근 근로자 수")
  private Integer dangerWorkerCount;

  @ApiModelProperty(notes = "작업진행 여부. 0:미진행, 1:진행")
  private Integer activated;

}
