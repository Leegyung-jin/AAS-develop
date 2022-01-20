package kr.co.hulan.aas.common.code;

public enum WorkerCheckType {
    ATTENDTION(1, "주요근로자"),
    HIGH_RISK(2,  "고위험근로자"),
    ;

    private int code;
    private String name;
    WorkerCheckType(int code, String name){
        this.code = code;
        this.name = name;
    }
    public int getCode() {
        return code;
    }
    public String getName(){
        return name;
    }
    public static WorkerCheckType get(int code){
        for(WorkerCheckType item : values()){
            if( item.getCode() == code ){
                return item;
            }
        }
        return null;
    }
}
