package kr.co.hulan.aas.mvc.model.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@ApiModel(value="G5ContentDto", description="내용 정보")
@AllArgsConstructor
@NoArgsConstructor
public class G5ContentDto {

    private String coId;
    private Integer coHtml;
    private String coSubject;
    private String coContent;
    private String coMobileContent;
    private String coSkin;
    private String coMobileSkin;
    private Integer coTagFilterUse;
    private Integer coHit;
    private String coIncludeHead;
    private String coIncludeTail;

}
