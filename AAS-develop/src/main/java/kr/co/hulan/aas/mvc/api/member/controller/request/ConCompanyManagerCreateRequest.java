package kr.co.hulan.aas.mvc.api.member.controller.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="ConCompanyManagerCreateRequest", description="건설사 관리자 생성 요청")
public class ConCompanyManagerCreateRequest {

  @NotBlank(message = "회원 아이디는 필수입니다.")
  @ApiModelProperty(notes = "회원 아이디", required = true)
  private String mbId;

  @NotBlank(message = "회원 성명은 필수입니다.")
  @ApiModelProperty(notes = "회원 성명", required = true)
  private String name;

  @NotBlank(message = "회원 패스워드가 있어야 합니다.")
  @ApiModelProperty(notes = "회원 패스워드", required = true)
  private String mbPassword;

  @NotBlank(message = "전화번호는 필수입니다.")
  @ApiModelProperty(notes = "전화번호", required = true)
  private String telephone;

  @NotNull(message = "건설사 필수입니다.")
  @ApiModelProperty(notes = "건설사 아이디", required = true)
  private String ccId;

  @ApiModelProperty(notes = "메모")
  private String mbMemo;

  @ApiModelProperty(notes = "탈퇴일")
  private String withdrawDate;

  @ApiModelProperty(notes = "차단일")
  private String blockDate;
}
