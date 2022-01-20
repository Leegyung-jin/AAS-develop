package kr.co.hulan.aas.mvc.api.monitor4_2.controller.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import kr.co.hulan.aas.mvc.api.monitor4_2.vo.windgauge.WindSpeedRangeVo;
import kr.co.hulan.aas.mvc.api.monitor4_2.vo.windgauge.WindSpeedRecentVo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="ImosWindGaugeMainResponse", description="IMOS 풍속계 메인 정보 응답")
public class ImosWindGaugeMainResponse {

  @ApiModelProperty(notes = "풍속계 경고구간 임계치 정보")
  private List<WindSpeedRangeVo> threshold;

  @ApiModelProperty(notes = "풍속계 현재 상태 리스트")
  private List<WindSpeedRecentVo> list;


}
