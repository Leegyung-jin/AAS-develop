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
@ApiModel(value = "AuthorityManagerDto", description = "관리자 페이지를 위한 사용자 정보")
public class AuthorityManagerDto {
    @ApiModelProperty(notes = "사용자 아이디")
    private String mbId;

    @ApiModelProperty(notes = "등급 아이디")
    private Integer mbLevel;

    @ApiModelProperty(notes = "등급명")
    private String mbLevelName;

    @ApiModelProperty(notes = "권한 아이디")
    private String authorityId;
    
    @ApiModelProperty(notes = "권한명")
    private String authorityName;
        
    @ApiModelProperty(notes = "수정자")
    private String updater;

    @ApiModelProperty(notes = "수정일")
    private java.util.Date updateDate;

    @ApiModelProperty(notes = "선택한 권한")
    private List<String> selectedAuthorityList;
}
