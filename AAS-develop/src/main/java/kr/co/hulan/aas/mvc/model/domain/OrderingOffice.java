package kr.co.hulan.aas.mvc.model.domain;

import java.util.Date;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Data
@Entity
@Table(name = "ordering_office")
public class OrderingOffice {

  @Id
  @Basic
  @Column(name = "office_no", columnDefinition = "bigint")
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Long officeNo;
  @Basic
  @Column(name = "office_name", columnDefinition = "varchar(128)")
  private String officeName;
  @Basic
  @Column(name = "telephone", columnDefinition = "varchar(32)")
  private String telephone;
  @Basic
  @Column(name = "biznum", columnDefinition = "varchar(10)")
  private String biznum;
  @Basic
  @Column(name = "zonecode", columnDefinition = "varchar(5)")
  private String zonecode;
  @Basic
  @Column(name = "bcode", columnDefinition = "varchar(10)")
  private String bcode;
  @Basic
  @Column(name = "address", columnDefinition = "varchar(256)")
  private String address;
  @Basic
  @Column(name = "address_detail", columnDefinition = "varchar(256)")
  private String addressDetail;
  @Basic
  @Column(name = "sido", columnDefinition = "varchar(32)")
  private String sido;
  @Basic
  @Column(name = "sigungu", columnDefinition = "varchar(32)")
  private String sigungu;
  @Basic
  @Column(name = "bname", columnDefinition = "varchar(32)")
  private String bname;

  @Basic
  @Column(name = "hicc_no", columnDefinition = "bigint")
  private Long hiccNo;

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

  /*
  @OneToMany
  @JoinColumn(name = "office_no")
  private List<OfficeWorkplaceGrp> groupList;
   */

  @PrePersist
  public void prePersist(){
    if( createDate == null ){
      createDate = new Date();
    }
    if( updateDate == null ){
      updateDate = new Date();
    }
  }
}
