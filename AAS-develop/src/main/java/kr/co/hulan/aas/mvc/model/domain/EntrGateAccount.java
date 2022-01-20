package kr.co.hulan.aas.mvc.model.domain;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

@NoArgsConstructor
@Data
@Entity
@Table(name = "entr_gate_account")
public class EntrGateAccount {

  @Id
  @Basic
  @Column(name = "account_id", columnDefinition="varchar(16)")
  private String accountId;
  @Basic
  @Column(name = "account_pwd", columnDefinition="varchar(64)")
  private String accountPwd;
  @Basic
  @Column(name = "account_name", columnDefinition="varchar(32)")
  private String accountName;
  @Basic
  @Column(name = "description", columnDefinition="varchar(1024)")
  private String description;
  @Basic
  @Column(name = "status", columnDefinition="int")
  private Integer status;
  @Basic
  @Column(name = "creator", columnDefinition="varchar(20)")
  private String creator;
  @Basic
  @Column(name = "create_date", columnDefinition="datetime")
  private java.util.Date createDate;
  @Basic
  @Column(name = "updater", columnDefinition="varchar(20)")
  private String updater;
  @Basic
  @Column(name = "update_date", columnDefinition="datetime")
  private java.util.Date updateDate;

  /*
  @OneToMany(mappedBy = "entrGateAccount", cascade = CascadeType.ALL, orphanRemoval = true)
  @JoinColumn(name = "account_id")
  private List<EntrGateWorkplace> workplaceList = new ArrayList<>();
   */

}
