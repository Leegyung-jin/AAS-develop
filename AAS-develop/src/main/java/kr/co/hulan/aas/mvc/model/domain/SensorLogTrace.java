package kr.co.hulan.aas.mvc.model.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

@NoArgsConstructor
@Data
@Entity
@Table(name = "sensor_log_trace")
public class SensorLogTrace {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Basic
    @Column(name = "slt_idx", columnDefinition="int(11)")
    private Integer sltIdx;
    @Basic
    @Column(name = "sd_idx", columnDefinition="int(11)")
    private Integer sdIdx;
    @Basic
    @Column(name = "sd_name", columnDefinition="varchar(100)")
    private String sdName;
    @Basic
    @Column(name = "si_code", columnDefinition="varchar(50)")
    private String siCode;
    @Basic
    @Column(name = "si_type", columnDefinition="varchar(50)")
    private String siType;
    @Basic
    @Column(name = "si_place1", columnDefinition="varchar(50)")
    private String siPlace1;
    @Basic
    @Column(name = "si_place2", columnDefinition="varchar(50)")
    private String siPlace2;
    @Basic
    @Column(name = "wp_id", columnDefinition="varchar(50)")
    private String wpId;
    @Basic
    @Column(name = "wp_name", columnDefinition="varchar(100)")
    private String wpName;
    @Basic
    @Column(name = "cc_id", columnDefinition="varchar(50)")
    private String ccId;
    @Basic
    @Column(name = "cc_name", columnDefinition="varchar(50)")
    private String ccName;
    @Basic
    @Column(name = "wpw_id", columnDefinition="varchar(50)")
    private String wpwId;
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
    @Column(name = "mb_name", columnDefinition="varchar(50)")
    private String mbName;
    @Basic
    @Column(name = "slt_in_sd_idx", columnDefinition="int(11)")
    private Integer sltInSdIdx;
    @Basic
    @Column(name = "slt_in_sd_name", columnDefinition="varchar(50)")
    private String sltInSdName;
    @Basic
    @Column(name = "slt_in_si_code", columnDefinition="varchar(50)")
    private String sltInSiCode;
    @Basic
    @Column(name = "slt_in_si_type", columnDefinition="varchar(20)")
    private String sltInSiType;
    @Basic
    @Column(name = "slt_in_datetime", columnDefinition="datetime")
    private java.util.Date sltInDatetime;
    @Basic
    @Column(name = "slt_out_sd_idx", columnDefinition="int(11)")
    private Integer sltOutSdIdx;
    @Basic
    @Column(name = "slt_out_sd_name", columnDefinition="varchar(50)")
    private String sltOutSdName;
    @Basic
    @Column(name = "slt_out_si_code", columnDefinition="varchar(50)")
    private String sltOutSiCode;
    @Basic
    @Column(name = "slt_out_si_type", columnDefinition="varchar(20)")
    private String sltOutSiType;
    @Basic
    @Column(name = "slt_out_datetime", columnDefinition="datetime")
    private java.util.Date sltOutDatetime;
    @Basic
    @Column(name = "slt_datetime", columnDefinition="datetime")
    private java.util.Date sltDatetime;

}
