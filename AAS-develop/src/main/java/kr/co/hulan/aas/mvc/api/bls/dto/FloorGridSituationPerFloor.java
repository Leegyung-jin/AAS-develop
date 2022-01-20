package kr.co.hulan.aas.mvc.api.bls.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Builder
@Data
@ApiModel(value="FloorGridSituations", description="현장 빌딩 층 내 Grid 상황")
@AllArgsConstructor
@NoArgsConstructor
public class FloorGridSituationPerFloor {

  @ApiModelProperty(notes = "빌딩 넘버(SEQ)")
  private Long buildingNo;

  @ApiModelProperty(notes = "빌딩 층")
  private Integer floor;

  @ApiModelProperty(notes = "빌딩 층명")
  private String floorName;

  @ApiModelProperty(notes = "지역 타입. 1: 빌딩, 2: 지상, 3: 지하")
  private Integer areaType;

  @ApiModelProperty(notes = "빌딩 층도면")
  public String imageUrl;

  @ApiModelProperty(notes = "단면도 층 표시 x 축 좌표 ")
  private Integer crossSectionGridX;

  @ApiModelProperty(notes = "단면도 층 표시 y 축 좌표")
  private Integer crossSectionGridY;


  @ApiModelProperty(notes = "층 내 Grid 상황")
  List<FloorGridSituation> floorGridSituationList;

  @JsonIgnore
  public void addFloorGridSituation(FloorGridSituation situation){
    if( floorGridSituationList == null ){
      floorGridSituationList = new ArrayList<FloorGridSituation>();
    }
    floorGridSituationList.add(situation);
  }

  @ApiModelProperty(notes = "해당 층내 총인원")
  public int getTotalWorkerCount(){
    int totalCount = 0;
    if( floorGridSituationList != null ){
      for(FloorGridSituation situ : floorGridSituationList){
        totalCount += situ.getWorkerCount();
      }
    }
    return totalCount;
  }

}

