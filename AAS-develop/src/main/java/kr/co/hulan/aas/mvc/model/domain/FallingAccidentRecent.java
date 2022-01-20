package kr.co.hulan.aas.mvc.model.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

@NoArgsConstructor
@Data
@Entity
@Table(name = "falling_accident_recent")
public class FallingAccidentRecent {

  @Id
  @Basic
  @Column(name = "mb_id", columnDefinition="varchar(20)")
  private String mbId;
  @Basic
  @Column(name = "wp_id", columnDefinition="varchar(50)")
  private String wpId;
  @Basic
  @Column(name = "measure_time", columnDefinition="timestamp")
  private java.util.Date measureTime;
  @Basic
  @Column(name = "dashboard_popup", columnDefinition="tinyint")
  private Integer dashboardPopup;

}
