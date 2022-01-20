package kr.co.hulan.aas.infra.broker.vo;

import lombok.Data;

@Data
public class NetworkVideoRecoderChEventDto {

  private String gid;
  private Long nvrNo;
  private Integer uid;
  private String name;
  private Integer sizeX;
  private Integer sizeY;
  private Integer ptzControlAuth;
  private Integer isPtz;
  private String id;
  private String pw;
  private String ip;
  private Integer port;
  private String url;

}
