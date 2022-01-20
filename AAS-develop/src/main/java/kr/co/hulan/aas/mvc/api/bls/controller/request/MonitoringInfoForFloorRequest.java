package kr.co.hulan.aas.mvc.api.bls.controller.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kr.co.hulan.aas.common.model.req.ConditionRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="MonitoringInfoForFloorRequest", description="BLS monitoring 빌딩 층별 Grid 상황 정보 요청")
public class MonitoringInfoForFloorRequest extends ConditionRequest {

  @ApiModelProperty(notes = "빌딩 층")
  private List<Integer> floorList;

  @Override
  public Map<String,Object> getConditionMap(){
    Map<String,Object> condition = super.getConditionMap();
    if( floorList != null && floorList.size() != 0 ){
      condition.put("floorList", floorList);
    }
    return condition;
  }
}
