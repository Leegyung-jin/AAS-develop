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
public class MemberOtpPhoneKey implements Serializable {
  private String mbId;
  private String phoneNo;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }

    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    MemberOtpPhoneKey that = (MemberOtpPhoneKey) o;

    return new EqualsBuilder()
        .append(mbId, that.mbId)
        .append(phoneNo, that.phoneNo)
        .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
        .append(mbId)
        .append(phoneNo)
        .toHashCode();
  }
}
