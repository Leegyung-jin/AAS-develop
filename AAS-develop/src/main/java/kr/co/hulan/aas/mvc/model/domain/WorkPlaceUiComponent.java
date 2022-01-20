package kr.co.hulan.aas.mvc.model.domain;

import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@NoArgsConstructor
@Data
@Entity
@IdClass(WorkPlaceUiComponentCompositeKey.class)
@Table(name = "work_place_ui_component")
public class WorkPlaceUiComponent {

  @Id
  @Basic
  @Column(name = "wp_id", columnDefinition="varchar(50)")
  private String wpId;
  @Id
  @Basic
  @Column(name = "location", columnDefinition="int")
  private Integer location;
  @Id
  @Basic
  @Column(name = "creator", columnDefinition="varchar(20)")
  private String creator;
  @Basic
  @Column(name = "cmpt_id", columnDefinition="varchar(16)")
  private String cmptId;
  @Basic
  @Column(name = "create_date", columnDefinition="datetime")
  private java.util.Date createDate;

  @PrePersist
  public void prePersist() {
    if( createDate == null ){
      createDate = new java.util.Date();
    }
  }
}
