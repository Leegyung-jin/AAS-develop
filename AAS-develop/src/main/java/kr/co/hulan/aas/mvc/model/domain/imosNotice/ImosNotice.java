package kr.co.hulan.aas.mvc.model.domain.imosNotice;

import kr.co.hulan.aas.common.code.EnableCode;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Data
@Entity
@Table(name = "imos_notice")
public class ImosNotice {

  @Id
  @Basic
  @Column(name = "imnt_no", columnDefinition="bigint")
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Long imntNo;
  @Basic
  @Column(name = "subject", columnDefinition="varchar(128)")
  private String subject;
  @Basic
  @Column(name = "content", columnDefinition="text")
  private String content;
  @Basic
  @Column(name = "noti_all_flag", columnDefinition="tinyint")
  private Integer notiAllFlag;
  @Basic
  @Column(name = "importance", columnDefinition="tinyint")
  private Integer importance;
  @Basic
  @Column(name = "create_date", columnDefinition="datetime")
  private java.util.Date createDate;
  @Basic
  @Column(name = "creator", columnDefinition="varchar(20)")
  private String creator;
  @Basic
  @Column(name = "update_date", columnDefinition="datetime")
  private java.util.Date updateDate;
  @Basic
  @Column(name = "updater", columnDefinition="varchar(20)")
  private String updater;

  @PrePersist
  public void prePersist(){
    if( notiAllFlag == null ){
      notiAllFlag = EnableCode.DISABLED.getCode();
    }
    if( createDate == null ){
      createDate = new java.util.Date();
    }
    if( updateDate == null ){
      updateDate = new java.util.Date();
    }
  }
}
