package kr.co.hulan.aas.mvc.api.gps.dto;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="WorkerInfo", description="근로자 정보")
public class WorkerInfo {

    @ApiModelProperty(notes = "현장 아이디")
    private String wpId;

    @ApiModelProperty(notes = "현장명")
    private String wpName;

    @ApiModelProperty(notes = "근로자 아이디")
    private String mbId;

    @ApiModelProperty(notes = "근로자명")
    private String mbName;

    @ApiModelProperty(notes = "등급. 2 : 근로자")
    private String mbLevel;

    @ApiModelProperty(notes = "장비기사명")
    private String mbLevelName;

    @ApiModelProperty(notes = "협력 업체 아이디")
    private String coopMbId;

    @ApiModelProperty(notes = "협력 업체명")
    private String coopMbName;
}
