package kr.co.hulan.aas.mvc.api.member.controller.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Map;
import kr.co.hulan.aas.common.model.req.DefaultPageRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="ConstructionSiteManagerPagingListRequest", description="건설현장 매니저 페이징 리스트 요청")
public class ConstructionSiteManagerPagingListRequest extends DefaultPageRequest {

  @ApiModelProperty(notes = "검색 조건명. COMPLEX : 전체, mbName:성명,  mbId:회원번호, telephone: 전화번호 ")
  private String searchName;

  @ApiModelProperty(notes = "검색 조건값")
  private String searchValue;

  @ApiModelProperty(notes = "현장 아이디")
  private String wpId;

  @ApiModelProperty(notes = "건설사 아이디")
  private String ccId;

  @ApiModelProperty(notes = "제외 현장 아이디")
  private String excludeWpId;

  @ApiModelProperty(notes = "제외 건설사 아이디")
  private String excludeCcId;

  @ApiModelProperty(notes = "소속 건설사 아이디")
  private String belongCcId;

  @JsonIgnore
  @Override
  public Map<String,Object> getConditionMap(){
    Map<String,Object> condition = super.getConditionMap();
    if(StringUtils.isNotBlank(searchName) && StringUtils.isNotBlank(searchValue)){
      condition.put(searchName, searchValue);
    }

    if( StringUtils.isNotBlank(wpId)){
      condition.put("wpId", wpId);
    }
    if( StringUtils.isNotBlank(ccId)){
      condition.put("ccId", ccId);
    }
    if( StringUtils.isNotBlank(excludeWpId)){
      condition.put("excludeWpId", excludeWpId);
    }
    if( StringUtils.isNotBlank(excludeCcId)){
      condition.put("excludeCcId", excludeCcId);
    }
    if( StringUtils.isNotBlank(belongCcId)){
      condition.put("belongCcId", belongCcId);
    }
    return condition;
  }
}