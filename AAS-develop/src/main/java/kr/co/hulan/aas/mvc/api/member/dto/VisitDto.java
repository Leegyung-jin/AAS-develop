package kr.co.hulan.aas.mvc.api.member.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@ApiModel(value="VisitDto", description="접속로그 정보")
@AllArgsConstructor
@NoArgsConstructor
public class VisitDto {

    @ApiModelProperty(notes = "로그넘버")
    private Integer viId;
    @ApiModelProperty(notes = "접속IP")
    private String viIp;
    @ApiModelProperty(notes = "접속일")
    private String viDate;
    @ApiModelProperty(notes = "접속시간")
    private String viTime;
    @ApiModelProperty(notes = "Referer")
    private String viReferer;
    @ApiModelProperty(notes = "User-Agent")
    private String viAgent;
    @ApiModelProperty(notes = "Browser")
    private String viBrowser;
    @ApiModelProperty(notes = "OS")
    private String viOs;
    @ApiModelProperty(notes = "Device")
    private String viDevice;

}
