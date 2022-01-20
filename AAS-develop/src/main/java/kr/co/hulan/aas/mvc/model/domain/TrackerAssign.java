package kr.co.hulan.aas.mvc.model.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

@NoArgsConstructor
@Data
@Entity
@Table(name = "tracker_assign")
public class TrackerAssign {

  @Id
  @Basic
  @Column(name = "tracker_id", columnDefinition="varchar(16)")
  private String trackerId;
  @Basic
  @Column(name = "wp_id", columnDefinition="varchar(50)")
  private String wpId;
  @Basic
  @Column(name = "coop_mb_id", columnDefinition="varchar(20)")
  private String coopMbId;
  @Basic
  @Column(name = "mb_id", columnDefinition="varchar(20)")
  private String mbId;
  @Basic
  @Column(name = "tal_no", columnDefinition="bigint")
  private Long talNo;

}
