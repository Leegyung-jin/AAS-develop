package kr.co.hulan.aas.mvc.model.domain;

import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import javax.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import org.apache.commons.lang3.StringUtils;

@NoArgsConstructor
@Data
@Entity
@Table(name = "work_equipment_info")
public class WorkEquipmentInfo {

    @Id
    @Basic
    @Column(name = "idx", columnDefinition="int(11)")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer idx;
    @Basic
    @Column(name = "wp_id", columnDefinition="varchar(50)")
    private String wpId;
    @Basic
    @Column(name = "coop_mb_id", columnDefinition="varchar(50)")
    private String coopMbId;
    @Basic
    @Column(name = "mb_id", columnDefinition="varchar(50)")
    private String mbId;
    @Basic
    @Column(name = "equipment_type", columnDefinition="tinyint(4)")
    private Integer equipmentType;
    @Basic
    @Column(name = "equipment_no", columnDefinition="varchar(12)")
    private String equipmentNo;
    @Basic
    @Column(name = "device_id", columnDefinition="varchar(50)")
    private String deviceId;
    @Basic
    @Column(name = "mac_address", columnDefinition="varchar(50)")
    private String macAddress;
    @Basic
    @Column(name = "ope_type", columnDefinition="tinyint")
    private Integer opeType;
    @Basic
    @Column(name = "ope_start", columnDefinition="date")
    private java.util.Date opeStart;
    @Basic
    @Column(name = "ope_end", columnDefinition="date")
    private Date opeEnd;
    @Basic
    @Column(name = "`desc`", columnDefinition="varchar(50)")
    private String desc;
    @Basic
    @Column(name = "rtc_url", columnDefinition="varchar(128)")
    private String rtcUrl;
    @Basic
    @Column(name = "upd_datetime", columnDefinition="datetime")
    private java.util.Date updDatetime;
    @Basic
    @Column(name = "updater", columnDefinition="varchar(50)")
    private String updater;

    @PrePersist
    public void prePersist() {
        if(StringUtils.isEmpty(mbId)){
            mbId = "";
        }

        if(updDatetime == null ){
            updDatetime = new Date();
        }
    }

    @PreUpdate
    public void preUpdate(){
        prePersist();
    }

}
