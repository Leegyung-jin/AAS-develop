package kr.co.hulan.aas.mvc.api.board.controller.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import kr.co.hulan.aas.common.model.req.DefaultPageRequest;
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
@ApiModel(value="ListNoticeBoardRequest", description="근로자 공지사항 리스트 요청")
public class ListNoticeBoardRequest extends DefaultPageRequest {

    @ApiModelProperty(notes = "검색 조건명. COMPLEX : 전체(제목 + 내용 + 회원 아이디 + 글쓴이), wrSubject : 제목, wrContent : 내용, wrSubjectContent : 제목+내용, mbId : 회원 아이디, wrName : 글쓴이")
    private String searchName;

    @ApiModelProperty(notes = "검색 조건값")
    private String searchValue;

    @ApiModelProperty(notes = "현장 아이디")
    private String wpId;

    @ApiModelProperty(notes = "검색 시작일. ( UNIX_TIMESTAMP * 1000 )")
    private Date startDate;

    @ApiModelProperty(notes = "검색 종료일. ( UNIX_TIMESTAMP * 1000 )")
    private Date endDate;

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
        if(startDate != null){
            condition.put("startDate", startDate);
        }
        if(endDate != null){
            condition.put("endDate", endDate);
        }
        return condition;
    }
}
