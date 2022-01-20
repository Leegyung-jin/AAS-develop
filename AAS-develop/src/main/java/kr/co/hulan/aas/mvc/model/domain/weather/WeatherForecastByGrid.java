package kr.co.hulan.aas.mvc.model.domain.weather;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@Data
@Entity
@IdClass(WeatherForecastByGridKey.class)
@Table(name = "weather_forecast_by_grid")
public class WeatherForecastByGrid {
  @Id
  @Basic
  @Column(name = "nx", columnDefinition="int")
  private Integer nx;
  @Id
  @Basic
  @Column(name = "ny", columnDefinition="int")
  private Integer ny;
  @Id
  @Basic
  @Column(name = "forecast_day", columnDefinition="varchar(8)")
  private String forecastDay;
  @Id
  @Basic
  @Column(name = "forecast_hour", columnDefinition="int")
  private Integer forecastHour;
  @Basic
  @Column(name = "rainfall", columnDefinition="varchar(16)")
  private String rainfall;
  @Basic
  @Column(name = "precipitation_form", columnDefinition="varchar(16)")
  private String precipitationForm;
  @Basic
  @Column(name = "precipitation", columnDefinition="varchar(16)")
  private String precipitation;
  @Basic
  @Column(name = "humidity", columnDefinition="varchar(16)")
  private String humidity;
  @Basic
  @Column(name = "amount_snow", columnDefinition="varchar(16)")
  private String amountSnow;
  @Basic
  @Column(name = "sky_form", columnDefinition="varchar(16)")
  private String skyForm;
  @Basic
  @Column(name = "temperature", columnDefinition="varchar(16)")
  private String temperature;
  @Basic
  @Column(name = "wind_direction", columnDefinition="varchar(16)")
  private String windDirection;
  @Basic
  @Column(name = "wind_speed", columnDefinition="varchar(16)")
  private String windSpeed;
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
