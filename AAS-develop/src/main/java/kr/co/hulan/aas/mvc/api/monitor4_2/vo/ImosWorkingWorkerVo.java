package kr.co.hulan.aas.mvc.api.monitor4_2.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import kr.co.hulan.aas.common.code.WorkInOutMethod;
import kr.co.hulan.aas.mvc.api.monitor.controller.response.AttendantVo;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@ApiModel(value="ImosWorkingWorkerVo", description="작업인원 정보")
public class ImosWorkingWorkerVo extends AttendantVo {

  @ApiModelProperty(notes = "종료처리 사유코드")
  private Integer closeReason;

  @ApiModelProperty(notes = "종료시간")
  private Date closeTime;

  @ApiModelProperty(notes = "출근유형")
  private Integer commuteType;

  @ApiModelProperty(notes = "종료처리 사유설명")
  public String getCloseReasonDesc(){
    ClosedWorkerReason reason = ClosedWorkerReason.get(closeReason);
    return reason != null ? reason.getDesc() : "Unknown("+closeReason+")";
  }

  @ApiModelProperty(notes = "출근유형명")
  public String getCommuteTypeName(){
    WorkInOutMethod method = WorkInOutMethod.get(commuteType);
    return method != null ? method.getName() : "Unknown("+commuteType+")";
  }

}
