package kr.co.hulan.aas.mvc.api.entergate.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@ApiModel(value="EntrGateAccountListVo", description="출입게이트 계정 정보")
@AllArgsConstructor
@NoArgsConstructor
public class EntrGateAccountListVo {

  @ApiModelProperty(notes = "연동 계정 아이디")
  private String accountId;
  @ApiModelProperty(notes = "계정명")
  private String accountName;
  @ApiModelProperty(notes = "상태. 0: 미사용, 1: 사용")
  private Integer status;
  @ApiModelProperty(notes = "현장 수")
  private Integer workplaceCount;
  @ApiModelProperty(notes = "생성자")
  private String creator;
  @ApiModelProperty(notes = "생성일")
  private java.util.Date createDate;
  @ApiModelProperty(notes = "수정자")
  private String updater;
  @ApiModelProperty(notes = "수정일")
  private java.util.Date updateDate;
}
