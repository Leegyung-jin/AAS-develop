package kr.co.hulan.aas.mvc.model.domain;


import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Data
@Entity
@Table(name = "worker_work_print")
public class WorkerWorkPrint {

  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Basic
  @Column(name = "wwp_idx", columnDefinition="int(11)")
  private Integer wwpIdx;

  @Basic
  @Column(name = "cc_id", columnDefinition="varchar(50)")
  private String ccId;

  @Basic
  @Column(name = "cc_name", columnDefinition="varchar(50)")
  private String ccName;

  @Basic
  @Column(name = "wp_id", columnDefinition="varchar(50)")
  private String wpId;

  @Basic
  @Column(name = "wp_name", columnDefinition="varchar(50)")
  private String wpName;

  @Basic
  @Column(name = "coop_mb_id", columnDefinition="varchar(50)")
  private String coopMbId;

  @Basic
  @Column(name = "coop_mb_name", columnDefinition="varchar(50)")
  private String coopMbName;

  @Basic
  @Column(name = "wwp_data", columnDefinition = "text")
  private String wwpData;

  @Basic
  @Column(name = "wwp_status", columnDefinition="tinyint(4)")
  private Integer wwpStatus;

  @Basic
  @Column(name = "wwp_date", columnDefinition="date")
  private java.util.Date wwpDate;

  @Basic
  @Column(name = "wwp_updatetime", columnDefinition="datetime")
  private java.util.Date wwpUpdatetime;

}
