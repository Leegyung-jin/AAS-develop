package kr.co.hulan.aas.infra.vworld;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix="infra.vworld")
public class VworldProperties {

  private String serviceUrl;
  private String serviceKey;

}
