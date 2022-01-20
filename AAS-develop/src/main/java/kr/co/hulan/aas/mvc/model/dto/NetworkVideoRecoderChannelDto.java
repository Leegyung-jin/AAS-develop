package kr.co.hulan.aas.mvc.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NetworkVideoRecoderChannelDto {

  @ApiModelProperty(notes = "채널 아이디")
  private String gid;
  @ApiModelProperty(notes = "NVR 관리번호")
  private Long nvrNo;
  @ApiModelProperty(notes = "NVR명")
  private String nvrName;
  @ApiModelProperty(notes = "채널 아이디 ( 계정별 Unique )")
  private Integer uid;
  @ApiModelProperty(notes = "채널명")
  private String name;
  @ApiModelProperty(notes = "영상원본 해상도(X)")
  private Integer sizeX;
  @ApiModelProperty(notes = "영상원본 해상도(Y)")
  private Integer sizeY;
  @ApiModelProperty(notes = "PTZ 제어권 소유 여부. 0: 미소유, 1: 소유")
  private Integer ptzControlAuth;
  @ApiModelProperty(notes = "PTZ 카메라 여부. 0: NO, 1: YES")
  private Integer isPtz;
  @ApiModelProperty(notes = "CCTV 계정 아이디")
  private String id;
  @ApiModelProperty(notes = "CCTV 계정 패스워드")
  private String pw;
  @ApiModelProperty(notes = "CCTV IP")
  private String ip;
  @ApiModelProperty(notes = "CCTV Port")
  private Integer port;
  @ApiModelProperty(notes = "CCTV URL")
  private String url;
  @ApiModelProperty(notes = "등록일")
  private java.util.Date createDate;
  @ApiModelProperty(notes = "등록자")
  private String creator;
  @ApiModelProperty(notes = "수정일")
  private java.util.Date updateDate;
  @ApiModelProperty(notes = "수정자")
  private String updater;

  @ApiModelProperty(notes = "사이즈 표현")
  public String getSize(){
    return String.format("%sX%s", ""+sizeX, ""+sizeY);
  }

  @ApiModelProperty(notes = "채널명")
  public String getGname(){
    return name;
  }
}
