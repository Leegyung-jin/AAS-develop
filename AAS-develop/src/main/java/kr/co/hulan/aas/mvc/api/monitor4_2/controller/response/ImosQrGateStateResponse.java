package kr.co.hulan.aas.mvc.api.monitor4_2.controller.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kr.co.hulan.aas.mvc.api.monitor4_1.vo.CurrentWorkStatusSummary;
import kr.co.hulan.aas.mvc.api.monitor4_1.vo.EnterGateSystemInfo;
import kr.co.hulan.aas.mvc.api.monitor4_2.vo.ImosQrGateAttendantStateVo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="ImosQrGateStateResponse", description="QR Reader 출입게이트 현황")
public class ImosQrGateStateResponse {

  @ApiModelProperty(notes = "출입시스템 정보")
  private EnterGateSystemInfo system;

  @ApiModelProperty(notes = "출역 인원 (출근자 유형별 현황)")
  private ImosQrGateAttendantStateVo attendantState;

  @ApiModelProperty(notes = "작업인원 현황 (작업 및 종료 인원 현황)")
  private CurrentWorkStatusSummary workStatus;


}
