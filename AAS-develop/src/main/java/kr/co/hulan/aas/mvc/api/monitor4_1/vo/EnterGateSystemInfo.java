package kr.co.hulan.aas.mvc.api.monitor4_1.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="EnterGateSystemInfo", description="출입시스템 정보")
public class EnterGateSystemInfo {
  @ApiModelProperty(notes = "업체 계정 아이디")
  private String accountId;
  @ApiModelProperty(notes = "업체명")
  private String accountName;
  @ApiModelProperty(notes = "현장 맵핑 코드")
  private String mappingCd;
  @ApiModelProperty(notes = "현장 아이디")
  private String wpId;
  @ApiModelProperty(notes = "현장명")
  private String wpName;
  @ApiModelProperty(notes = "관리자 사이트 URL")
  private String adminUrl;
  @ApiModelProperty(notes = "관리자 사이트 계정")
  private String adminId;
  @ApiModelProperty(notes = "관리자 사이트 비번")
  private String adminPwd;
}
