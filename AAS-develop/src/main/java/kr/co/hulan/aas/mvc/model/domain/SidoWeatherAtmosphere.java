package kr.co.hulan.aas.mvc.model.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

@NoArgsConstructor
@Data
@Entity
@Table(name = "sido_weather_atmosphere")
public class SidoWeatherAtmosphere {

  @Id
  @Basic
  @Column(name = "sido_cd", columnDefinition="varchar(2)")
  private String sidoCd;
  @Basic
  @Column(name = "humidity", columnDefinition="varchar(16)")
  private String humidity;
  @Basic
  @Column(name = "wind_direction", columnDefinition="varchar(16)")
  private String windDirection;
  @Basic
  @Column(name = "wind_speed", columnDefinition="varchar(16)")
  private String windSpeed;
  @Basic
  @Column(name = "precipitation_form", columnDefinition="varchar(16)")
  private String precipitationForm;
  @Basic
  @Column(name = "precipitation_form_name", columnDefinition="varchar(16)")
  private String precipitationFormName;
  @Basic
  @Column(name = "precipitation", columnDefinition="varchar(16)")
  private String precipitation;
  @Basic
  @Column(name = "sky_form", columnDefinition="varchar(16)")
  private String skyForm;
  @Basic
  @Column(name = "sky_form_name", columnDefinition="varchar(16)")
  private String skyFormName;
  @Basic
  @Column(name = "rainfall", columnDefinition="varchar(16)")
  private String rainfall;
  @Basic
  @Column(name = "temperature", columnDefinition="varchar(16)")
  private String temperature;
  @Basic
  @Column(name = "pm25_value", columnDefinition="varchar(16)")
  private String pm25Value;
  @Basic
  @Column(name = "pm25_grade", columnDefinition="varchar(16)")
  private String pm25Grade;
  @Basic
  @Column(name = "weather_ultra_update", columnDefinition="datetime")
  private java.util.Date weatherUltraUpdate;
  @Basic
  @Column(name = "weather_vil_update", columnDefinition="datetime")
  private java.util.Date weatherVilUpdate;
  @Basic
  @Column(name = "atm_update", columnDefinition="datetime")
  private java.util.Date atmUpdate;
  @Basic
  @Column(name = "update_date", columnDefinition="datetime")
  private java.util.Date updateDate;
}
