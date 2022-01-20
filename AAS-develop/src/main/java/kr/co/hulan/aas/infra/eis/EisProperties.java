package kr.co.hulan.aas.infra.eis;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix="eis.config")
public class EisProperties {

  private static String SYNC_CHANNEL_URL_FORMAT = "/api/nvr/v1/%s/sync";

  private String url;

  public String getChannelSyncUrl(long nvrNo){
    return url+String.format(SYNC_CHANNEL_URL_FORMAT, ""+nvrNo);
  }


}
