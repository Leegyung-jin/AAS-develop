package kr.co.hulan.aas.mvc.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@ApiModel(value="G5WriteWpboardDto", description="현장 게시판 정보")
@AllArgsConstructor
@NoArgsConstructor
public class G5WriteWpboardDto {

    private Integer wrId;
    //private Integer wrNum;
    //private String wrReply;
    private Integer wrParent;
    //private Integer wrIsComment;
    //private Integer wrComment;
    //private String wrCommentReply;
    private String caName;
    //private String wrOption;
    private String wrSubject;
    private String wrContent;
    //private String wrLink1;
    //private String wrLink2;
    //private Integer wrLink1Hit;
    //private Integer wrLink2Hit;
    private Integer wrHit;
    //private Integer wrGood;
    //private Integer wrNogood;
    private String mbId;
    private String wrPassword;
    private String wrName;
    //private String wrEmail;
    //private String wrHomepage;
    private java.util.Date wrDatetime;
    private Integer wrFile;
    //private String wrLast;
    private String wrIp;
    //private String wrFacebookUser;
    //private String wrTwitterUser;
    //private String wr1;
    private String wr2;
    private String wr3;
    private String wr4;
    private String wr5;
    private String wr6;
    //private String wr7;
    //private String wr8;
    private String wr9;
    //private String wr10;
    private String wr11;
    private java.util.Date wr12;
    //private String wr13;
    //private String wr14;
    //private String wr15;
    //private String wr16;
    //private String wr17;
    //private String wr18;
    //private String wr19;
    //private String wr20;


}
