package kr.co.hulan.aas.mvc.model.domain;

import java.math.MathContext;
import java.math.RoundingMode;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@NoArgsConstructor
@Data
@Entity
@Table(name = "gps_check_policy_info")
public class GpsCheckPolicyInfo {

  @Id
  @Basic
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Column(name = "idx", columnDefinition="int(11)")
  private Integer idx;

  @Basic
  @Column(name = "wp_id", columnDefinition="varchar(50)")
  private String wpId;

  @Basic
  @Column(name = "gps_center_long", columnDefinition="double")
  private Double gpsCenterLong;

  @Basic
  @Column(name = "gps_center_lat", columnDefinition="double")
  private Double gpsCenterLat;

  @Basic
  @Column(name = "gps_dist_limit", columnDefinition="decimal(4)")
  private BigDecimal gpsDistLimit;

  @Basic
  @Column(name = "gps_round_long", columnDefinition="double")
  private Double gpsRoundLong;

  @Basic
  @Column(name = "gps_round_lat", columnDefinition="double")
  private Double gpsRoundLat;

  @Basic
  @Column(name = "gps_dist_limit_meter", columnDefinition="integer")
  private Integer gpsDistLimitMeter;

  @Basic
  @Column(name = "report_interval", columnDefinition="bigint(20)")
  private Long reportInterval;

  @Basic
  @Column(name = "gps_interval", columnDefinition="bigint(20)")
  private Long gpsInterval;

  @Basic
  @Column(name = "allow_start", columnDefinition="varchar(6)")
  private String allowStart;

  @Basic
  @Column(name = "allow_end", columnDefinition="varchar(6)")
  private String allowEnd;

  @Basic
  @Column(name = "upd_datetime", columnDefinition="datetime")
  private java.util.Date updDatetime;

  @Basic
  @Column(name = "updater", columnDefinition="varchar(50)")
  private String updater;



  @PrePersist
  public void prePersist(){
    if( gpsDistLimitMeter != null ){
      gpsDistLimit =  new BigDecimal(gpsDistLimitMeter).divide(new BigDecimal(1000), RoundingMode.UP);
    }
    else {
      gpsDistLimit = null;
    }
  }

  @PreUpdate
  public void preUpdate(){
    if( gpsDistLimitMeter != null ){
      gpsDistLimit =  new BigDecimal(gpsDistLimitMeter).divide(new BigDecimal(1000), RoundingMode.UP);
    }
    else {
      gpsDistLimit = null;
    }
  }

}
