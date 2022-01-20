package kr.co.hulan.aas.mvc.api.board.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Data
@ApiModel(value="WpBoardDto", description="현장게시글 정보")
@AllArgsConstructor
@NoArgsConstructor
public class WpBoardDto {

    @ApiModelProperty(notes = "현장게시글 번호")
    private Integer wrId;
    @ApiModelProperty(notes = "현장게시글 제목")
    private String wrSubject;
    @ApiModelProperty(notes = "현장게시글 내용")
    private String wrContent;
    @ApiModelProperty(notes = "카테고리명")
    private String caName;
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
    @ApiModelProperty(notes = "현장 아이디")
    private String wr2;
    @ApiModelProperty(notes = "현장명")
    private String wr3;
    @ApiModelProperty(notes = "협력사 아이디")
    private String wr4;
    @ApiModelProperty(notes = "협력사명")
    private String wr5;
    @ApiModelProperty(notes = "처리결과 ( 미입력,  승인 ,  미승인, 결과입력 )")
    private String wr6;
    @ApiModelProperty(notes = "등록자 등급. 3 : 현장 관리자, 4 : 협력사")
    private String wr9;
    @ApiModelProperty(notes = "조치/승인 내용")
    private String wr11;
    @ApiModelProperty(notes = "조치/승인일")
    private String wr12;

    @ApiModelProperty(notes = "첨부 파일 리스트")
    private List<AttachedFileDto> fileList = new ArrayList<AttachedFileDto>();


}
