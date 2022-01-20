package kr.co.hulan.aas.mvc.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@Data
@ApiModel(value="SensorLogDto", description="센서 로그 정보")
@AllArgsConstructor
@NoArgsConstructor
public class SensorLogDto {

    @ApiModelProperty(notes = "센서 로그 아이디")
    private Integer slIdx;

    @ApiModelProperty(notes = "구역 아이디")
    private Integer sdIdx;

    @ApiModelProperty(notes = "구역명")
    private String sdName;

    @ApiModelProperty(notes = "안전 센서명")
    private String siCode;
    private String siType;
    private String siPlace1;
    private String siPlace2;
    private String wpId;
    private String wpName;
    private String ccId;
    private String ccName;
    private String wpwId;
    private String coopMbId;
    private String coopMbName;
    private String mbId;
    private String mbName;
    @ApiModelProperty(notes = "로그 생성 시간")
    private java.util.Date slDatetime;

    // derived
    @ApiModelProperty(notes = "일반푸시 [ 1:ON, 2:OFF ]")
    private String pushNormal = "";
    @ApiModelProperty(notes = "위험푸시(사이렌) [ 1:ON, 2:OFF ]")
    private String pushDanger = "";
    @ApiModelProperty(notes = "디바이스 아이디")
    private String deviceId = "";
    private String appVersion;

    @JsonIgnore
    public boolean isAllowedPushDanger(){
        return StringUtils.equals(pushDanger, "1");
    }

}
