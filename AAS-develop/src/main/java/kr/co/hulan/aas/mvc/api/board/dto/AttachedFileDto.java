package kr.co.hulan.aas.mvc.api.board.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kr.co.hulan.aas.common.utils.YamlPropertiesAccessor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@Data
@ApiModel(value="AttachedFileDto", description="첨부 파일 정보")
@AllArgsConstructor
@NoArgsConstructor
public class AttachedFileDto {

    @JsonIgnoreProperties
    public static String BOARD_FILE_DOWNLOAD_URL =
            YamlPropertiesAccessor.getMessage("file.repository.download")
                    +YamlPropertiesAccessor.getMessage("file.repository.boardFilePath");

    @ApiModelProperty(notes = "게시판 종류")
    private String boTable;
    @ApiModelProperty(notes = "게시글 아이디")
    private Integer wrId;
    @ApiModelProperty(notes = "첨부 파일 넘버")
    private Integer bfNo;
    @ApiModelProperty(notes = "원본 파일명")
    private String bfSource;
    @ApiModelProperty(notes = "저장 파일명")
    private String bfFile;
    @ApiModelProperty(notes = "파일 사이즈")
    private Integer bfFilesize;
    @ApiModelProperty(notes = "폭")
    private Integer bfWidth;
    @ApiModelProperty(notes = "높이")
    private Integer bfHeight;
    @ApiModelProperty(notes = "파일 종류")
    private Integer bfType;
    @ApiModelProperty(notes = "파일 등록일")
    private java.util.Date bfDatetime;

    @ApiModelProperty(notes = "파일 다운로드 URL")
    public String getBfFileDownloadUrl(){
        if(StringUtils.isNotBlank(bfFile)){
            return BOARD_FILE_DOWNLOAD_URL + boTable +"/"+ bfFile;
        }
        return "";
    }

}
