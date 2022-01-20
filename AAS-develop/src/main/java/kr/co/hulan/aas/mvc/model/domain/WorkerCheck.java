package kr.co.hulan.aas.mvc.model.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

@NoArgsConstructor
@Data
@Entity
@Table(name = "worker_check")
public class WorkerCheck {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "wc_idx", columnDefinition="int(11)")
    private Integer wcIdx;
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
    @Column(name = "wc_type", columnDefinition="integer(11)")
    private Integer wcType;
    @Basic
    @Column(name = "wpc_id", columnDefinition="varchar(50)")
    private String wpcId;
    @Basic
    @Column(name = "mb_id", columnDefinition="varchar(20)")
    private String mbId;
    @Basic
    @Column(name = "mb_name", columnDefinition="varchar(20)")
    private String mbName;
    @Basic
    @Column(name = "worker_mb_id", columnDefinition="varchar(20)")
    private String workerMbId;
    @Basic
    @Column(name = "worker_mb_name", columnDefinition="varchar(20)")
    private String workerMbName;
    @Basic
    @Column(name = "wc_memo", columnDefinition="text")
    private String wcMemo;
    @Basic
    @Column(name = "wc_datetime", columnDefinition="datetime")
    private java.util.Date wcDatetime;

}
