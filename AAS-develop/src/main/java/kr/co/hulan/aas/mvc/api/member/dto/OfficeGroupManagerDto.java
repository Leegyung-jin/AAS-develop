package kr.co.hulan.aas.mvc.api.member.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@ApiModel(value="OfficeManagerDto", description="발주사 현장그룹 매니저 정보")
@AllArgsConstructor
@NoArgsConstructor
public class OfficeGroupManagerDto {

  @ApiModelProperty(notes = "회원 일련번호")
  private Long mbNo;
  @ApiModelProperty(notes = "회원 아이디")
  private String mbId;
  @ApiModelProperty(notes = "회원 성명")
  private String name;
  @ApiModelProperty(notes = "전화번호")
  private String telephone;
  @ApiModelProperty(notes = "근로자 번호")
  private String memberShipNo;

  @ApiModelProperty(notes = "메모")
  private String mbMemo;

  @ApiModelProperty(notes = "발주사 관리번호(소속 발주사)")
  private Long officeNo;
  @ApiModelProperty(notes = "발주사명(소속 발주사)")
  private String officeName;

  @ApiModelProperty(notes = "가입일")
  private java.util.Date registDate;
  @ApiModelProperty(notes = "탈퇴일")
  private String withdrawDate;
  @ApiModelProperty(notes = "차단일")
  private String blockDate;
}
