package kr.co.hulan.aas.infra.broker.vo;

import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ImosNoticeEventDto {

  @ApiModelProperty(notes = "현장 공지사항 넘버")
  private Long imntNo;
  @ApiModelProperty(notes = "제목")
  private String subject;
  @ApiModelProperty(notes = "내용")
  private String content;
  @ApiModelProperty(notes = "중요도. 1: 1단계, 2: 2단계, 3: 3단계")
  private Integer importance;
  @ApiModelProperty(notes = "전체현장 공지여부. 0: 선택공지, 1: 전체공지")
  private Integer notiAllFlag;

  @ApiModelProperty(notes = "전송 현장 아이디 리스트. 선택공지일 경우 유효")
  private List<String> workplaceList;

}
