package kr.co.hulan.aas.common.exception.oauth;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

public class CustomOauthExceptionSerializer extends StdSerializer<CustomOauthException> {

  public CustomOauthExceptionSerializer() {
    super(CustomOauthException.class);
  }

  @Override
  public void serialize(CustomOauthException value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
    jsonGenerator.writeStartObject();
    jsonGenerator.writeNumberField("return_code", value.getReturnCode());
    jsonGenerator.writeObjectField("return_message", value.getReturnMessage());


    OAuth2Exception exp = OAuth2Exception.create(value.getOAuth2ErrorCode(), value.getReturnMessage());
    if( value.getAdditionalInformation() != null && value.getAdditionalInformation().size() > 0 ){
      for (Map.Entry<String, String> entry : value.getAdditionalInformation().entrySet()) {
        String key = entry.getKey();
        String add = entry.getValue();
        exp.addAdditionalInformation(key, add);
      }
    }
    jsonGenerator.writeObjectField("context", exp);
    jsonGenerator.writeEndObject();
  }
}
