package kr.co.hulan.aas.infra.weather;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.sql.Time;
import java.util.Date;
import java.util.Map;
import javax.persistence.Basic;
import javax.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="WeatherInfo", description="날씨 정보")
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherInfo {

    @ApiModelProperty(notes = "현장 아이디")
    private String wpId;

    @ApiModelProperty(notes = "예보지점 X좌표")
    private int nx;

    @ApiModelProperty(notes = "예보지점 Y좌표")
    private int ny;

    @ApiModelProperty(notes = "습도 단위")
    public String humidityUnit;

    @ApiModelProperty(notes = "습도")
    private String humidity;

    @ApiModelProperty(notes = "온도 단위")
    private String temperatureUnit;

    @ApiModelProperty(notes = "온도")
    private String temperature;

    @ApiModelProperty(notes = "풍속 단위")
    private String windSpeedUnit;

    @ApiModelProperty(notes = "풍속")
    private String windSpeed;

    @ApiModelProperty(notes = "풍향")
    private String windDirection;

    @ApiModelProperty(notes = "강수 형태 코드")
    private String precipitationForm;

    @ApiModelProperty(notes = "강수 형태명")
    private String precipitationFormName;

    @ApiModelProperty(notes = "강수량")
    private String precipitation;

    @ApiModelProperty(notes = "강수량 단위")
    private String precipitationUnit;

    @ApiModelProperty(notes = "하늘형태 코드")
    private String skyForm;

    @ApiModelProperty(notes = "하늘 형태명")
    private String skyFormName;

    @ApiModelProperty(notes = "강수확률")
    private String rainfall;

    @ApiModelProperty(notes = "강수확률 단위")
    private String rainfallUnit;

    @ApiModelProperty(notes = "기준일자(yyyyMMdd)")
    private String baseDate;

    @ApiModelProperty(notes = "기준시간(HHmm)")
    private String baseTime;

    @ApiModelProperty(notes = "업데이트 시간")
    private Date updateDate;

    @JsonIgnore
    @Override
    public String toString() {
        return "WeatherInfo{" +
            "wpId='" + wpId + '\'' +
            ", nx=" + nx +
            ", ny=" + ny +
            ", humidityUnit='" + humidityUnit + '\'' +
            ", humidity='" + humidity + '\'' +
            ", temperatureUnit='" + temperatureUnit + '\'' +
            ", temperature='" + temperature + '\'' +
            ", windSpeedUnit='" + windSpeedUnit + '\'' +
            ", windSpeed='" + windSpeed + '\'' +
            ", windDirection='" + windDirection + '\'' +
            ", precipitationForm='" + precipitationForm + '\'' +
            ", precipitation='" + precipitation + '\'' +
            ", precipitationUnit='" + precipitationUnit + '\'' +
            ", baseDate='" + baseDate + '\'' +
            ", baseTime='" + baseTime + '\'' +
            '}';
    }
}
