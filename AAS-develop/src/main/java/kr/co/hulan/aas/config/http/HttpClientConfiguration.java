package kr.co.hulan.aas.config.http;

import kr.co.hulan.aas.config.properties.HttpClientProperties;
import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HttpClientConfiguration {

    @Autowired
    private HttpClientProperties httpClientProperties;

    @Bean(destroyMethod = "close")
    public CloseableHttpClient httpClient() {
        CloseableHttpClient client = HttpClients.custom()
                .setConnectionManager(connectionPoolManager())
                .setKeepAliveStrategy(keepAliveStrategy())
                .setDefaultRequestConfig(requestConfig())
                //.setRetryHandler(new DefaultHttpRequestRetryHandler(3, true))
                .build();
        return client;
    }

    public PoolingHttpClientConnectionManager connectionPoolManager() {
        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager();
        connManager.setMaxTotal(httpClientProperties.getMaxPool());
        connManager.setDefaultMaxPerRoute(httpClientProperties.getRouteMaxPool());
        return connManager;
    }

    public ConnectionKeepAliveStrategy keepAliveStrategy() {
        return new ConnectionKeepAliveStrategy() {
            @Override
            public long getKeepAliveDuration(HttpResponse response, HttpContext context) {
                HeaderElementIterator it = new BasicHeaderElementIterator
                        (response.headerIterator(HTTP.CONN_KEEP_ALIVE));
                while (it.hasNext()) {
                    HeaderElement he = it.nextElement();
                    String param = he.getName();
                    String value = he.getValue();
                    if (value != null && param.equalsIgnoreCase("timeout")) {
                        return Long.parseLong(value) * 1000;
                    }
                }
                return httpClientProperties.getTimeout();
            }
        };
    }

    public RequestConfig requestConfig() {
        int timeout = httpClientProperties.getTimeout();
        return RequestConfig.custom()
                .setConnectTimeout(timeout)
                .setConnectionRequestTimeout(timeout)
                .setSocketTimeout(timeout).build();
    }

}
