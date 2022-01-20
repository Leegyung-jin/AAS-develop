package kr.co.hulan.aas.mvc.api.monitor.controller.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import kr.co.hulan.aas.common.code.MemberLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="AttendantVo", description="출력인원 정보")
public class AttendantVo {
    @ApiModelProperty(notes = "현장명")
    private String wpName;
    @ApiModelProperty(notes = "협력사명")
    private String coopMbName;
    @ApiModelProperty(notes = "근로자 아이디(전화번호)")
    private String workerMbId;
    @ApiModelProperty(notes = "성명")
    private String workerMbName;
    @ApiModelProperty(notes = "공종A명")
    private String workSectionNameA;
    @ApiModelProperty(notes = "공종B명")
    private String workSectionNameB;
    @ApiModelProperty(notes = "체온")
    private String temperature;
    /*
    @ApiModelProperty(notes = "현재 위치(건물)")
    private String currentLocation;

    @ApiModelProperty(notes = "구역명")
    private String sdName;
    @ApiModelProperty(notes = "위치1")
    private String siPlace1;
    @ApiModelProperty(notes = "위치2")
    private String siPlace2;
     */

    @ApiModelProperty(notes = "사용자 레벨(사용자 등급)")
    private Integer mbLevel;

    @ApiModelProperty(notes = "사용자 레벨(사용자 등급)명")
    public String getMbLevelName(){
        MemberLevel memberLevel = MemberLevel.get(mbLevel);
        return memberLevel != null ? memberLevel.getName() : "";
    }

    @ApiModelProperty(notes = "출근 시간")
    private Date measureTime;
}
