package kr.co.hulan.aas.mvc.api.monitor4_2.controller.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Map;
import kr.co.hulan.aas.common.model.req.ConditionRequest;
import kr.co.hulan.aas.common.utils.AuthenticationHelper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="ImosWorkLogWorkerSearchRequest", description="근로자 상태 정보 리스트 요청")
public class ImosWorkLogWorkerExportRequest extends ConditionRequest  {

  @ApiModelProperty(notes = "출근 유형. 2: QRCode, 3: 안면인식, 4: 출입센서, 5: Geofence, 6: GeofencePoligon, 99: 기타")
  private Integer commuteType;

  @ApiModelProperty(notes = "현재 작업여부. 0: 종료, 1: 작업")
  private Integer currentWorking;

  @ApiModelProperty(notes = "협력사 아이디")
  private String coopMbId;

  @JsonIgnore
  @Override
  public Map<String,Object> getConditionMap(){
    Map<String,Object> condition = AuthenticationHelper.addAdditionalConditionByLevel(super.getConditionMap());
    if(StringUtils.isNotBlank(coopMbId)){
      condition.put("coopMbId", coopMbId);
    }
    if(commuteType != null){
      condition.put("commuteType", commuteType);
    }
    if(currentWorking != null){
      condition.put("currentWorking", currentWorking);
    }
    return condition;
  }

}
