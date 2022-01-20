package kr.co.hulan.aas.mvc.api.device.controller.request;

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
@ApiModel(value="WorkPlaceCctvListRequest", description="현장 CCTV 검색 요청")
public class WorkPlaceCctvListRequest extends DefaultPageRequest {

  @ApiModelProperty(notes = "검색 조건명. COMPLEX : 전체( 현장명, CCTV명 ), wpName: 공사명(현장명), cctvName: CCTV명 ")
  private String searchName;

  @ApiModelProperty(notes = "검색 조건값")
  private String searchValue;

  @ApiModelProperty(notes = "현장 아이디")
  private String wpId;

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
    return condition;
  }
}
