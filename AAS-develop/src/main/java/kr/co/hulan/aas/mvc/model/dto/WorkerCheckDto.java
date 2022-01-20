package kr.co.hulan.aas.mvc.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@ApiModel(value="WorkerCheckDto", description="고위험/주요근로자 정보")
@AllArgsConstructor
@NoArgsConstructor
public class WorkerCheckDto {

    private Integer wcIdx;
    private String wpId;
    private String wpName;
    private String coopMbId;
    private String coopMbName;

    @ApiModelProperty(notes = "공종A명")
    private String workSectionNameA;

    @ApiModelProperty(notes = "공종B명")
    private String workSectionNameB;

    private Integer wcType;
    private String wpcId;
    private String mbId;
    private String mbName;
    private String workerMbId;
    private String workerMbName;
    private String wcMemo;
    private java.util.Date wcDatetime;

    // Derived

    @ApiModelProperty(notes = "건설사 아이디")
    private String ccId;
    @ApiModelProperty(notes = "건설사명")
    private String ccName;
}
