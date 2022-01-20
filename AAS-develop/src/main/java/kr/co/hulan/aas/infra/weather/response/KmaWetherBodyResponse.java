package kr.co.hulan.aas.infra.weather.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 *
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class KmaWetherBodyResponse<T> {

  private String dataType;
  private KmaWetherBodyItemsResponse<T> items;
  private Integer numOfRows;
  private Integer pageNo;
  private Integer totalCount;

  public String getDataType() {
    return dataType;
  }

  public void setDataType(String dataType) {
    this.dataType = dataType;
  }

  public KmaWetherBodyItemsResponse<T> getItems() {
    return items;
  }

  public void setItems(KmaWetherBodyItemsResponse<T> items) {
    this.items = items;
  }

  public Integer getNumOfRows() {
    return numOfRows;
  }

  public void setNumOfRows(Integer numOfRows) {
    this.numOfRows = numOfRows;
  }

  public Integer getPageNo() {
    return pageNo;
  }

  public void setPageNo(Integer pageNo) {
    this.pageNo = pageNo;
  }

  public Integer getTotalCount() {
    return totalCount;
  }

  public void setTotalCount(Integer totalCount) {
    this.totalCount = totalCount;
  }

  public boolean isEndResult(){
    if( totalCount != null && pageNo != null && numOfRows != null ){
      if( pageNo > 0 && numOfRows > 0 ){
        return totalCount <=  pageNo * numOfRows;
      }
    }
    return true;
  }
  public boolean containsItemData(){
    return items != null
        && items.getItem() != null
        && items.getItem().size() > 0
        ;
  }
}
