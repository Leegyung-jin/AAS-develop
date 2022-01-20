package kr.co.hulan.aas.mvc.model.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

@NoArgsConstructor
@Data
@Entity
@Table(name = "risk_accssmnt_item")
public class RiskAccssmntItem {

  @Id
  @Basic
  @Column(name = "ra_item_no", columnDefinition="bigint")
  private Long raItemNo;
  @Basic
  @Column(name = "section", columnDefinition="varchar(32)")
  private String section;
  @Basic
  @Column(name = "work_name", columnDefinition="varchar(64)")
  private String workName;
  @Basic
  @Column(name = "work_detail", columnDefinition="varchar(128)")
  private String workDetail;
  @Basic
  @Column(name = "risk_factor", columnDefinition="varchar(128)")
  private String riskFactor;
  @Basic
  @Column(name = "occur_type", columnDefinition="varchar(8)")
  private String occurType;
  @Basic
  @Column(name = "safety_measure", columnDefinition="varchar(128)")
  private String safetyMeasure;
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
