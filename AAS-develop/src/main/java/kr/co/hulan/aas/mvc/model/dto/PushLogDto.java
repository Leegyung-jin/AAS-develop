package kr.co.hulan.aas.mvc.model.dto;


import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@ApiModel(value="PushLogDto", description="푸쉬 로그 정보")
@AllArgsConstructor
@NoArgsConstructor
public class PushLogDto {

    private Integer plIdx;
    private String url;
    private String token;
    private String title;
    private String content;
    private String subData;
    private java.util.Date plDatetime;
    private String plResponse;

    // derived
    private boolean pushDanger;

}
