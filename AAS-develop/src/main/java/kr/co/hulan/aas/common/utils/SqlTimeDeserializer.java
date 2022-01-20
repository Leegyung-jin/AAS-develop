package kr.co.hulan.aas.common.utils;

import java.io.IOException;
import java.sql.Time;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.apache.commons.lang3.StringUtils;

public class SqlTimeDeserializer extends JsonDeserializer<Time> {

    @Override
    public Time deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        String timeStr = jp.getValueAsString();
        if(StringUtils.isNotEmpty(timeStr)){
            return Time.valueOf(jp.getValueAsString() + ":00");
        }
        else {
            return null;
        }
    }
}
