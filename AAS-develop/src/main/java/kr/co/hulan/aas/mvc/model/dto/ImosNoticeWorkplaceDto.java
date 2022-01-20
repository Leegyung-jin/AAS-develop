package kr.co.hulan.aas.mvc.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="ImosNoticeWorkplaceDto", description="현장관제 공지 대상 현장 정보")
public class ImosNoticeWorkplaceDto {

  @ApiModelProperty(notes = "현장관제 공지번호")
  private Long imntNo;

  @NotBlank(message = "현장 아이디는 필수입니다.")
  @ApiModelProperty(notes = "현장 아이디", required = true)
  private String wpId;

  @ApiModelProperty(notes = "현장명")
  private String wpName;

  @ApiModelProperty(notes = "생성일")
  private java.util.Date createDate;

  @ApiModelProperty(notes = "생성자")
  private String creator;
}
