package kr.co.hulan.aas.mvc.api.gps.controller.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Map;
import javax.validation.constraints.NotEmpty;
import kr.co.hulan.aas.common.model.req.ConditionRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="SearchGpsMonitoringDataRequest", description="GPS 안전모니터 정보 제공 요청")
public class SearchGpsMonitoringDataRequest extends ConditionRequest {

  @NotEmpty
  @ApiModelProperty(notes = "현장 아이디", required = true)
  private String wpId;

  @ApiModelProperty(notes = "Marker 표시 근로자 아이디")
  private String markerMbId;

  @ApiModelProperty(notes = "검색 조건명.")
  private String searchName;

  @ApiModelProperty(notes = "검색 조건값")
  private String searchValue;

  @JsonIgnore
  @Override
  public Map<String,Object> getConditionMap(){
    Map<String,Object> condition = super.getConditionMap();
    if(StringUtils.isNotBlank(searchName) && StringUtils.isNotBlank(searchValue)){
      condition.put(searchName, searchValue);
    }
    if(StringUtils.isNotBlank(wpId)){
      condition.put("wp_id", wpId);
    }
    if(StringUtils.isNotBlank(markerMbId)){
      condition.put("markerMbId", markerMbId);
    }
    return condition;
  }
}
