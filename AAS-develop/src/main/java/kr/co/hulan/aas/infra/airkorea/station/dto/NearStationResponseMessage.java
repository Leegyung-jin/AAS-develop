package kr.co.hulan.aas.infra.airkorea.station.dto;

import io.swagger.annotations.ApiModel;
import kr.co.hulan.aas.infra.airkorea.common.DataGoKrCommonResponseMessage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@ApiModel(value="NearStationResponseMessage", description="근접측정소 목록 조회 응답 전문")
public class NearStationResponseMessage extends
    DataGoKrCommonResponseMessage<NearStationBody> {

  @Override
  public boolean isSuccess(){
    return super.isSuccess() && getResponseBody().getItems() != null;
  }
}
