package kr.co.hulan.aas.mvc.api.monitor.controller.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="AssignWorkplaceUiComponentRequest", description="현장 UI component 할당 요청")
public class AssignWorkplaceUiComponentRequest {

  @NotBlank(message = "컴포넌트 아이디는 필수입니다.")
  @ApiModelProperty(notes = "할당 컴포넌트 아이디", required = true)
  private String cmptId;

}
