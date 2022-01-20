package kr.co.hulan.aas.mvc.api.bls.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import kr.co.hulan.aas.common.code.SensorType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@Data
@ApiModel(value="FloorGridSituations", description="현장 빌딩 층 상황")
@AllArgsConstructor
@NoArgsConstructor
public class FloorGridSituation {

  @ApiModelProperty(notes = "빌딩 넘버(SEQ)")
  private Long buildingNo;

  @ApiModelProperty(notes = "빌딩 층")
  private Integer floor;

  @ApiModelProperty(notes = "빌딩 층명")
  private String floorName;

  @ApiModelProperty(notes = "x 축 좌표")
  private Integer gridX;

  @ApiModelProperty(notes = "y 축 좌표")
  private Integer gridY;

  @ApiModelProperty(notes = "근로자 수")
  private Integer workerCount;

  @ApiModelProperty(notes = "센서 아이디")
  private Integer siIdx;

  @ApiModelProperty(notes = "안전센서번호")
  private String siCode;

  @ApiModelProperty(notes = "안전센서유형")
  private String siType;

  @ApiModelProperty(notes = "구역명")
  private String sdName;

  @ApiModelProperty(notes = "위치1")
  private String siPlace1;

  @ApiModelProperty(notes = "위치2")
  private String siPlace2;

  @ApiModelProperty(notes = "기본색")
  private String defaultColor;

  @ApiModelProperty(notes = "점멸색")
  private String flashColor;

  @ApiModelProperty(notes = "점멸 지원여부. 0: 미지원, 1:지원")
  private Integer supportFlash;

  @ApiModelProperty(notes = "marker 표시 여부. 0: 미표시, 1: 표시")
  private Integer marker;

  @JsonIgnoreProperties
  @ApiModelProperty(notes = "장비 아이콘 Url 리스트 텍스트", hidden = true)
  private String equipmentIconUrls;

  @ApiModelProperty(notes = "기본색")
  public String getDefaultColor(){
    if( workerCount == null || workerCount == 0 ){
      if( SensorType.DANGER_ZONE != SensorType.get(siType) ){
        return "gray";
      }
    }
    return defaultColor;
  }

  @ApiModelProperty(notes = "장비 아이콘 Url 리스트")
  public List<String> getEquipmentIconUrlList(){
    List<String> urlList = new ArrayList<String>();
    if(StringUtils.isNotBlank(equipmentIconUrls) ){
      String[] tokens = StringUtils.split(equipmentIconUrls, "|");
      if( tokens != null && tokens.length > 0 ){
        for( String token : tokens ){
          urlList.add(token);
        }
      }
    }
    return urlList;
  }


}
