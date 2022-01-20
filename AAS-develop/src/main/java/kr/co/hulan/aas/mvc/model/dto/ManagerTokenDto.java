package kr.co.hulan.aas.mvc.model.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@ApiModel(value="ManagerTokenDto", description="매니저 토큰 정보")
@AllArgsConstructor
@NoArgsConstructor
public class ManagerTokenDto {

    private Integer mtIdx;
    private String mbId;
    private String mtToken;
    private Integer mtYn;
    private String appVersion;
    private java.util.Date mtDatetime;
}
