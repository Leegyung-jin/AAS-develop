package kr.co.hulan.aas.mvc.model.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import org.apache.commons.lang3.StringUtils;

@NoArgsConstructor
@Data
@Entity
@Table(name = "office_workplace_grp")
public class OfficeWorkplaceGrp {

  @Id
  @Basic
  @Column(name = "wp_grp_no", columnDefinition = "bigint")
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Long wpGrpNo;
  @Basic
  @Column(name = "office_no", columnDefinition = "bigint")
  private Long officeNo;
  @Basic
  @Column(name = "office_grp_name", columnDefinition = "varchar(128)")
  private String officeGrpName;
  @Basic
  @Column(name = "create_date", columnDefinition = "datetime")
  private java.util.Date createDate;
  @Basic
  @Column(name = "creator", columnDefinition = "varchar(20)")
  private String creator;
  @Basic
  @Column(name = "update_date", columnDefinition = "datetime")
  private java.util.Date updateDate;
  @Basic
  @Column(name = "updater", columnDefinition = "varchar(20)")
  private String updater;

  @OneToMany(mappedBy="wpGrpNo")
  private List<OfficeWorkplaceManager> members;

  @PrePersist
  public void prePersist(){
    if( createDate == null ){
      createDate = new java.util.Date();
    }
    if( updateDate == null ){
      updateDate = new Date();
    }
  }

  public boolean containMember(String mbId){
    return members.stream().anyMatch( member -> StringUtils.equals(member.getMbId(), mbId));
  }

  public java.util.Optional<OfficeWorkplaceManager> getMember(String mbId){
    return members.stream().filter( member -> StringUtils.equals(member.getMbId(), mbId)).findFirst();
  }
}
