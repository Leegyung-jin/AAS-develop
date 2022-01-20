package kr.co.hulan.aas.mvc.model.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

@NoArgsConstructor
@Data
@Entity
@IdClass(GpsLocationCompositeKey.class)
@Table(name = "gps_location")
public class GpsLocation {

    @Id
    @Basic
    @Column(name = "mb_id", columnDefinition="varchar(50)")
    private String mbId;

    @Id
    @Basic
    @Column(name = "device_id", columnDefinition="varchar(50)")
    private String deviceId;

    @Basic
    @Column(name = "wp_id", columnDefinition="varchar(50)")
    private String wpId;
    @Basic
    @Column(name = "coop_mb_id", columnDefinition="varchar(50)")
    private String coopMbId;

    @Basic
    @Column(name = "provider", columnDefinition="varchar(6)")
    private String provider;
    @Basic
    @Column(name = "longitude", columnDefinition="double")
    private Long longitude;
    @Basic
    @Column(name = "latitude", columnDefinition="double")
    private Long latitude;
    @Basic
    @Column(name = "altitude", columnDefinition="double")
    private Long altitude;
    @Basic
    @Column(name = "accuracy", columnDefinition="double")
    private Long accuracy;
    @Basic
    @Column(name = "speed", columnDefinition="double")
    private Long speed;
    @Basic
    @Column(name = "bearing", columnDefinition="double")
    private Long bearing;
    @Basic
    @Column(name = "battery", columnDefinition="double")
    private Long battery;
    @Basic
    @Column(name = "sensor_scan", columnDefinition="int(5)")
    private Integer sensorScan;
    @Basic
    @Column(name = "measure_time", columnDefinition="datetime")
    private java.util.Date measureTime;

}
