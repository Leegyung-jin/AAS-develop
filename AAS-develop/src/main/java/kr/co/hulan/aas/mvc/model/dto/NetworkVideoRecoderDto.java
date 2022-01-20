package kr.co.hulan.aas.mvc.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kr.co.hulan.aas.common.code.NetworkVideoRecoderType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="NetworkVideoRecoderDto", description="NVR 정보")
public class NetworkVideoRecoderDto {

  @ApiModelProperty(notes = "NVR 관리번호")
  private Long nvrNo;
  @ApiModelProperty(notes = "NVR명")
  private String nvrName;
  @ApiModelProperty(notes = "NVR 유형. 1: 일반, 2: 지능형(IntelliVix) ")
  private Integer nvrType;
  @ApiModelProperty(notes = "설명")
  private String description;

  @ApiModelProperty(notes = "계정 아이디")
  private String id;
  @ApiModelProperty(notes = "계정 패스워드")
  private String pw;
  @ApiModelProperty(notes = "IP")
  private String ip;
  @ApiModelProperty(notes = "Port")
  private Integer port;
  @ApiModelProperty(notes = "상태. 0: 미사용, 1: 사용")
  private Integer status;
  @ApiModelProperty(notes = "현장 아이디")
  private String wpId;
  @ApiModelProperty(notes = "현장명")
  private String wpName;
  @ApiModelProperty(notes = "등록일")
  private java.util.Date createDate;
  @ApiModelProperty(notes = "등록자")
  private String creator;
  @ApiModelProperty(notes = "수정일")
  private java.util.Date updateDate;
  @ApiModelProperty(notes = "수정자")
  private String updater;

  @ApiModelProperty(notes = "Host")
  public String getHost(){
    return String.format("%s:%s", ip, ""+port);
  }

  @ApiModelProperty(notes = "NVR 유형명")
  public String getNvrTypeName(){
    NetworkVideoRecoderType ntype = NetworkVideoRecoderType.get(nvrType);
    return ntype != null ? ntype.getName() : "";
  }

}
