package kr.co.hulan.aas.mvc.api.entergate.controller.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import kr.co.hulan.aas.common.code.EnableCode;
import kr.co.hulan.aas.common.code.EnterGateType;
import kr.co.hulan.aas.common.validator.EnumCodeValid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="EntrGateWorkplaceUpdateRequest", description="출입게이트 현장 할당 정보 수정 요청")
public class EntrGateWorkplaceUpdateRequest {

  @NotBlank(message = "업체 계정 아이디는 필수입니다.")
  @ApiModelProperty(notes = "업체 계정 아이디", required = true)
  private String accountId;
  @NotBlank(message = "현장 맵핑 코드는 필수입니다.")
  @ApiModelProperty(notes = "현장 맵핑 코드", required = true)
  private String mappingCd;
  @EnumCodeValid(target = EnterGateType.class, message = "출입게이트 유형이 올바르지 않습니다.")
  @NotNull(message = "출입게이트 유형은 필수입니다.")
  @ApiModelProperty(notes = "출입게이트 유형. 1: 안면인식, 2: QR Reader")
  private Integer gateType;
  @EnumCodeValid(target = EnableCode.class, message = "상태가 올바르지 않습니다.")
  @NotNull(message = "상태는 필수입니다.")
  @ApiModelProperty(notes = "상태. 0: 미사용, 1: 사용", required = true)
  private Integer status;

  @ApiModelProperty(notes = "관리자 사이트 URL")
  private String adminUrl;
  @ApiModelProperty(notes = "관리자 사이트 계정")
  private String adminId;
  @ApiModelProperty(notes = "관리자 사이트 비번")
  private String adminPwd;
}
