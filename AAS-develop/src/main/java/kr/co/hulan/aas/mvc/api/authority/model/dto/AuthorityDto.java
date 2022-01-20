package kr.co.hulan.aas.mvc.api.authority.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "AuthorityDto", description = "권한 정보")
public class AuthorityDto {

    @ApiModelProperty(notes = "권한ID")
    private String authorityId;

    @ApiModelProperty(notes = "권한명")
    private String authorityName;

    @ApiModelProperty(notes = "설명")
    private String description;

    @ApiModelProperty(notes = "생성일")
    private java.util.Date createDate;

    @ApiModelProperty(notes = "생성자")
    private String creator;

    @ApiModelProperty(notes = "수정일")
    private java.util.Date updateDate;

    @ApiModelProperty(notes = "수정자")
    private String updater;

    @ApiModelProperty(notes = "권한을 포함(선택)한 등급")
    private List<String> selectedLevelList;

    @ApiModelProperty(notes = "권한을 사용중인 사용자")
    private List<AuthorityUserDto> usedAuthorityUserList;
}