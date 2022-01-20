package kr.co.hulan.aas.common.code;

public enum MeasureEnvironmentType {

  TEMPERATURE(1, "온도", "℃", "TEMPERATURE.png"),
  OXYGEN(2, "산소", "%", "OXYGEN.png"),
  HYDROGEN_SULFIDE(3, "황화수소", "ppm", "HYDROGEN_SULFIDE.png"),
  CARBON_MONOXIDE(4, "일산화탄소", "ppm", "CARBON_MONOXIDE.png"),
  METHANE(5, "메탄", "%", "METHANE.png"),
  HUMIDITY(6, "습도", "%", "HUMIDITY.png")
  ;
  private int code;
  private String name;
  private String unit;
  private String iconFileName;
  MeasureEnvironmentType(int code, String name, String unit, String iconFileName){
    this.code = code;
    this.name = name;
    this.unit = unit;
    this.iconFileName = iconFileName;
  }
  public int getCode() {
    return code;
  }
  public String getName() {
    return name;
  }
  public String getUnit() {
    return unit;
  }
  public String getIconFilePath() {
    return "smart_monitor/measure/"+iconFileName;
  }

}
