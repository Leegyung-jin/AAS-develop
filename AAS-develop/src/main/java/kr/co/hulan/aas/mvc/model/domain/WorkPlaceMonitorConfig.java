package kr.co.hulan.aas.mvc.model.domain;

import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import org.apache.commons.lang3.StringUtils;

@NoArgsConstructor
@Data
@Entity
@Table(name = "work_place_monitor_config")
public class WorkPlaceMonitorConfig {

  @Id
  @Basic
  @Column(name = "wp_id", columnDefinition="varchar(50)")
  private String wpId;
  @Basic
  @Column(name = "support_ble", columnDefinition="tinyint")
  private Integer supportBle;
  @Basic
  @Column(name = "support_gps", columnDefinition="tinyint")
  private Integer supportGps;
  @Basic
  @Column(name = "support_3d", columnDefinition="tinyint")
  private Integer support3d;
  @Basic
  @Column(name = "current_worker_gps", columnDefinition="tinyint")
  private Integer currentWorkerGps;
  @Basic
  @Column(name = "current_worker_ble", columnDefinition="tinyint")
  private Integer currentWorkerBle;
  @Basic
  @Column(name = "environment_dust", columnDefinition="tinyint")
  private Integer environmentDust;
  @Basic
  @Column(name = "environment_noise", columnDefinition="tinyint")
  private Integer environmentNoise;
  @Basic
  @Column(name = "environment_gas", columnDefinition="tinyint")
  private Integer environmentGas;
  @Basic
  @Column(name = "falling_event", columnDefinition="tinyint")
  private Integer fallingEvent;
  @Basic
  @Column(name = "kalman_filter", columnDefinition="tinyint")
  private Integer kalmanFilter;
  @Basic
  @Column(name = "nvr_event", columnDefinition="tinyint")
  private Integer nvrEvent;
  @Basic
  @Column(name = "nx", columnDefinition="int")
  private Integer nx;
  @Basic
  @Column(name = "ny", columnDefinition="int")
  private Integer ny;
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
  public void prePersist() {
    if(supportBle == null ){
      supportBle = 0;
    }
    if(supportGps == null ){
      supportGps = 0;
    }
    if(support3d == null ){
      support3d = 0;
    }
    if(currentWorkerGps == null ){
      currentWorkerGps = 0;
    }
    if(currentWorkerBle == null ){
      currentWorkerBle = 0;
    }
    if(environmentDust == null ){
      environmentDust = 0;
    }
    if(environmentNoise == null ){
      environmentNoise = 0;
    }
    if(environmentGas == null ){
      environmentGas = 0;
    }
    if(fallingEvent == null ){
      fallingEvent = 0;
    }
    if(kalmanFilter == null ){
      kalmanFilter = 0;
    }
    if(nvrEvent == null ){
      nvrEvent = 0;
    }
    if(createDate == null ){
      createDate = new java.util.Date();
    }
    if(updateDate == null ){
      updateDate = new Date();
    }
  }

  @PreUpdate
  public void preUpdate(){
    prePersist();
  }
}
