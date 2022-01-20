package kr.co.hulan.aas.mvc.api.bls.controller.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kr.co.hulan.aas.mvc.api.bls.dto.BuildingSituation;
import kr.co.hulan.aas.mvc.api.bls.dto.FloorSituation;
import kr.co.hulan.aas.mvc.api.bls.dto.FloorSituationPerBuilding;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="MonitoringInfoForBuildingRequest", description="BLS monitoring 빌딩 내 층 상황 정보 응답")
public class MonitoringInfoForBuildingResponse {

  List<FloorSituationPerBuilding> buildingSituationList;

  @ApiModelProperty(notes = "전체 사용자수")
  public int getTotalWorkerCount(){
    int totalCount = 0;
    if( buildingSituationList != null ){
      for(FloorSituationPerBuilding situ : buildingSituationList){
        List<FloorSituation> floorList = situ.getFloorSituationList();
        for(FloorSituation floorSitu : floorList){
          totalCount += floorSitu.getWorkerCount();
        }
      }
    }
    return totalCount;
  }

}
