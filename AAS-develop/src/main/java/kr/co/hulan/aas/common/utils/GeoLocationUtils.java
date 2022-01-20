package kr.co.hulan.aas.common.utils;

import java.text.DecimalFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeoLocationUtils {

  private static Logger logger = LoggerFactory.getLogger(GeoLocationUtils.class);

  /*
  public static Point getPoint(Double longitude, Double latitude){
    if( longitude != null && latitude != null ){
      try{
        String pointWKT = String.format("POINT(%s %s)", longitude, latitude);
        // WKTReader를 통해 WKT를 실제 타입으로 변환합니다.
        return (Point) new WKTReader().read(pointWKT);
      }
      catch(Exception e){
        logger.error(e.getMessage(), e);
      }
    }
    return null;
  }
   */


  /**
   * 두 좌표 거리 구하기
   * @param latitude1 Start latitude
   * @param longitude1 Start longitude
   * @param latitude2 End latitude
   * @param longitude2 End longitude
   * @return Distance(m)
   */
  public static double geoDistance(double latitude1, double longitude1, double latitude2, double longitude2) {

    DecimalFormat df = new DecimalFormat("#.#####");

    if ((latitude1 == latitude2) && (longitude1 == longitude2)) {
      return 0;
    } else {
      double theta = longitude1 - longitude2;
      double distance = Math.sin(Math.toRadians(latitude1)) * Math.sin(Math.toRadians(latitude2)) +
          Math.cos(Math.toRadians(latitude1)) * Math.cos(Math.toRadians(latitude2)) * Math.cos(Math.toRadians(theta));
      distance = Math.acos(distance);
      distance = Math.toDegrees(distance);
      distance = distance * 60 * 1.1515;
      distance = distance * 1.609344;

      //distance --> 0보다 클 경우
      if (0 < distance) {
        distance = Double.valueOf(df.format(distance));    //소숫점 다섯째 자리에서 반올림

        // distance --> 0일 경우
      } else {
        distance = 0;
      }

      distance = distance * 1000; //km --> m으로 변환

      return distance;
    }

  }

  /**
   * 두 좌표 방위각 구하기
   * @param latitude1 Start latitude
   * @param longitude1 Start longitude
   * @param latitude2 End latitude
   * @param longitude2 End longitude
   * @return bearing
   */
  public static short bearingP1toP2(double latitude1, double longitude1, double latitude2, double longitude2) {
    // 현재 위치 : 위도나 경도는 지구 중심을 기반으로 하는 각도이기 때문에 라디안 각도로 변환한다.
    double Cur_Lat_radian = latitude1 * (Math.PI / 180);
    double Cur_Lon_radian = longitude1 * (Math.PI / 180);


    // 목표 위치 : 위도나 경도는 지구 중심을 기반으로 하는 각도이기 때문에 라디안 각도로 변환한다.
    double Dest_Lat_radian = latitude2 * (Math.PI / 180);
    double Dest_Lon_radian = longitude2 * (Math.PI / 180);

    // radian distance
    double radian_distance = 0;
    radian_distance = Math.acos(Math.sin(Cur_Lat_radian) * Math.sin(Dest_Lat_radian)
        + Math.cos(Cur_Lat_radian) * Math.cos(Dest_Lat_radian) * Math.cos(Cur_Lon_radian - Dest_Lon_radian));

    // 목적지 이동 방향을 구한다.(현재 좌표에서 다음 좌표로 이동하기 위해서는 방향을 설정해야 한다. 라디안값이다.
    double radian_bearing = Math.acos((Math.sin(Dest_Lat_radian) - Math.sin(Cur_Lat_radian)
        * Math.cos(radian_distance)) / (Math.cos(Cur_Lat_radian) * Math.sin(radian_distance)));// acos의 인수로 주어지는 x는 360분법의 각도가 아닌 radian(호도)값이다.

    double true_bearing = 0;
    if (Math.sin(Dest_Lon_radian - Cur_Lon_radian) < 0) {
      true_bearing = radian_bearing * (180 / Math.PI);
      true_bearing = 360 - true_bearing;
    } else {
      true_bearing = radian_bearing * (180 / Math.PI);
    }

    return (short) true_bearing;
  }

  /**
   * 특정 좌표에서 방위각, 거리를 가지고 원하는 좌표 값 획득
   * @param latitude latitude
   * @param longitude longitude
   * @param direction_degree direction
   * @param length_degree length
   * @return double[2] location = {longitude, latitude}
   */
  public static double[] geoMove(double latitude, double longitude, double direction_degree, double length_degree) {
    double[] location = new double[2];

    double x = longitude + length_degree * Math.cos(direction_degree * Math.PI / 180);
    double y = latitude + length_degree * Math.sin(direction_degree * Math.PI / 180);

    location[0] = x;
    location[1] = y;

    return location;
  }

  public static void main(String args[]){
    // geoMove(37.466689003213816, 126.43304188897983, 0, 141);

    // 126.43710957431847,37.47294336530962,  ,126.4358036893123,37.47221633795957, 141
    // 267.4330418889798, 37.466689003213816
    /*
    System.out.println(geoDistance(37.47294336530962, 126.43710957431847, 37.466689003213816, 267.4330418889798 ));
    double[] movePoints = geoMove(37.47294336530962, 126.43710957431847, 0, 141);
    System.out.println(movePoints[0]+", "+movePoints[1]);

    -- 126.42983857,37.46742873
-- 126.43012500000002,37.466386
-- 126.42933500000002,37.466386
-- 126.42979172,37.46611131

-- 01231400073,gps,126.429283,37.46751,0,0,0,0,0,,2020-10-13 07:15:28
    */

    // 11054.24


    System.out.println(geoDistance(
        37.46751, 126.429283,
        37.46611131, 126.42979172 ));
    System.out.println(geoDistance(
        37.46751, 126.429283,
        37.466386, 126.42933500000002 ));
    System.out.println(geoDistance(
        37.46751, 126.429283,
        37.466386, 126.43012500000002 ));
    System.out.println(geoDistance(
        37.46751, 126.429283,
        37.46742873, 126.42983857 ));

  }
}
