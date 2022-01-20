package kr.co.hulan.aas.mvc.api.bls.controller.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kr.co.hulan.aas.common.model.req.ConditionRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="MonitoringInfoForWorkplaceRequest", description="BLS monitoring 현장 내 빌딩 상황 정보 요청")
public class MonitoringInfoForWorkplaceRequest extends ConditionRequest  {

  @NotEmpty
  @ApiModelProperty(notes = "현장 아이디.", required = true)
  private String wpId;

  @Override
  public Map<String,Object> getConditionMap(){
    Map<String,Object> condition = super.getConditionMap();
    if(StringUtils.isNotBlank(wpId)){
      condition.put("wpId", wpId);
    }
    return condition;
  }
}
