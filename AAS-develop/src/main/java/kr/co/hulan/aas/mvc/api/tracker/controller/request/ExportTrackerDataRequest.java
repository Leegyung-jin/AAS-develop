package kr.co.hulan.aas.mvc.api.tracker.controller.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Map;
import kr.co.hulan.aas.common.model.req.ConditionRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="ExportTrackerDataRequest", description="트랙커 데이터 Export 요청")
public class ExportTrackerDataRequest extends ConditionRequest  {


  @ApiModelProperty(notes = "검색 조건명. COMPLEX: 전체(트랙커 아이디, 근로자 아이디/이름/번호), trackerId: 트랙커 아이디, mbId : 근로자 아이디, mbName : 근로자명, memberShipNo:근로자번호")
  private String searchName;

  @ApiModelProperty(notes = "검색 조건값")
  private String searchValue;

  @ApiModelProperty(notes = "건설사 아이디")
  private String ccId;

  @ApiModelProperty(notes = "현장 아이디")
  private String wpId;

  @ApiModelProperty(notes = "협력사 아이디")
  private String coopMbId;

  @ApiModelProperty(notes = "근로자 할당 여부. 0: 미할당, 1: 할당, 없으면 전체")
  private Integer assign;

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
    if(StringUtils.isNotBlank(coopMbId)){
      condition.put("coopMbId", coopMbId);
    }
    if(assign != null){
      condition.put("assign", assign);
    }
    return condition;
  }

}
