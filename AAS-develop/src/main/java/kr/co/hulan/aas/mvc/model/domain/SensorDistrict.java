package kr.co.hulan.aas.mvc.model.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

@NoArgsConstructor
@Data
@Entity
@Table(name = "sensor_district")
public class SensorDistrict {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Basic
    @Column(name = "sd_idx", columnDefinition="int(11)")
    private Integer sdIdx;
    @Basic
    @Column(name = "wp_id", columnDefinition="varchar(50)")
    private String wpId;
    @Basic
    @Column(name = "sd_name", columnDefinition="varchar(100)")
    private String sdName;
    @Basic
    @Column(name = "sd_memo", columnDefinition="text")
    private String sdMemo;

    @Basic
    @Column(name = "default_color", columnDefinition="varchar(8)")
    private String defaultColor;

    @Basic
    @Column(name = "flash_color", columnDefinition="varchar(8)")
    private String flashColor;

}
