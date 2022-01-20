package kr.co.hulan.aas.mvc.api.monitor4_1.controller.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import java.util.Map;
import kr.co.hulan.aas.common.model.req.ConditionRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="EntergateWorkerListRequest", description="출입게이트 출근자 검색")
public class EntergateWorkerListRequest extends ConditionRequest  {

  @ApiModelProperty(notes = "잔류 여부. 0: 미잔류, 1: 잔류")
  private Integer residual;

  @ApiModelProperty(notes = "앱 사용 여부. 0: 미사용, 1: 사용")
  private Integer useApp;

  @JsonIgnore
  @Override
  public Map<String,Object> getConditionMap(){
    Map<String,Object> condition = super.getConditionMap();
    if(residual != null){
      condition.put("residual", residual);
    }
    if(useApp != null){
      condition.put("useApp", useApp);
    }
    return condition;
  }
}
