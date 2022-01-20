package kr.co.hulan.aas.infra.broker;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import kr.co.hulan.aas.config.broker.DefaultKafkaMessage;
import kr.co.hulan.aas.config.broker.KafkaProducerConfig;
import kr.co.hulan.aas.infra.broker.vo.DefaultKafkaEventType;
import kr.co.hulan.aas.infra.broker.vo.ImosNoticeEventDto;
import kr.co.hulan.aas.infra.broker.vo.NetworkVideoRecoderEventDto;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaEventProducer {

  private Logger logger = LoggerFactory.getLogger(KafkaEventProducer.class);

  @Autowired
  ModelMapper modelMapper;
  private Gson gson = new GsonBuilder().create();

  @Autowired
  KafkaTemplate<String, String> kafkaTemplate;

  public void sendEventMessage(DefaultKafkaEventType eventType, ImosNoticeEventDto dto) {
    logger.info(String.format("Produce message : %s", dto.toString()));
    DefaultKafkaMessage<ImosNoticeEventDto> message = new DefaultKafkaMessage<ImosNoticeEventDto>();
    message.setEventType(eventType.getCode());
    message.setEventTime(System.currentTimeMillis());
    message.setContext(dto);
    this.kafkaTemplate.send(KafkaProducerConfig.TOPIC_IMOSNOTICE_FACT_CHANGED, gson.toJson(message));
  }

  public void sendEventMessage(DefaultKafkaEventType eventType, NetworkVideoRecoderEventDto dto) {
    logger.info(String.format("Produce message : %s", dto.toString()));
    DefaultKafkaMessage<NetworkVideoRecoderEventDto> message = new DefaultKafkaMessage<NetworkVideoRecoderEventDto>();
    message.setEventType(eventType.getCode());
    message.setEventTime(System.currentTimeMillis());
    message.setContext(dto);
    this.kafkaTemplate.send(KafkaProducerConfig.TOPIC_NVR_FACT_CHANGED, gson.toJson(message));
  }
}