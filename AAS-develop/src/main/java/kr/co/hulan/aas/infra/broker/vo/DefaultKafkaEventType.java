package kr.co.hulan.aas.infra.broker.vo;

public enum DefaultKafkaEventType {
  CREATE(1, "생성"),
  UPDATE(2, "수정"),
  DELETE(3, "삭제")
      ;
  private int code;
  private String name;
  DefaultKafkaEventType(int code, String name){
    this.code = code;
    this.name = name;
  }
  public int getCode() {
    return code;
  }
  public String getName(){
    return name;
  }
  public static DefaultKafkaEventType get(Integer code){
    if( code != null ){
      for(DefaultKafkaEventType item : values()){
        if( item.getCode() == code ){
          return item;
        }
      }
    }
    return null;
  }
}
