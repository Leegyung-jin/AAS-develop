package kr.co.hulan.aas.mvc.model.domain;

import java.util.Date;
import kr.co.hulan.aas.common.code.EnableCode;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Data
@Entity
@IdClass(BookmarkBleKey.class)
@Table(name = "bookmark_ble")
public class BookmarkBle {

  @Id
  @Basic
  @Column(name = "mb_id", columnDefinition="varchar(20)")
  private String mbId;
  @Id
  @Basic
  @Column(name = "wp_id", columnDefinition="varchar(50)")
  private String wpId;
  @Id
  @Basic
  @Column(name = "building_no", columnDefinition="bigint")
  private Long buildingNo;
  @Id
  @Basic
  @Column(name = "floor", columnDefinition="int")
  private Integer floor;
  @Basic
  @Column(name = "update_date", columnDefinition="datetime")
  private java.util.Date updateDate;


  @PrePersist
  public void prePersist() {
    if(updateDate == null ){
      updateDate = new Date();
    }
  }
}
