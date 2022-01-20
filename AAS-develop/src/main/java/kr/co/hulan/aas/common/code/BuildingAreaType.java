package kr.co.hulan.aas.common.code;

import org.apache.commons.lang3.StringUtils;

public enum BuildingAreaType {
    BUILDING(1, "빌딩"),
    GROUND(2,  "지상"),
    UNDERGROUND(3,  "지하"),
    DISTRICT_GROUP(4,  "구획"),
    ;

    private int code;
    private String name;
    BuildingAreaType(int code, String name){
        this.code = code;
        this.name = name;
    }
    public int getCode() {
        return code;
    }
    public String getName(){
        return name;
    }
    public static BuildingAreaType get(int code){
        for(BuildingAreaType item : values()){
            if( item.getCode() == code ){
                return item;
            }
        }
        return null;
    }
}
