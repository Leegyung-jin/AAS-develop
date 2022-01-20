package kr.co.hulan.aas.mvc.api.monitor4_2.vo.nvr;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kr.co.hulan.aas.common.code.Storage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="NvrEventFileVo", description="NVR 이벤트 이미지 파일 정보")
public class NvrEventFileVo {

  @ApiModelProperty(notes = "이벤트 파일 관리번호")
  private Long fileNo;
  @ApiModelProperty(notes = "이벤트 로그 넘버")
  private Long elogNo;
  @ApiModelProperty(notes = "이벤트 발생일")
  private java.util.Date tm;

  @JsonProperty(access= Access.WRITE_ONLY)
  @ApiModelProperty(notes = "대표 파일명", hidden = true)
  private String fileName;
  @JsonProperty(access= Access.WRITE_ONLY)
  @ApiModelProperty(notes = "대표 파일 저장 위치(상대경로)", hidden = true)
  private String filePath;
  @JsonProperty(access= Access.WRITE_ONLY)
  @ApiModelProperty(notes = "대표 파일 저장소. 0: local Storage ", hidden = true)
  private Integer fileLocation;

  @ApiModelProperty(notes = "대표 파일 다운로드 URL")
  public String getFileDownloadUrl(){
    if(StringUtils.isNotEmpty(fileName)
        && StringUtils.isNotEmpty(filePath)
        && fileLocation != null ){
      Storage storage = Storage.get(fileLocation);
      if( storage != null ){
        StringBuilder sb = new StringBuilder();
        sb.append(storage.getDownloadUrlBase());
        sb.append(filePath);
        sb.append(fileName);
        return sb.toString();
      }
    }
    return "";
  }
}
