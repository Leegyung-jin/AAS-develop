package kr.co.hulan.aas.mvc.model.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

@NoArgsConstructor
@Data
@Entity
@IdClass(WorkGeofenceGroupKey.class)
@Table(name = "work_geofence_group")
public class WorkGeofenceGroup {

  @Id
  @Basic
  @Column(name = "wp_id", columnDefinition="varchar(50)")
  private String wpId;
  @Id
  @Basic
  @Column(name = "wp_seq", columnDefinition="integer")
  private Integer wpSeq;
  @Basic
  @Column(name = "fence_name", columnDefinition="varchar(32)")
  private String fenceName;
  @Basic
  @Column(name = "center_latitude", columnDefinition="double")
  private Double centerLatitude;
  @Basic
  @Column(name = "center_longitude", columnDefinition="double")
  private Double centerLongitude;
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

}
