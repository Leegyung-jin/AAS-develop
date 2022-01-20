package kr.co.hulan.aas.mvc.model.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

@NoArgsConstructor
@Data
@Entity
@Table(name = "wind_speed_recent")
public class WindSpeedRecent {

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
  @Column(name = "recently_avg", columnDefinition="double")
  private Long recentlyAvg;
  @Basic
  @Column(name = "recently_max", columnDefinition="double")
  private Long recentlyMax;
  @Basic
  @Column(name = "latest_avg", columnDefinition="double")
  private Long latestAvg;
  @Basic
  @Column(name = "latest_max", columnDefinition="double")
  private Long latestMax;
  @Basic
  @Column(name = "dashboard_popup", columnDefinition="tinyint")
  private Integer dashboardPopup;

}
