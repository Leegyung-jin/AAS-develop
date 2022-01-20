package kr.co.hulan.aas.mvc.api.bls.controller.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kr.co.hulan.aas.mvc.api.bls.dto.WorkerBySensor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="MonitoringInfoWorkersBySensorResponse", description="특정 Sensor 내 접근자 리스트 응답")
public class MonitoringInfoWorkersBySensorResponse {

    @ApiModelProperty(notes = "센서 아이디")
    private Integer siIdx;

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

    @ApiModelProperty(notes = "구역 아이디")
    private String sdIdx;

    @ApiModelProperty(notes = "구역명")
    private String sdName;

    @ApiModelProperty(notes = "빌딩명")
    private String buildingName;

    @ApiModelProperty(notes = "빌딩 층")
    private Integer floor;

    @ApiModelProperty(notes = "건설사명")
    private String ccName;

    @ApiModelProperty(notes = "현장명")
    private String wpName;

    @ApiModelProperty(notes = "Sensor 내 접근자 리스트")
    List<WorkerBySensor> worker_list;

}
