package kr.co.hulan.aas.infra.airkorea;

import kr.co.hulan.aas.config.properties.DataGoKrSvcProperties;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "airkorea")
public class AirkoreaProperties {
  private DataGoKrSvcProperties airEnvironmentSvc;
  private DataGoKrSvcProperties stationSvc;
}
