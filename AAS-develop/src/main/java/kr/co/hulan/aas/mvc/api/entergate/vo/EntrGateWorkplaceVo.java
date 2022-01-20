package kr.co.hulan.aas.mvc.api.entergate.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kr.co.hulan.aas.common.code.EnterGateType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@ApiModel(value="EntrGateWorkplaceVo", description="출입게이트 현장 할당 정보")
@AllArgsConstructor
@NoArgsConstructor
public class EntrGateWorkplaceVo {

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
  @ApiModelProperty(notes = "상태. 0: 미사용, 1: 사용")
  private Integer status;
  @ApiModelProperty(notes = "출입게이트 유형. 1: 안면인식, 2: QR Reader")
  private Integer gateType;
  @ApiModelProperty(notes = "관리자 사이트 URL")
  private String adminUrl;
  @ApiModelProperty(notes = "관리자 사이트 계정")
  private String adminId;
  @ApiModelProperty(notes = "관리자 사이트 비번")
  private String adminPwd;
  @ApiModelProperty(notes = "생성자")
  private String creator;
  @ApiModelProperty(notes = "생성일")
  private java.util.Date createDate;
  @ApiModelProperty(notes = "수정자")
  private String updater;
  @ApiModelProperty(notes = "수정일")
  private java.util.Date updateDate;

  @ApiModelProperty(notes = "API 호출 타입명")
  public String getGateTypeName(){
    EnterGateType enterGateType = EnterGateType.get(gateType);
    return enterGateType != null ? enterGateType.getName() : "";
  }
}
