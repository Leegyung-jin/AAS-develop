package kr.co.hulan.aas.mvc.model.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Data
@Entity
@Table(name = "hicc_region")
@IdClass(HiccRegionKey.class)
public class HiccRegion {

  @Id
  @Basic
  @Column(name = "hicc_no", columnDefinition="bigint")
  private Long hiccNo;
  @Id
  @Basic
  @Column(name = "sido_cd", columnDefinition="varchar(2)")
  private String sidoCd;
  @Basic
  @Column(name = "hrg_no", columnDefinition="bigint")
  private Long hrgNo;
  @Basic
  @Column(name = "create_date", columnDefinition="datetime")
  private java.util.Date createDate;
  @Basic
  @Column(name = "creator", columnDefinition="varchar(20)")
  private String creator;

}
