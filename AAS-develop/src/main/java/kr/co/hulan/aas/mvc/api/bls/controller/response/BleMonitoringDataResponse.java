package kr.co.hulan.aas.mvc.api.bls.controller.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import java.util.Map;
import kr.co.hulan.aas.infra.weather.WeatherInfo;
import kr.co.hulan.aas.mvc.api.bls.controller.request.BleMonitoringDataRequest;
import kr.co.hulan.aas.mvc.api.bls.dto.FloorSituationDto;
import kr.co.hulan.aas.mvc.api.monitor.controller.response.WorkplaceExternalLink;
import kr.co.hulan.aas.mvc.api.monitor.dto.EnvironmentMeasureDeviceVo;
import kr.co.hulan.aas.mvc.api.monitor.dto.WorkplaceSummaryDto;
import kr.co.hulan.aas.mvc.model.dto.FallingAccidentDto;
import kr.co.hulan.aas.mvc.model.dto.ImageAnalyticInfoDto;
import kr.co.hulan.aas.mvc.model.dto.WorkPlaceDto;
import kr.co.hulan.aas.mvc.model.dto.WorkPlaceMonitorConfigDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="BleMonitoringDataResponse", description="BLE 스마트 안전모니터 데이터 응답")
public class BleMonitoringDataResponse<Object> {

  // 공통정보
  @ApiModelProperty(notes = "요청 정보")
  private BleMonitoringDataRequest request;

  @ApiModelProperty(notes = "현장 정보")
  private WorkplaceSummaryDto workplace;

  @ApiModelProperty(notes = "스마트 안전모니터 현장 설정 정보")
  private WorkPlaceMonitorConfigDto monitorConfig;

  @ApiModelProperty(notes = "현장 관련 메모 정보")
  private String memo;

  @ApiModelProperty(notes = "출력인원")
  private Integer slTotal;

  @ApiModelProperty(notes = "현재인원")
  private Integer currentWorkerCount;

  @ApiModelProperty(notes = "센서 타입별 현재인원")
  private Map<String,Object> workerCount;

  /*
  @ApiModelProperty(notes = "센서 In/Out 타입별 근로자수")
  private Map<String,Object> workerCount;
  */

  @ApiModelProperty(notes = "고위험 근로자 리스트")
  private List<Map<String,Object>> workerCheck;

  @ApiModelProperty(notes = "위험지역 접근 근로자 리스트")
  private List<Map<String,Object>> workerDangerArea;

  @ApiModelProperty(notes = "외부 링크 정보")
  private List<WorkplaceExternalLink> linkInfo;

  @ApiModelProperty(notes = "날씨 정보")
  private WeatherInfo weatherInfo;

  @ApiModelProperty(notes = "메인 화면 이미지 링크")
  private String imageUrl;

  @ApiModelProperty(notes = "구역별 근로자 현황")
  List<FloorSituationDto> floorSituations;

  @ApiModelProperty(notes = "영상 분석 이벤트 ")
  private ImageAnalyticInfoDto analyticInfo;

  @ApiModelProperty(notes = "위험 물질 측정 정보")
  private List<EnvironmentMeasureDeviceVo> hazardMeasureDevice;

  @ApiModelProperty(notes = "낙하 이벤트 정보")
  private List<FallingAccidentDto> fallingAccident;

  // BLE
  @ApiModelProperty(notes = "요청 Data")
  Object bleDataList;

}
