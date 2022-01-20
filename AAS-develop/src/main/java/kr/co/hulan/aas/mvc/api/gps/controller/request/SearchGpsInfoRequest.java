package kr.co.hulan.aas.mvc.api.gps.controller.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="SearchGpsInfoRequest", description="GPS 정보 제공 요청")
public class SearchGpsInfoRequest {

    @NotEmpty
    @ApiModelProperty(notes = "현장 아이디", required = true)
    private String wpId;

    @ApiModelProperty(notes = "현장 관리자 아이디", required = true)
    private String mbId;

    @ApiModelProperty(notes = "검색조건 정보")
    private SearchCondition condition;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @ApiModel(value="SearchCondition", description="검색조건 정보")
    static class SearchCondition {

        @ApiModelProperty(notes = "검색 조건. 1:장비코드, 2:협력사 아이디")
        private Integer conditionType;

        @ApiModelProperty(notes = "검색값")
        private String conditionValue;
    }
}
