package kr.co.hulan.aas.mvc.model.domain.imosNotice;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Data
@Entity
@Table(name = "imos_notice_file")
public class ImosNoticeFile {

  @Id
  @Basic
  @Column(name = "file_no", columnDefinition="bigint")
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Long fileNo;
  @Basic
  @Column(name = "imnt_no", columnDefinition="bigint")
  private Long imntNo;
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
  @Column(name = "create_date", columnDefinition="datetime")
  private java.util.Date createDate;
  @Basic
  @Column(name = "creator", columnDefinition="varchar(20)")
  private String creator;

  @PrePersist
  public void prePersist(){
    if( createDate == null ){
      createDate = new java.util.Date();
    }
  }
}
