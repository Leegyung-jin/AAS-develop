package kr.co.hulan.aas.mvc.model.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

@NoArgsConstructor
@Data
@Entity
@Table(name = "risk_accssmnt_inspect_apprvl")
public class RiskAccssmntInspectApprvl {

  @Id
  @Basic
  @Column(name = "rai_apprvl_no", columnDefinition="bigint")
  private Long raiApprvlNo;
  @Basic
  @Column(name = "rai_no", columnDefinition="bigint")
  private Long raiNo;
  @Basic
  @Column(name = "action", columnDefinition="int")
  private Integer action;
  @Basic
  @Column(name = "comment", columnDefinition="varchar(256)")
  private String comment;
  @Basic
  @Column(name = "create_date", columnDefinition="datetime")
  private java.util.Date createDate;
  @Basic
  @Column(name = "creator", columnDefinition="varchar(20)")
  private String creator;

}
