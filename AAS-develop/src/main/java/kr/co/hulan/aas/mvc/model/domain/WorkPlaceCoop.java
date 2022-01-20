package kr.co.hulan.aas.mvc.model.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.util.Date;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "work_place_coop")
public class WorkPlaceCoop {

    @Id
    @Basic
    @Column(name = "wpc_id", columnDefinition="varchar(50)")
    private String wpcId;
    @Basic
    @Column(name = "wp_id", columnDefinition="varchar(50)")
    private String wpId;
    @Basic
    @Column(name = "cc_id", columnDefinition="varchar(50)")
    private String ccId;
    @Basic
    @Column(name = "wp_name", columnDefinition="varchar(50)")
    private String wpName;
    @Basic
    @Column(name = "coop_mb_id", columnDefinition="varchar(20)")
    private String coopMbId;
    @Basic
    @Column(name = "coop_mb_name", columnDefinition="varchar(20)")
    private String coopMbName;
    @Basic
    @Column(name = "work_section_a", columnDefinition="varchar(8)")
    private String workSectionA;
    @Basic
    @Column(name = "wpc_work", columnDefinition="varchar(255)")
    private String wpcWork;
    @Basic
    @Column(name = "wpc_datetime", columnDefinition="datetime")
    private java.util.Date wpcDatetime;

    @PrePersist
    public void prePersist() {
        if(StringUtils.isEmpty(workSectionA)){
            workSectionA = null;
        }
        if(wpcDatetime == null ){
            wpcDatetime = new Date();
        }
    }

}
