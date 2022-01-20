package kr.co.hulan.aas.mvc.api.safetySituation.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@ApiModel(value="WorkerSafetySituationDto", description="근로자별 안전관리 현황 정보")
@AllArgsConstructor
@NoArgsConstructor
public class WorkerSafetySituationDto {

    @ApiModelProperty(notes = "날짜")
    private java.util.Date sliDatetime;

    @ApiModelProperty(notes = "협력사 아이디")
    private String coopMbId;

    @ApiModelProperty(notes = "협력사명")
    private String coopMbName;

    @ApiModelProperty(notes = "성명")
    private String mbName;

    @ApiModelProperty(notes = "아이디(근로자 휴대폰 번호)")
    private String mbId;

    @ApiModelProperty(notes = "현장 아이디")
    private String wpId;

    @ApiModelProperty(notes = "공종A명")
    private String workSectionNameA;

    @ApiModelProperty(notes = "공종B명")
    private String workSectionNameB;

    @ApiModelProperty(notes = "안전조회 참여일")
    private java.util.Date safeDatetime;

    @ApiModelProperty(notes = "센서 In 시간")
    private java.util.Date sliInDatetime;

    @ApiModelProperty(notes = "센서 Out 시간")
    private java.util.Date sliOutDatetime;

    @ApiModelProperty(notes = "당월 출근일")
    private Integer workerMonthCount;

    @ApiModelProperty(notes = "전체 출근일")
    private Integer workerTotalCount;






}
