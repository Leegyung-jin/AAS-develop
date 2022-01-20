package kr.co.hulan.aas.mvc.api.monitor4_1.controller.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kr.co.hulan.aas.mvc.api.monitor4_1.vo.EnterGateStatInfo;
import kr.co.hulan.aas.mvc.api.monitor4_1.vo.EnterGateSystemInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="EntergateMemberStatResponse", description="출입게이트 인원 현황 정보")
public class EntergateMemberStatResponse {

  @ApiModelProperty(notes = "출입시스템 정보")
  private EnterGateSystemInfo system;

  @ApiModelProperty(notes = "출입시스템 출역 정보")
  private EnterGateStatInfo stat;






}
