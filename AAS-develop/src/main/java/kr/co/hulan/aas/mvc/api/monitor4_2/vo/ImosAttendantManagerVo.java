package kr.co.hulan.aas.mvc.api.monitor4_2.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import kr.co.hulan.aas.common.code.MemberLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="ImosAttendantManagerVo", description="IMOS QrGate 출근 매니저 정보")
public class ImosAttendantManagerVo {
  @ApiModelProperty(notes = "현장명")
  private String wpName;
  @ApiModelProperty(notes = "매니저 아이디(전화번호)")
  private String mbId;
  @ApiModelProperty(notes = "성명")
  private String mbName;

  @ApiModelProperty(notes = "관련 회사명")
  private String companyName;

  @ApiModelProperty(notes = "체온")
  private String temperature;

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
