package kr.co.hulan.aas.mvc.api.monitor.controller.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import java.util.Map;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import kr.co.hulan.aas.common.model.req.ConditionRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="SearchGpsLocationHistoryRequestV4_1", description="[V4.1] 근로자/장비 이동경로 정보 요청")
public class SearchGpsLocationHistoryRequestV4_1 extends ConditionRequest  {

  @NotEmpty(message = "데이터 종류는 필수입니다.")
  @ApiModelProperty(notes = "데이터 종류.  device: 디바이스 기준, worker : 근로자 기준", required = true)
  private String dataType;

  @NotEmpty(message = "데이터 타입은 필수입니다.")
  @ApiModelProperty(notes = "dataType 이 device 이면 device_id, worker 이면 mb_id ", required = true)
  private String dataKey;

  @NotNull(message = "검색 시작일은 필수입니다.")
  @ApiModelProperty(notes = "검색 시작일")
  private Date startDate;

  @NotNull(message = "검색 종료일은 필수입니다.")
  @ApiModelProperty(notes = "검색 종료일")
  private Date endDate;

  @JsonIgnore
  @Override
  public Map<String,Object> getConditionMap(){
    Map<String,Object> condition = super.getConditionMap();
    if(StringUtils.isNotBlank(dataKey)){
      condition.put("dataKey", dataKey);
    }
    if(StringUtils.isNotBlank(dataType)){
      condition.put("dataType", dataType);
    }
    if( startDate != null ){
      condition.put("startDate", startDate);
    }
    if( endDate != null ){
      condition.put("endDate", endDate);
    }
    return condition;
  }
}
