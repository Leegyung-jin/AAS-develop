package kr.co.hulan.aas.mvc.api.board.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@ApiModel(value="NoticeDto", description="근로자 공지사항 정보")
@AllArgsConstructor
@NoArgsConstructor
public class NoticeDto {

    @ApiModelProperty(notes = "공지사항 번호")
    private Integer wrId;
    @ApiModelProperty(notes = "공지사항 제목")
    private String wrSubject;
    @ApiModelProperty(notes = "공지사항 내용")
    private String wrContent;
    @ApiModelProperty(notes = "조회수")
    private Integer wrHit;
    @ApiModelProperty(notes = "작성자 아이디")
    private String mbId;
    @ApiModelProperty(notes = "작성자명")
    private String wrName;
    @ApiModelProperty(notes = "생성일")
    private java.util.Date wrDatetime;
    @ApiModelProperty(notes = "첨부 파일 수")
    private Integer wrFile;
    @ApiModelProperty(notes = "작성시 IP")
    private String wrIp;
    @ApiModelProperty(notes = "긴급 알림 여부")
    private String wr1;
    @ApiModelProperty(notes = "현장 아이디")
    private String wr2;
    @ApiModelProperty(notes = "현장명")
    private String wr3;
    @ApiModelProperty(notes = "협력사 아이디")
    private String wr4;
    @ApiModelProperty(notes = "현장 전체 근로자 공지여부")
    private String allWorkplaceWorkerNotice;

    @ApiModelProperty(notes = "공지여부")
    private String wrNotice;

    @ApiModelProperty(notes = "첨부 파일 리스트")
    private List<AttachedFileDto> fileList;

}
