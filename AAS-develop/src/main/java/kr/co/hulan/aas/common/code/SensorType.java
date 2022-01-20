package kr.co.hulan.aas.common.code;

import org.apache.commons.lang3.StringUtils;

public enum SensorType {

    ENTRY_EXIT( "출입용"),
    SAFETY_MEETING( "안전조회"),
    DANGER_ZONE( "위험지역"),
    NORMAL_ZONE( "일반"),
    DANGER_ZONE_NEAR( "위험지역 인접구역"),
    MONITORING_DANGER( "위험감시"),
    ;

    private String name;
    SensorType(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }
    public static SensorType get(String name){
        for(SensorType item : values()){
            if( StringUtils.equals(name, item.getName())){
                return item;
            }
        }
        return null;
    }

}
