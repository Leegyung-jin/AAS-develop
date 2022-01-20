package kr.co.hulan.aas.mvc.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kr.co.hulan.aas.common.model.ExcelDataRow;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Data
@ApiModel(value="SensorLogTraceDto", description="센서 로그 Trace 정보")
@AllArgsConstructor
@NoArgsConstructor
public class SensorLogTraceDto implements ExcelDataRow {

    private Integer sltIdx;
    private Integer sdIdx;
    private String sdName;
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
    private String workSectionA;
    private String workSectionNameA;
    private String workSectionB;
    private String workSectionNameB;
    private String mbId;
    private String mbName;
    private Integer sltInSdIdx;
    private String sltInSdName;
    private String sltInSiCode;
    private String sltInSiType;
    private java.util.Date sltInDatetime;
    private Integer sltOutSdIdx;
    private String sltOutSdName;
    private String sltOutSiCode;
    private String sltOutSiType;
    private java.util.Date sltOutDatetime;
    private java.util.Date sltDatetime;

    // derived
    @ApiModelProperty(notes = "일반푸시 [ 1:ON, 2:OFF ]")
    private String pushNormal = "";
    @ApiModelProperty(notes = "위험푸시(사이렌) [ 1:ON, 2:OFF ]")
    private String pushDanger = "";
    @ApiModelProperty(notes = "디바이스 아이디")
    private String deviceId = "";
    @ApiModelProperty(notes = "앱 버전")
    private String appVersion = "";

    @JsonIgnore
    public boolean isAllowedPushDanger(){
        return StringUtils.equals(pushDanger, "1");
    }

    @JsonIgnore
    public String[] toArray() {
        // "시간","IN","OUT","건설사","공사명","협력사","아이디","성명","유형","구역","위치1","위치2"
        List<String> list = new ArrayList<String>();
        list.add( sltInDatetime != null ? new SimpleDateFormat("yyyy-MM-dd").format(sltInDatetime) : "" );
        list.add( sltInDatetime != null ? new SimpleDateFormat("HH:mm:ss").format(sltInDatetime) : "" );
        list.add( sltOutDatetime != null ? new SimpleDateFormat("HH:mm:ss").format(sltOutDatetime) : "" );
        list.add( StringUtils.defaultIfEmpty(ccName, ""));
        list.add( StringUtils.defaultIfEmpty(wpName, ""));
        list.add( StringUtils.defaultIfEmpty(coopMbName, ""));
        list.add( StringUtils.defaultIfEmpty(mbId, ""));
        list.add( StringUtils.defaultIfEmpty(mbName, ""));
        list.add( StringUtils.defaultIfEmpty(siType, ""));
        list.add( StringUtils.defaultIfEmpty(sdName, ""));
        list.add( StringUtils.defaultIfEmpty(siPlace1, ""));
        list.add( StringUtils.defaultIfEmpty(siPlace2, ""));
        return list.toArray(new String[0]);
    }
}
