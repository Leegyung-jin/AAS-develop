package kr.co.hulan.aas.mvc.api.common.service;

import kr.co.hulan.aas.infra.sms.SmsProperties;
import kr.co.hulan.aas.mvc.dao.mapper.SmsDao;
import kr.co.hulan.aas.mvc.model.dto.SmsDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SmsService {

  private Logger logger = LoggerFactory.getLogger(SmsService.class);

  @Autowired
  private SmsProperties smsProperties;

  @Autowired
  private SmsDao smsDao;

  public boolean isSupported(){
    return smsProperties.isSupport();
  }

  @Transactional("mybatisTransactionManager")
  public void sendSms(SmsDto sms){
    if( isSupported() ){
      sms.setCallback(smsProperties.getCallback());
      smsDao.sendSms(sms);
    }
    else {
      logger.info("SmsService is not supported");
    }
  }



}
