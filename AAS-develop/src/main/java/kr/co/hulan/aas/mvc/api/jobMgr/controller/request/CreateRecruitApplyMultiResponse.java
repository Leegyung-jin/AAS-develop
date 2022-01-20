package kr.co.hulan.aas.mvc.api.jobMgr.controller.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="CreateRecruitApplyMultiResponse", description="구직 정보 멀티 생성 응답")
public class CreateRecruitApplyMultiResponse {

  @ApiModelProperty(notes = "결과 코드")
  private Integer returnCode;

  @ApiModelProperty(notes = "결과 메세지")
  private String returnMessage;

  @ApiModelProperty(notes = "대상 근로자 정보")
  private CreateRecruitApplyRequest workerInfo;

}
