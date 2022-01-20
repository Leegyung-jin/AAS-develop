package kr.co.hulan.aas.mvc.api.building.controller.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kr.co.hulan.aas.common.model.req.DefaultPageRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import javax.validation.constraints.NotNull;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="ListSensorBuildingLocationRequest", description="센서 빌딩 위치 정보 요청")
public class ListSensorBuildingLocationRequest extends DefaultPageRequest {

  @ApiModelProperty(notes = "검색 조건명. COMPLEX : 전체(공사명, 건설사명, 빌딩명, 안전센서번호, 구역, 위치1, 위치2)"+
      ", wpName : 공사명(현장명), ccName : 건설사명, buildingName : 빌딩명, si_code : 안전센서코드, sd_name : 구역명, si_place1 : 위치1, si_place2 : 위치2  ")
  private String searchName;

  @ApiModelProperty(notes = "검색 조건값")
  private String searchValue;

  @ApiModelProperty(notes = "현장 아이디")
  private String wpId;

  @ApiModelProperty(notes = "빌딩 넘버(SEQ)")
  private Long buildingNo;

  @ApiModelProperty(notes = "빌딩 층")
  private Integer floor;

  @ApiModelProperty(notes = "센서 유형")
  private String siType;

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
    if(buildingNo != null){
      condition.put("buildingNo", buildingNo);
    }
    if(floor != null){
      condition.put("floor", floor);
    }
    if(StringUtils.isNotEmpty(siType)){
      condition.put("siType", siType);
    }
    return condition;
  }
}
