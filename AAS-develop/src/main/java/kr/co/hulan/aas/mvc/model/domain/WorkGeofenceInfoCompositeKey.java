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
public class WorkGeofenceInfoCompositeKey implements Serializable {

  private String wpId;
  private Integer wpSeq;
  private Integer seq;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;

    if (o == null || getClass() != o.getClass()) return false;

    WorkGeofenceInfoCompositeKey that = (WorkGeofenceInfoCompositeKey) o;

    return new EqualsBuilder()
        .append(wpId, that.wpId)
        .append(wpSeq, that.wpSeq)
        .append(seq, that.seq)
        .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
        .append(wpId)
        .append(wpSeq)
        .append(seq)
        .toHashCode();
  }
}
