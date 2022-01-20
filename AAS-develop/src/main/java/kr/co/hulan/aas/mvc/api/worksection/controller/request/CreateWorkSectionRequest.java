package kr.co.hulan.aas.mvc.api.worksection.controller.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kr.co.hulan.aas.common.utils.SqlTimeDeserializer;
import kr.co.hulan.aas.mvc.api.file.dto.UploadedFile;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="CreateWorkSectionRequest", description="공정 생성 요청")
public class CreateWorkSectionRequest {

    @NotEmpty
    @Length(max=8)
    @ApiModelProperty(notes = "공종코드", required = true)
    private String sectionCd;

    @NotEmpty
    @Length(max=20)
    @ApiModelProperty(notes = "공종명", required = true)
    private String sectionName;

    @ApiModelProperty(notes = "공종설명")
    private String description;

    @ApiModelProperty(notes = "상위공종코드")
    private String parentSectionCd;

}
