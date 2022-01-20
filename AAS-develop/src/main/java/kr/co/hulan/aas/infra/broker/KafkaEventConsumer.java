package kr.co.hulan.aas.infra.broker;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import kr.co.hulan.aas.config.broker.DefaultKafkaMessage;
import kr.co.hulan.aas.config.broker.KafkaConsumerConfig;
import kr.co.hulan.aas.infra.broker.vo.NetworkVideoRecoderChEventDto;
import kr.co.hulan.aas.mvc.api.device.service.WorkPlaceCctvService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaEventConsumer {

  private Logger logger = LoggerFactory.getLogger(KafkaEventConsumer.class);

  @Autowired
  ModelMapper modelMapper;

  Gson gson = new GsonBuilder().create();

  @Autowired
  private WorkPlaceCctvService workPlaceCctvService;

  @KafkaListener(topics={ KafkaConsumerConfig.TOPIC_NVR_CH_FACT_CHANGED}, groupId = KafkaConsumerConfig.KAFKA_GROUP_ID)
  public void eventReceive(String payload) throws IOException {
    logger.info(this.getClass().getSimpleName()+"|"+payload);
    try{
      DefaultKafkaMessage<NetworkVideoRecoderChEventDto> in = gson.fromJson(payload, TypeToken
          .getParameterized(DefaultKafkaMessage.class, NetworkVideoRecoderChEventDto.class).getType());
      if( !in.isValid()){
        logger.error(this.getClass().getSimpleName()+"|INVALID DATA|"+in.toString());
        return;
      }
      NetworkVideoRecoderChEventDto eventVo = in.getContext();
      workPlaceCctvService.receiveNvrChannelChangedEvent(eventVo, in.getEventTime());
    }
    catch(Exception e){
      logger.error(this.getClass().getSimpleName()+"|INVALID PAYLOAD|"+payload, e);
    }
  }
}
