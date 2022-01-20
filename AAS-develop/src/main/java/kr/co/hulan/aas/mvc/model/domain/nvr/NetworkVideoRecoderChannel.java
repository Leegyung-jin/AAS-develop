package kr.co.hulan.aas.mvc.model.domain.nvr;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@Entity
@Table(name = "network_video_recoder_ch")
public class NetworkVideoRecoderChannel {

  @Id
  @Basic
  @Column(name = "gid", columnDefinition="varchar(40)")
  private String gid;
  @Basic
  @Column(name = "nvr_no", columnDefinition="bigint")
  private Long nvrNo;
  @Basic
  @Column(name = "uid", columnDefinition="int")
  private Integer uid;
  @Basic
  @Column(name = "name", columnDefinition="varchar(128)")
  private String name;
  @Basic
  @Column(name = "size_x", columnDefinition="int")
  private Integer sizeX;
  @Basic
  @Column(name = "size_y", columnDefinition="int")
  private Integer sizeY;
  @Basic
  @Column(name = "ptz_control_auth", columnDefinition="int")
  private Integer ptzControlAuth;
  @Basic
  @Column(name = "is_ptz", columnDefinition="int")
  private Integer isPtz;
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
  @Column(name = "url", columnDefinition="varchar(128)")
  private String url;
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
