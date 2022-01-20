package kr.co.hulan.aas.mvc.model.domain.weather;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Data
@Entity
@Table(name = "weather_region")
public class WeatherRegion {

  @Id
  @Basic
  @Column(name = "wfr_cd", columnDefinition="varchar(8)")
  private String wfrCd;
  @Basic
  @Column(name = "wfr_name", columnDefinition="varchar(16)")
  private String wfrName;
  @Basic
  @Column(name = "create_date", columnDefinition="datetime")
  private java.util.Date createDate;

}
