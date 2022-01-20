package kr.co.hulan.aas.mvc.model.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

@NoArgsConstructor
@Data
@Entity
@Table(name = "sensor_building_location")
public class SensorBuildingLocation {

    @Id
    @Basic
    @Column(name = "si_idx", columnDefinition="int")
    private Integer siIdx;
    @Basic
    @Column(name = "building_no", columnDefinition="bigint")
    private Long buildingNo;
    @Basic
    @Column(name = "floor", columnDefinition="int")
    private Integer floor;
    @Basic
    @Column(name = "grid_x", columnDefinition="int")
    private Integer gridX;
    @Basic
    @Column(name = "grid_y", columnDefinition="int")
    private Integer gridY;
    @Basic
    @Column(name = "create_date", columnDefinition="datetime")
    private java.util.Date createDate;
    @Basic
    @Column(name = "creator", columnDefinition="varchar(20)")
    private String creator;
    @Basic
    @Column(name = "update_date", columnDefinition="datetime")
    private java.util.Date updateDate;
    @Basic
    @Column(name = "updater", columnDefinition="varchar(20)")
    private String updater;
}
