package kr.co.hulan.aas.mvc.model.domain;

import kr.co.hulan.aas.common.code.EnableCode;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

@NoArgsConstructor
@Data
@Entity
@Table(name = "work_place_cctv")
public class WorkPlaceCctv {

  @Id
  @Basic
  @Column(name = "cctv_no", columnDefinition="bigint")
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Long cctvNo;
  @Basic
  @Column(name = "cctv_name", columnDefinition="varchar(128)")
  private String cctvName;
  @Basic
  @Column(name = "cctv_kind", columnDefinition="integer")
  private Integer cctvKind;
  @Basic
  @Column(name = "cctv_url", columnDefinition="varchar(512)")
  private String cctvUrl;
  @Basic
  @Column(name = "description", columnDefinition="varchar(1024)")
  private String description;
  @Basic
  @Column(name = "wp_id", columnDefinition="varchar(50)")
  private String wpId;
  @Basic
  @Column(name = "status", columnDefinition="integer")
  private Integer status;
  @Basic
  @Column(name = "ptz_status", columnDefinition="integer")
  private Integer ptzStatus;
  @Basic
  @Column(name = "gid", columnDefinition="varchar(40)")
  private String gid;
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
    if( status == null ){
      this.status = EnableCode.ENABLED.getCode();
    }
    if( ptzStatus == null ){
      this.ptzStatus = EnableCode.DISABLED.getCode();
    }
    if( this.createDate == null ){
      this.createDate = new java.util.Date();
    }
    if( this.updateDate == null ){
      this.updateDate = new java.util.Date();
    }
  }

}

