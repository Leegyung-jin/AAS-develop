package kr.co.hulan.aas.mvc.api.member.controller.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="OfficeGroupManagerUpdateRequest", description="발주사 현장그룹 매니저 수정 요청")
public class OfficeGroupManagerUpdateRequest {

  @NotBlank(message = "회원 성명은 필수입니다.")
  @ApiModelProperty(notes = "회원 성명", required = true)
  private String name;

  @ApiModelProperty(notes = "회원 패스워드")
  private String mbPassword;

  @NotBlank(message = "전화번호는 필수입니다.")
  @ApiModelProperty(notes = "전화번호", required = true)
  private String telephone;

  @ApiModelProperty(notes = "메모")
  private String mbMemo;

  @ApiModelProperty(notes = "탈퇴일")
  private String withdrawDate;

  @ApiModelProperty(notes = "차단일")
  private String blockDate;
}
