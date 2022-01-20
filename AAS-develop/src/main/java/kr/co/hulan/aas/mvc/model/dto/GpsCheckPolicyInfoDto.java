package kr.co.hulan.aas.mvc.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.persistence.Basic;
import javax.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@ApiModel(value="GpsCheckPolicyInfoDto", description="현장별 GPS 정책")
@AllArgsConstructor
@NoArgsConstructor
public class GpsCheckPolicyInfoDto {

    @ApiModelProperty(notes = "GPS 정책 아이디")
    private Integer idx;

    @ApiModelProperty(notes = "현장 아이디")
    private String wpId;

    @ApiModelProperty(notes = "현장명")
    private String wpName;

    @ApiModelProperty(notes = "기준 longitude 좌표")
    private Double gpsCenterLong;

    @ApiModelProperty(notes = "기준 latitude 좌표")
    private Double gpsCenterLat;

    @ApiModelProperty(notes = "기준 좌표 에서 제한 거리 Km (해당 거리 이내의 측위 정보만 수집)")
    private BigDecimal gpsDistLimit;

    @ApiModelProperty(notes = "기준 longitude 좌표")
    private Double gpsRoundLong;

    @ApiModelProperty(notes = "기준 latitude 좌표")
    private Double gpsRoundLat;

    @ApiModelProperty(notes = "기준 좌표 에서 제한 거리 m (해당 거리 이내의 측위 정보만 수집)")
    private BigDecimal gpsDistLimitMeter;

    @ApiModelProperty(notes = "보고 주기(ms) - 해당 주기 이상 미 동작 시 서버에 report를 실시")
    private Long reportInterval;

    @ApiModelProperty(notes = "GPS 스캔 주기(ms) - 해당 주기 이상 미 동작 시 단말에서 로컬 알림 발생.")
    private Long gpsInterval;

    @ApiModelProperty(notes = "GPS  측위 시작 시간.  HHmmss")
    private String allowStart;

    @ApiModelProperty(notes = "GPS  측위 종료 시간.  HHmmss")
    private String allowEnd;

    @ApiModelProperty(notes = "등록/변경 일시")
    private java.util.Date updDatetime;

    @ApiModelProperty(notes = "등록/변경자")
    private String updater;

}
