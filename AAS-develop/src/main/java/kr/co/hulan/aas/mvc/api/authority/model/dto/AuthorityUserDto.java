package kr.co.hulan.aas.mvc.api.authority.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "AuthorityUserDto", description = "권한 부여가 가능한 사용자 정보")
public class AuthorityUserDto {
    @ApiModelProperty(notes = "사용자 아이디")
    private String mbId;

    @ApiModelProperty(notes = "사용자명")
    private String mbName;

    @ApiModelProperty(notes = "등급 아이디")
    private String mbLevel;

    @ApiModelProperty(notes = "등급명")
    private String mbLevelName;

    @ApiModelProperty(notes = "생성자")
    private String creator;

    @ApiModelProperty(notes = "생성일")
    private java.util.Date createDate;
    
    @ApiModelProperty(notes = "수정자")
    private String updater;

    @ApiModelProperty(notes = "수정일")
    private java.util.Date updateDate;

    @ApiModelProperty(notes = "권한 아이디")
    private String authorityId;
}
