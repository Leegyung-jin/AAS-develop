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
public class BookmarkGpsKey implements Serializable  {

  private String mbId;
  private String wpId;
  private Integer wpSeq;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    BookmarkGpsKey that = (BookmarkGpsKey) o;

    return new EqualsBuilder()
        .append(mbId, that.mbId)
        .append(wpId, that.wpId)
        .append(wpSeq, that.wpSeq)
        .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
        .append(mbId)
        .append(wpId)
        .append(wpSeq)
        .toHashCode();
  }
}
