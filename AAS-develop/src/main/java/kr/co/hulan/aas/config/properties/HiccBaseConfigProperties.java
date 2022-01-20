package kr.co.hulan.aas.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix="hicc.base")
public class HiccBaseConfigProperties {

  private String title;
  private String iconUrl;
  private String bgColor;
  private String bgImgUrl;
}
