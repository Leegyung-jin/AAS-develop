package kr.co.hulan.aas.mvc.model.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

@NoArgsConstructor
@Data
@Entity
@Table(name = "sensor_policy_info")
public class SensorPolicyInfo {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Basic
    @Column(name = "sp_idx", columnDefinition="int(11) unsigned")
    private Integer spIdx;
    @Basic
    @Column(name = "si_type", columnDefinition="varchar(20)")
    private String siType;
    @Basic
    @Column(name = "minor_min", columnDefinition="int(6)")
    private Integer minorMin;
    @Basic
    @Column(name = "minor_max", columnDefinition="int(6)")
    private Integer minorMax;
    @Basic
    @Column(name = "scan_interval", columnDefinition="int(10)")
    private Integer scanInterval;
    @Basic
    @Column(name = "idle_interval", columnDefinition="int(10)")
    private Integer idleInterval;
    @Basic
    @Column(name = "report_interval", columnDefinition="int(10)")
    private Integer reportInterval;
    @Basic
    @Column(name = "wp_id", columnDefinition="varchar(50)")
    private String wpId;
    @Basic
    @Column(name = "wp_name", columnDefinition="varchar(100)")
    private String wpName;
    @Basic
    @Column(name = "create_date", columnDefinition="datetime")
    private java.util.Date createDate;
    @Basic
    @Column(name = "update_date", columnDefinition="datetime")
    private java.util.Date updateDate;
    @Basic
    @Column(name = "mb_id", columnDefinition="varchar(20)")
    private String mbId;

}
