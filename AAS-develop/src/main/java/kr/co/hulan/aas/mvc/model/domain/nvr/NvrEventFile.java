package kr.co.hulan.aas.mvc.model.domain.nvr;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
@Table(name = "nvr_event_file")
public class NvrEventFile {

  @Id
  @Basic
  @Column(name = "file_no", columnDefinition="bigint")
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Long fileNo;
  @Basic
  @Column(name = "event_no", columnDefinition="bigint")
  private Long eventNo;
  @Basic
  @Column(name = "elog_no", columnDefinition="bigint")
  private Long elogNo;
  @Basic
  @Column(name = "stat", columnDefinition="varchar(2)")
  private String stat;
  @Basic
  @Column(name = "recv_host", columnDefinition="varchar(64)")
  private String recvHost;
  @Basic
  @Column(name = "file_name", columnDefinition="varchar(255)")
  private String fileName;
  @Basic
  @Column(name = "file_path", columnDefinition="varchar(255)")
  private String filePath;
  @Basic
  @Column(name = "file_location", columnDefinition="int")
  private Integer fileLocation;
  @Basic
  @Column(name = "create_date", columnDefinition="datetime")
  private java.util.Date createDate;

  @PrePersist
  public void prePersist(){
    if( createDate == null ){
      createDate = new java.util.Date();
    }
  }
}
