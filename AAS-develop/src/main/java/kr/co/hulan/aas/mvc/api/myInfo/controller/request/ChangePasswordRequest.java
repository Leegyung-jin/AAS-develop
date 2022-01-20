package kr.co.hulan.aas.mvc.api.myInfo.controller.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="ChangePasswordRequest", description="패스워드 변경 요청")
public class ChangePasswordRequest {

  @NotEmpty
  @ApiModelProperty(notes = "기존 패스워드", required = true)
  private String currentPwd;

  @NotEmpty
  @Pattern(regexp="^(?=.*\\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&*]).{8,16}", message = "패스워드는 영문,숫자,특수문자(!@#$%) 를 최소한 한개를 포함하고 8자이상 16자 이하여야 합니다.")
  @ApiModelProperty(notes = "신규 패스워드", required = true)
  private String newPwd;

}

