package kr.co.hulan.aas.mvc.api.board.controller.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kr.co.hulan.aas.common.model.req.ConditionRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="ExportNoticeBoardRequest", description="근로자 공지사항 데이터 Export 요청")
public class ExportNoticeBoardRequest extends ConditionRequest {

    @ApiModelProperty(notes = "검색 조건명. wrSubject : 제목, wrContent : 내용, wrSubjectContent : 제목+내용, mbId : 회원 아이디, workerMbName : 근로자명, mbId : 등록자 아이디, wrName : 글쓴이")
    private String searchName;

    @ApiModelProperty(notes = "검색 조건값")
    private String searchValue;

    @ApiModelProperty(notes = "현장 아이디")
    private String wpId;

    @JsonIgnore
    @Override
    public Map<String,Object> getConditionMap(){
        Map<String,Object> condition = super.getConditionMap();
        if(StringUtils.isNotBlank(searchName) && StringUtils.isNotBlank(searchValue)){
            condition.put(searchName, searchValue);
        }
        if(StringUtils.isNotBlank(wpId)){
            condition.put("wpId", wpId);
        }
        return condition;
    }
}
