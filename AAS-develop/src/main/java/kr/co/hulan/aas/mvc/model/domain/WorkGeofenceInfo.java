package kr.co.hulan.aas.mvc.model.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

@NoArgsConstructor
@Data
@Entity
@IdClass(WorkGeofenceInfoCompositeKey.class)
@Table(name = "work_geofence_info")
public class WorkGeofenceInfo {

    @Id
    @Basic
    @Column(name = "wp_id", columnDefinition="varchar(50)")
    private String wpId;

    @Id
    @Basic
    @Column(name = "wp_seq", columnDefinition="tinyint")
    private Integer wpSeq;

    @Id
    @Basic
    @Column(name = "seq", columnDefinition="tinyint(4)")
    private Integer seq;

    @Basic
    @Column(name = "latitude", columnDefinition="double")
    private Double latitude;
    @Basic
    @Column(name = "longitude", columnDefinition="double")
    private Double longitude;
    @Basic
    @Column(name = "upd_datetime", columnDefinition="datetime")
    private java.util.Date updDatetime;
    @Basic
    @Column(name = "updater", columnDefinition="varchar(50)")
    private String updater;

}
