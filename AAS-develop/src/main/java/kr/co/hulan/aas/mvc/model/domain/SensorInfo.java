package kr.co.hulan.aas.mvc.model.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

@NoArgsConstructor
@Data
@Entity
@Table(name = "sensor_info")
public class SensorInfo {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "si_idx", columnDefinition="int(11)")
    private Integer siIdx;
    @Basic
    @Column(name = "wp_id", columnDefinition="varchar(50)")
    private String wpId;
    @Basic
    @Column(name = "wp_name", columnDefinition="varchar(100)")
    private String wpName;
    @Basic
    @Column(name = "sd_idx", columnDefinition="int(11)")
    private Integer sdIdx;
    @Basic
    @Column(name = "sd_name", columnDefinition="varchar(100)")
    private String sdName;
    @Basic
    @Column(name = "si_code", columnDefinition="varchar(100)")
    private String siCode;
    @Basic
    @Column(name = "si_type", columnDefinition="varchar(20)")
    private String siType;
    @Basic
    @Column(name = "si_place1", columnDefinition="varchar(50)")
    private String siPlace1;
    @Basic
    @Column(name = "si_place2", columnDefinition="varchar(50)")
    private String siPlace2;
    @Basic
    @Column(name = "si_push", columnDefinition="tinyint(4)")
    private Integer siPush;
    @Basic
    @Column(name = "si_datetime", columnDefinition="datetime")
    private java.util.Date siDatetime;
    @Basic
    @Column(name = "uuid", columnDefinition="varchar(64)")
    private String uuid;
    @Basic
    @Column(name = "major", columnDefinition="smallint")
    private Integer major;
    @Basic
    @Column(name = "minor", columnDefinition="smallint")
    private Integer minor;


}
