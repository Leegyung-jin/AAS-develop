package kr.co.hulan.aas.mvc.api.level.model.dto;

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
@ApiModel(value = "LevelDto", description = "등급 정보 목록")
public class LevelDto {

    @ApiModelProperty(notes = "등급")
    private Integer mbLevel;

    @ApiModelProperty(notes = "등급명")
    private String mbLevelName;

    @ApiModelProperty(notes = "설명")
    private String description;

    @ApiModelProperty(notes = "등록일")
    private java.util.Date createDate;

    @ApiModelProperty(notes = "등록자")
    private String creator;

    @ApiModelProperty(notes = "수정일")
    private java.util.Date updateDate;

    @ApiModelProperty(notes = "수정자")
    private String updater;

    @ApiModelProperty(notes = "등급에 포함된 권한 목록")
    private List<String> selectedAuthorityList;

}
