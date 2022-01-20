package kr.co.hulan.aas.mvc.model.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

@NoArgsConstructor
@Data
@Entity
@Table(name = "hulan_ui_component_level")
@IdClass(HulanUiComponentLevelKey.class)
public class HulanUiComponentLevel {

  @Id
  @Basic
  @Column(name = "hcmpt_id", columnDefinition="varchar(16)")
  private String hcmptId;
  @Id
  @Basic
  @Column(name = "mb_level", columnDefinition="tinyint")
  private Integer mbLevel;
  @Basic
  @Column(name = "create_date", columnDefinition="datetime")
  private java.util.Date createDate;
  @Basic
  @Column(name = "creator", columnDefinition="varchar(20)")
  private String creator;

}
