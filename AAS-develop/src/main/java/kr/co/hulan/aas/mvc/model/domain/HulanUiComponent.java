package kr.co.hulan.aas.mvc.model.domain;

import kr.co.hulan.aas.common.code.EnableCode;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

@NoArgsConstructor
@Data
@Entity
@Table(name = "hulan_ui_component")
public class HulanUiComponent {

  @Id
  @Basic
  @Column(name = "hcmpt_id", columnDefinition="varchar(16)")
  private String hcmptId;
  @Basic
  @Column(name = "hcmpt_name", columnDefinition="varchar(32)")
  private String hcmptName;
  @Basic
  @Column(name = "description", columnDefinition="varchar(1024)")
  private String description;
  @Basic
  @Column(name = "site_type", columnDefinition="int")
  private Integer siteType;
  @Basic
  @Column(name = "ui_type", columnDefinition="int")
  private Integer uiType;
  @Basic
  @Column(name = "width", columnDefinition="int")
  private Integer width;
  @Basic
  @Column(name = "height", columnDefinition="int")
  private Integer height;
  @Basic
  @Column(name = "file_name", columnDefinition="varchar(255)")
  private String fileName;
  @Basic
  @Column(name = "file_name_org", columnDefinition="varchar(255)")
  private String fileNameOrg;
  @Basic
  @Column(name = "file_path", columnDefinition="varchar(255)")
  private String filePath;
  @Basic
  @Column(name = "file_location", columnDefinition="int")
  private Integer fileLocation;
  @Basic
  @Column(name = "status", columnDefinition="tinyint")
  private Integer status;
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


  public boolean isUsable(){
    return EnableCode.get(status) == EnableCode.ENABLED;
  }
}
