package kr.co.hulan.aas.mvc.api.worksection.controller.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kr.co.hulan.aas.mvc.model.dto.WorkSectionDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="WorkSectionDetailResponse", description="공정 상세 응답")
public class WorkSectionDetailResponse {

    @ApiModelProperty(notes = "공정 상세 정보")
    private WorkSectionDto workSection;

    @ApiModelProperty(notes = "하위 공정 리스트")
    private List<WorkSectionDto> childList;

    public boolean isLeaf(){
        return workSection != null && StringUtils.isNotEmpty( workSection.getParentSectionCd() )
                && ( childList == null || childList.size() == 0  );
    }

}
