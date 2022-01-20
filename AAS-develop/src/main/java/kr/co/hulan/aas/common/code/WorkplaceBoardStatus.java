package kr.co.hulan.aas.common.code;

public enum WorkplaceBoardStatus {
    NO_INPUT(0, "미입력"),
    INPUT_RESULT(1, "결과입력"),
    APPROVAL(2, "승인"),
    NOT_APPROVAL(3, "미승인"),
    ;

    private int code;
    private String name;
    WorkplaceBoardStatus(int code, String name){
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }
    public String getName(){
        return name;
    }
    public static WorkplaceBoardStatus get(int code){
        for(WorkplaceBoardStatus item : values()){
            if(code == item.getCode()){
                return item;
            }
        }
        return null;
    }
}
