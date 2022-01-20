package kr.co.hulan.aas.mvc.model.domain.imosNotice;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Data
@Entity
@IdClass(ImosNoticeWorkplaceKey.class)
@Table(name = "imos_notice_workplace")
public class ImosNoticeWorkplace {

  @Id
  @Basic
  @Column(name = "imnt_no", columnDefinition="bigint")
  private Long imntNo;
  @Id
  @Basic
  @Column(name = "wp_id", columnDefinition="varchar(50)")
  private String wpId;
  @Basic
  @Column(name = "create_date", columnDefinition="datetime")
  private java.util.Date createDate;
  @Basic
  @Column(name = "creator", columnDefinition="varchar(20)")
  private String creator;

  @PrePersist
  public void prePersist(){
    if( createDate == null ){
      createDate = new java.util.Date();
    }
  }

}
