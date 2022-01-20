package kr.co.hulan.aas.mvc.model.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

@NoArgsConstructor
@Data
@Entity
@Table(name = "gps_device_info")
public class GpsDeviceInfo {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Basic
    @Column(name = "idx", columnDefinition="int(11)")
    private Integer idx;
    @Basic
    @Column(name = "wp_id", columnDefinition="varchar(50)")
    private String wpId;
    @Basic
    @Column(name = "device_id", columnDefinition="varchar(50)")
    private String deviceId;
    @Basic
    @Column(name = "mac_address", columnDefinition="varchar(50)")
    private String macAddress;
    @Basic
    @Column(name = "upd_datetime", columnDefinition="datetime")
    private java.util.Date updDatetime;
    @Basic
    @Column(name = "mb_id", columnDefinition="varchar(50)")
    private String mbId;

}
