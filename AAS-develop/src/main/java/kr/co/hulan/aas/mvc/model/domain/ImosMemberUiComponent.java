package kr.co.hulan.aas.mvc.model.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

@NoArgsConstructor
@Data
@Entity
@Table(name = "imos_member_ui_component")
public class ImosMemberUiComponent {

  @Id
  @Basic
  @Column(name = "imuc_no", columnDefinition="bigint")
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Long imucNo;
  @Basic
  @Column(name = "wp_id", columnDefinition="varchar(50)")
  private String wpId;
  @Basic
  @Column(name = "mb_id", columnDefinition="varchar(20)")
  private String mbId;
  @Basic
  @Column(name = "deploy_page", columnDefinition="int")
  private Integer deployPage;
  @Basic
  @Column(name = "pos_x", columnDefinition="int")
  private Integer posX;
  @Basic
  @Column(name = "pos_y", columnDefinition="int")
  private Integer posY;
  @Basic
  @Column(name = "hcmpt_id", columnDefinition="varchar(16)")
  private String hcmptId;
  @Basic
  @Column(name = "custom_data", columnDefinition="varchar(1024)")
  private String customData;
  @Basic
  @Column(name = "create_date", columnDefinition="datetime")
  private java.util.Date createDate;



  @ManyToOne(targetEntity= HulanUiComponent.class, fetch=FetchType.LAZY)
  @JoinColumn(name="hcmpt_id", referencedColumnName="hcmpt_id", insertable = false, updatable = false)
  private HulanUiComponent uiComponent;
}
