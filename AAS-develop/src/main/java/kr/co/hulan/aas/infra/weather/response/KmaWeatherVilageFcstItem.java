package kr.co.hulan.aas.infra.weather.response;

import io.swagger.annotations.ApiModel;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="KmaWeatherVilageFcstItem", description="날씨 예보정보 (중기기온예보)")
public class KmaWeatherVilageFcstItem {

  private String baseDate;
  private String baseTime;
  private Integer nx;
  private Integer ny;
  /* 예보일자 yyyyMMdd */
  private String fcstDate;
  /* 예보시간 HHmm */
  private String fcstTime;

  private String category;
  private String fcstValue;

  public LocalDateTime getFcstDateTime(){
    return LocalDateTime.parse(fcstDate+fcstTime, DateTimeFormatter.ofPattern("yyyyMMddHHmm"));
  }
}
