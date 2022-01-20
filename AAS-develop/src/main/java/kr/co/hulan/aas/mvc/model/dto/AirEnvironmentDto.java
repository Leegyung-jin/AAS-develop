package kr.co.hulan.aas.mvc.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kr.co.hulan.aas.infra.airkorea.environment.dto.EnvironmentItemGrade;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@ApiModel(value="AirEnvironmentDto", description="미세먼지 측정 수치")
@AllArgsConstructor
@NoArgsConstructor
public class AirEnvironmentDto {

  @ApiModelProperty(notes = "측정소명")
  private String stationName;
  @ApiModelProperty(notes = "시도명")
  private String sidoName;
  @ApiModelProperty(notes = "망명")
  private String mangName;
  @ApiModelProperty(notes = "통합대기환경수치")
  private String khaiValue;
  @ApiModelProperty(notes = "통합대기환경수치 지수 등급")
  private String khaiGrade;
  @ApiModelProperty(notes = "통합대기환경수치 지수 등급명")
  public String getKhaiGradeName(){
    EnvironmentItemGrade grade = EnvironmentItemGrade.get(khaiGrade);
    return grade != null ? grade.getName() : "";
  }
  @ApiModelProperty(notes = "초미세먼지(PM2.5) 농도 (단위 : ㎍/㎥)")
  private String pm25Value;
  @ApiModelProperty(notes = "초미세먼지(PM2.5) 24시간예측이동농도 (단위 : ㎍/㎥)")
  private String pm25Value24;
  @ApiModelProperty(notes = "초미세먼지(PM2.5) 1시간 지수 등급")
  private String pm25Grade1h;
  @ApiModelProperty(notes = "초미세먼지(PM2.5) 1시간 지수 등급명")
  public String getPm25Grade1hName(){
    EnvironmentItemGrade grade = EnvironmentItemGrade.get(pm25Grade1h);
    return grade != null ? grade.getName() : "";
  }
  @ApiModelProperty(notes = "초미세먼지(PM2.5) 24시간 지수 등급")
  private String pm25Grade;
  @ApiModelProperty(notes = "초미세먼지(PM2.5) 24시간 지수 등급명")
  public String getPm25GradeName(){
    EnvironmentItemGrade grade = EnvironmentItemGrade.get(pm25Grade);
    return grade != null ? grade.getName() : "";
  }

  @ApiModelProperty(notes = "초미세먼지(PM2.5) 측정자료 상태 정보(점검및교정,장비점검,자료이상,통신장애)")
  private String pm25Flag;
  @ApiModelProperty(notes = "미세먼지(PM10) 농도 (단위 : ㎍/㎥)")
  private String pm10Value;
  @ApiModelProperty(notes = "미세먼지(PM10) 24시간예측이동농도 (단위 : ㎍/㎥)")
  private String pm10Value24;
  @ApiModelProperty(notes = "미세먼지(PM10) 1시간 지수 등급")
  private String pm10Grade1h;
  @ApiModelProperty(notes = "미세먼지(PM10) 1시간 지수 등급명")
  public String getPm10Grade1hName(){
    EnvironmentItemGrade grade = EnvironmentItemGrade.get(pm10Grade1h);
    return grade != null ? grade.getName() : "";
  }
  @ApiModelProperty(notes = "미세먼지(PM10) 24시간 지수 등급")
  private String pm10Grade;
  @ApiModelProperty(notes = "미세먼지(PM10) 24시간 지수 등급명")
  public String getPm10GradeName(){
    EnvironmentItemGrade grade = EnvironmentItemGrade.get(pm10Grade);
    return grade != null ? grade.getName() : "";
  }
  @ApiModelProperty(notes = "미세먼지(PM10) 측정자료 상태 정보(점검및교정,장비점검,자료이상,통신장애)")
  private String pm10Flag;

  @ApiModelProperty(notes = "아황산가스 농도 (단위 : ppm)")
  private String so2Value;
  @ApiModelProperty(notes = "아황산가스 지수 등급")
  private String so2Grade;
  @ApiModelProperty(notes = "아황산가스 지수 등급명")
  public String getSo2GradeName(){
    EnvironmentItemGrade grade = EnvironmentItemGrade.get(so2Grade);
    return grade != null ? grade.getName() : "";
  }
  @ApiModelProperty(notes = "아황산가스 측정자료 상태 정보(점검및교정,장비점검,자료이상,통신장애)")
  private String so2Flag;
  @ApiModelProperty(notes = "오존 농도 (단위 : ppm)")
  private String o3Value;
  @ApiModelProperty(notes = "오존 지수 등급")
  private String o3Grade;
  @ApiModelProperty(notes = "오존 지수 등급명")
  public String getO3GradeName(){
    EnvironmentItemGrade grade = EnvironmentItemGrade.get(o3Grade);
    return grade != null ? grade.getName() : "";
  }
  @ApiModelProperty(notes = "오존 측정자료 상태 정보(점검및교정,장비점검,자료이상,통신장애)")
  private String o3Flag;
  @ApiModelProperty(notes = "일산화탄소 농도 (단위 : ppm)")
  private String coValue;
  @ApiModelProperty(notes = "일산화탄소 지수 등급")
  private String coGrade;
  @ApiModelProperty(notes = "일산화탄소 지수 등급명")
  public String getCoGradeName(){
    EnvironmentItemGrade grade = EnvironmentItemGrade.get(coGrade);
    return grade != null ? grade.getName() : "";
  }
  @ApiModelProperty(notes = "일산화탄소 측정자료 상태 정보(점검및교정,장비점검,자료이상,통신장애)")
  private String coFlag;
  @ApiModelProperty(notes = "이산화질소 농도 (단위 : ppm)")
  private String no2Value;
  @ApiModelProperty(notes = "이산화질소 지수 등급")
  private String no2Grade;
  @ApiModelProperty(notes = "이산화질소 지수 등급명")
  public String getNo2GradeName(){
    EnvironmentItemGrade grade = EnvironmentItemGrade.get(no2Grade);
    return grade != null ? grade.getName() : "";
  }
  @ApiModelProperty(notes = "이산화질소 측정자료 상태 정보(점검및교정,장비점검,자료이상,통신장애)")
  private String no2Flag;
  @ApiModelProperty(notes = "측정시간")
  private java.util.Date dataTime;

}
