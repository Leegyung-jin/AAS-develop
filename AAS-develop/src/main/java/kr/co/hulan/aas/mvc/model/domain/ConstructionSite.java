package kr.co.hulan.aas.mvc.model.domain;

import java.util.Date;
import java.util.List;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Data
@Entity
@Table(name = "construction_site")
@IdClass(ConstructionSiteKey.class)
public class ConstructionSite {

  @Id
  @Basic
  @Column(name = "wp_id", columnDefinition="varchar(50)")
  private String wpId;
  @Id
  @Basic
  @Column(name = "cc_id", columnDefinition="varchar(50)")
  private String ccId;
  @Basic
  @Column(name = "create_date", columnDefinition="datetime")
  private java.util.Date createDate;
  @Basic
  @Column(name = "creator", columnDefinition="varchar(20)")
  private String creator;

  @PrePersist
  public void prePersist(){
    if( createDate == null ){
      this.createDate = new Date();
    }
  }

}
