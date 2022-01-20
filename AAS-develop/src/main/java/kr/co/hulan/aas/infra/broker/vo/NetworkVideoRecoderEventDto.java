package kr.co.hulan.aas.infra.broker.vo;

import lombok.Data;

@Data
public class NetworkVideoRecoderEventDto {

  private Long nvrNo;
  private String nvrName;
  private Integer nvrType;
  private String description;
  private String id;
  private String pw;
  private String ip;
  private Integer port;
  private Integer status;
  private String wpId;

}
