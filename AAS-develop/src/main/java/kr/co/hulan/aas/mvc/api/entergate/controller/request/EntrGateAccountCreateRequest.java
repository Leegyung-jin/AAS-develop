package kr.co.hulan.aas.mvc.api.entergate.controller.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import kr.co.hulan.aas.mvc.api.entergate.vo.EntrGateWorkplaceVo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="EntrGateAccountListRequest", description="출입게이트 업체 계정 정보 생성 요청")
public class EntrGateAccountCreateRequest {

  @NotBlank(message = "연동 계정 아이디는 필수입니다.")
  @ApiModelProperty(notes = "연동 계정 아이디", required = true)
  private String accountId;
  @NotBlank(message = "계정명은 필수입니다.")
  @ApiModelProperty(notes = "계정명", required = true)
  private String accountName;
  @NotBlank(message = "계정비밀번호는 필수입니다.")
  @ApiModelProperty(notes = "계정비밀번호", required = true)
  private String accountPwd;
  @ApiModelProperty(notes = "설명")
  private String description;
  @NotNull(message = "상태는 필수입니다.")
  @ApiModelProperty(notes = "상태. 0: 미사용, 1: 사용", required = true)
  private Integer status;

}
