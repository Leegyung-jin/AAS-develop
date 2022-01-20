package kr.co.hulan.aas.mvc.api.hicc.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import kr.co.hulan.aas.mvc.model.dto.ImosNoticeFileDto;
import kr.co.hulan.aas.mvc.model.dto.ImosNoticeWorkplaceDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="ImosNoticeVo", description="현장 공지사항")
public class ImosNoticeVo {

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

  @ApiModelProperty(notes = "전송 현장 리스트. 선택공지일 경우 유효")
  private List<ImosNoticeWorkplaceDto> noticeWorkplaceList;

  @ApiModelProperty(notes = "첨부파일 리스트")
  private List<ImosNoticeFileDto> detachedFileList;

}
