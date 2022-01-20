package kr.co.hulan.aas.common.code;

public enum WorkerWorkPrintStatus {
    CANCEL(-1, "취소"),
    NOT_SUBMITTED(0, "미제출"),
    WAITING_APPROVAL(1, "승인대기"),
    APPROVAL_COMPLETED(2, "승인완료"),
    ;

    private int code;
    private String name;
    WorkerWorkPrintStatus(int code, String name){
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }
    public String getName(){
        return name;
    }
    public static WorkerWorkPrintStatus get(int code){
        for(WorkerWorkPrintStatus item : values()){
            if(code == item.getCode()){
                return item;
            }
        }
        return null;
    }
}
