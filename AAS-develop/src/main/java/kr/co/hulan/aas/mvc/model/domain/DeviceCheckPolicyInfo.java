package kr.co.hulan.aas.mvc.model.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

@NoArgsConstructor
@Data
@Entity
@Table(name = "device_check_policy_info")
public class DeviceCheckPolicyInfo {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Basic
    @Column(name = "idx", columnDefinition="bigint(20)")
    private Long idx;
    @Basic
    @Column(name = "wp_id", columnDefinition="varchar(50)")
    private String wpId;
    @Basic
    @Column(name = "ble_check", columnDefinition="tinyint(4)")
    private Integer bleCheck;
    @Basic
    @Column(name = "loc_check", columnDefinition="tinyint(4)")
    private Integer locCheck;
    @Basic
    @Column(name = "power_check", columnDefinition="tinyint(4)")
    private Integer powerCheck;
    @Basic
    @Column(name = "alarm_type", columnDefinition="tinyint(4)")
    private Integer alarmType;
    @Basic
    @Column(name = "report_type", columnDefinition="tinyint(4)")
    private Integer reportType;
    @Basic
    @Column(name = "report_sub_type", columnDefinition="tinyint(4)")
    private Integer reportSubType;
    @Basic
    @Column(name = "report_interval", columnDefinition="bigint(20)")
    private Long reportInterval;
    @Basic
    @Column(name = "alarm_interval", columnDefinition="bigint(20)")
    private Long alarmInterval;
    @Basic
    @Column(name = "noti_allow_start", columnDefinition="varchar(6)")
    private String notiAllowStart;
    @Basic
    @Column(name = "noti_allow_end", columnDefinition="varchar(6)")
    private String notiAllowEnd;
    @Basic
    @Column(name = "udp_datetime", columnDefinition="datetime")
    private java.util.Date udpDatetime;
    @Basic
    @Column(name = "updater", columnDefinition="varchar(50)")
    private String updater;

}
