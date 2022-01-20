package kr.co.hulan.aas.mvc.model.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import org.springframework.util.StringUtils;

@NoArgsConstructor
@Data
@Entity
@Table(name = "tracker")
public class Tracker {

  @Id
  @Basic
  @Column(name = "tracker_id", columnDefinition="varchar(16)")
  private String trackerId;
  @Basic
  @Column(name = "device_model", columnDefinition="varchar(64)")
  private String deviceModel;
  @Basic
  @Column(name = "wp_id", columnDefinition="varchar(50)")
  private String wpId;
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
  public void prePresist(){
    if(StringUtils.isEmpty(wpId)){
      wpId = null;
    }
    if( createDate == null ){
      createDate = new java.util.Date();
    }
    if( updateDate == null ){
      updateDate = new java.util.Date();
    }
  }

  @PreUpdate
  public void preUpdate(){
    if(StringUtils.isEmpty(wpId)){
      wpId = null;
    }
    if( updateDate == null ){
      updateDate = new java.util.Date();
    }
  }

}
