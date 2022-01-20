package kr.co.hulan.aas.infra.broker.vo;

public enum NetworkVideoRecoderChEventType {
  CREATE(1, "생성"),
  UPDATE(2, "수정"),
  DELETE(3, "삭제")
  ;
  private int code;
  private String name;
  NetworkVideoRecoderChEventType(int code, String name){
    this.code = code;
    this.name = name;
  }
  public int getCode() {
    return code;
  }
  public String getName(){
    return name;
  }
  public static NetworkVideoRecoderChEventType get(Integer code){
    if( code != null ){
      for(NetworkVideoRecoderChEventType item : values()){
        if( item.getCode() == code ){
          return item;
        }
      }
    }
    return null;
  }
}
