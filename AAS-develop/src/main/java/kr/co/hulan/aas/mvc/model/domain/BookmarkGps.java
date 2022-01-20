package kr.co.hulan.aas.mvc.model.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import org.springframework.util.StringUtils;

@NoArgsConstructor
@Data
@Entity
@IdClass(BookmarkGpsKey.class)
@Table(name = "bookmark_gps")
public class BookmarkGps {

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
  @Column(name = "wp_seq", columnDefinition="int")
  private Integer wpSeq;
  @Basic
  @Column(name = "update_date", columnDefinition="datetime")
  private java.util.Date updateDate;

  @PrePersist
  public void prePresist(){
    if( updateDate == null ){
      updateDate = new java.util.Date();
    }
  }
}
