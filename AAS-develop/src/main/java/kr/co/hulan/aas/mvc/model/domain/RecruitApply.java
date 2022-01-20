package kr.co.hulan.aas.mvc.model.domain;

import java.util.Date;
import kr.co.hulan.aas.common.code.RecruitApplyStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Data
@Entity
@Table(name = "recruit_apply")
public class RecruitApply {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Basic
    @Column(name = "ra_idx", columnDefinition="int(11)")
    private Integer raIdx;
    @Basic
    @Column(name = "rc_idx", columnDefinition="int(11)")
    private Integer rcIdx;
    @Basic
    @Column(name = "wp_id", columnDefinition="varchar(50)")
    private String wpId;
    @Basic
    @Column(name = "wp_name", columnDefinition="varchar(100)")
    private String wpName;
    @Basic
    @Column(name = "coop_mb_id", columnDefinition="varchar(20)")
    private String coopMbId;
    @Basic
    @Column(name = "coop_mb_name", columnDefinition="varchar(50)")
    private String coopMbName;
    @Basic
    @Column(name = "mb_id", columnDefinition="varchar(20)")
    private String mbId;
    @Basic
    @Column(name = "mb_name", columnDefinition="varchar(20)")
    private String mbName;
    @Basic
    @Column(name = "mb_birth", columnDefinition="varchar(20)")
    private String mbBirth;
    @Basic
    @Column(name = "ra_datetime", columnDefinition="datetime")
    private java.util.Date raDatetime;
    @Basic
    @Column(name = "ra_status", columnDefinition="varchar(10)")
    private String raStatus;

    @PrePersist
    public void prePersist(){
        if( raDatetime == null ){
            raDatetime = new Date();
        }
        if( rcIdx == null ){
            rcIdx = 0;
        }
        if( raStatus == null ){
            raStatus = RecruitApplyStatus.READY.getCode();
        }
    }
}
