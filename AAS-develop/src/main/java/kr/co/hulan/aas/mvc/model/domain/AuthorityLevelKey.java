package kr.co.hulan.aas.mvc.model.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.io.Serializable;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorityLevelKey implements Serializable {

    private Integer mbLevel;
    private String authorityId;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        AuthorityLevelKey that = (AuthorityLevelKey) o;

        return new EqualsBuilder()
                .append(mbLevel, that.mbLevel)
                .append(authorityId, that.authorityId)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(mbLevel)
                .append(authorityId)
                .toHashCode();
    }
}
