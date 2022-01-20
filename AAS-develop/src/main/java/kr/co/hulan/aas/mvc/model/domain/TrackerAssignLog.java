package kr.co.hulan.aas.mvc.model.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Data
@Entity
@Table(name = "tracker_assign_log")
public class TrackerAssignLog {

  @Id
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  @Basic
  @Column(name = "tal_no", columnDefinition="bigint")
  private Long talNo;
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
  @Column(name = "assign_date", columnDefinition="datetime")
  private java.util.Date assignDate;
  @Basic
  @Column(name = "assigner", columnDefinition="varchar(20)")
  private String assigner;
  @Basic
  @Column(name = "collect_date", columnDefinition="datetime")
  private java.util.Date collectDate;
  @Basic
  @Column(name = "collector", columnDefinition="varchar(20)")
  private String collector;

}
