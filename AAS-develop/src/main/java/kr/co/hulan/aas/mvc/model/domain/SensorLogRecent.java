package kr.co.hulan.aas.mvc.model.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

@NoArgsConstructor
@Data
@Entity
@IdClass(SensorLogRecentCompositeKey.class)
@Table(name = "sensor_log_recent")
public class SensorLogRecent {

    @Id
    @Basic
    @Column(name = "mb_id", columnDefinition="varchar(20)")
    private String mbId;

    @Id
    @Basic
    @Column(name = "slr_datetime", columnDefinition="datetime")
    private java.util.Date slrDatetime;

    @Basic
    @Column(name = "wp_id", columnDefinition="varchar(50)")
    private String wpId;

    @Basic
    @Column(name = "si_idx", columnDefinition="int")
    private Integer siIdx;

    @Basic
    @Column(name = "coop_mb_id", columnDefinition="varchar(20)")
    private String coopMbId;

    @Basic
    @Column(name = "in_out_type", columnDefinition="tinyint(1)")
    private Integer inOutType;


}
