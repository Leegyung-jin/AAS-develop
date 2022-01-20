package kr.co.hulan.aas.mvc.model.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

@NoArgsConstructor
@Data
@Entity
@Table(name = "risk_accssmnt_inspect")
public class RiskAccssmntInspect {

  @Id
  @Basic
  @Column(name = "rai_no", columnDefinition="bigint")
  private Long raiNo;
  @Basic
  @Column(name = "wp_id", columnDefinition="varchar(50)")
  private String wpId;
  @Basic
  @Column(name = "coop_mb_id", columnDefinition="varchar(20)")
  private String coopMbId;
  @Basic
  @Column(name = "start_date", columnDefinition="date")
  private java.util.Date startDate;
  @Basic
  @Column(name = "end_date", columnDefinition="date")
  private java.util.Date endDate;
  @Basic
  @Column(name = "man_mb_id", columnDefinition="varchar(20)")
  private String manMbId;
  @Basic
  @Column(name = "status", columnDefinition="int")
  private Integer status;
  @Basic
  @Column(name = "rai_apprvl_no", columnDefinition="bigint")
  private Integer raiApprvlNo;
  @Basic
  @Column(name = "create_date", columnDefinition="datetime")
  private java.util.Date createDate;
  @Basic
  @Column(name = "update_date", columnDefinition="datetime")
  private java.util.Date updateDate;

}
