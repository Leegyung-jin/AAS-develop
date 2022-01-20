package kr.co.hulan.aas.mvc.api.building.controller.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kr.co.hulan.aas.common.model.req.ConditionRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="ExportSensorBuildingSituationDataRequest", description="센서 빌딩 위치 현황 정보  Data Export 요청")
public class ExportSensorBuildingSituationDataRequest extends ConditionRequest {


  @ApiModelProperty(notes = "검색 조건명. COMPLEX : 전체(공사명, 건설사명, 빌딩명),  wpName : 공사명(현장명), ccName : 건설사명, buildingName : 빌딩명 ")
  private String searchName;

  @ApiModelProperty(notes = "검색 조건값")
  private String searchValue;

  @ApiModelProperty(notes = "건설사 아이디")
  private String ccId;

  @ApiModelProperty(notes = "현장 아이디")
  private String wpId;

  @ApiModelProperty(notes = "빌딩 넘버(SEQ)")
  private Long buildingNo;

  @ApiModelProperty(notes = "빌딩 층")
  private Integer floor;


  @JsonIgnore
  @Override
  public Map<String,Object> getConditionMap(){
    Map<String,Object> condition = super.getConditionMap();
    if(StringUtils.isNotBlank(searchName) && StringUtils.isNotBlank(searchValue)){
      condition.put(searchName, searchValue);
    }
    if(StringUtils.isNotBlank(ccId)){
      condition.put("ccId", ccId);
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
    return condition;
  }


}
