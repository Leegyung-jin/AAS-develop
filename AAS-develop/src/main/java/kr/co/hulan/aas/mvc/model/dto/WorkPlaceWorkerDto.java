package kr.co.hulan.aas.mvc.model.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kr.co.hulan.aas.common.code.WorkerFatique;
import kr.co.hulan.aas.common.utils.GenerateIdUtils;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@ApiModel(value="WorkPlaceWorkerDto", description="현장 근로자 정보")
@AllArgsConstructor
@NoArgsConstructor
public class WorkPlaceWorkerDto {


    private String wpwId;
    @ApiModelProperty(notes = "현장 아이디")
    private String wpId;
    @ApiModelProperty(notes = "현장명")
    private String wpName;
    private String wpcId;
    private String coopMbId;
    @ApiModelProperty(notes = "협력사명")
    private String coopMbName;
    private String wpcWork;
    @ApiModelProperty(notes = "공종A코드")
    private String workSectionA;
    @ApiModelProperty(notes = "공종A명")
    private String workSectionNameA;
    @ApiModelProperty(notes = "공종B코드")
    private String workSectionB;
    @ApiModelProperty(notes = "공종B명")
    private String workSectionNameB;
    @ApiModelProperty(notes = "근로자 아이디(전화번호)")
    private String workerMbId;
    @ApiModelProperty(notes = "성명")
    private String workerMbName;

    @ApiModelProperty(notes = "퇴출 여부. 0 : 아니오, 1 : 예")
    private Integer wpwOut;
    @ApiModelProperty(notes = "퇴출 사유")
    private String wpwOutMemo;
    @ApiModelProperty(notes = "혈압. 0 : 정상, 1 : 주의, 2 : 고혈압")
    private Integer wpwBp;

    @ApiModelProperty(notes = "수술경험(최근1년이내). 0 : 없음, 1 : 있음")
    private Integer wpwOper;
    @ApiModelProperty(notes = "당뇨. 0 : 없음, 1 : 있음")
    private Integer wpwDis1;
    @ApiModelProperty(notes = "고지혈증. 0 : 없음, 1 : 있음")
    private Integer wpwDis2;
    @ApiModelProperty(notes = "심근경색. 0 : 없음, 1 : 있음")
    private Integer wpwDis3;
    @ApiModelProperty(notes = "뇌출혈. 0 : 없음, 1 : 있음")
    private Integer wpwDis4;
    private Integer wpwShow;
    private Integer wpwStatus;
    private java.util.Date wpwDatetime;

    // derived
    @ApiModelProperty(notes = "날짜")
    private String sliDate;
    @ApiModelProperty(notes = "건설사 아이디")
    private String ccId;
    @ApiModelProperty(notes = "건설사명")
    private String ccName;

    @ApiModelProperty(notes = "생년월일")
    private String birthday = "";
    @ApiModelProperty(notes = "주소")
    private String mbAddr1 = "";
    @ApiModelProperty(notes = "상세주소")
    private String mbAddr2 = "";
    @ApiModelProperty(notes = "혈액형")
    private String bloodType = "";

    @ApiModelProperty(notes = "금일 근무시간")
    private Integer todayWorkTime;
    @ApiModelProperty(notes = "전일 근무시간")
    private Integer yesterdayWorkTime;
    @ApiModelProperty(notes = "금주 근무시간")
    private Integer weekWorkTime;


    @ApiModelProperty(notes = "지정일")
    private java.util.Date targetDate;

    @ApiModelProperty(notes = "안전교육 참석 시간")
    private java.util.Date educationAttendDate;

    @ApiModelProperty(notes = "안전교육 참석 여부")
    private String attendStatus = "";
}
