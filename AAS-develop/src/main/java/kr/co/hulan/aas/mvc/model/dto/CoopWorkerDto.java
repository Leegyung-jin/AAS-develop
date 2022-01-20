package kr.co.hulan.aas.mvc.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@Data
@ApiModel(value="CoopWorkerDto", description="가입시 협력사 정보")
@AllArgsConstructor
@NoArgsConstructor
public class CoopWorkerDto {

    @ApiModelProperty(notes = "근로자 아이디")
    private String workerMbId;

    @ApiModelProperty(notes = "협력사 아이디")
    private String coopMbId;

    @ApiModelProperty(notes = "생성일")
    private Date createDatetime;

    @ApiModelProperty(notes = "수정일")
    private Date updateDatetime;

}
