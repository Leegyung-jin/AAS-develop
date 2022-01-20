package kr.co.hulan.aas.mvc.api.workplace.controller.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import kr.co.hulan.aas.common.utils.SqlTimeDeserializer;
import kr.co.hulan.aas.mvc.api.file.dto.UploadedFile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="CreateWorkplaceRequest", description="현장 생성 요청")
public class CreateWorkplaceRequest {

    @NotBlank(message = "시설분류는 필수입니다.")
    @ApiModelProperty(notes = "시설 분류", required = true)
    private String wpCate;

    @NotBlank(message = "건설사는 필수입니다.")
    @ApiModelProperty(notes = "건설사 아이디", required = true)
    private String ccId;

    @NotBlank(message = "현장 관리자는 필수입니다.")
    @ApiModelProperty(notes = "현장 관리자 아이디", required = true)
    private String manMbId;

    @NotBlank(message = "현장명은 필수입니다.")
    @ApiModelProperty(notes = "현장명(공사명)", required = true)
    private String wpName;

    @NotBlank(message = "주소은 필수입니다.")
    @ApiModelProperty(notes = "주소 - 시도", required = true)
    private String wpSido;

    @NotBlank(message = "주소은 필수입니다.")
    @ApiModelProperty(notes = "주소 - 구군", required = true)
    private String wpGugun;

    @NotBlank(message = "주소은 필수입니다.")
    @ApiModelProperty(notes = "주소 - 상세 주소", required = true)
    private String wpAddr;


    @ApiModelProperty(notes = "주소 상세 정보 데이터(Json string)")
    private String addressData;

    @NotBlank(message = "현장 전화번호는 필수입니다.")
    @ApiModelProperty(notes = "현장 전화번호", required = true)
    private String wpTel;

    @ApiModelProperty(notes = "착공일")
    private java.util.Date wpStartDate;

    @ApiModelProperty(notes = "준공일")
    private java.util.Date wpEndDate;

    @ApiModelProperty(notes = "무재해 시작일")
    private java.util.Date uninjuryRecordDate;

    @NotNull(message = "완료상태는 필수입니다.")
    @ApiModelProperty(notes = "완료상태", required = true)
    private Integer wpEndStatus;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    @JsonDeserialize(using = SqlTimeDeserializer.class)
    @ApiModelProperty(notes = "교육 시작시간. HH:mm")
    private java.sql.Time wpEdutimeStart;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    @JsonDeserialize(using = SqlTimeDeserializer.class)
    @ApiModelProperty(notes = "교육 완료시간. HH:mm")
    private java.sql.Time wpEdutimeEnd;

    @ApiModelProperty(notes = "메모")
    private String wpMemo;

    @ApiModelProperty(notes = "조감도 파일")
    private UploadedFile viewMapFile;

    @NotBlank(message = "디폴트 협력사 아이디는 필수입니다.")
    @ApiModelProperty(notes = "디폴트 협력사 아이디", required = true)
    private String defaultCoopMbId;

    @ApiModelProperty(notes = "GPS 현재인원 표시여부. 0: OFF, 1: ON")
    private Integer currentWorkerGps;

    @ApiModelProperty(notes = "BLE 현재인원 표시여부. 0: OFF, 1: ON")
    private Integer currentWorkerBle;

    @ApiModelProperty(notes = "먼지 수치 표시여부. 0: OFF, 1: ON")
    private Integer environmentDust;

    @ApiModelProperty(notes = "소음 수치 표시여부. 0: OFF, 1: ON")
    private Integer environmentNoise;

    @ApiModelProperty(notes = "위험물질 수치 표시여부. 0: OFF, 1: ON")
    private Integer environmentGas;

    @ApiModelProperty(notes = "BLE 현장 모니터링 지원여부. 0: 미지원, 1: 지원")
    private Integer supportBle;
    @ApiModelProperty(notes = "GPS 현장 모니터링 지원여부. 0: 미지원, 1: 지원")
    private Integer supportGps;

    @JsonProperty("support_3d")
    @ApiModelProperty(notes = "3D 현장 모니터링 지원여부. 0: 미지원, 1: 지원")
    private Integer support3d;

    @ApiModelProperty(notes = "추락감지 지원 여부. 0: 미지원, 1: 지원")
    private Integer fallingEvent;

    @ApiModelProperty(notes = "칼만 필터 지원 여부. 0: 미지원, 1: 지원")
    private Integer kalmanFilter;

    @ApiModelProperty(notes = "미세먼지 측정소명")
    private String stationName;

    @NotNull(message = "Activation Geofence 경도는 필수입니다.")
    @ApiModelProperty(notes = "Activation Geofence 경도", required = true)
    private Double activationGeofenceLongitude;

    @NotNull(message = "Activation Geofence 위도는 필수입니다.")
    @ApiModelProperty(notes = "Activation Geofence 위도", required = true)
    private Double activationGeofenceLatitude;

    @NotNull(message = "Activation Geofence 반경은 필수입니다.")
    @Min(value = 1000, message = "최소반경은 1000 m 입니다")
    @ApiModelProperty(notes = "Activation Geofence 반경 (단위 m)", required = true)
    private Integer activationGeofenceRadius;

    @ApiModelProperty(notes = "발주사 번호")
    private Long officeNo;

    @ApiModelProperty(notes = "공사 규모")
    private String  constructScale;

    @JsonIgnore
    public void init(){
        if(supportBle == null ){
            supportBle = 0;
        }
        if(supportGps == null ){
            supportGps = 0;
        }
        if(support3d == null ){
            support3d = 0;
        }
        if(currentWorkerGps == null ){
            currentWorkerGps = 0;
        }
        if(currentWorkerBle == null ){
            currentWorkerBle = 0;
        }
        if(environmentDust == null ){
            environmentDust = 0;
        }
        if(currentWorkerGps == null ){
            currentWorkerGps = 0;
        }
        if(currentWorkerBle == null ){
            currentWorkerBle = 0;
        }
        if(environmentDust == null ){
            environmentDust = 0;
        }
        if(environmentNoise == null ){
            environmentNoise = 0;
        }
        if(environmentGas == null ){
            environmentGas = 0;
        }

    }

}
