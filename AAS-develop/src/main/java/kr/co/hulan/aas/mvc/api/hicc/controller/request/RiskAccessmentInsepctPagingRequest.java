package kr.co.hulan.aas.mvc.api.hicc.controller.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Map;
import kr.co.hulan.aas.common.model.req.DefaultPageRequest;
import kr.co.hulan.aas.common.utils.AuthenticationHelper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="RiskAccessmentInsepctPagingRequest", description="위험성 평가 리스트(페이징) 요청")
public class RiskAccessmentInsepctPagingRequest extends DefaultPageRequest  {

  @ApiModelProperty(notes = "검색 조건명. COMPLEX : 전체, wpName: 현장명, coopMbName: 협력사명, sectionName: 공종명", hidden = true)
  private String searchName = "COMPLEX";

  @ApiModelProperty(notes = "검색 조건값")
  private String searchValue;

  @ApiModelProperty(notes = "공종")
  private String section;

  @ApiModelProperty(notes = "현장 아이디")
  private String wpId;

  @ApiModelProperty(notes = "처리 여부. '': 전체, 0: 미처리, 1: 처리")
  private Integer completed;

  @JsonIgnore
  @Override
  public Map<String,Object> getConditionMap(){
    Map<String,Object> condition = super.getConditionMap();
    if(StringUtils.isNotBlank(searchName) && StringUtils.isNotBlank(searchValue)){
      condition.put(searchName, searchValue);
    }
    if( StringUtils.isNotBlank(section)){
      condition.put("section", section);
    }
    if( StringUtils.isNotBlank(wpId)){
      condition.put("wpId", wpId);
    }
    if( completed != null ){
      condition.put("completed", completed);
    }
    condition = AuthenticationHelper.addAdditionalConditionByLevel(condition);
    return condition;
  }
}
