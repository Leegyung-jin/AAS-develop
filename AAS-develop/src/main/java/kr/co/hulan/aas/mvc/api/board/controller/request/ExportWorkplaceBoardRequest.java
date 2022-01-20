package kr.co.hulan.aas.mvc.api.board.controller.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kr.co.hulan.aas.common.model.req.ConditionRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="ExportWorkplaceBoardRequest", description="현장 게시판 데이터 Export 요청")
public class ExportWorkplaceBoardRequest extends ConditionRequest {

    @ApiModelProperty(notes = "검색 조건명. wrSubject : 제목, wrContent : 내용, wrSubjectContent : 제목+내용, mbId : 회원 아이디, wrName : 글쓴이")
    private String searchName;

    @ApiModelProperty(notes = "검색 조건값")
    private String searchValue;

    @ApiModelProperty(notes = "현장 아이디")
    private String wpId;

    @ApiModelProperty(notes = "협력사 아이디")
    private String coopMbId;

    @ApiModelProperty(notes = "현장 게시판 글 분류, 값이 없을 경우 전체 조회, 현장관리자의 경우 '안전' 내지는 '공사', 협력사의 경우 '승인요청' 가능 ")
    private String caName;

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
        if(StringUtils.isNotBlank(coopMbId)){
            condition.put("coopMbId", coopMbId);
        }
        if(StringUtils.isNotBlank(caName)){
            condition.put("caName", caName);
        }
        return condition;
    }
}