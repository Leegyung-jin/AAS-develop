package kr.co.hulan.aas.mvc.api.building.controller.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kr.co.hulan.aas.mvc.api.building.dto.SensorBuildingSituationDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="ListSensorBuildingSituationResponse", description="센서 빌딩 위치 현황 정보 검색 응답")
public class ListSensorBuildingSituationResponse {


  @ApiModelProperty(notes = "전체 수")
  private long totalCount;
  @ApiModelProperty(notes = "센서 빌딩 층별 현황 정보 리스트")
  private List<SensorBuildingSituationDto> list;

}
