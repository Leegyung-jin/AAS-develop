package kr.co.hulan.aas.common.code;

public enum MemberLevel {

    WORKER(2, "근로자"),
    COOPERATIVE_COMPANY(3, "협력사"),
    FIELD_MANAGER(4, "현장관리자"),
    CONSTRUNCTION_COMPANY_MANAGER(5,"건설사 관리자"),
    WP_GROUP_MANAGER(6,"발주사 현장그룹 매니저"),
    OFFICE_MANAGER(7,"발주사 관리자"),
    SUPER_ADMIN(10, "어드민")
    ;

    private int code;
    private String name;
    MemberLevel(int code, String name){
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }
    public String getName(){
        return name;
    }
    public static MemberLevel get(Integer code){
        if( code != null ){
            for(MemberLevel item : values()){
                if(code == item.getCode()){
                    return item;
                }
            }
        }
        return null;
    }


}
