package kr.co.hulan.aas.mvc.model.domain.nvr;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
@Table(name = "nvr_event")
public class NvrEvent {

  @Id
  @Basic
  @Column(name = "event_no", columnDefinition="bigint")
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Long eventNo;
  @Basic
  @Column(name = "elog_id", columnDefinition="varchar(64)")
  private String elogId;
  @Basic
  @Column(name = "start_elog_no", columnDefinition="bigint")
  private Long startElogNo;
  @Basic
  @Column(name = "start_tm", columnDefinition="datetime")
  private java.util.Date startTm;
  @Basic
  @Column(name = "end_elog_no", columnDefinition="bigint")
  private Long endElogNo;
  @Basic
  @Column(name = "end_tm", columnDefinition="datetime")
  private java.util.Date endTm;
  @Basic
  @Column(name = "event_duration", columnDefinition="bigint")
  private Long eventDuration;
  @Basic
  @Column(name = "wp_id", columnDefinition="varchar(50)")
  private String wpId;
  @Basic
  @Column(name = "nvr_no", columnDefinition="bigint")
  private Long nvrNo;
  @Basic
  @Column(name = "nvr_name", columnDefinition="varchar(64)")
  private String nvrName;
  @Basic
  @Column(name = "gid", columnDefinition="varchar(40)")
  private String gid;
  @Basic
  @Column(name = "name", columnDefinition="varchar(128)")
  private String name;
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
  @Column(name = "action_status", columnDefinition="int")
  private Integer actionStatus;
  @Basic
  @Column(name = "action_method", columnDefinition="int")
  private Integer actionMethod;
  @Basic
  @Column(name = "action_end_date", columnDefinition="datetime")
  private java.util.Date actionEndDate;
  @Basic
  @Column(name = "action_end_treator", columnDefinition="varchar(20)")
  private String actionEndTreator;
  @Basic
  @Column(name = "memo", columnDefinition="varchar(1024)")
  private String memo;
  @Basic
  @Column(name = "create_date", columnDefinition="datetime")
  private java.util.Date createDate;
  @Basic
  @Column(name = "update_date", columnDefinition="datetime")
  private java.util.Date updateDate;
  @Basic
  @Column(name = "updater", columnDefinition="varchar(20)")
  private String updater;


  @PrePersist
  public void prePersist(){
    if( createDate == null ){
      createDate = new java.util.Date();
    }
    if( updateDate == null ){
      updateDate = new java.util.Date();
    }
  }


  @PreUpdate
  public void preUpdate(){
    prePersist();
  }
}
