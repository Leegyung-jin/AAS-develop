package kr.co.hulan.aas.mvc.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@Data
@ApiModel(value="SensorInfoDto", description="센서 정보")
@AllArgsConstructor
@NoArgsConstructor
public class SensorInfoDto {

    @ApiModelProperty(notes = "센서 아이디")
    private Integer siIdx;

    @ApiModelProperty(notes = "현장 아이디")
    private String wpId;
    @ApiModelProperty(notes = "현장명")
    private String wpName;
    @ApiModelProperty(notes = "구역 아이디")
    private Integer sdIdx;
    @ApiModelProperty(notes = "구역명")
    private String sdName;
    @ApiModelProperty(notes = "안전센서번호")
    private String siCode;
    @ApiModelProperty(notes = "유형")
    private String siType;
    @ApiModelProperty(notes = "위치1")
    private String siPlace1;
    @ApiModelProperty(notes = "위치2")
    private String siPlace2;
    @ApiModelProperty(notes = "현장관리자 PUSH알림. 0 : 수신안함, 1 :수신 ")
    private Integer siPush;
    @ApiModelProperty(notes = "등록일")
    private java.util.Date siDatetime;
    @ApiModelProperty(notes = "센서 uuid")
    private String uuid;
    @ApiModelProperty(notes = "센서 major identifier")
    private Integer major;
    @ApiModelProperty(notes = "센서 minor identifier")
    private Integer minor;

    // Derived
    @ApiModelProperty(notes = "건설사 아이디")
    private String ccId;
    @ApiModelProperty(notes = "건설사명")
    private String ccName;
    @ApiModelProperty(notes = "센서 설치수")
    private Long sensorCount;

    @ApiModelProperty(notes = "빌딩 넘버(SEQ)")
    private Long buildingNo;
    @ApiModelProperty(notes = "빌딩명")
    private String buildingName;
    @ApiModelProperty(notes = "빌딩 층")
    private Integer floor;
    @ApiModelProperty(notes = "빌딩 층명")
    private String floorName;


    @JsonIgnore
    public void makeSiCode(){
        StringBuilder sb = new StringBuilder();
        // sb.append("si_");
        if( major != null ){
            String majorStr = ""+major;

            // 메이저 뒷 2자리만 이용하는 이유. si_code 는 단지 센서에 셋팅할 명 용도로 사용되며 unique 하지 않다.
            // 실제 단말에서 센서 구분은 major 와 minor 를 사용한다.
            sb.append(StringUtils.leftPad(StringUtils.substring(majorStr, majorStr.length() > 2 ? majorStr.length() - 2 : 0,  majorStr.length()),2, "0"  )).append("_");
        }
        if( minor != null ){
            sb.append(minor);
        }
        siCode = StringUtils.removeEnd(sb.toString(), "_");
    }


}
