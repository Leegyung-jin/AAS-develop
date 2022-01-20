package kr.co.hulan.aas.mvc.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="ImosNoticeDto", description="현장관제 공지 정보")
public class ImosNoticeDto {

  @ApiModelProperty(notes = "현장관제 공지번호")
  private Long imntNo;
  @ApiModelProperty(notes = "제목")
  private String subject;
  @ApiModelProperty(notes = "내용")
  private String content;
  @ApiModelProperty(notes = "중요도. 1: 1단계, 2: 2단계, 3: 3단계")
  private Integer importance;
  @ApiModelProperty(notes = "전체현장 공지여부. 0: 선택공지, 1: 전체공지")
  private Integer notiAllFlag;
  @ApiModelProperty(notes = "생성일")
  private java.util.Date createDate;
  @ApiModelProperty(notes = "생성자")
  private String creator;
  @ApiModelProperty(notes = "최종 수정일")
  private java.util.Date updateDate;
  @ApiModelProperty(notes = "최종 수정자")
  private String updater;

  // Derived
  @ApiModelProperty(notes = "현장명 요약")
  private String wpNameSummary;

}
