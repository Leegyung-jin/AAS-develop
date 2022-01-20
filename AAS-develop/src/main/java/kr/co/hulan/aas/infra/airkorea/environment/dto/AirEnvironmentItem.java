package kr.co.hulan.aas.infra.airkorea.environment.dto;

import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import lombok.Data;

@Data
public class AirEnvironmentItem {

  @ApiModelProperty(notes = "오염도 측정시간")
  private Date dataTime;
  @ApiModelProperty(notes = "시도명")
  private String sidoName;
  @ApiModelProperty(notes = "측정소명")
  private String stationName;
  @ApiModelProperty(notes = "측정망 정보. 도시대기, 도로변대기, 국가배경농도, 교외대기, 항만")
  private String mangName;

  @ApiModelProperty(notes = "통합대기환경수치")
  private String khaiValue;
  @ApiModelProperty(notes = "통합대기환경수치 지수 등급")
  private String khaiGrade;

  @ApiModelProperty(notes = "초미세먼지(PM2.5) 농도 (단위 : ㎍/㎥)")
  private String pm25Value;
  @ApiModelProperty(notes = "초미세먼지(PM2.5) 24시간예측이동농도 (단위 : ㎍/㎥)")
  private String pm25Value24;
  @ApiModelProperty(notes = "초미세먼지(PM2.5) 1시간 등급자료")
  private String pm25Grade1h;
  @ApiModelProperty(notes = "초미세먼지(PM2.5) 24시간 등급자료")
  private String pm25Grade;
  @ApiModelProperty(notes = "초미세먼지(PM2.5) 측정자료 상태 정보(점검및교정,장비점검,자료이상,통신장애)")
  private String pm25Flag;

  @ApiModelProperty(notes = "미세먼지(PM10) 농도 (단위 : ㎍/㎥)")
  private String pm10Value;
  @ApiModelProperty(notes = "미세먼지(PM10) 24시간예측이동농도 (단위 : ㎍/㎥)")
  private String pm10Value24;
  @ApiModelProperty(notes = "미세먼지(PM10) 1시간 등급자료")
  private String pm10Grade1h;
  @ApiModelProperty(notes = "미세먼지(PM10) 24시간 등급자료")
  private String pm10Grade;
  @ApiModelProperty(notes = "미세먼지(PM10) 측정자료 상태 정보(점검및교정,장비점검,자료이상,통신장애)")
  private String pm10Flag;

  @ApiModelProperty(notes = "아황산가스 농도 (단위 : ppm)")
  private String so2Value;
  @ApiModelProperty(notes = "아황산가스 지수 등급")
  private String so2Grade;
  @ApiModelProperty(notes = "아황산가스 측정자료 상태 정보(점검및교정,장비점검,자료이상,통신장애)")
  private String so2Flag;

  @ApiModelProperty(notes = "오존 농도 (단위 : ppm)")
  private String o3Value;
  @ApiModelProperty(notes = "오존 지수 등급")
  private String o3Grade;
  @ApiModelProperty(notes = "오존 측정자료 상태 정보(점검및교정,장비점검,자료이상,통신장애)")
  private String o3Flag;

  @ApiModelProperty(notes = "일산화탄소 농도 (단위 : ppm)")
  private String coValue;
  @ApiModelProperty(notes = "일산화탄소 지수 등급")
  private String coGrade;
  @ApiModelProperty(notes = "일산화탄소 측정자료 상태 정보(점검및교정,장비점검,자료이상,통신장애)")
  private String coFlag;

  @ApiModelProperty(notes = "이산화질소 농도 (단위 : ppm)")
  private String no2Value;
  @ApiModelProperty(notes = "이산화질소 지수 등급")
  private String no2Grade;
  @ApiModelProperty(notes = "이산화질소 측정자료 상태 정보(점검및교정,장비점검,자료이상,통신장애)")
  private String no2Flag;

  @Override
  public String toString() {
    return "AirEnvironmentItem{" +
        "dataTime=" + dataTime +
        ", sidoName='" + sidoName + '\'' +
        ", stationName='" + stationName + '\'' +
        ", mangName='" + mangName + '\'' +
        ", khaiValue='" + khaiValue + '\'' +
        ", khaiGrade='" + khaiGrade + '\'' +
        ", pm25Value='" + pm25Value + '\'' +
        ", pm25Value24='" + pm25Value24 + '\'' +
        ", pm25Grade1h='" + pm25Grade1h + '\'' +
        ", pm25Grade='" + pm25Grade + '\'' +
        ", pm25Flag='" + pm25Flag + '\'' +
        ", pm10Value='" + pm10Value + '\'' +
        ", pm10Value24='" + pm10Value24 + '\'' +
        ", pm10Grade1h='" + pm10Grade1h + '\'' +
        ", pm10Grade='" + pm10Grade + '\'' +
        ", pm10Flag='" + pm10Flag + '\'' +
        ", so2Value='" + so2Value + '\'' +
        ", so2Grade='" + so2Grade + '\'' +
        ", so2Flag='" + so2Flag + '\'' +
        ", o3Value='" + o3Value + '\'' +
        ", o3Grade='" + o3Grade + '\'' +
        ", o3Flag='" + o3Flag + '\'' +
        ", coValue='" + coValue + '\'' +
        ", coGrade='" + coGrade + '\'' +
        ", coFlag='" + coFlag + '\'' +
        ", no2Value='" + no2Value + '\'' +
        ", no2Grade='" + no2Grade + '\'' +
        ", no2Flag='" + no2Flag + '\'' +
        '}';
  }
}
