package kr.co.hulan.aas.config.broker;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.HashMap;
import java.util.Map;
import lombok.Data;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix="kafka")
public class KafkaConfigProperties {

  @JsonIgnoreProperties
  private static final String JAAS_TEMPLATE = "org.apache.kafka.common.security.plain.PlainLoginModule required username=\"%s\" password=\"%s\";";

  private String servers;
  private String account;
  private String password;

  private String adminAccount;
  private String adminPassword;

  public Map<String, Object> consumerDefaultConfig() {
    Map<String, Object> configs = new HashMap<>();
    configs.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, getServers() );
    configs.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest" );
    configs.put("spring.json.trusted.packages", "*" );
    configs.put("security.protocol", "SASL_PLAINTEXT" );
    configs.put("sasl.mechanism", "PLAIN" );
    configs.put("sasl.jaas.config", String.format(JAAS_TEMPLATE, getAccount(), getPassword()) );
    return configs;
  }

  public Map<String, Object> producerDefaultConfig() {
    Map<String, Object> configs = new HashMap<>();
    configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, getServers() );
    configs.put("spring.json.add.type.headers", false );
    configs.put("security.protocol", "SASL_PLAINTEXT" );
    configs.put("sasl.mechanism", "PLAIN" );
    configs.put("sasl.jaas.config", String.format(JAAS_TEMPLATE, getAccount(), getPassword()) );
    return configs;
  }

  public Map<String, Object> adminConfig() {
    Map<String, Object> configs = new HashMap<>();
    configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, getServers() );
    configs.put("security.protocol", "SASL_PLAINTEXT" );
    configs.put("sasl.mechanism", "PLAIN" );
    configs.put("sasl.jaas.config", String.format(JAAS_TEMPLATE, getAdminAccount(), getAdminPassword()) );
    return configs;
  }

}
