package kr.co.hulan.aas.mvc.api.component.controller.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="DeleteMultiUiComponentRequest", description="현장 모니터링 UI 정보 멀티 삭제 요청")
public class DeleteMultiUiComponentRequest {

  @Size(min = 1, message = "최소 한개의 아이디는 있어야 합니다.")
  @NotNull(message = "컴포넌트 아이디 리스트는 필수입니다.")
  @ApiModelProperty(notes = "컴포넌트 아이디 리스트", required = true)
  private List<String> cmptIdList;
}
