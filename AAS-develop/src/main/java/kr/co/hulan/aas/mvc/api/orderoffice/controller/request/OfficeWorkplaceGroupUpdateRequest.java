package kr.co.hulan.aas.mvc.api.orderoffice.controller.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="OfficeWorkplaceGroupUpdateRequest", description="발주사 현장그룹 수정 요청")
public class OfficeWorkplaceGroupUpdateRequest {

  @NotBlank(message = "발주사 현장그룹명은 필수입니다.")
  @ApiModelProperty(notes = "발주사 현장그룹명", required = true)
  private String officeGrpName;

  @NotNull(message = "발주사 현장그룹 관리자는 필수입니다.")
  @Size(min=1, message = "현장그룹 관리자는 최소 한명을 할당하여야 합니다.")
  @ApiModelProperty(notes = "현장그룹 관리자 리스트", required = true)
  private List<String> managerMbIdList;

}
