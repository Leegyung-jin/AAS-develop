package kr.co.hulan.aas.common.code;

import java.math.BigDecimal;
import org.apache.commons.lang3.StringUtils;

/* NOT USED.  풍속 임계치는 DB 로 관리. wind_speed_range_policy 참고   */
public enum WindSpeedCategory {

  LEVEL_1( 1, "바람이 매우 약하게 불고 있습니다", 0, 1, 0, 2, ""),
  LEVEL_2( 2, "바람이 약하게 불고 있습니다", 2, 4, 3, 7, ""),
  LEVEL_3( 3, "바람이 다소 불고 있습니다", 5, 8, 8, 12, ""),
  LEVEL_4( 4, "바람이 다소 강하게 불고 있습니다", 9, 12, 13, 18, ""),
  LEVEL_5( 5, "바람이 강하게 불고 있습니다", 13, 17, 19, 25, "폭풍주의보"),
  LEVEL_6( 6, "바람이 매우 강하게 불고 있습니다", 18, 9999, 26, 9999, "폭풍경보"),
  ;

  private int code;
  private String name;
  private BigDecimal speedMin;
  private BigDecimal speedMax;
  private BigDecimal maxSpeedMin;
  private BigDecimal maxSpeedMax;
  private String emergencyName;
  WindSpeedCategory(int code, String name, int speedMin, int speedMax, int maxSpeedMin, int maxSpeedMax, String emergencyName){
    this.code = code;
    this.name = name;
    this.speedMin = new BigDecimal(speedMin);
    this.speedMax = new BigDecimal(speedMax);
    this.maxSpeedMin = new BigDecimal(maxSpeedMin);
    this.maxSpeedMax = new BigDecimal(maxSpeedMax);
    this.emergencyName = emergencyName;
  }
  public String getName(){
    return name;
  }
  public String getEmergencyName(){ return emergencyName;  }
  public BigDecimal getSpeedMin(){ return speedMin; }
  public BigDecimal getSpeedMax(){ return speedMax; }
  public BigDecimal getMaxSpeedMin(){ return maxSpeedMin; }
  public BigDecimal getMaxSpeedMax(){ return maxSpeedMax; }
  public boolean isEmergency(){
    return StringUtils.isNotBlank(emergencyName);
  }
  private boolean isAppliedSpeed(BigDecimal speed){
    return speedMin.compareTo(speed) <= 0 && speedMax.compareTo(speed) >= 0 ;
  }
  private boolean isAppliedMaxSpeed(BigDecimal speed){
    return maxSpeedMin.compareTo(speed) <= 0 && maxSpeedMax.compareTo(speed) >= 0 ;
  }
  public static WindSpeedCategory get(Double speed, Double maxSpeed){
    if( speed != null && maxSpeed != null ){
      for(WindSpeedCategory item : values()){
        if( item.isAppliedSpeed(new BigDecimal(speed)) || item.isAppliedMaxSpeed(new BigDecimal(maxSpeed)) ){
          return item;
        }
      }
    }
    return null;
  }

}
