package kr.co.hulan.aas.mvc.api.gps.controller.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kr.co.hulan.aas.mvc.api.gps.dto.GpsInfo;
import kr.co.hulan.aas.infra.weather.WeatherInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="SearchGpsInfoResult", description="GPS 정보 조회 결과")
@JsonIgnoreProperties(ignoreUnknown = true)
public class SearchGpsInfoResult {

    @ApiModelProperty(notes = "결과코드 (“00”: 성공, 그 외 오류)")
    private String resultCode;

    @ApiModelProperty(notes = "오류 메시지 (결과코드가 “00”이 아닐시 사용됨)")
    private String message;

    @ApiModelProperty(notes = "날씨 정보")
    private WeatherInfo weatherInfo;

    @ApiModelProperty(notes = "GPS 측위 근로자 정보 리스트")
    private List<GpsInfo> gpsList = new ArrayList<GpsInfo>();

    public SearchGpsInfoResult(String resultCode, String message ){
        this.resultCode = resultCode;
        this.message = message;
    }

}
