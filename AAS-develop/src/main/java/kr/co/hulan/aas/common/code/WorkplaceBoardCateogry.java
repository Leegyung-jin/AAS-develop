package kr.co.hulan.aas.common.code;

import org.apache.commons.lang3.StringUtils;

public enum WorkplaceBoardCateogry {
    SAFETY(1, "안전",
        new int[]{ MemberLevel.SUPER_ADMIN.getCode(), MemberLevel.FIELD_MANAGER.getCode() },
        new int[]{ MemberLevel.SUPER_ADMIN.getCode(), MemberLevel.COOPERATIVE_COMPANY.getCode() }
    ),
    CONSTRUCTION(2, "공사",
        new int[]{ MemberLevel.SUPER_ADMIN.getCode(), MemberLevel.FIELD_MANAGER.getCode() },
        new int[]{ MemberLevel.SUPER_ADMIN.getCode(), MemberLevel.COOPERATIVE_COMPANY.getCode() }
    ),
    WORK_APPROVAL(3, "작업승인",
        new int[]{ MemberLevel.SUPER_ADMIN.getCode(), MemberLevel.COOPERATIVE_COMPANY.getCode() },
        new int[]{ MemberLevel.SUPER_ADMIN.getCode(), MemberLevel.FIELD_MANAGER.getCode() }
    ),
    ;

    private int code;
    private String name;
    private int[] regLevels;
    private int[] replyLevels;
    WorkplaceBoardCateogry(int code, String name, int[] regLevels, int[] replyLevels ){
        this.code = code;
        this.name = name;
        this.regLevels = regLevels;
        this.replyLevels = replyLevels;
    }

    public int getCode() {
        return code;
    }
    public String getName(){
        return name;
    }
    public boolean isRegistableLevel(int level){
        for( int regLevel : regLevels ){
            if( regLevel == level ){
                return true;
            }
        }
        return false;
    }
    public boolean isRepliableLevel(int level){
        for( int replyLevel : replyLevels ){
            if( replyLevel == level ){
                return true;
            }
        }
        return false;
    }
    public static WorkplaceBoardCateogry get(int code){
        for(WorkplaceBoardCateogry item : values()){
            if(code == item.getCode()){
                return item;
            }
        }
        return null;
    }
    public static WorkplaceBoardCateogry getByName(String name){
        for(WorkplaceBoardCateogry item : values()){
            if( StringUtils.equals(item.getName(), name)){
                return item;
            }
        }
        return null;
    }


}
