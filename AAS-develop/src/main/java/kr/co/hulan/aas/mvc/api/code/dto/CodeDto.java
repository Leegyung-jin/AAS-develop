package kr.co.hulan.aas.mvc.api.code.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CodeDto {

    @ApiModelProperty(notes = "코드")
    private String code;

    @ApiModelProperty(notes = "코드명")
    private String value;

    @ApiModelProperty(notes = "코드(숫자). 숫자형인 경우만 유효")
    public Integer getCodeInt(){
        int intCode = NumberUtils.toInt(code, -9999999);
        return intCode != -9999999 ? intCode : null;
    }

}
