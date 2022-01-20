package kr.co.hulan.aas.mvc.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@ApiModel(value="SensorLogInoutDto", description="센서 접근기록 저장(입/출기록) 정보")
@AllArgsConstructor
@NoArgsConstructor
public class SensorLogInoutDto {


    private Integer sliIdx;
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
    @ApiModelProperty(notes = "협력사명")
    private String coopMbName;
    @ApiModelProperty(notes = "아이디")
    private String mbId;
    @ApiModelProperty(notes = "성명")
    private String mbName;
    private Integer sliInSdIdx;
    private String sliInSdName;
    private String sliInSiCode;
    private String sliInSiType;
    @ApiModelProperty(notes = "센서 In 시간")
    private java.util.Date sliInDatetime;
    private Integer sliOutSdIdx;
    private String sliOutSdName;
    private String sliOutSiCode;
    private String sliOutSiType;
    @ApiModelProperty(notes = "센서 Out 시간")
    private java.util.Date sliOutDatetime;
    @ApiModelProperty(notes = "날짜")
    private java.util.Date sliDatetime;
    private String sliMiddleDatetime;

    @ApiModelProperty(notes = "근로자 공정A")
    private String workSectionNameA;
    @ApiModelProperty(notes = "근로자 공정B")
    private String workSectionNameB;

    // Derived
    @ApiModelProperty(notes = "안전조회 참여일")
    private java.util.Date safeDatetime;

    @ApiModelProperty(notes = "당월 출근일")
    private Integer workerMonthCount;

    @ApiModelProperty(notes = "전체 출근일")
    private Integer workerTotalCount;
}
