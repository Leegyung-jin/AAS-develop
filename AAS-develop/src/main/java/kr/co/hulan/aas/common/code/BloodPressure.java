package kr.co.hulan.aas.common.code;

public enum BloodPressure {
    NOMRAL(0,"정상(120이하)"),
    CAUTION(1, "주의(120~140)"),
    HIGH_BLOOD_PRESSURE(2, "고혈압(140이상)")
    ;

    private int code;
    private String name;
    BloodPressure(int code, String name){
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }
    public String getName(){
        return name;
    }
    public static BloodPressure get(int code){
        for(BloodPressure item : values()){
            if(code == item.getCode()){
                return item;
            }
        }
        return null;
    }
}
