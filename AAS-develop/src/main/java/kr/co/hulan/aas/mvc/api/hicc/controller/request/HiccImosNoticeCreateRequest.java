package kr.co.hulan.aas.mvc.api.hicc.controller.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import kr.co.hulan.aas.mvc.api.file.dto.UploadedFile;
import kr.co.hulan.aas.mvc.model.dto.ImosNoticeFileDto;
import kr.co.hulan.aas.mvc.model.dto.ImosNoticeWorkplaceDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="HiccImosNoticeCreateRequest", description="[통합관제] 현장 공지사항 생성 요청")
public class HiccImosNoticeCreateRequest {

  @NotBlank(message = "제목은 필수입니다.")
  @ApiModelProperty(notes = "제목", required = true)
  private String subject;
  @NotBlank(message = "내용은 필수입니다.")
  @ApiModelProperty(notes = "내용", required = true)
  private String content;

  @NotNull(message = "중요도는 필수입니다.")
  @ApiModelProperty(notes = "중요도. 1: 1단계, 2: 2단계, 3: 3단계", required = true)
  private Integer importance;
  @NotNull(message = "전체현장 공지여부는 필수입니다.")
  @ApiModelProperty(notes = "전체현장 공지여부. 0: 선택공지, 1: 전체공지", required = true)
  private Integer notiAllFlag;

  @ApiModelProperty(notes = "전송 현장 아이디 리스트. 선택공지일 경우 유효")
  private List<String> workplaceList;

  @ApiModelProperty(notes = "업로드 첨부파일")
  private List<UploadedFile> uploadFileList;
}
