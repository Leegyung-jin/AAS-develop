package kr.co.hulan.aas.mvc.api.monitor.controller.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import java.util.Map;
import javax.validation.constraints.NotEmpty;
import kr.co.hulan.aas.common.model.req.ConditionRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="ExportAttendantSituationRequest", description="협력사별 출력인원 현황 조회 요청")
public class ExportAttendantSituationRequest extends ConditionRequest {

  @NotEmpty(message="현장 아이디는 필수입니다.")
  @ApiModelProperty(notes = "현장 아이디")
  private String wpId;

  @ApiModelProperty(notes = "협력사 아이디")
  private String coopMbId;

  @ApiModelProperty(notes = "검색일. 없으면 당일로 검색")
  private Date wwpDate;

  @JsonIgnore
  @Override
  public Map<String,Object> getConditionMap(){
    Map<String,Object> condition = super.getConditionMap();
    if(StringUtils.isNotBlank(wpId)){
      condition.put("wpId", wpId);
    }
    if(StringUtils.isNotBlank(coopMbId)){
      condition.put("coopMbId", coopMbId);
    }
    if( wwpDate != null ){
      condition.put("wwpDate", wwpDate);
    }
    else {
      condition.put("wwpDate", new Date());
    }
    return condition;
  }
}
