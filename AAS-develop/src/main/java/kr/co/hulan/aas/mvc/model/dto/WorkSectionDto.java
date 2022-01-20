package kr.co.hulan.aas.mvc.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Builder
@Data
@ApiModel(value="WorkSectionDto", description="공종 정보")
@AllArgsConstructor
@NoArgsConstructor
public class WorkSectionDto {

    @ApiModelProperty(notes = "공종코드")
    private String sectionCd;
    @ApiModelProperty(notes = "공종명")
    private String sectionName;
    @ApiModelProperty(notes = "공종설명")
    private String description;
    @ApiModelProperty(notes = "상위공종코드")
    private String parentSectionCd;
    @ApiModelProperty(notes = "상위공종명")
    private String parentSectionName;
    @ApiModelProperty(notes = "생성일")
    private Date createDate;
    @ApiModelProperty(notes = "생성자 아이디(mb_id)")
    private String creator;
    @ApiModelProperty(notes = "수정일")
    private Date updateDate;
    @ApiModelProperty(notes = "수정자 아이디(mb_id)")
    private String updater;

}
