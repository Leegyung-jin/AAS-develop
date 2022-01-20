package kr.co.hulan.aas.mvc.api.hicc.controller.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Map;
import kr.co.hulan.aas.common.model.req.ConditionRequest;
import kr.co.hulan.aas.common.model.req.DefaultPageRequest;
import kr.co.hulan.aas.common.utils.AuthenticationHelper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="CooperativeSectionStatExportRequest", description="공종별 협력사 풀 데이터 Export 요청")
public class CooperativeSectionStatExportRequest extends ConditionRequest {

  @ApiModelProperty(notes = "현장 아이디")
  private String wpId;

  @JsonIgnore
  @Override
  public Map<String,Object> getConditionMap(){
    Map<String,Object> condition = super.getConditionMap();
    if( StringUtils.isNotBlank(wpId)){
      condition.put("wpId", wpId);
    }
    condition = AuthenticationHelper.addAdditionalConditionByLevel(condition);
    return condition;
  }
}
