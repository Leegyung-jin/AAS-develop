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
public class OfficeWorkplaceManagerKey implements Serializable {
  private Long wpGrpNo;
  private String mbId;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    OfficeWorkplaceManagerKey that = (OfficeWorkplaceManagerKey) o;

    return new EqualsBuilder()
        .append(wpGrpNo, that.wpGrpNo)
        .append(mbId, that.mbId)
        .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
        .append(wpGrpNo)
        .append(mbId)
        .toHashCode();
  }
}
