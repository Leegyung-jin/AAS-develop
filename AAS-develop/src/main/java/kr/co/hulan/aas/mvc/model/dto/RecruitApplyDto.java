package kr.co.hulan.aas.mvc.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@ApiModel(value="RecruitApplyDto", description="구직 정보")
@AllArgsConstructor
@NoArgsConstructor
public class RecruitApplyDto {

    private Integer raIdx;
    private Integer rcIdx;
    private String wpId;
    private String wpName;
    private String coopMbId;
    private String coopMbName;
    private String mbId;
    private String mbName;
    private String mbBirth;
    private java.util.Date raDatetime;
    private String raStatus;

    @ApiModelProperty(notes = "건설사 아이디")
    private String ccId;
    @ApiModelProperty(notes = "건설사명")
    private String ccName;
    @ApiModelProperty(notes = "구인 타이틀")
    private String rcTitle;

    @ApiModelProperty(notes = "push token")
    private String deviceId;

    @ApiModelProperty(notes = "App Version")
    private String appVersion;

}
