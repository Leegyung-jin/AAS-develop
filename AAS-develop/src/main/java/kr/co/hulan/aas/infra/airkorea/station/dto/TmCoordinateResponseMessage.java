package kr.co.hulan.aas.infra.airkorea.station.dto;

import io.swagger.annotations.ApiModel;
import kr.co.hulan.aas.infra.airkorea.common.DataGoKrCommonResponseMessage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@ApiModel(value="TmCoordinateResponseMessage", description="법정동명 기반 TM 좌표 조회 응답 전문")
public class TmCoordinateResponseMessage extends
    DataGoKrCommonResponseMessage<TmCoordinateBody> {

}
