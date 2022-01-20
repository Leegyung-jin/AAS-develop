package kr.co.hulan.aas.mvc.api.bls.controller.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Map;
import javax.validation.constraints.NotEmpty;
import kr.co.hulan.aas.common.model.req.ConditionRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="SearchUserLocationRequest", description="근로자 위치 검색")
public class SearchUserLocationRequest extends ConditionRequest  {


  @NotEmpty
  @ApiModelProperty(notes = "현장 아이디.", required = true)
  private String wpId;

  @NotEmpty
  @ApiModelProperty(notes = "근로자 아이디.", required = true)
  private String mbId;

  @JsonIgnore
  @Override
  public Map<String,Object> getConditionMap(){
    Map<String,Object> condition = super.getConditionMap();
    if(StringUtils.isNotBlank(wpId)){
      condition.put("wpId", wpId);
    }
    if(StringUtils.isNotBlank(mbId)){
      condition.put("mbId", mbId);
    }
    return condition;
  }


}
