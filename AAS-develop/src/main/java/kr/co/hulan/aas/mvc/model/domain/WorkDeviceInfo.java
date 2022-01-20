package kr.co.hulan.aas.mvc.model.domain;

import kr.co.hulan.aas.common.code.EnableCode;
import kr.co.hulan.aas.common.code.MacAddressType;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import org.apache.commons.lang3.StringUtils;

@NoArgsConstructor
@Data
@Entity
@Table(name = "work_device_info")
public class WorkDeviceInfo {

    @Id
    @Basic
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name = "idx", columnDefinition="int")
    private Integer idx;
    @Basic
    @Column(name = "wp_id", columnDefinition="varchar(50)")
    private String wpId;
    @Basic
    @Column(name = "device_type", columnDefinition = "tinyint")
    private Integer deviceType;
    @Basic
    @Column(name = "device_id", columnDefinition="varchar(50)")
    private String deviceId;
    @Basic
    @Column(name = "mac_address_type", columnDefinition = "tinyint")
    private Integer macAddressType;
    @Basic
    @Column(name = "mac_address", columnDefinition="varchar(32)")
    private String macAddress;
    @Basic
    @Column(name = "use_sensory_temp", columnDefinition="tinyint")
    private Integer useSensoryTemp;
    @Basic
    @Column(name = "ref_device", columnDefinition="int")
    private Integer refDevice;
    @Basic
    @Column(name = "update_datetime", columnDefinition="datetime")
    private java.util.Date updateDatetime;
    @Basic
    @Column(name = "updater", columnDefinition="varchar(20)")
    private String updater;


    @PrePersist
    public void prePersist(){
        if( macAddressType == null ){
            macAddressType = MacAddressType.NORMAL.getCode();
        }

        if( useSensoryTemp == null ){
            useSensoryTemp = EnableCode.DISABLED.getCode();
        }

        if( updateDatetime == null ){
            updateDatetime = new java.util.Date();
        }
    }

    @PreUpdate
    public void preUpdate(){
        prePersist();
    }
}
