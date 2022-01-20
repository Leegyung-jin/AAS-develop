package kr.co.hulan.aas.mvc.api.hicc.controller.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import kr.co.hulan.aas.mvc.api.hicc.vo.AttendanceWeekHistoryVo;
import kr.co.hulan.aas.mvc.api.hicc.vo.HiccSidoWeatherVo;
import kr.co.hulan.aas.mvc.api.hicc.vo.main.HiccNationWideWorkplaceAllVo;
import kr.co.hulan.aas.mvc.api.hicc.vo.main.HiccNationWideStateIndicatorVo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="HiccNationWideInfoResponse", description="전국 현황 정보 응답")
public class HiccNationWideInfoResponse {

  @ApiModelProperty(notes = "전국 현황 지도 URL")
  private String mapUrl;

  @ApiModelProperty(notes = "전국 현장 현황 정보")
  private HiccNationWideWorkplaceAllVo nationWide;

  @ApiModelProperty(notes = "전국 시도별 날씨 정보")
  private List<HiccSidoWeatherVo> sidoWeathers;

  @ApiModelProperty(notes = "전국 지표 ( 현재 전체 작업인원 및 종료, 위험근로자 등 ) 정보")
  private HiccNationWideStateIndicatorVo stateIndicator;

  @ApiModelProperty(notes = "출역인원 수 현황 정보")
  private AttendanceWeekHistoryVo attendanceWeekState;
}
