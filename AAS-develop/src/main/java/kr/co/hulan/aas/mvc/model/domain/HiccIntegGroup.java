package kr.co.hulan.aas.mvc.model.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

@NoArgsConstructor
@Data
@Entity
@Table(name = "hicc_integ_group")
public class HiccIntegGroup {

  @Id
  @Basic
  @Column(name = "hrg_no", columnDefinition="bigint")
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Long hrgNo;
  @Basic
  @Column(name = "hrg_name", columnDefinition="varchar(32)")
  private String hrgName;
  @Basic
  @Column(name = "hicc_no", columnDefinition="bigint")
  private Long hiccNo;
  @Basic
  @Column(name = "order_seq", columnDefinition="integer")
  private Integer orderSeq;

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
