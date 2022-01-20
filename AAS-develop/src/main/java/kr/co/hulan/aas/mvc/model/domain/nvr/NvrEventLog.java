package kr.co.hulan.aas.mvc.model.domain.nvr;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
@Table(name = "nvr_event_log")
public class NvrEventLog {

  @Id
  @Basic
  @Column(name = "elog_no", columnDefinition="bigint")
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Long elogNo;
  @Basic
  @Column(name = "elog_id", columnDefinition="varchar(64)")
  private String elogId;
  @Basic
  @Column(name = "tm", columnDefinition="datetime")
  private java.util.Date tm;
  @Basic
  @Column(name = "nvr_no", columnDefinition="bigint")
  private Long nvrNo;
  @Basic
  @Column(name = "uid", columnDefinition="integer")
  private Integer uid;
  @Basic
  @Column(name = "gid", columnDefinition="varchar(36)")
  private String gid;
  @Basic
  @Column(name = "id", columnDefinition="bigint")
  private Long id;
  @Basic
  @Column(name = "type", columnDefinition="varchar(2)")
  private String type;
  @Basic
  @Column(name = "stat", columnDefinition="varchar(2)")
  private String stat;
  @Basic
  @Column(name = "pattr", columnDefinition="varchar(8)")
  private String pattr;
  @Basic
  @Column(name = "ezn", columnDefinition="varchar(64)")
  private String ezn;
  @Basic
  @Column(name = "recv_host", columnDefinition="varchar(64)")
  private String recvHost;
  @Basic
  @Column(name = "create_date", columnDefinition="datetime")
  private java.util.Date createDate;

  @PrePersist
  public void prePersist(){
    if( createDate == null ){
      createDate = new java.util.Date();
    }
  }
}
