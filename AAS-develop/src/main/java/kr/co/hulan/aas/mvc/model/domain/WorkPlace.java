package kr.co.hulan.aas.mvc.model.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Date;
import kr.co.hulan.aas.common.utils.GenerateIdUtils;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Data
@Entity
@Table(name = "work_place")
public class WorkPlace {

    @Id
    @Basic
    @Column(name = "wp_id", columnDefinition="varchar(50)")
    private String wpId;

    @Basic
    @Column(name = "wp_idx", columnDefinition="int(6)")
    private Integer wpIdx;

    @Basic
    @Column(name = "wp_cate", columnDefinition="varchar(50)")
    private String wpCate;

    @Basic
    @Column(name = "cc_id", columnDefinition="varchar(50)")
    private String ccId;

    @Basic
    @Column(name = "cc_name", columnDefinition="varchar(50)")
    private String ccName;
    @Basic
    @Column(name = "man_mb_id", columnDefinition="varchar(20)")
    private String manMbId;
    @Basic
    @Column(name = "man_mb_name", columnDefinition="varchar(20)")
    private String manMbName;
    @Basic
    @Column(name = "wp_name", columnDefinition="varchar(100)")
    private String wpName;
    @Basic
    @Column(name = "wp_sido", columnDefinition="varchar(30)")
    private String wpSido;
    @Basic
    @Column(name = "wp_gugun", columnDefinition="varchar(30)")
    private String wpGugun;
    @Basic
    @Column(name = "wp_addr", columnDefinition="varchar(255)")
    private String wpAddr;
    @Basic
    @Column(name = "wp_tel", columnDefinition="varchar(20)")
    private String wpTel;
    @Basic
    @Column(name = "wp_start_date", columnDefinition="date")
    private java.util.Date wpStartDate;
    @Basic
    @Column(name = "wp_end_date", columnDefinition="date")
    private java.util.Date wpEndDate;
    @Basic
    @Column(name = "wp_edutime_start", columnDefinition="time")
    private java.sql.Time wpEdutimeStart;
    @Basic
    @Column(name = "wp_edutime_end", columnDefinition="time")
    private java.sql.Time wpEdutimeEnd;
    @Basic
    @Column(name = "wp_end_status", columnDefinition="tinyint(4)")
    private Integer wpEndStatus;
    @Basic
    @Column(name = "wp_memo", columnDefinition="text")
    private String wpMemo;
    @Basic
    @Column(name = "wp_datetime", columnDefinition="datetime")
    private java.util.Date wpDatetime;
    @Basic
    @Column(name = "uninjury_record_date", columnDefinition="date")
    private java.util.Date uninjuryRecordDate;

    @Basic
    @Column(name = "uninjury_record_change", columnDefinition="date")
    private java.util.Date uninjuryRecordChange;

    @Basic
    @Column(name = "view_map_file_name", columnDefinition="varchar(255)")
    private String viewMapFileName;
    @Basic
    @Column(name = "view_map_file_name_org", columnDefinition="varchar(255)")
    private String viewMapFileNameOrg;
    @Basic
    @Column(name = "view_map_file_path", columnDefinition="varchar(255)")
    private String viewMapFilePath;
    @Basic
    @Column(name = "view_map_file_location", columnDefinition="integer")
    private Integer viewMapFileLocation;
    @Basic
    @Column(name = "default_coop_mb_id", columnDefinition="varchar(20)")
    private String defaultCoopMbId;

    @Basic
    @Column(name = "station_name", columnDefinition="varchar(32)")
    private String stationName;

    @Basic
    @Column(name = "activation_geofence_longitude", columnDefinition="double")
    private Double activationGeofenceLongitude;

    @Basic
    @Column(name = "activation_geofence_latitude", columnDefinition="double")
    private Double activationGeofenceLatitude;

    @Basic
    @Column(name = "activation_geofence_radius", columnDefinition="integer")
    private Integer activationGeofenceRadius;

    @Basic
    @Column(name = "office_no", columnDefinition="bigint")
    private Long officeNo;

    @Basic
    @Column(name = "construct_scale", columnDefinition="varchar(256)")
    private String  constructScale;

    @JsonIgnore
    public void createId(){
        this.wpId = GenerateIdUtils.getUuidKey();
    }

    @PrePersist
    public void prePersist(){
        if( wpDatetime == null ){
            wpDatetime = new Date();
        }
    }

    @PreUpdate
    public void preUpdate(){
        prePersist();
    }

}
