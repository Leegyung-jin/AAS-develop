package kr.co.hulan.aas.mvc.api.bls.controller.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kr.co.hulan.aas.common.model.req.ConditionRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="MonitoringInfoForBuildingRequest", description="BLS monitoring 빌딩 내 층 상황 정보 요청")
public class MonitoringInfoForBuildingRequest extends ConditionRequest  {

  @ApiModelProperty(notes = "빌딩 넘버(SEQ) 리스트. null 내지는 비어있는 경우 전체를 의미")
  private List<Long> buildingNoList;

  @Override
  public Map<String,Object> getConditionMap(){
    Map<String,Object> condition = super.getConditionMap();
    if( buildingNoList != null && buildingNoList.size() != 0 ){
      condition.put("buildingNoList", buildingNoList);
    }
    return condition;
  }

}
