package kr.co.hulan.aas.mvc.model.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

@NoArgsConstructor
@Data
@Entity
@Table(name = "construction_site_manager")
@IdClass(ConstructionSiteManagerKey.class)
public class ConstructionSiteManager {

  @Id
  @Basic
  @Column(name = "wp_id", columnDefinition="varchar(50)")
  private String wpId;
  @Id
  @Basic
  @Column(name = "mb_id", columnDefinition="varchar(20)")
  private String mbId;
  @Basic
  @Column(name = "cc_id", columnDefinition="varchar(50)")
  private String ccId;
  @Basic
  @Column(name = "create_date", columnDefinition="datetime")
  private java.util.Date createDate;
  @Basic
  @Column(name = "creator", columnDefinition="varchar(20)")
  private String creator;

}
