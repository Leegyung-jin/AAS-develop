package kr.co.hulan.aas.mvc.api.workplace.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@ApiModel(value="ConstructionSiteMangerVo", description="건설현장 관리자 정보")
@AllArgsConstructor
@NoArgsConstructor
public class ConstructionSiteManagerVo {

  @ApiModelProperty(notes = "회원 일련번호")
  private Long mbNo;
  @NotBlank(message = "회원 아이디는 필수입니다.")
  @ApiModelProperty(notes = "회원 아이디", required = true)
  private String mbId;
  @ApiModelProperty(notes = "회원 성명")
  private String name;
  @ApiModelProperty(notes = "전화번호")
  private String telephone;
  @ApiModelProperty(notes = "가입일")
  private java.util.Date registDate;
}
