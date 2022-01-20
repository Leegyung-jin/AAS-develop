package kr.co.hulan.aas.common.utils;

import kr.co.hulan.aas.infra.weather.WeatherGridXY;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GpsGridUtils {

  private static Logger logger = LoggerFactory.getLogger(GpsGridUtils.class);

  private static double RE = 6371.00877; // 지구 반경(km)
  private static double GRID = 5.0; // 격자 간격(km)
  private static double SLAT1 = 30.0; // 투영 위도1(degree)
  private static double SLAT2 = 60.0; // 투영 위도2(degree)
  private static double OLON = 126.0; // 기준점 경도(degree)
  private static double OLAT = 38.0; // 기준점 위도(degree)
  private static double XO = 43; // 기준점 X좌표(GRID)
  private static double YO = 136; // 기1준점 Y좌표(GRID)

  private static double DEGRAD = Math.PI / 180.0;
  private static double re = RE / GRID;
  private static double slat1 = SLAT1 * DEGRAD;
  private static double slat2 = SLAT2 * DEGRAD;
  private static double olon = OLON * DEGRAD;
  private static double olat = OLAT * DEGRAD;

  private static double sn = Math.log(Math.cos(slat1) / Math.cos(slat2)) / Math.log(Math.tan(Math.PI * 0.25 + slat2 * 0.5) / Math.tan(Math.PI * 0.25 + slat1 * 0.5));
  private static double sf = Math.pow(Math.tan(Math.PI * 0.25 + slat1 * 0.5), sn) * Math.cos(slat1) / sn;
  private static double ro = re * sf / Math.pow(Math.tan(Math.PI * 0.25 + olat * 0.5), sn);


  public static WeatherGridXY getWeatherGrid(double lat, double lon){
    try{
      double ra = re * sf / Math.pow(Math.tan(Math.PI * 0.25 + (lat) * DEGRAD * 0.5), sn);
      double theta = lon * DEGRAD - olon;
      if (theta > Math.PI){
        theta -= 2.0 * Math.PI;
      }
      if (theta < -Math.PI){
        theta += 2.0 * Math.PI;
      }
      theta *= sn;

      int nx = (int) Math.floor(ra * Math.sin(theta) + XO + 0.5);
      int ny = (int) Math.floor(ro - ra * Math.cos(theta) + YO + 0.5);
      return new WeatherGridXY(nx,ny);
    }
    catch(Exception e){
      logger.error(e.getMessage(), e);
    }
    return null;
  }
}
