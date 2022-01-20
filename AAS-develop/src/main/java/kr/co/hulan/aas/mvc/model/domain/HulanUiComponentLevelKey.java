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
public class HulanUiComponentLevelKey implements Serializable {
  private String hcmptId;
  private Integer mbLevel;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    HulanUiComponentLevelKey that = (HulanUiComponentLevelKey) o;

    return new EqualsBuilder()
        .append(hcmptId, that.hcmptId)
        .append(mbLevel, that.mbLevel)
        .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
        .append(hcmptId)
        .append(mbLevel)
        .toHashCode();
  }
}
