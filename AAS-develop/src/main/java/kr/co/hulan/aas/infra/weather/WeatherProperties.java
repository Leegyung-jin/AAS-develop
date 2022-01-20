package kr.co.hulan.aas.infra.weather;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix="infra.weather")
public class WeatherProperties {

  private String serviceUltraSrtNcstUrl;       // 초단기 실황 조회
  private String serviceUltraSrtFcstUrl;   // 초단기예보
  private String serviceVilageFcstUrl; // 동네예보 단기 조회
  private String serviceMidLandFcstUrl;  // 중기육상예보조회
  private String serviceMidTaUrl;  // 중기기온조회
  private String serviceLandFcstUrl; // 동네예보통보문(육상예보조회)
  private String serviceKey;
  private int threadCount;
  private long cacheTime;

  public long getCacheTimeMilli(){
    return cacheTime * 60 * 1000;
  }

}
