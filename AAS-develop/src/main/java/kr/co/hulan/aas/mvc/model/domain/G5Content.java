package kr.co.hulan.aas.mvc.model.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

@NoArgsConstructor
@Data
@Entity
@Table(name = "g5_content")
public class G5Content {

    @Id
    @Basic
    @Column(name = "co_id", columnDefinition="varchar(20)")
    private String coId;
    @Basic
    @Column(name = "co_html", columnDefinition="tinyint(4)")
    private Integer coHtml;
    @Basic
    @Column(name = "co_subject", columnDefinition="varchar(180)")
    private String coSubject;
    @Basic
    @Column(name = "co_content", columnDefinition="longtext")
    private String coContent;
    @Basic
    @Column(name = "co_mobile_content", columnDefinition="longtext")
    private String coMobileContent;
    @Basic
    @Column(name = "co_skin", columnDefinition="varchar(180)")
    private String coSkin;
    @Basic
    @Column(name = "co_mobile_skin", columnDefinition="varchar(180)")
    private String coMobileSkin;
    @Basic
    @Column(name = "co_tag_filter_use", columnDefinition="tinyint(4)")
    private Integer coTagFilterUse;
    @Basic
    @Column(name = "co_hit", columnDefinition="int(11)")
    private Integer coHit;
    @Basic
    @Column(name = "co_include_head", columnDefinition="varchar(180)")
    private String coIncludeHead;
    @Basic
    @Column(name = "co_include_tail", columnDefinition="varchar(180)")
    private String coIncludeTail;

}
