package kr.co.hulan.aas.mvc.api.gps.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="GpsLocation", description="현장 근로자 GPS 측위 정보")
@JsonIgnoreProperties(ignoreUnknown = true)
public class GpsLocation {

    @ApiModelProperty(notes = "GPS provider")
    private String provider;

    @ApiModelProperty(notes = "경도")
    private Double longitude;

    @ApiModelProperty(notes = "위도")
    private Double latitude;

    @ApiModelProperty(notes = "고도")
    private Double altitude;

    @ApiModelProperty(notes = "위/경도 정확도")
    private Double accuracy;

    @ApiModelProperty(notes = "속도")
    private Double speed;

    @ApiModelProperty(notes = "방위")
    private Double bearing;

    @ApiModelProperty(notes = "배터리 충전량")
    private Double battery;

    @ApiModelProperty(notes = "센서 접근 시간. (yyyy-MM-DD HH:mm:ss)")
    private String measureTime;

}
