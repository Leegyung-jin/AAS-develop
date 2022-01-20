package kr.co.hulan.aas.mvc.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@ApiModel(value="G5WriteNoticeDto", description="근로자 공지사항 정보")
@AllArgsConstructor
@NoArgsConstructor
public class G5WriteNoticeDto {

    private Integer wrId;
    private Integer wrNum;
    private String wrReply;
    private Integer wrParent;
    private Integer wrIsComment;
    private Integer wrComment;
    private String wrCommentReply;
    private String caName;
    private String wrOption;
    private String wrSubject;
    private String wrContent;
    private String wrLink1;
    private String wrLink2;
    private Integer wrLink1Hit;
    private Integer wrLink2Hit;
    @ApiModelProperty(notes = "조회수")
    private Integer wrHit;
    private Integer wrGood;
    private Integer wrNogood;
    @ApiModelProperty(notes = "작성자 아이디")
    private String mbId;
    private String wrPassword;
    @ApiModelProperty(notes = "작성자명")
    private String wrName;
    private String wrEmail;
    private String wrHomepage;
    @ApiModelProperty(notes = "생성일")
    private java.util.Date wrDatetime;
    private Integer wrFile;
    private String wrLast;
    @ApiModelProperty(notes = "작성시 IP")
    private String wrIp;
    private String wrFacebookUser;
    private String wrTwitterUser;
    @ApiModelProperty(notes = "긴급 알림 여부")
    private String wr1;
    @ApiModelProperty(notes = "현장 아이디")
    private String wr2;
    @ApiModelProperty(notes = "현장명")
    private String wr3;
    @ApiModelProperty(notes = "협력사 아이디")
    private String wr4;
    private String wr5;
    private String wr6;
    private String wr7;
    private String wr8;
    private String wr9;
    private String wr10;
    @ApiModelProperty(notes = "현장 전체 근로자 공지여부")
    private String allWorkplaceWorkerNotice;
    private String wr12;
    private String wr13;
    private String wr14;
    private String wr15;
    private String wr16;
    private String wr17;
    private String wr18;
    private String wr19;
    private String wr20;

    // derived
    @ApiModelProperty(notes = "공지여부")
    private String wrNotice;

}
