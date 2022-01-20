package kr.co.hulan.aas.mvc.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@ApiModel(value="MemberOtpPhoneDto", description="OTP 수신 전화번호")
@AllArgsConstructor
@NoArgsConstructor
public class MemberOtpPhoneDto {

  @ApiModelProperty(notes = "사용자 아이디")
  private String mbId;

  @NotBlank(message = "전화번호는 필수입니다")
  @ApiModelProperty(notes = "전화번호", required = true)
  private String phoneNo;

  @ApiModelProperty(notes = "UUID")
  private String uuid;

  @ApiModelProperty(notes = "등록일")
  private Date createDate;
  @ApiModelProperty(notes = "등록자")
  private String creator;

}
