package kr.co.hulan.aas.mvc.model.domain;

import java.sql.Time;
import kr.co.hulan.aas.common.code.WeatherItemType;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;
import org.apache.commons.lang3.StringUtils;

@NoArgsConstructor
@Data
@Entity
@Table(name = "work_place_weather")
public class WorkPlaceWeather {

  @Id
  @Basic
  @Column(name = "wp_id", columnDefinition="varchar(50)")
  private String wpId;
  @Basic
  @Column(name = "base_date", columnDefinition="date")
  private Date baseDate;
  @Basic
  @Column(name = "base_time", columnDefinition="time")
  private Time baseTime;
  @Basic
  @Column(name = "nx", columnDefinition="integer")
  private Integer nx;
  @Basic
  @Column(name = "ny", columnDefinition="integer")
  private Integer ny;
  @Basic
  @Column(name = "humidity", columnDefinition="varchar(16)")
  private String humidity;
  @Basic
  @Column(name = "humidity_unit", columnDefinition="varchar(8)")
  private String humidityUnit;
  @Basic
  @Column(name = "wind_direction", columnDefinition="varchar(16)")
  private String windDirection;
  @Basic
  @Column(name = "wind_speed", columnDefinition="varchar(16)")
  private String windSpeed;
  @Basic
  @Column(name = "wind_speed_unit", columnDefinition="varchar(8)")
  private String windSpeedUnit;
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
  @Column(name = "precipitation_unit", columnDefinition="varchar(8)")
  private String precipitationUnit;
  @Basic
  @Column(name = "temperature", columnDefinition="varchar(16)")
  private String temperature;
  @Basic
  @Column(name = "temperature_unit", columnDefinition="varchar(8)")
  private String temperatureUnit;
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
  @Column(name = "rainfall_unit", columnDefinition="varchar(8)")
  private String rainfallUnit;
  @Basic
  @Column(name = "update_date", columnDefinition="datetime")
  private java.util.Date updateDate;


  @PrePersist
  public void prePersist() {
    if( updateDate == null ){
      updateDate = new java.util.Date();
    }
    if(StringUtils.isBlank(temperatureUnit) ){
      temperatureUnit = WeatherItemType.TEMPERATURE.getUnit();
    }
    if(StringUtils.isBlank(precipitationUnit) ){
      precipitationUnit = WeatherItemType.PRECIPITATION.getUnit();
    }
    if(StringUtils.isBlank(windSpeedUnit) ){
      windSpeedUnit = WeatherItemType.WIND_SPEED.getUnit();
    }
    if(StringUtils.isBlank(humidityUnit) ){
      humidityUnit = WeatherItemType.HUMIDITY.getUnit();
    }
    if(StringUtils.isBlank(rainfallUnit) ){
      rainfallUnit = WeatherItemType.RAINFALL.getUnit();
    }
  }
}
