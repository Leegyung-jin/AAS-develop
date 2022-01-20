package kr.co.hulan.aas.mvc.api.common.service;

import java.util.Date;
import java.util.List;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.code.OtpUsage;
import kr.co.hulan.aas.common.exception.CommonException;
import kr.co.hulan.aas.config.properties.OtpProperties;
import kr.co.hulan.aas.mvc.dao.repository.MemberOtpPhoneRepository;
import kr.co.hulan.aas.mvc.dao.repository.MemberOtpRepository;
import kr.co.hulan.aas.mvc.model.domain.MemberOtp;
import kr.co.hulan.aas.mvc.model.domain.MemberOtpPhone;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OtpService {

  @Autowired
  private OtpProperties otpProperties;

  @Autowired
  private MemberOtpRepository memberOtpRepository;

  @Autowired
  private MemberOtpPhoneRepository memberOtpPhoneRepository;

  public boolean isSupportOtp(){
    return otpProperties.isSupport();
  }

  public boolean isSupportPhone(String mbId, String phoneNo){
    return memberOtpPhoneRepository.existsByMbIdAndPhoneNo(mbId, phoneNo);
  }

  @Transactional("transactionManager")
  public MemberOtp saveOtp(String mbId ){
    Date checkDate = DateUtils.addSeconds(new Date(), -10);
    long count = memberOtpRepository.countByMbIdAndCreateDateAfter(  mbId, checkDate );
    if( count > 0 ){
      throw new CommonException(BaseCode.ERR_ETC_EXCEPTION.code(), "10초 이내 OTP 중복 발송은 허용되지 않습니다.");
    }

    MemberOtp otp = new MemberOtp();
    otp.setMbId(mbId);
    otp.setOtpNum( createOtpNumber());
    otp.setOtpUsage(OtpUsage.LOGIN.getCode());
    otp.setCreateDate(new Date());

    memberOtpRepository.save(otp);
    return otp;

  }

  @Transactional("transactionManager")
  public void validateOtp(String mbId, int otpNum){
    Date checkDate = DateUtils.addMinutes(new Date(), -5);
    MemberOtp otp = memberOtpRepository.findTopByMbIdAndCreateDateAfterAndOtpNum(
        mbId,
        checkDate,
        otpNum
    );
    if( otp == null ){
      throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "OTP 번호가 일치하지 않습니다.");
    }
    otp.setValidateDate(new Date());
    memberOtpRepository.save(otp);
  }

  private int createOtpNumber(){
    if( isSupportOtp() ){
      return (int) (Math.random()* ( 9000 - 1 ) )+1000;
    }
    else {
      return otpProperties.getDefaultOtp();
    }
  }
}
