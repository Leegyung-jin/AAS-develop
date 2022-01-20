package kr.co.hulan.aas.common.code;

public enum BuildingFloorType {
    FLOOR(1, "층"),
    ROOF(1000,  "옥상"),
    GANAFORM(2000,  "갱폼"),
    DISTRICT_GROUP(3000,  "구획"),
            ;
    private int code;
    private String name;
    BuildingFloorType(int code, String name){
        this.code = code;
        this.name = name;
    }
    public int getCode() {
        return code;
    }
    public String getName(){
        return name;
    }
    public static BuildingFloorType get(int code){
        for(BuildingFloorType item : values()){
            if( item.getCode() == code ){
                return item;
            }
        }
        return null;
    }
}
