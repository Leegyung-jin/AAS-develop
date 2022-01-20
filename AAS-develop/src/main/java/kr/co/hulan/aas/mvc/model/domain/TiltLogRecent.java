package kr.co.hulan.aas.mvc.model.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

@NoArgsConstructor
@Data
@Entity
@Table(name = "tilt_log_recent")
public class TiltLogRecent {

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
  @Column(name = "tilt_x", columnDefinition="double")
  private Double tiltX;
  @Basic
  @Column(name = "tilt_y", columnDefinition="double")
  private Double tiltY;
  @Basic
  @Column(name = "tilt_z", columnDefinition="double")
  private Double tiltZ;
  @Basic
  @Column(name = "dashboard_popup", columnDefinition="tinyint")
  private Integer dashboardPopup;

}
