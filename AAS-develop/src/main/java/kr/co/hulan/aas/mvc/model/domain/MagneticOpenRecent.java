package kr.co.hulan.aas.mvc.model.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

@NoArgsConstructor
@Data
@Entity
@Table(name = "magnetic_open_recent")
public class MagneticOpenRecent {

  @Id
  @Basic
  @Column(name = "mac_address", columnDefinition="varchar(50)")
  private String macAddress;
  @Basic
  @Column(name = "measure_time", columnDefinition="timestamp")
  private java.util.Date measureTime;
  @Basic
  @Column(name = "display_name", columnDefinition="varchar(50)")
  private String displayName;
  @Basic
  @Column(name = "status", columnDefinition="tinyint")
  private Integer status;
  @Basic
  @Column(name = "dashboard_popup", columnDefinition="tinyint")
  private Integer dashboardPopup;

}
