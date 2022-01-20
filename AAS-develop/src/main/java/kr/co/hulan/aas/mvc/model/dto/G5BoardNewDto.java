package kr.co.hulan.aas.mvc.model.dto;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@ApiModel(value="G5BoardNewDto", description="최근 게시물 정보")
@AllArgsConstructor
@NoArgsConstructor
public class G5BoardNewDto {

    private String boTable;
    private Integer wrId;
    private Integer wrParent;
    private Date bnDatetime;
    private String mbId;

    // derived
    private String wrSubject;
    private String wrName;


}
