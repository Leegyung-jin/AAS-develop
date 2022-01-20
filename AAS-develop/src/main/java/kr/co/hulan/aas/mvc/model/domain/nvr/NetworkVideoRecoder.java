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
@Table(name = "network_video_recoder")
public class NetworkVideoRecoder {

  @Id
  @Basic
  @Column(name = "nvr_no", columnDefinition="bigint")
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Long nvrNo;
  @Basic
  @Column(name = "nvr_name", columnDefinition="varchar(64)")
  private String nvrName;
  @Basic
  @Column(name = "nvr_type", columnDefinition="tinyint")
  private Integer nvrType;
  @Basic
  @Column(name = "description", columnDefinition="varchar(1024)")
  private String description;
  @Basic
  @Column(name = "id", columnDefinition="varchar(16)")
  private String id;
  @Basic
  @Column(name = "pw", columnDefinition="varchar(16)")
  private String pw;
  @Basic
  @Column(name = "ip", columnDefinition="varchar(32)")
  private String ip;
  @Basic
  @Column(name = "port", columnDefinition="int")
  private Integer port;
  @Basic
  @Column(name = "status", columnDefinition="tinyint")
  private Integer status;
  @Basic
  @Column(name = "wp_id", columnDefinition="varchar(50)")
  private String wpId;
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
  public void prePersist(){
    if( createDate == null ){
      createDate = new java.util.Date();
    }
    if( updateDate == null ){
      updateDate = new java.util.Date();
    }
  }
}
