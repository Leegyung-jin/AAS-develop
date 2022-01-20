package kr.co.hulan.aas.mvc.model.domain.imosNotice;

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
public class ImosNoticeWorkplaceKey implements Serializable {

  private Long imntNo;
  private String wpId;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    ImosNoticeWorkplaceKey that = (ImosNoticeWorkplaceKey) o;

    return new EqualsBuilder()
        .append(imntNo, that.imntNo)
        .append(wpId, that.wpId)
        .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
        .append(imntNo)
        .append(wpId)
        .toHashCode();
  }
}
