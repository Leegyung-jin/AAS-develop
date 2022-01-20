package kr.co.hulan.aas.mvc.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@Data
@ApiModel(value="HulanSequenceDto", description="Sequence 정보")
@AllArgsConstructor
@NoArgsConstructor
public class HulanSequenceDto {

    private String seqName;
    private long nextVal;
    private String etc1;

    @JsonIgnore
    public String generateMemberKey(){
        return etc1 + StringUtils.leftPad(String.valueOf(nextVal%1000), 3, "0" );
    }
}
