package kr.co.hulan.aas.mvc.model.domain;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class WorkPlaceUiComponentCompositeKey implements Serializable  {

  private String wpId;
  private Integer location;
  private String creator;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    WorkPlaceUiComponentCompositeKey that = (WorkPlaceUiComponentCompositeKey) o;

    return new EqualsBuilder()
        .append(wpId, that.wpId)
        .append(location, that.location)
        .append(creator, that.creator)
        .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
        .append(wpId)
        .append(location)
        .append(creator)
        .toHashCode();
  }
}
