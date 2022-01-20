package kr.co.hulan.aas.mvc.api.bls.controller.request;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Map;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import kr.co.hulan.aas.common.model.req.ConditionRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="BleMonitoringDataRequest", description="BLE 스마트 안전모니터 데이터 요청")
public class BleMonitoringDataRequest extends ConditionRequest  {

  @JsonIgnoreProperties
  public static enum BLE_SMART_SEARCH_TYPE {
    VIEW_MAP(1, "조감도"),
    CROSS_SECTION(2, "단면도"),
    VIEW_FLOOR(3, "평면도")
    ;
    private int code;
    private String name;
    BLE_SMART_SEARCH_TYPE(int code, String name){
      this.code = code;
      this.name = name;
    }
    public int getCode() {
      return code;
    }
    public String getName(){
      return name;
    }
    public static BLE_SMART_SEARCH_TYPE get(int code){
      for(BLE_SMART_SEARCH_TYPE item : values()){
        if(code == item.getCode()){
          return item;
        }
      }
      return null;
    }
  }

  @ApiModelProperty(notes = "검색 종류. 1: 조감도(Default), 2:단면도, 3:평면도")
  private Integer searchType;

  @NotEmpty(message = "현장 정보가 존재하지 않습니다.")
  @ApiModelProperty(notes = "현장 아이디")
  private String wpId;

  @ApiModelProperty(notes = "빌딩 넘버")
  private Long buildingNo;

  @ApiModelProperty(notes = "빌딩 층")
  private Integer floor;

  @ApiModelProperty(notes = "Marker 표시 근로자 아이디")
  private String markerMbId;

  @JsonIgnore
  @AssertTrue(message = "검색 종류에 맞는 정보가 존재하지 않습니다.")
  public boolean validRequest(){
    if( searchType != null ){
      if( searchType > BLE_SMART_SEARCH_TYPE.CROSS_SECTION.getCode() && floor == null  ){
        return false ;
      }
      if( searchType > 1 && buildingNo == null  ){
        return false;
      }
    }
    return StringUtils.isNotEmpty(wpId);
  }

  @Override
  public Map<String,Object> getConditionMap(){
    Map<String,Object> condition = super.getConditionMap();
    if( searchType != null ){
      condition.put("searchType", searchType);
    }
    if( StringUtils.isNotEmpty(wpId)){
      condition.put("wpId", wpId);
    }
    if( buildingNo != null ){
      condition.put("buildingNo", buildingNo);
    }
    if( floor != null ){
      condition.put("floor", floor);
    }
    if( StringUtils.isNotEmpty(markerMbId)){
      condition.put("markerMbId", markerMbId);
    }
    return condition;
  }


}
