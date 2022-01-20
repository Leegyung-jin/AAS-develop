package kr.co.hulan.aas.mvc.api.member.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@ApiModel(value="ConstructionSiteManagerDto", description="건설현장 관리자 정보")
@AllArgsConstructor
@NoArgsConstructor
public class ConstructionSiteManagerDto {

  @ApiModelProperty(notes = "회원 일련번호")
  private Long mbNo;
  @ApiModelProperty(notes = "회원 아이디")
  private String mbId;
  @ApiModelProperty(notes = "회원 성명")
  private String name;
  @ApiModelProperty(notes = "전화번호")
  private String telephone;
  @ApiModelProperty(notes = "가입일")
  private java.util.Date registDate;

}
