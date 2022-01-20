package kr.co.hulan.aas.infra.airkorea.environment.dto;

import io.swagger.annotations.ApiModel;
import kr.co.hulan.aas.infra.airkorea.common.DataGoKrCommonResponseMessage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@ApiModel(value="AirEnvironmentResponseMessage", description="시도별 실시간 측정 정보 응답 전문")
public class AirEnvironmentResponseMessage extends
    DataGoKrCommonResponseMessage<AirEnvironmentBody> {

  @Override
  public boolean isSuccess(){
    return super.isSuccess() && getResponseBody().getItems() != null;
  }
}
