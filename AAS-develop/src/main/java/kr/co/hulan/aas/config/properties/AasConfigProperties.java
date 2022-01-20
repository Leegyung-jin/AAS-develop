package kr.co.hulan.aas.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix="aas.config")
public class AasConfigProperties {

  private long maxGasSearchPeriod;
}
