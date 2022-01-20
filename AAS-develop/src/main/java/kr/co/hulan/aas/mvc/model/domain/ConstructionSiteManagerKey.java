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
public class ConstructionSiteManagerKey implements Serializable {

  private String wpId;
  private String mbId;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    ConstructionSiteManagerKey that = (ConstructionSiteManagerKey) o;

    return new EqualsBuilder()
        .append(wpId, that.wpId)
        .append(mbId, that.mbId)
        .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
        .append(wpId)
        .append(mbId)
        .toHashCode();
  }
}
