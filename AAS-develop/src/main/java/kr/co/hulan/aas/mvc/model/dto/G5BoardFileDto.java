package kr.co.hulan.aas.mvc.model.dto;


import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@ApiModel(value="G5BoardFileDto", description="게시판 첨부 파일 정보")
@AllArgsConstructor
@NoArgsConstructor
public class G5BoardFileDto {

    private String boTable;
    private Integer wrId;
    private Integer bfNo;

    private String bfSource;
    private String bfFile;
    private Integer bfDownload;
    private String bfContent;
    private Integer bfFilesize;
    private Integer bfWidth;
    private Integer bfHeight;
    private Integer bfType;
    private java.util.Date bfDatetime;

    public G5BoardFileDto(String boTable, int wrId, int bfNo){
        this.boTable = boTable;
        this.wrId = wrId;
        this.bfNo = bfNo;
    }

}

