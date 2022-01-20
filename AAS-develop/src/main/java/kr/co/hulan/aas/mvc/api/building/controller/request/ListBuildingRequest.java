package kr.co.hulan.aas.mvc.api.building.controller.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kr.co.hulan.aas.common.model.req.DefaultPageRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="ListBuildingRequest", description="빌딩 정보 검색 요청")
public class ListBuildingRequest extends DefaultPageRequest {


  @ApiModelProperty(notes = "검색 조건명. COMPLEX : 전체(공사명, 건설사명, 빌딩명), wpName : 공사명(현장명), ccName : 건설사명, buildingName : 빌딩명 ")
  private String searchName;

  @ApiModelProperty(notes = "검색 조건값")
  private String searchValue;

  @ApiModelProperty(notes = "현장 아이디")
  private String wpId;

  @ApiModelProperty(notes = "지역 타입. 1: 빌딩, 2: 지상, 3: 지하, 4: 구획")
  private Integer areaType;

  @JsonIgnore
  @Override
  public Map<String,Object> getConditionMap(){
    Map<String,Object> condition = super.getConditionMap();
    if(StringUtils.isNotBlank(searchName) && StringUtils.isNotBlank(searchValue)){
      condition.put(searchName, searchValue);
    }
    if(StringUtils.isNotBlank(wpId)){
      condition.put("wpId", wpId);
    }
    if( areaType != null ){
      condition.put("areaType", areaType);
    }

    return condition;
  }
}
