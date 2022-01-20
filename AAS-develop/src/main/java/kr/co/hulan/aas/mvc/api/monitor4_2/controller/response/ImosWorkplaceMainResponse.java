package kr.co.hulan.aas.mvc.api.monitor4_2.controller.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import kr.co.hulan.aas.infra.weather.WeatherInfo;
import kr.co.hulan.aas.mvc.api.board.dto.NoticeDto;
import kr.co.hulan.aas.mvc.api.monitor.controller.response.WorkplaceSupportMonitoringDto;
import kr.co.hulan.aas.mvc.api.monitor4_2.vo.ImosMemberUiComponentVo;
import kr.co.hulan.aas.mvc.model.dto.AirEnvironmentDto;
import kr.co.hulan.aas.mvc.model.dto.WorkPlaceUiComponentDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="ImosWorkplaceMainResponse", description="현장 메인 정보 응답")
public class ImosWorkplaceMainResponse {

  @ApiModelProperty(notes = "지원 현장 정보")
  private WorkplaceSupportMonitoringDto monitoringWorkplace;

  @ApiModelProperty(notes = "현장 UI 컴포넌트 리스트")
  private List<ImosMemberUiComponentVo> uiComponentList;

  @ApiModelProperty(notes = "현장 날씨 정보")
  private WeatherInfo weather;

  @ApiModelProperty(notes = "마지막(최근) 공지사항")
  private NoticeDto notice;

  @ApiModelProperty(notes = "미세먼지 정보")
  private AirEnvironmentDto dust;
}
