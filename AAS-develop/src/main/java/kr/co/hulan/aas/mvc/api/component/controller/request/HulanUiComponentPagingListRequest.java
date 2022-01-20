package kr.co.hulan.aas.mvc.api.component.controller.request;

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
@ApiModel(value="HulanUiComponentPagingListRequest", description="현장 모니터링 UI 컴포넌트 검색 요청")
public class HulanUiComponentPagingListRequest extends DefaultPageRequest  {

  @ApiModelProperty(notes = "검색 조건명. COMPLEX: 전체(컴포넌트 아이디, 컴포넌트명), hcmptId: 컴포넌트 아이디, hcmptName: 컴포넌트명")
  private String searchName;

  @ApiModelProperty(notes = "검색 조건값")
  private String searchValue;

  @ApiModelProperty(notes = "사이트 유형. 1: IMOS, 2: HICC(통합관제)")
  private Integer siteType;

  @ApiModelProperty(notes = "UI 유형. 1: 메인화면 유형, 2: 컴포넌트 유형")
  private Integer uiType;

  @ApiModelProperty(notes = "사용 여부. 0: 미사용, 1: 사용, 없으면 전체")
  private Integer status;

  @JsonIgnore
  @Override
  public Map<String,Object> getConditionMap(){
    Map<String,Object> condition = super.getConditionMap();
    if(StringUtils.isNotBlank(searchName) && StringUtils.isNotBlank(searchValue)){
      condition.put(searchName, searchValue);
    }
    if(siteType != null){
      condition.put("siteType", siteType);
    }
    if(uiType != null){
      condition.put("uiType", uiType);
    }
    if(status != null){
      condition.put("status", status);
    }
    return condition;
  }
}
