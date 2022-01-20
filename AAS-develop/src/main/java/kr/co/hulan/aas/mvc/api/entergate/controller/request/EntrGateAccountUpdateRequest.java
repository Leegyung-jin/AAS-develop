package kr.co.hulan.aas.mvc.api.entergate.controller.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="EntrGateAccountListRequest", description="출입게이트 업체 계정 정보 수정 요청")
public class EntrGateAccountUpdateRequest {

  @ApiModelProperty(notes = "계정비밀번호")
  private String accountPwd;
  @NotBlank(message = "계정명은 필수입니다.")
  @ApiModelProperty(notes = "계정명", required = true)
  private String accountName;
  @ApiModelProperty(notes = "관리자 사이트 URL")
  private String adminUrl;
  @ApiModelProperty(notes = "설명")
  private String description;
  @NotNull(message = "상태는 필수입니다.")
  @ApiModelProperty(notes = "상태. 0: 미사용, 1: 사용", required = true)
  private Integer status;

}
