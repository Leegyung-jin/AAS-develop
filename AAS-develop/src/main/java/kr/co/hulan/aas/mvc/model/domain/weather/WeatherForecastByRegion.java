package kr.co.hulan.aas.mvc.model.domain.weather;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Data
@Entity
@IdClass(WeatherForecastByRegionKey.class)
@Table(name = "weather_forecast_by_region")
public class WeatherForecastByRegion {

  @Id
  @Basic
  @Column(name = "wfr_cd", columnDefinition="varchar(8)")
  private String wfrCd;
  @Id
  @Basic
  @Column(name = "forecast_day", columnDefinition="varchar(8)")
  private String forecastDay;
  @Basic
  @Column(name = "am_rainfall", columnDefinition="varchar(16)")
  private String amRainfall;
  @Basic
  @Column(name = "pm_rainfall", columnDefinition="varchar(16)")
  private String pmRainfall;
  @Basic
  @Column(name = "am_wf_form_name", columnDefinition="varchar(32)")
  private String amWfFormName;
  @Basic
  @Column(name = "pm_wf_form_name", columnDefinition="varchar(32)")
  private String pmWfFormName;
  @Basic
  @Column(name = "am_sky_form", columnDefinition="varchar(16)")
  private String amSkyForm;
  @Basic
  @Column(name = "pm_sky_form", columnDefinition="varchar(16)")
  private String pmSkyForm;
  @Basic
  @Column(name = "am_precipitation_form", columnDefinition="varchar(16)")
  private String amPrecipitationForm;
  @Basic
  @Column(name = "pm_precipitation_form", columnDefinition="varchar(16)")
  private String pmPrecipitationForm;
  @Basic
  @Column(name = "create_date", columnDefinition="datetime")
  private java.util.Date createDate;
  @Basic
  @Column(name = "update_date", columnDefinition="datetime")
  private java.util.Date updateDate;

  @PrePersist
  public void prePersist(){
    if( createDate == null ){
      createDate = new java.util.Date();
    }
    if( updateDate == null ){
      updateDate = new java.util.Date();
    }
  }

}
