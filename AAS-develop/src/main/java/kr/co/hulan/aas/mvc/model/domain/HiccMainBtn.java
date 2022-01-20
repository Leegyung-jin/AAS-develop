package kr.co.hulan.aas.mvc.model.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

@NoArgsConstructor
@Data
@Entity
@Table(name = "hicc_main_btn")
public class HiccMainBtn {

  @Id
  @Basic
  @Column(name = "hbtn_no", columnDefinition="bigint")
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Long hbtnNo;
  @Basic
  @Column(name = "hicc_no", columnDefinition="bigint")
  private Long hiccNo;
  @Basic
  @Column(name = "hbtn_name", columnDefinition="varchar(16)")
  private String hbtnName;
  @Basic
  @Column(name = "hbtn_link_url", columnDefinition="varchar(256)")
  private String hbtnLinkUrl;
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
