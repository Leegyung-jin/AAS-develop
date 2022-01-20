package kr.co.hulan.aas.mvc.model.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

@NoArgsConstructor
@Data
@Entity
@Table(name = "g5_write_notice")
public class G5WriteNotice {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Basic
    @Column(name = "wr_id", columnDefinition="int(11)")
    private Integer wrId;
    @Basic
    @Column(name = "wr_num", columnDefinition="int(11)")
    private Integer wrNum;
    @Basic
    @Column(name = "wr_reply", columnDefinition="varchar(10)")
    private String wrReply;
    @Basic
    @Column(name = "wr_parent", columnDefinition="int(11)")
    private Integer wrParent;
    @Basic
    @Column(name = "wr_is_comment", columnDefinition="tinyint(4)")
    private Integer wrIsComment;
    @Basic
    @Column(name = "wr_comment", columnDefinition="int(11)")
    private Integer wrComment;
    @Basic
    @Column(name = "wr_comment_reply", columnDefinition="varchar(5)")
    private String wrCommentReply;
    @Basic
    @Column(name = "ca_name", columnDefinition="varchar(180)")
    private String caName;
    @Basic
    @Column(name = "wr_option", columnDefinition="set('html1', 'html2', 'secret', 'mail')")
    private String wrOption;
    @Basic
    @Column(name = "wr_subject", columnDefinition="varchar(180)")
    private String wrSubject;
    @Basic
    @Column(name = "wr_content", columnDefinition="text")
    private String wrContent;
    @Basic
    @Column(name = "wr_link1", columnDefinition="text")
    private String wrLink1;
    @Basic
    @Column(name = "wr_link2", columnDefinition="text")
    private String wrLink2;
    @Basic
    @Column(name = "wr_link1_hit", columnDefinition="int(11)")
    private Integer wrLink1Hit;
    @Basic
    @Column(name = "wr_link2_hit", columnDefinition="int(11)")
    private Integer wrLink2Hit;
    @Basic
    @Column(name = "wr_hit", columnDefinition="int(11)")
    private Integer wrHit;
    @Basic
    @Column(name = "wr_good", columnDefinition="int(11)")
    private Integer wrGood;
    @Basic
    @Column(name = "wr_nogood", columnDefinition="int(11)")
    private Integer wrNogood;
    @Basic
    @Column(name = "mb_id", columnDefinition="varchar(20)")
    private String mbId;
    @Basic
    @Column(name = "wr_password", columnDefinition="varchar(180)")
    private String wrPassword;
    @Basic
    @Column(name = "wr_name", columnDefinition="varchar(180)")
    private String wrName;
    @Basic
    @Column(name = "wr_email", columnDefinition="varchar(180)")
    private String wrEmail;
    @Basic
    @Column(name = "wr_homepage", columnDefinition="varchar(180)")
    private String wrHomepage;
    @Basic
    @Column(name = "wr_datetime", columnDefinition="datetime")
    private java.util.Date wrDatetime;
    @Basic
    @Column(name = "wr_file", columnDefinition="tinyint(4)")
    private Integer wrFile;
    @Basic
    @Column(name = "wr_last", columnDefinition="varchar(19)")
    private String wrLast;
    @Basic
    @Column(name = "wr_ip", columnDefinition="varchar(180)")
    private String wrIp;
    @Basic
    @Column(name = "wr_facebook_user", columnDefinition="varchar(180)")
    private String wrFacebookUser;
    @Basic
    @Column(name = "wr_twitter_user", columnDefinition="varchar(180)")
    private String wrTwitterUser;
    @Basic
    @Column(name = "wr_1", columnDefinition="varchar(180)")
    private String wr1;
    @Basic
    @Column(name = "wr_2", columnDefinition="varchar(180)")
    private String wr2;
    @Basic
    @Column(name = "wr_3", columnDefinition="varchar(180)")
    private String wr3;
    @Basic
    @Column(name = "wr_4", columnDefinition="varchar(180)")
    private String wr4;
    @Basic
    @Column(name = "wr_5", columnDefinition="varchar(180)")
    private String wr5;
    @Basic
    @Column(name = "wr_6", columnDefinition="varchar(180)")
    private String wr6;
    @Basic
    @Column(name = "wr_7", columnDefinition="varchar(180)")
    private String wr7;
    @Basic
    @Column(name = "wr_8", columnDefinition="varchar(180)")
    private String wr8;
    @Basic
    @Column(name = "wr_9", columnDefinition="varchar(180)")
    private String wr9;
    @Basic
    @Column(name = "wr_10", columnDefinition="varchar(180)")
    private String wr10;
    @Basic
    @Column(name = "wr_11", columnDefinition="varchar(180)")
    private String allWorkplaceWorkerNotice;
    @Basic
    @Column(name = "wr_12", columnDefinition="varchar(180)")
    private String wr12;
    @Basic
    @Column(name = "wr_13", columnDefinition="varchar(180)")
    private String wr13;
    @Basic
    @Column(name = "wr_14", columnDefinition="varchar(180)")
    private String wr14;
    @Basic
    @Column(name = "wr_15", columnDefinition="varchar(180)")
    private String wr15;
    @Basic
    @Column(name = "wr_16", columnDefinition="varchar(180)")
    private String wr16;
    @Basic
    @Column(name = "wr_17", columnDefinition="varchar(180)")
    private String wr17;
    @Basic
    @Column(name = "wr_18", columnDefinition="varchar(180)")
    private String wr18;
    @Basic
    @Column(name = "wr_19", columnDefinition="varchar(180)")
    private String wr19;
    @Basic
    @Column(name = "wr_20", columnDefinition="varchar(180)")
    private String wr20;

}
