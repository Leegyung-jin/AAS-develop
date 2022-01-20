package kr.co.hulan.aas.mvc.api.gps.controller.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="UpdateGpsCheckPolicyInfoRequest", description="GPS 정책 수정 요청")
public class UpdateGpsCheckPolicyInfoRequest {

    @NotNull
    @ApiModelProperty(notes = "GPS 정책 아이디", required = true)
    private Integer idx;

    @NotEmpty
    @ApiModelProperty(notes = "현장 아이디", required = true)
    private String wpId;

    @NotNull
    @ApiModelProperty(notes = "기준 longitude 좌표", required = true)
    private Double gpsCenterLong;

    @NotNull
    @ApiModelProperty(notes = "기준 latitude 좌표", required = true)
    private Double gpsCenterLat;

    /*
    @NotNull
    @ApiModelProperty(notes = "기준 좌표 에서 제한 거리 Km (해당 거리 이내의 측위 정보만 수집)", required = true)
    private BigDecimal gpsDistLimit;
     */

    @NotNull
    @ApiModelProperty(notes = "거리 측정용 longitude 좌표", required = true)
    private Double gpsRoundLong;

    @NotNull
    @ApiModelProperty(notes = "거리 측정용 latitude 좌표", required = true)
    private Double gpsRoundLat;

    @NotNull
    @ApiModelProperty(notes = "기준 좌표 에서 제한 거리 m (해당 거리 이내의 측위 정보만 수집)", required = true)
    private Integer gpsDistLimitMeter;

    @NotNull
    @ApiModelProperty(notes = "보고 주기(ms) - 해당 주기 이상 미 동작 시 서버에 report를 실시", required = true)
    private Long reportInterval;

    @NotNull
    @ApiModelProperty(notes = "GPS 스캔 주기(ms) - 해당 주기 이상 미 동작 시 단말에서 로컬 알림 발생.", required = true)
    private Long gpsInterval;

    @NotEmpty
    @ApiModelProperty(notes = "GPS  측위 시작 시간.  HHmmss", required = true)
    private String allowStart;

    @NotEmpty
    @ApiModelProperty(notes = "GPS  측위 종료 시간.  HHmmss", required = true)
    private String allowEnd;
}
