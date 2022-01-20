package kr.co.hulan.aas.mvc.model.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SensorLogRecentCompositeKey implements Serializable {
  private String mbId;
  private java.util.Date slrDatetime;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;

    if (o == null || getClass() != o.getClass()) return false;

    SensorLogRecentCompositeKey that = (SensorLogRecentCompositeKey) o;

    return new EqualsBuilder()
        .append(mbId, that.mbId)
        .append(slrDatetime, that.slrDatetime)
        .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
        .append(mbId)
        .append(slrDatetime)
        .toHashCode();
  }
}
