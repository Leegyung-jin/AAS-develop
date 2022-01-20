package kr.co.hulan.aas.mvc.api.bls.controller.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value="BleMonitoringDataForUserRequest", description="BLE 스마트 안전모니터 데이터 검색 ( 특정 사용자 )")
public class BleMonitoringDataForUserRequest extends ConditionRequest  {

  @NotEmpty(message = "현장 정보가 존재하지 않습니다.")
  @ApiModelProperty(notes = "현장 아이디")
  private String wpId;

  @NotEmpty(message = "근로자 정보가 존재하지 않습니다.")
  @ApiModelProperty(notes = "근로자 아이디")
  private String mbId;


  @Override
  public Map<String,Object> getConditionMap(){
    Map<String,Object> condition = super.getConditionMap();
    if( StringUtils.isNotEmpty(wpId)){
      condition.put("wpId", wpId);
    }
    if( StringUtils.isNotEmpty(mbId)){
      condition.put("mbId", mbId);
    }
    return condition;
  }
}
