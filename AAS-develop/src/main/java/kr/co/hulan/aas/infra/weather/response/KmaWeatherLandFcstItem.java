package kr.co.hulan.aas.infra.weather.response;

import io.swagger.annotations.ApiModel;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="KmaWeatherLandFcstItem", description="날씨 예보정보 (동네예보통보문 육상예보조회)")
public class KmaWeatherLandFcstItem {

  private String regId;

  /* 발표 시간. yyyyMMddHHmm */
  private String announceTime;
  /*
  발표 번호
    17시부터 ~익일 5시 이전.
    - 0 : 오늘오후, 1 : 내일오전, 2 : 내일오후, 3 : 모레오전, 4 : 모레오후, 5 : 글피오전, 6 : 글피오후
    5시부터 ~11시 이전
    - 0 : 오늘오전, 1 : 오늘오후, 2 : 내일오전, 3 : 내일오후, 4 : 모래오전, 5 : 모레오후
    11시부터 ~ 17시 이전
    - 0 : 오늘오후, 1 : 내일오전, 2 : 내일오후, 3 : 모레오전, 4 : 모레오후
  */
  private Integer numEf;
  /* 풍향(1) */
  private String wd1;
  /* 풍향연결코드 */
  private Integer wdTnd;
  /* 풍향(2) */
  private String wd2;
  /* 풍속 강도코드.  1: 약간 강, 2: 강, 3: 매우 강*/
  private String wsIt;
  /* 예상기온. 당일 데이터는 주지 않음 */
  private String ta;
  /* 강수확률 */
  private String rnSt;
  /* 날씨 */
  private String wf;
  /* 날씨 코드(하늘상태).  DB01: 맑음, DB03: 구름많음, DB04: 흐림 */
  private String wfCd;
  /* 강수 형태.  0: 강수없음, 1: 비, 2: 비/눈, 3: 눈: 4: 소나기 */
  private String rnYn;

  public boolean isMorning(){
    LocalDateTime announceDate = getAnnounceLocalDateTime();
    if( announceDate.getHour() >= 5 && announceDate.getHour() < 11 ){
      return numEf%2 == 0;
    }
    else {
      return numEf%2 == 1;
    }
  }

  public boolean isTodayForecasting(){
    LocalDateTime announceDate = getAnnounceLocalDateTime();
    if( announceDate.getHour() >= 5 && announceDate.getHour() < 11 ){
      return numEf == 0 || numEf == 1;
    }
    else {
      return numEf == 0;
    }
  }

  public String getForecastDate(){
    LocalDateTime announceDate = getAnnounceLocalDateTime();
    if( announceDate.getHour() >= 5 && announceDate.getHour() < 11 ){
      return announceDate.plusDays((long) numEf/ 2).format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    }
    else {
      return announceDate.plusDays((long) (numEf + 1 )/ 2).format(DateTimeFormatter.ofPattern("yyyyMMdd"));
    }
  }

  private LocalDateTime getAnnounceLocalDateTime(){
    return LocalDateTime.parse(announceTime, DateTimeFormatter.ofPattern("yyyyMMddHHmm"));
  }


}
