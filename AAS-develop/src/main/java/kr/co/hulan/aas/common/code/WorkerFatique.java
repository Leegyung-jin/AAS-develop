package kr.co.hulan.aas.common.code;

public enum WorkerFatique {
    NOMRAL(0, 9*60, "좋음"),
    GOOD(9*60, 10*60, "양호"),
    DANGER(10*60, 12*60, "위험"),
    NEED_RELAX(12*60, 24*60+1, "휴식필요")
    ;

    private int min;
    private int max;
    private String name;
    WorkerFatique(int min, int max, String name){
        this.min = min;
        this.max = max;
        this.name = name;
    }
    public int getMin() {
        return min;
    }
    public int getMax() {
        return max;
    }
    public String getName(){
        return name;
    }
    public static WorkerFatique get(int workTime){
        for(WorkerFatique item : values()){
            if( item.getMin() <= workTime && item.getMax() > workTime ){
                return item;
            }
        }
        return null;
    }
}
