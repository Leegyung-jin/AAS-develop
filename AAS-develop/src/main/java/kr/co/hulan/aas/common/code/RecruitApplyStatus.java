package kr.co.hulan.aas.common.code;

import org.apache.commons.lang3.StringUtils;

public enum RecruitApplyStatus {

    READY("0", "대기"),
    UNDER_SCREENING("1", "심사중"),
    TRANSFER_WORKPLACE("2", "현장 편입")
    ;

    private String code;
    private String name;
    RecruitApplyStatus(String code, String name){
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }
    public String getName(){
        return name;
    }
    public static RecruitApplyStatus get(String code){
        for(RecruitApplyStatus item : values()){
            if(StringUtils.equals(code, item.getCode()) ){
                return item;
            }
        }
        return null;
    }
}
