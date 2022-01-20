package kr.co.hulan.aas.mvc.model.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

@NoArgsConstructor
@Data
@Entity
@IdClass(ImageAnalyticInfoKey.class)
@Table(name = "image_analytic_info")
public class ImageAnalyticInfo {

  @Id
  @Basic
  @Column(name = "wp_id", columnDefinition="varchar(50)")
  private String wpId;
  @Id
  @Basic
  @Column(name = "mac_address", columnDefinition="varchar(24)")
  private String macAddress;
  @Id
  @Basic
  @Column(name = "event_type", columnDefinition="tinyint(1)")
  private Integer eventType;
  @Basic
  @Column(name = "event_status", columnDefinition="tinyint(1)")
  private Integer eventStatus;
  @Basic
  @Column(name = "file_path", columnDefinition="varchar(128)")
  private String filePath;
  @Basic
  @Column(name = "file_name", columnDefinition="varchar(128)")
  private String fileName;
  @Basic
  @Column(name = "org_file_name", columnDefinition="varchar(128)")
  private String orgFileName;
  @Basic
  @Column(name = "event_datetime", columnDefinition="datetime")
  private java.util.Date eventDatetime;
  @Basic
  @Column(name = "event_view", columnDefinition="tinyint")
  private Integer eventView;

}
