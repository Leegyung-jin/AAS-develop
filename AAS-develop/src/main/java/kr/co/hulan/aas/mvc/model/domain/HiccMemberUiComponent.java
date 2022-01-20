package kr.co.hulan.aas.mvc.model.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Data
@Entity
@Table(name = "hicc_member_ui_component")
public class HiccMemberUiComponent {

  @Id
  @Basic
  @Column(name = "hmuc_no", columnDefinition="bigint")
  @GeneratedValue(strategy=GenerationType.IDENTITY)
  private Long hmucNo;
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
  @Basic
  @Column(name = "creator", columnDefinition="varchar(20)")
  private String creator;

  @ManyToOne(targetEntity= HulanUiComponent.class, fetch=FetchType.LAZY)
  @JoinColumn(name="hcmpt_id", referencedColumnName="hcmpt_id", insertable = false, updatable = false)
  private HulanUiComponent uiComponent;

}
