package kr.co.hulan.aas.mvc.model.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Data
@Entity
@Table(name = "entr_gate_workplace")
public class EntrGateWorkplace {

  @Id
  @Basic
  @Column(name = "wp_id", columnDefinition="varchar(50)")
  private String wpId;
  @Basic
  @Column(name = "account_id", columnDefinition="varchar(16)")
  private String accountId;
  @Basic
  @Column(name = "mapping_cd", columnDefinition="varchar(32)")
  private String mappingCd;
  @Basic
  @Column(name = "status", columnDefinition="int")
  private Integer status;
  @Basic
  @Column(name = "gate_type", columnDefinition="tinyint")
  private Integer gateType;
  @Basic
  @Column(name = "admin_url", columnDefinition="varchar(128)")
  private String adminUrl;
  @Basic
  @Column(name = "admin_id", columnDefinition="varchar(16)")
  private String adminId;
  @Basic
  @Column(name = "admin_pwd", columnDefinition="varchar(64)")
  private String adminPwd;
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

  /*
  @ManyToOne
  @JoinColumn(name = "account_id")
  private EntrGateAccount entrGateAccount;
   */
}
