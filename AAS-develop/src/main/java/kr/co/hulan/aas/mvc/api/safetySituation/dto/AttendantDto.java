package kr.co.hulan.aas.mvc.api.safetySituation.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@ApiModel(value="AttendantDto", description="출근자 정보")
@AllArgsConstructor
@NoArgsConstructor
public class AttendantDto {

    @ApiModelProperty(notes = "출근자 아이디(전화번호)")
    private String workerMbId;
    @ApiModelProperty(notes = "출근자 성명")
    private String workerMbName;

}
