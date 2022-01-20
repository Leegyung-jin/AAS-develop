package kr.co.hulan.aas.mvc.model.domain;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HiccRegionKey implements Serializable {
  private Long hiccNo;
  private String sidoCd;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    HiccRegionKey that = (HiccRegionKey) o;

    return new EqualsBuilder()
        .append(hiccNo, that.hiccNo)
        .append(sidoCd, that.sidoCd)
        .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
        .append(hiccNo)
        .append(sidoCd)
        .toHashCode();
  }
}
