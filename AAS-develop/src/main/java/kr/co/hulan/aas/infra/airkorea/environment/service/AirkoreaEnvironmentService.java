package kr.co.hulan.aas.infra.airkorea.environment.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import kr.co.hulan.aas.infra.airkorea.environment.dto.AirEnvironmentItem;
import kr.co.hulan.aas.mvc.dao.repository.AirEnvironmentRepository;
import kr.co.hulan.aas.mvc.model.domain.AirEnvironment;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AirkoreaEnvironmentService {

  private static Logger logger = LoggerFactory.getLogger(AirkoreaEnvironmentService.class);

  @Autowired
  private ModelMapper modelMapper;

  @Autowired
  private AirEnvironmentRepository airEnvironmentRepository;

  @Transactional("transactionManager")
  public void saveAirEnvironmemt(List<AirEnvironmentItem> items){
    List<AirEnvironment> saveList = new ArrayList<>();
    for( AirEnvironmentItem item : items ){
      AirEnvironment env = modelMapper.map(item, AirEnvironment.class);
      env.setCreateDate(new Date());

      logger.debug(""+item);
      logger.debug(""+env);

      if( env.getDataTime() != null ){
        saveList.add(env);
      }
      else {
        logger.warn("invalid air environment data|"+env);
      }
    }
    airEnvironmentRepository.saveAll(saveList);
  }

}
