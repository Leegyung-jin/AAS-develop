package kr.co.hulan.aas.common.code;

import kr.co.hulan.aas.common.utils.I18nMessageSource;

public interface I18nEnum {

    default public String getLocalizedMessage() {
        return I18nMessageSource.getMessage( getPrefixMessageName()+"."+getSubMessageName());
    }

    default public String getPrefixMessageName() {
        return this.getClass().getSimpleName();
    }

    default public String getSubMessageName() {
        return name();
    }

    String name();
}
