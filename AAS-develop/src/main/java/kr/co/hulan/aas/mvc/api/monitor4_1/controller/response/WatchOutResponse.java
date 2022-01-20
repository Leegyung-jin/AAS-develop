package kr.co.hulan.aas.mvc.api.monitor4_1.controller.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import kr.co.hulan.aas.mvc.api.monitor4_1.vo.DetectedSensorWorkerVo;
import kr.co.hulan.aas.mvc.model.dto.FallingAccidentDto;
import kr.co.hulan.aas.mvc.model.dto.ImageAnalyticInfoDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="WatchOutResponse", description="요주의 근로자 정보")
public class WatchOutResponse {

  @ApiModelProperty(notes = "위험지역 접근 근로자 리스트")
  private List<DetectedSensorWorkerVo> dangerAreaWorkers;

  @ApiModelProperty(notes = "고위험 근로자 리스트")
  private List<DetectedSensorWorkerVo> highRiskWorkers;

  @ApiModelProperty(notes = "추락감지 근로자 리스트")
  private List<FallingAccidentDto> fallingAccidentWorkers;

  @ApiModelProperty(notes = "영상분석 이벤트 리스트 ( 안전모 미착용, 화재 알람 )")
  private List<ImageAnalyticInfoDto> imageAnalyticInfos;

}
