package kr.co.hulan.aas.mvc.model.domain;

import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Data
@Entity
@Table(name = "office_workplace_manager")
@IdClass(OfficeWorkplaceManagerKey.class)
public class OfficeWorkplaceManager {

  @Id
  @Basic
  @Column(name = "wp_grp_no", columnDefinition="bigint")
  private Long wpGrpNo;
  @Id
  @Basic
  @Column(name = "mb_id", columnDefinition="varchar(20)")
  private String mbId;
  @Basic
  @Column(name = "create_date", columnDefinition="datetime")
  private java.util.Date createDate;
  @Basic
  @Column(name = "creator", columnDefinition="varchar(20)")
  private String creator;

  @PrePersist
  public void prePersist(){
    if( createDate == null ){
      createDate = new Date();
    }
  }
}
