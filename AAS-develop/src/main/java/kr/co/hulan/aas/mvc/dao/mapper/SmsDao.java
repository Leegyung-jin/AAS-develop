package kr.co.hulan.aas.mvc.dao.mapper;

import kr.co.hulan.aas.mvc.model.dto.SmsDto;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface SmsDao {

  void sendSms(SmsDto sms);

}
