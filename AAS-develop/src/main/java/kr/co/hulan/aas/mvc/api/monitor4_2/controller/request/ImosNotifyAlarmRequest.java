package kr.co.hulan.aas.mvc.api.monitor4_2.controller.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="NotifyAlarmRequest", description="위험 지역 접근 기록 알람 요청")
public class ImosNotifyAlarmRequest {

  @NotBlank(message = "제목은 필수입니다.")
  @ApiModelProperty(notes = "제목", required = true)
  private String puSubject;

  @NotBlank(message = "내용은 필수입니다.")
  @ApiModelProperty(notes = "내용", required = true)
  private String puContent;


}
