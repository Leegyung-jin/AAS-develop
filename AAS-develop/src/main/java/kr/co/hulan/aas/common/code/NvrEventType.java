package kr.co.hulan.aas.common.code;

import kr.co.hulan.aas.common.validator.CodeForEnum;
import org.apache.commons.lang3.StringUtils;

public enum NvrEventType {

  EVENT_0("0", "배회"),
  EVENT_1("1", "경로 통과"),
  EVENT_2("2", "방향성 이동"),
  EVENT_3("3", "진입"),
  EVENT_4("4", "진출"),
  EVENT_5("5", "멈춤"),
  EVENT_6("6", "버려짐"),
  EVENT_7("7", "경계선 통과"),
  EVENT_8("8", "연기", "화재 위험"),
  EVENT_9("9", "불꽃"),
  EVENT_10("10", "쓰러짐", "근로자 쓰러짐"),
  EVENT_11("11", "군집"),
  EVENT_12("12", "폭력"),
  EVENT_13("13", "멀티 경계선 통과"),
  EVENT_14("14", "차량 사고"),
  EVENT_15("15", "차량 멈춤"),
  EVENT_16("16", "교통 정체"),
  EVENT_17("17", "색상 변화"),
  EVENT_18("18", "차량 주차"),
  EVENT_19("19", "제거됨"),
  EVENT_20("20", "위험수위"),
  EVENT_21("21", "영역색상"),
  EVENT_22("22", "체류"),
  EVENT_23("23", "체류-초과인원"),
  EVENT_24("24", "체류-시간초과"),
  EVENT_25("25", "체류-단독인원체류"),
  EVENT_26("26", "안전모 미착용", "안전모 미착용"),
  EVENT_29("29", "홀로 남겨짐"),
  EVENT_30("30", "접근"),
  EVENT_31("31", "따로 떨어짐"),
  EVENT_32("32", "행동인식"),
  ;
  private String code;
  private String name;
  private String exposeName;
  NvrEventType(String code, String name){
    this.code = code;
    this.name = name;
    this.exposeName = name;
  }
  NvrEventType(String code, String name, String exposeName){
    this.code = code;
    this.name = name;
    this.exposeName = exposeName;
  }

  public String getCode() {
    return code;
  }
  public String getName(){
    return name;
  }
  public String getExposeName(){
    return exposeName;
  }
  public static NvrEventType get(String code){
    if( StringUtils.isNotBlank(code) ){
      for(NvrEventType item : values()){
        if( StringUtils.equals(code,  item.getCode()) ){
          return item;
        }
      }
    }
    return null;
  }



}

