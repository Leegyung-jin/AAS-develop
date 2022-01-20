package kr.co.hulan.aas.mvc.api.hicc.controller.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Map;
import kr.co.hulan.aas.common.model.req.ConditionRequest;
import kr.co.hulan.aas.common.utils.AuthenticationHelper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="CooperativeWorkplaceAttendanceStatExportRequest", description="협력사 현장별 출역 현황 Data Export 요청")
public class CooperativeWorkplaceAttendanceStatExportRequest extends ConditionRequest {

  @JsonProperty(access = Access.READ_ONLY)
  @ApiModelProperty(notes = "검색 조건명. ", hidden = true)
  private String searchName;

  @JsonProperty(access = Access.READ_ONLY)
  @ApiModelProperty(notes = "검색 조건값", hidden = true)
  private String searchValue;

  @JsonIgnore
  @Override
  public Map<String,Object> getConditionMap(){
    Map<String,Object> condition = super.getConditionMap();
    if(StringUtils.isNotBlank(searchName) && StringUtils.isNotBlank(searchValue)){
      condition.put(searchName, searchValue);
    }
    condition = AuthenticationHelper.addAdditionalConditionByLevel(condition);
    return condition;
  }
}
