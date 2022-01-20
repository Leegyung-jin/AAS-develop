package kr.co.hulan.aas.config.broker;

import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

@EnableKafka
@Configuration
public class KafkaProducerConfig {

  public static final String TOPIC_IMOSNOTICE_FACT_CHANGED = "kunsulup.imos-notice.fact.changed.0";
  public static final String TOPIC_NVR_FACT_CHANGED = "kunsulup.nvr.fact.changed.0";

  @Autowired
  private KafkaConfigProperties kafkaConfigProperties;

  public ProducerFactory<String, String> defaultProducerFactory(){
    return new DefaultKafkaProducerFactory<String, String>(
        kafkaConfigProperties.producerDefaultConfig(),
        new StringSerializer(),
        new StringSerializer()
    );
  }

  @Bean
  public KafkaTemplate<String, String> kafkaTemplate(){
    return new KafkaTemplate<>( defaultProducerFactory() );
  }

}
