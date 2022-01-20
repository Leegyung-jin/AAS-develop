package kr.co.hulan.aas.common.code;

public enum DeviceType {
  ENTRANCE_GATE(0, "출입게이트(QR)"),
  ENTRANCE_DEVICE(1,  "출입 표출 디바이스"),
  FIXED_GPS(2,  "고정형 GPS"),
  VIDEO_ANALYSIS_EQUIP(3,  "영상분석 서버"),
  HAZARD_SENSOR(4,  "유해물질 측정 센서"),
  HAZARD_ALARM(5,  "유해물질 경보기"),
  TILT_SENSOR(6,  "기울기 센서"),
  OPENING_SENSOR(7,  "개구부 센서"),
  WIND_GAUGE(8,  "풍속계"),
  LORA_GATEWAY(9,  "LoRa 게이트웨이"),
  TEMPERATURE_MOISTURE_SENSOR(10,  "온습도 센서"),
  NOISE_SENSOR(11,  "소음측정기"),
  ;
  private int code;
  private String name;
  DeviceType(int code, String name){
    this.code = code;
    this.name = name;
  }
  public int getCode() {
    return code;
  }
  public String getName(){
    return name;
  }
  public static DeviceType get(int code){
    for(DeviceType item : values()){
      if( item.getCode() == code ){
        return item;
      }
    }
    return null;
  }
}
