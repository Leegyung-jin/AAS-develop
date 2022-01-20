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
public class WorkPlaceSafetyScoreKey implements Serializable  {

  private String scoreDate;
  private String wpId;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    WorkPlaceSafetyScoreKey that = (WorkPlaceSafetyScoreKey) o;

    return new EqualsBuilder()
        .append(scoreDate, that.scoreDate)
        .append(wpId, that.wpId)
        .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
        .append(scoreDate)
        .append(wpId)
        .toHashCode();
  }
}
