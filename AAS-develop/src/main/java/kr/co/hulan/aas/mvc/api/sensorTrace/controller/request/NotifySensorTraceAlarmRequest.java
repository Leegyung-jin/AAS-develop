package kr.co.hulan.aas.mvc.api.sensorTrace.controller.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kr.co.hulan.aas.common.model.req.ConditionRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="NotifySensorTraceAlarmRequest", description="위험지역/위치세부기록 알람 요청")
public class NotifySensorTraceAlarmRequest extends ConditionRequest {

    @NotEmpty
    @ApiModelProperty(notes = "제목", required = true)
    private String puSubject;

    @NotEmpty
    @ApiModelProperty(notes = "내용", required = true)
    private String puContent;

    @ApiModelProperty(notes = "긴급 알림 여부.  1 : Yes")
    private Integer puChk;

    @NotNull
    @Size(min=1)
    @ApiModelProperty(notes = "대상 센서 Trace 로그 번호 리스트")
    private List<Integer> sltIdxs;

}
