package kr.co.hulan.aas.mvc.api.monitor4_2.controller.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.DateDeserializers.SqlDateDeserializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import java.util.Map;
import kr.co.hulan.aas.common.model.req.DefaultPageRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value="ImosTiltDeviceStatePagingListRequest", description="기울기 센서 상태 정보 검색(페이징)")
public class ImosTiltDeviceStatePagingListRequest extends DefaultPageRequest {

  @ApiModelProperty(notes = "검색 조건명. COMPLEX : 전체, macAddress: Mac 주소, deviceId: 디바이스 아이디")
  private String searchName = "COMPLEX";

  @ApiModelProperty(notes = "검색 조건값")
  private String searchValue;

  @JsonIgnore
  @Override
  public Map<String,Object> getConditionMap(){
    Map<String,Object> condition = super.getConditionMap();
    if(StringUtils.isNotBlank(searchName) && StringUtils.isNotBlank(searchValue)){
      condition.put(searchName, searchValue);
    }
    return condition;
  }
}
