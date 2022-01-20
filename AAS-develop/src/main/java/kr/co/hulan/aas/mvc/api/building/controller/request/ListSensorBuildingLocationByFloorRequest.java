package kr.co.hulan.aas.mvc.api.building.controller.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kr.co.hulan.aas.common.model.req.ConditionRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="ListSensorBuildingLocationByFloorRequest", description="빌딩 층별 센서 위치 정보 요청")
public class ListSensorBuildingLocationByFloorRequest extends ConditionRequest {

  @NotNull
  @ApiModelProperty(notes = "빌딩 넘버(SEQ)")
  private Long buildingNo;

  @NotNull
  @ApiModelProperty(notes = "빌딩 층")
  private Integer floor;

  @JsonIgnore
  @Override
  public Map<String,Object> getConditionMap(){
    Map<String,Object> condition = super.getConditionMap();
    if(buildingNo != null){
      condition.put("buildingNo", buildingNo);
    }
    if(floor != null){
      condition.put("floor", floor);
    }
    return condition;
  }
}
