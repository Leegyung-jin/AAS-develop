package kr.co.hulan.aas.mvc.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.persistence.Basic;
import javax.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@ApiModel(value="HiccMainBtnDto", description="통합 관제 메인 버튼 정보")
@AllArgsConstructor
@NoArgsConstructor
public class HiccMainBtnDto {

  @ApiModelProperty(notes = "버튼 관리넘버")
  private Long hbtnNo;
  @ApiModelProperty(notes = "통합관제 관리넘버")
  private Long hiccNo;
  @ApiModelProperty(notes = "버튼명")
  private String hbtnName;
  @ApiModelProperty(notes = "버튼 링크 URL")
  private String hbtnLinkUrl;
  @ApiModelProperty(notes = "생성일")
  private java.util.Date createDate;
  @ApiModelProperty(notes = "생성자")
  private String creator;
  @ApiModelProperty(notes = "수정일")
  private java.util.Date updateDate;
  @ApiModelProperty(notes = "수정자")
  private String updater;

}
