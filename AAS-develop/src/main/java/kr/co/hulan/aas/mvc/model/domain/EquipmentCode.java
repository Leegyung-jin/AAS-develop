package kr.co.hulan.aas.mvc.model.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

@NoArgsConstructor
@Data
@Entity
@Table(name = "equipment_code")
public class EquipmentCode {

    @Id
    @Basic
    @Column(name = "equipment_type", columnDefinition="tinyint(4)")
    private Integer equipmentType;
    @Basic
    @Column(name = "equipment_name", columnDefinition="varchar(50)")
    private String equipmentName;
    @Basic
    @Column(name = "icon_type", columnDefinition="tinyint(4)")
    private Integer iconType;

}
