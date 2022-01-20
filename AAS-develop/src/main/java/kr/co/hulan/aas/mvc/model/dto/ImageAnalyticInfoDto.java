package kr.co.hulan.aas.mvc.model.dto;

import io.swagger.annotations.ApiModel;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Id;
import kr.co.hulan.aas.common.code.Storage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@Data
@ApiModel(value="ImageAnalyticInfoDto", description="영상 분석 이벤트 정보")
@AllArgsConstructor
@NoArgsConstructor
public class ImageAnalyticInfoDto {
  private String wpId;
  private String macAddress;
  private Integer eventType;
  private Integer eventStatus;
  private String filePath;
  private String fileName;
  private String orgFileName;
  private java.util.Date eventDatetime;
  private Integer eventView;

  public String getEventImageDownloadUrl(){
    if(StringUtils.isNotEmpty(fileName)
        && StringUtils.isNotEmpty(filePath)){
      Storage storage = Storage.get(Storage.LOCAL_STORAGE.getCode());
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
