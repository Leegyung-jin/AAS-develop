package kr.co.hulan.aas.mvc.model.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Date;
import java.sql.Timestamp;

@NoArgsConstructor
@Data
@Entity
@Table(name = "air_environment")
public class AirEnvironment {

  @Id
  @Basic
  @Column(name = "station_name", columnDefinition="varchar(32)")
  private String stationName;
  @Basic
  @Column(name = "sido_name", columnDefinition="varchar(8)")
  private String sidoName;
  @Basic
  @Column(name = "mang_name", columnDefinition="varchar(16)")
  private String mangName;
  @Basic
  @Column(name = "khai_value", columnDefinition="varchar(16)")
  private String khaiValue;
  @Basic
  @Column(name = "khai_grade", columnDefinition="varchar(16)")
  private String khaiGrade;
  @Basic
  @Column(name = "pm25_value", columnDefinition="varchar(16)")
  private String pm25Value;
  @Basic
  @Column(name = "pm25_value24", columnDefinition="varchar(16)")
  private String pm25Value24;
  @Basic
  @Column(name = "pm25_grade1h", columnDefinition="varchar(16)")
  private String pm25Grade1h;
  @Basic
  @Column(name = "pm25_grade", columnDefinition="varchar(16)")
  private String pm25Grade;
  @Basic
  @Column(name = "pm25_flag", columnDefinition="varchar(16)")
  private String pm25Flag;
  @Basic
  @Column(name = "pm10_value", columnDefinition="varchar(16)")
  private String pm10Value;
  @Basic
  @Column(name = "pm10_value24", columnDefinition="varchar(16)")
  private String pm10Value24;
  @Basic
  @Column(name = "pm10_grade1h", columnDefinition="varchar(16)")
  private String pm10Grade1h;
  @Basic
  @Column(name = "pm10_grade", columnDefinition="varchar(16)")
  private String pm10Grade;
  @Basic
  @Column(name = "pm10_flag", columnDefinition="varchar(16)")
  private String pm10Flag;
  @Basic
  @Column(name = "so2_value", columnDefinition="varchar(16)")
  private String so2Value;
  @Basic
  @Column(name = "so2_grade", columnDefinition="varchar(16)")
  private String so2Grade;
  @Basic
  @Column(name = "so2_flag", columnDefinition="varchar(16)")
  private String so2Flag;
  @Basic
  @Column(name = "o3_value", columnDefinition="varchar(16)")
  private String o3Value;
  @Basic
  @Column(name = "o3_grade", columnDefinition="varchar(16)")
  private String o3Grade;
  @Basic
  @Column(name = "o3_flag", columnDefinition="varchar(16)")
  private String o3Flag;
  @Basic
  @Column(name = "co_value", columnDefinition="varchar(16)")
  private String coValue;
  @Basic
  @Column(name = "co_grade", columnDefinition="varchar(16)")
  private String coGrade;
  @Basic
  @Column(name = "co_flag", columnDefinition="varchar(16)")
  private String coFlag;
  @Basic
  @Column(name = "no2_value", columnDefinition="varchar(16)")
  private String no2Value;
  @Basic
  @Column(name = "no2_grade", columnDefinition="varchar(16)")
  private String no2Grade;
  @Basic
  @Column(name = "no2_flag", columnDefinition="varchar(16)")
  private String no2Flag;
  @Basic
  @Column(name = "data_time", columnDefinition="datetime")
  private java.util.Date dataTime;
  @Basic
  @Column(name = "create_date", columnDefinition="datetime")
  private java.util.Date createDate;

}
