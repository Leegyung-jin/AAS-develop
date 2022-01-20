package kr.co.hulan.aas.mvc.model.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.util.Date;

@NoArgsConstructor
@Data
@Entity
@Table(name = "work_place_worker")
public class WorkPlaceWorker {

    @Id
    @Basic
    @Column(name = "wpw_id", columnDefinition="varchar(50)")
    private String wpwId;
    @Basic
    @Column(name = "wp_id", columnDefinition="varchar(50)")
    private String wpId;
    @Basic
    @Column(name = "wp_name", columnDefinition="varchar(50)")
    private String wpName;
    @Basic
    @Column(name = "wpc_id", columnDefinition="varchar(50)")
    private String wpcId;
    @Basic
    @Column(name = "coop_mb_id", columnDefinition="varchar(50)")
    private String coopMbId;
    @Basic
    @Column(name = "coop_mb_name", columnDefinition="varchar(50)")
    private String coopMbName;
    @Basic
    @Column(name = "wpc_work", columnDefinition="varchar(50)")
    private String wpcWork;
    @Basic
    @Column(name = "worker_mb_id", columnDefinition="varchar(20)")
    private String workerMbId;
    @Basic
    @Column(name = "worker_mb_name", columnDefinition="varchar(50)")
    private String workerMbName;
    @Basic
    @Column(name = "wpw_out", columnDefinition="tinyint(4)")
    private Integer wpwOut;
    @Basic
    @Column(name = "wpw_out_memo", columnDefinition="varchar(255)")
    private String wpwOutMemo;
    @Basic
    @Column(name = "wpw_bp", columnDefinition="tinyint(4)")
    private Integer wpwBp;
    @Basic
    @Column(name = "wpw_oper", columnDefinition="tinyint(4)")
    private Integer wpwOper;
    @Basic
    @Column(name = "wpw_dis1", columnDefinition="tinyint(4)")
    private Integer wpwDis1;
    @Basic
    @Column(name = "wpw_dis2", columnDefinition="tinyint(4)")
    private Integer wpwDis2;
    @Basic
    @Column(name = "wpw_dis3", columnDefinition="tinyint(4)")
    private Integer wpwDis3;
    @Basic
    @Column(name = "wpw_dis4", columnDefinition="tinyint(4)")
    private Integer wpwDis4;
    @Basic
    @Column(name = "wpw_show", columnDefinition="tinyint(4)")
    private Integer wpwShow;
    @Basic
    @Column(name = "wpw_status", columnDefinition="tinyint(4)")
    private Integer wpwStatus;
    @Basic
    @Column(name = "wpw_datetime", columnDefinition="datetime")
    private java.util.Date wpwDatetime;
    @Basic
    @Column(name = "work_section_b", columnDefinition="varchar(8)")
    private String workSectionB;

    @PrePersist
    public void prePersist() {
        if( wpwShow == null ){
            wpwShow = 1;
        }
        if( wpwStatus == null ){
            wpwStatus = 1;
        }
        if( wpwDatetime == null ){
            wpwDatetime = new Date();
        }
        if(StringUtils.isEmpty(workSectionB) ){
            workSectionB = null;
        }
    }
}
