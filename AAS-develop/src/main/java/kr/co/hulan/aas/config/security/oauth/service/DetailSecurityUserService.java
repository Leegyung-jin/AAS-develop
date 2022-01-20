package kr.co.hulan.aas.config.security.oauth.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import kr.co.hulan.aas.config.properties.LoginPolicyProperties;
import kr.co.hulan.aas.mvc.dao.mapper.SecurityUserDao;
import kr.co.hulan.aas.config.security.oauth.model.SecurityMemberResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DetailSecurityUserService {

  @Autowired
  private LoginPolicyProperties loginPolicyProperties;

  @Autowired
  private SecurityUserDao securityUserDao;

  public SecurityMemberResponse getDetailSecurityUser(String id) {
    SecurityMemberResponse res = securityUserDao.getDetailSecurityUser(id);
    if( res != null && res.getPwdChangeDate() != null ){
      addCheckNeedChangePassword(res);
    }
    return res;
  }

  private void addCheckNeedChangePassword(SecurityMemberResponse res) {
    if ( loginPolicyProperties != null && loginPolicyProperties.getPwdChangeNotiAfter() != null  ){
      LocalDate today = LocalDate.now();
      LocalDate pwdChangeLocalDate = new java.sql.Date(res.getPwdChangeDate().getTime()).toLocalDate();
      long passedDays = ChronoUnit.DAYS.between(pwdChangeLocalDate, today);
      res.setNeedChgPwdNoti(passedDays >= loginPolicyProperties.getPwdChangeNotiAfter());
    }
  }

}
