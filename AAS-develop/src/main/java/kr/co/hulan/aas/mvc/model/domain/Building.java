package kr.co.hulan.aas.mvc.model.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

@NoArgsConstructor
@Data
@Entity
@Table(name = "building")
public class Building {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Basic
    @Column(name = "building_no", columnDefinition="bigint")
    private Long buildingNo;
    @Basic
    @Column(name = "wp_id", columnDefinition="varchar(50)")
    private String wpId;
    @Basic
    @Column(name = "building_name", columnDefinition="varchar(64)")
    private String buildingName;
    @Basic
    @Column(name = "area_type", columnDefinition="int")
    private Integer areaType;
    @Basic
    @Column(name = "contain_roof", columnDefinition="int")
    private Integer containRoof;
    @Basic
    @Column(name = "contain_gangform", columnDefinition="int")
    private Integer containGangform;
    @Basic
    @Column(name = "floor_upstair", columnDefinition="int")
    private Integer floorUpstair;
    @Basic
    @Column(name = "floor_downstair", columnDefinition="int")
    private Integer floorDownstair;
    @Basic
    @Column(name = "pos_x", columnDefinition="int")
    private Integer posX;
    @Basic
    @Column(name = "pos_y", columnDefinition="int")
    private Integer posY;
    @Basic
    @Column(name = "box_x", columnDefinition="int")
    private Integer boxX;
    @Basic
    @Column(name = "box_y", columnDefinition="int")
    private Integer boxY;
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
    @Column(name = "cross_section_file_name", columnDefinition="varchar(255)")
    private String crossSectionFileName;
    @Basic
    @Column(name = "cross_section_file_name_org", columnDefinition="varchar(255)")
    private String crossSectionFileNameOrg;
    @Basic
    @Column(name = "cross_section_file_path", columnDefinition="varchar(255)")
    private String crossSectionFilePath;
    @Basic
    @Column(name = "cross_section_file_location", columnDefinition="integer")
    private Integer crossSectionFileLocation;

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
