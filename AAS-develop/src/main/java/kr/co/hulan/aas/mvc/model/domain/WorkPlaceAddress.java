package kr.co.hulan.aas.mvc.model.domain;

import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Data
@Entity
@Table(name = "work_place_address")
public class WorkPlaceAddress {

  @Id
  @Basic
  @Column(name = "wp_id", columnDefinition="varchar(50)")
  private String wpId;
  @Basic
  @Column(name = "zonecode", columnDefinition="varchar(5)")
  private String zonecode;
  @Basic
  @Column(name = "address", columnDefinition="varchar(256)")
  private String address;
  @Basic
  @Column(name = "road_address", columnDefinition="varchar(256)")
  private String roadAddress;
  @Basic
  @Column(name = "jibun_address", columnDefinition="varchar(256)")
  private String jibunAddress;
  @Basic
  @Column(name = "building_name", columnDefinition="varchar(64)")
  private String buildingName;
  @Basic
  @Column(name = "sido", columnDefinition="varchar(32)")
  private String sido;
  @Basic
  @Column(name = "sigungu", columnDefinition="varchar(32)")
  private String sigungu;
  @Basic
  @Column(name = "sigungu_code", columnDefinition="varchar(5)")
  private String sigunguCode;
  @Basic
  @Column(name = "roadname_code", columnDefinition="varchar(10)")
  private String roadnameCode;
  @Basic
  @Column(name = "roadname", columnDefinition="varchar(32)")
  private String roadname;
  @Basic
  @Column(name = "bname", columnDefinition="varchar(32)")
  private String bname;
  @Basic
  @Column(name = "bname1", columnDefinition="varchar(32)")
  private String bname1;
  @Basic
  @Column(name = "bname2", columnDefinition="varchar(32)")
  private String bname2;
  @Basic
  @Column(name = "bcode", columnDefinition="varchar(10)")
  private String bcode;
  @Basic
  @Column(name = "hname", columnDefinition="varchar(32)")
  private String hname;
  @Basic
  @Column(name = "create_date", columnDefinition="datetime")
  private java.util.Date createDate;

  @PrePersist
  public void prePersist(){
    if( createDate == null ){
      createDate = new Date();
    }
  }

  @PreUpdate
  public void preUpdate(){
    prePersist();
  }
}
