package kr.co.hulan.aas.mvc.api.bls.controller.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kr.co.hulan.aas.mvc.api.bls.dto.BuildingSituation;
import kr.co.hulan.aas.mvc.api.bls.dto.FloorGridSituation;
import kr.co.hulan.aas.mvc.api.bls.dto.FloorSituation;
import kr.co.hulan.aas.mvc.model.dto.BuildingDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="SearchBlsMonitoringInfoResponse", description="BLS monitoring 정보 응답")
public class SearchBlsMonitoringInfoResponse {

  @ApiModelProperty(notes = "빌딩 별 상황")
  List<BuildingSituation> buildingSituations;

  @ApiModelProperty(notes = "빌딩 정보")
  BuildingDto buildingDto;

  @ApiModelProperty(notes = "빌딩 층별 상황")
  List<FloorSituation> floorSituations;

  @ApiModelProperty(notes = "빌딩 층내 Grid 상황")
  List<FloorGridSituation> floorGridSituations;


}
