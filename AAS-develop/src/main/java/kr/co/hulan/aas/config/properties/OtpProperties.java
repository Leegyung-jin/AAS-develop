package kr.co.hulan.aas.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "otp")
public class OtpProperties {
  private boolean support;
  private int defaultOtp;
}
