package kr.co.hulan.aas.mvc.model.domain;

import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import org.springframework.util.StringUtils;

@NoArgsConstructor
@Data
@Entity
@Table(name = "ui_component")
public class UiComponent {

  @Id
  @Basic
  @Column(name = "cmpt_id", columnDefinition="varchar(16)")
  private String cmptId;
  @Basic
  @Column(name = "cmpt_name", columnDefinition="varchar(32)")
  private String cmptName;
  @Basic
  @Column(name = "description", columnDefinition="varchar(1024)")
  private String description;
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
  @Column(name = "file_location", columnDefinition="integer")
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

  @PrePersist
  public void prePersist() {
    if( createDate == null ){
      createDate = new Date();
    }
    if( updateDate == null ){
      updateDate = new Date();
    }
  }
}
