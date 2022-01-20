package kr.co.hulan.aas.mvc.model.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

@NoArgsConstructor
@Data
@Entity
@Table(name = "hicc_info")
public class HiccInfo {

  @Id
  @Basic
  @Column(name = "hicc_no", columnDefinition="bigint")
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Long hiccNo;
  @Basic
  @Column(name = "hicc_name", columnDefinition="varchar(32)")
  private String hiccName;
  @Basic
  @Column(name = "map_file_name", columnDefinition="varchar(255)")
  private String mapFileName;
  @Basic
  @Column(name = "map_file_name_org", columnDefinition="varchar(255)")
  private String mapFileNameOrg;
  @Basic
  @Column(name = "map_file_path", columnDefinition="varchar(255)")
  private String mapFilePath;
  @Basic
  @Column(name = "map_file_location", columnDefinition="int")
  private Integer mapFileLocation;
  @Basic
  @Column(name = "icon_file_name", columnDefinition="varchar(255)")
  private String iconFileName;
  @Basic
  @Column(name = "icon_file_name_org", columnDefinition="varchar(255)")
  private String iconFileNameOrg;
  @Basic
  @Column(name = "icon_file_path", columnDefinition="varchar(255)")
  private String iconFilePath;
  @Basic
  @Column(name = "icon_file_location", columnDefinition="int")
  private Integer iconFileLocation;
  @Basic
  @Column(name = "bg_img_file_name", columnDefinition="varchar(255)")
  private String bgImgFileName;
  @Basic
  @Column(name = "bg_img_file_name_org", columnDefinition="varchar(255)")
  private String bgImgFileNameOrg;
  @Basic
  @Column(name = "bg_img_file_path", columnDefinition="varchar(255)")
  private String bgImgFilePath;
  @Basic
  @Column(name = "bg_img_file_location", columnDefinition="int")
  private Integer bgImgFileLocation;
  @Basic
  @Column(name = "bg_color", columnDefinition="varchar(16)")
  private String bgColor;
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
