package kr.co.hulan.aas.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix="client.http")
public class HttpClientProperties {
    private boolean support;
    private int maxPool;
    private int routeMaxPool;
    private int timeout;

    private String pushRequestUrl;
    private String gpsRequestUrl;
    private String commonMonitorRequestUrl;
}
