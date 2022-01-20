package kr.co.hulan.aas.mvc.api.bls.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@ApiModel(value="BuildingSituation", description="현장 빌딩 상황")
@AllArgsConstructor
@NoArgsConstructor
public class BuildingSituation {

  @ApiModelProperty(notes = "현장 아이디")
  private String wpId;
  @ApiModelProperty(notes = "현장명")
  private String wpName;

  @ApiModelProperty(notes = "빌딩 넘버(SEQ)")
  private Long buildingNo;
  @ApiModelProperty(notes = "빌딩명")
  private String buildingName;
  @ApiModelProperty(notes = "지역 타입. 1: 빌딩, 2: 지상, 3: 지하")
  private Integer areaType;

  @ApiModelProperty(notes = "최고 층수")
  private Integer floorUpstair;
  @ApiModelProperty(notes = "최저 층수")
  private Integer floorDownstair;
  @ApiModelProperty(notes = "도면 위치 X 좌표")
  private Integer posX;
  @ApiModelProperty(notes = "도면 위치 Y 좌표")
  private Integer posY;
  @ApiModelProperty(notes = "건물정보란 도면 위치 X 좌표")
  private Integer boxX;
  @ApiModelProperty(notes = "건물정보란 도면 위치 Y 좌표")
  private Integer boxY;

  @ApiModelProperty(notes = "빌딩 내 근로자 수")
  private Integer workerCount;
  @ApiModelProperty(notes = "빌딩 내 갱폼 근로자 수")
  private Integer gangFormCount;
  @ApiModelProperty(notes = "빌딩 내 기준층 근로자 수")
  private Integer normalFloorCount;

  @ApiModelProperty(notes = "옥상 포함 여부. 0: 미포함, 1: 포함")
  private Integer containRoof;
  @ApiModelProperty(notes = "갱폼 포함 여부. 0: 미포함, 1: 포함")
  private Integer containGangform;


  @ApiModelProperty(notes = "층이 1개뿐인 경우 해당 층 넘버")
  private Integer oneStoriedFloor;
  @ApiModelProperty(notes = "층이 1개뿐인 경우 해당 층명")
  private String oneStoriedFloorName;

  @ApiModelProperty(notes = "총 층수")
  public Integer getTotalFloor(){
    if( floorUpstair != null
        && floorDownstair != null
        && floorUpstair >=  floorDownstair
    ){
      if( floorUpstair > 0 ){
        return floorDownstair > 0 ?
            floorUpstair - floorDownstair + 1 :
            floorDownstair < 0 ?
                floorUpstair - floorDownstair : floorUpstair;
      }
      else if( floorUpstair == 0 ){
        return -floorDownstair;
      }
      else {
        return Math.abs( floorUpstair - floorDownstair ) + 1;
      }
    }
    return null;
  }
}
