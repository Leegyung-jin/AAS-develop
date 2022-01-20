package kr.co.hulan.aas.common.model.req;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.data.domain.PageRequest;

@Data
public abstract class DefaultPageRequest extends ConditionRequest {

  @NotNull
  @Min(1)
  @Valid
  @ApiModelProperty(notes = "페이지", required = true)
  private Integer page = 0;

  @NotNull
  @Min(5)
  @Max(50)
  @Valid
  @ApiModelProperty(notes = "페이지 사이즈", required = true)
  private Integer pageSize = 0;

  @JsonIgnore
  public PageRequest of() {
    return PageRequest.of(page - 1, pageSize);
  }

  @JsonIgnore
  @Override
  public Map<String, Object> getConditionMap() {
    Map<String, Object> map = new HashMap<>();
    PageRequest pageRequest = this.of();
    if (pageRequest != null) {
      map.put("startRow", pageRequest.getPageNumber() * pageRequest.getPageSize());
      map.put("pageSize", pageRequest.getPageSize());
    }
    return map;
  }
}

