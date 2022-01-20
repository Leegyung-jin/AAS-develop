package kr.co.hulan.aas.common.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.MessageSourceAccessor;

public class I18nMessageSource {

    private static Logger logger = LoggerFactory.getLogger(I18nMessageSource.class);

    private MessageSourceAccessor messageSourceAccessor;

    private static I18nMessageSource instance = new I18nMessageSource();

    private I18nMessageSource() {
    }

    private MessageSourceAccessor getMessageSourceAccessor() {
        return messageSourceAccessor;
    }

    private void setMessageSourceAccessor(MessageSourceAccessor messageSourceAccessor) {
        this.messageSourceAccessor = messageSourceAccessor;
    }

    private String getString(String code, String defaultMessage){
        try{
            return messageSourceAccessor.getMessage(code);
        }
        catch(Exception e){
            logger.error("cann't load message ["+code+"], return defaultMessage ["+defaultMessage+"]");
        }
        return defaultMessage;
    }

    public static synchronized I18nMessageSource getInstance(MessageSourceAccessor messageSourceAccessor){
        instance.setMessageSourceAccessor(messageSourceAccessor);
        return instance;
    }

    public static String getMessage(String code){
        return getMessage(code, "");
    }

    public static String getMessage(String code, String defaultMessage){
        return instance.getString(code, defaultMessage);
    }

}
