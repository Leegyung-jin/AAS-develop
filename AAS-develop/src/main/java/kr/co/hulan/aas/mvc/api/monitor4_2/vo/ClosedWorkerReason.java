package kr.co.hulan.aas.mvc.api.monitor4_2.vo;

public enum ClosedWorkerReason {

  NOT_SUPPORT(0, "모니터링 미지원", "미지원"),
  OK(1, "정상", "정상"),
  NOT_EXISTS_DEVICE_STATUS(11, "단말 상태 로그 없음", "앱 미사용"),
  NOT_EXISTS_TODAY_DEVICE_STATUS(12,  "단말 상태 금일 로그 없음", "앱 미사용(당일)"),
  DEVICE_IDLE(13,  "APP 종료 내지는 음영구역", "앱 종료"),
  BLE_OFF(14,  "BLE 사용 미설정", "BLE 미사용"),
  GPS_OFF(15,  "GPS 사용 미설정", "GPS 미사용"),
  NOT_EXISTS_GPS_LOG(21,  "GPS 로그 없음", "GPS 미수신"),
  EXPIRED_GPS_LOG(22,  "GPS 로그 시간 만료", "GPS 미수신"),
  NOT_EXISTS_SENSOR_LOG(31,  "센서 감지 이력 없음", "센서 미감지"),
  NOT_EXISTS_TODAY_SENSOR_LOG(32,  "금일 센서 감지 이력 없음", "센서 미감지(당일)"),
  LEAVE_WORKPLACE(33,  "외출 및 퇴근", "외출 및 퇴근"),
  NOT_REGISTERED_DETECT_SENSOR(34,  "미등록 센서에 감지", "미등록 센서"),

  ;
  private int code;
  private String name;
  private String desc;
  ClosedWorkerReason(int code, String name, String desc){
    this.code = code;
    this.name = name;
    this.desc = desc;
  }
  public int getCode() {
    return code;
  }
  public String getName(){
    return name;
  }
  public String getDesc(){
    return desc;
  }
  public static ClosedWorkerReason get(Integer code){
    if( code != null ){
      for(ClosedWorkerReason item : values()){
        if( code == item.getCode() ){
          return item;
        }
      }
    }
    return null;
  }
}
