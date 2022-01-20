package kr.co.hulan.aas.mvc.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kr.co.hulan.aas.common.utils.GenerateIdUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@ApiModel(value="WorkPlaceCoopDto", description="협력사 현장 편입 정보")
@AllArgsConstructor
@NoArgsConstructor
public class WorkPlaceCoopDto {

    private String wpcId;
    @ApiModelProperty(notes = "현장 아이디")
    private String wpId;
    @ApiModelProperty(notes = "현장명(공사명)")
    private String wpName;
    @ApiModelProperty(notes = "건설사 아이디")
    private String ccId;
    @ApiModelProperty(notes = "건설사명")
    private String ccName;
    @ApiModelProperty(notes = "협력사 아이디")
    private String coopMbId;
    @ApiModelProperty(notes = "협력사명")
    private String coopMbName;
    @ApiModelProperty(notes = "기존공종명")
    private String wpcWork;
    @ApiModelProperty(notes = "공종A코드")
    private String workSectionA;
    @ApiModelProperty(notes = "공종A명")
    private String workSectionNameA;

    private String wpSido;
    private String wpGugun;
    private String wpAddr;
    private java.util.Date wpcDatetime;



    @ApiModelProperty(notes = "등록 노동자 수")
    private Long workerCount;
    @ApiModelProperty(notes = "당일 노동자 수")
    private Long workerTodayCount;
    @ApiModelProperty(notes = "당월 노동자 수")
    private Long workerMonthCount;
    @ApiModelProperty(notes = "전체 노동자 수")
    private Long workerTotalCount;

    @ApiModelProperty(notes = "당일 안전교육 참석자수")
    private Long workerSafetyEducationCount;

    @ApiModelProperty(notes = "지정일")
    private java.util.Date targetDate;

}
