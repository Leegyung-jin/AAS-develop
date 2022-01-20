package kr.co.hulan.aas.mvc.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@ApiModel(value="WorkerWarnDto", description="근로자 경고 정보")
@AllArgsConstructor
@NoArgsConstructor
public class WorkerWarnDto {

    private Integer wwIdx;
    private String wpId;
    private String wpName;
    private String coopMbId;
    private String coopMbName;

    @ApiModelProperty(notes = "공종A명")
    private String workSectionNameA;

    @ApiModelProperty(notes = "공종B명")
    private String workSectionNameB;

    private String mbId;
    private String mbName;
    private String workerMbId;
    private String workerMbName;
    private String wwContent;
    private java.util.Date wwDatetime;

    // Derived

    @ApiModelProperty(notes = "건설사 아이디")
    private String ccId;
    @ApiModelProperty(notes = "건설사명")
    private String ccName;
}
