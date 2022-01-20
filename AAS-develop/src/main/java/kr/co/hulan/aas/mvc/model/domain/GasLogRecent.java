package kr.co.hulan.aas.mvc.model.domain;

import java.math.BigDecimal;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

@NoArgsConstructor
@Data
@Entity
@Table(name = "gas_log_recent")
public class GasLogRecent {

  @Id
  @Basic
  @Column(name = "mac_address", columnDefinition="varchar(50)")
  private String macAddress;
  @Basic
  @Column(name = "measure_time", columnDefinition="timestamp")
  private java.util.Date measureTime;
  @Basic
  @Column(name = "temperature", columnDefinition="double")
  private BigDecimal temperature;
  @Basic
  @Column(name = "humidity", columnDefinition="double")
  private BigDecimal humidity;
  @Basic
  @Column(name = "o2", columnDefinition="double")
  private BigDecimal o2;
  @Basic
  @Column(name = "h2s", columnDefinition="double")
  private BigDecimal h2S;
  @Basic
  @Column(name = "co", columnDefinition="double")
  private BigDecimal co;
  @Basic
  @Column(name = "ch4", columnDefinition="double")
  private BigDecimal ch4;
  @Basic
  @Column(name = "battery", columnDefinition="double")
  private BigDecimal battery;
  @Basic
  @Column(name = "temperature_level", columnDefinition="tinyint")
  private Integer temperatureLevel;
  @Basic
  @Column(name = "o2_level", columnDefinition="tinyint")
  private Integer o2Level;
  @Basic
  @Column(name = "h2s_level", columnDefinition="tinyint")
  private Integer h2SLevel;
  @Basic
  @Column(name = "co_level", columnDefinition="tinyint")
  private Integer coLevel;
  @Basic
  @Column(name = "ch4_level", columnDefinition="tinyint")
  private Integer ch4Level;
  @Basic
  @Column(name = "dashboard_popup", columnDefinition="tinyint")
  private Integer dashboardPopup;

}
