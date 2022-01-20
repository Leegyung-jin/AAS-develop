package kr.co.hulan.aas.mvc.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@ApiModel(value="RecruitDto", description="구인 정보")
@AllArgsConstructor
@NoArgsConstructor
public class RecruitDto {

    private Integer rcIdx;
    private String rcTitle;
    private String wpId;
    private String wpName;
    private String wpcId;
    private String coopMbId;
    private String coopMbName;
    private String rcWork1;
    private String rcWork2;
    private String rcWork3;
    private String workSectionA;
    private String workSectionNameA;
    private String workSectionB;
    private String workSectionNameB;
    private String rcPayAmount;
    private String rcPayUnit;
    private String rcTel;
    private String rcContent;
    private java.util.Date rcDatetime;
    private java.util.Date rcUpdatetime;

    //derived
    private Long recruitApplyCount;
    @ApiModelProperty(notes = "건설사 아이디")
    private String ccId;
    @ApiModelProperty(notes = "건설사명")
    private String ccName;

    @ApiModelProperty(notes = "올리기 가능 여부. 0 : 불가, 1 : 가능")
    public String getEnabledUpload(){
        return ( System.currentTimeMillis() - getRcUpdatetime().getTime() < 24 * 60 * 60 * 1000 ) ? "0" : "1";
    }

}
