package kr.co.hulan.aas.mvc.model.domain;

import kr.co.hulan.aas.common.code.EnableCode;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Data
@Entity
@IdClass(BuildingFloorCompositeKey.class)
@Table(name = "building_floor")
public class BuildingFloor {

    @Id
    @Basic
    @Column(name = "building_no", columnDefinition="bigint")
    private Long buildingNo;
    @Id
    @Basic
    @Column(name = "floor", columnDefinition="int")
    private Integer floor;

    @Basic
    @Column(name = "floor_name", columnDefinition="varchar(64)")
    private String floorName;
    @Basic
    @Column(name = "floor_type", columnDefinition="int")
    private Integer floorType;
    @Basic
    @Column(name = "activated", columnDefinition="int")
    private Integer activated;

    @Basic
    @Column(name = "view_floor_file_name", columnDefinition="varchar(255)")
    private String viewFloorFileName;
    @Basic
    @Column(name = "view_floor_file_name_org", columnDefinition="varchar(255)")
    private String viewFloorFileNameOrg;
    @Basic
    @Column(name = "view_floor_file_path", columnDefinition="varchar(255)")
    private String viewFloorFilePath;
    @Basic
    @Column(name = "view_floor_file_location", columnDefinition="integer")
    private Integer viewFloorFileLocation;

    @Basic
    @Column(name = "cross_section_grid_x", columnDefinition="int")
    private Integer crossSectionGridX;
    @Basic
    @Column(name = "cross_section_grid_y", columnDefinition="int")
    private Integer crossSectionGridY;

    @Basic
    @Column(name = "box_x", columnDefinition="int")
    private Integer boxX;
    @Basic
    @Column(name = "box_y", columnDefinition="int")
    private Integer boxY;

    @Basic
    @Column(name = "update_date", columnDefinition="datetime")
    private java.util.Date updateDate;
    @Basic
    @Column(name = "updater", columnDefinition="varchar(20)")
    private String updater;


    @PrePersist
    public void prePersist(){
        if( activated == null ){
            activated = EnableCode.DISABLED.getCode();
        }

        if( boxX == null ){
            boxX = 10;
        }
        if( boxY == null ){
            boxY = 10;
        }
    }
}
