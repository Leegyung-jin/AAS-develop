package kr.co.hulan.aas.mvc.model.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

@NoArgsConstructor
@Data
@Entity
@Table(name = "admin_msg_info")
public class AdminMsgInfo {

  @Id
  @Basic
  @Column(name = "idx", columnDefinition="int")
  private Integer idx;
  @Basic
  @Column(name = "mb_id", columnDefinition="varchar(50)")
  private String mbId;
  @Basic
  @Column(name = "wp_id", columnDefinition="varchar(50)")
  private String wpId;
  @Basic
  @Column(name = "coop_mb_id", columnDefinition="varchar(50)")
  private String coopMbId;
  @Basic
  @Column(name = "msg_type", columnDefinition="tinyint")
  private Integer msgType;
  @Basic
  @Column(name = "subject", columnDefinition="varchar(256)")
  private String subject;
  @Basic
  @Column(name = "msg", columnDefinition="varchar(4000)")
  private String msg;
  @Basic
  @Column(name = "is_send", columnDefinition="tinyint")
  private Integer isSend;
  @Basic
  @Column(name = "upt_datetime", columnDefinition="datetime")
  private java.util.Date uptDatetime;

}
