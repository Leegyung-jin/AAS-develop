package kr.co.hulan.aas.mvc.api.common.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.code.EnableCode;
import kr.co.hulan.aas.common.code.MemberLevel;
import kr.co.hulan.aas.common.exception.CommonException;
import kr.co.hulan.aas.common.model.res.DefaultHttpRes;
import kr.co.hulan.aas.common.utils.I18nMessageSource;
import kr.co.hulan.aas.common.utils.MaskUtils;
import kr.co.hulan.aas.common.utils.MysqlPasswordEncoder;
import kr.co.hulan.aas.common.utils.PhoneNoUtils;
import kr.co.hulan.aas.config.security.oauth.model.SecurityUser;
import kr.co.hulan.aas.config.security.oauth.service.SecurityUserService;
import kr.co.hulan.aas.infra.oauth.OAuthProxyService;
import kr.co.hulan.aas.mvc.dao.mapper.G5MemberDao;
import kr.co.hulan.aas.mvc.dao.repository.MemberOtpPhoneRepository;
import kr.co.hulan.aas.mvc.model.domain.G5Member;
import kr.co.hulan.aas.mvc.model.domain.MemberOtp;
import kr.co.hulan.aas.mvc.model.dto.G5MemberDto;
import kr.co.hulan.aas.mvc.model.dto.MemberOtpPhoneDto;
import kr.co.hulan.aas.mvc.model.dto.SmsDto;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;
import org.springframework.web.HttpRequestMethodNotSupportedException;

@Component
public class LoginManager {

  @Autowired
  private SecurityUserService securityUserService;

  @Autowired
  private OtpService otpService;

  @Autowired
  private SmsService smsService;

  @Autowired
  private OAuthProxyService oauthProxyService;

  @Autowired
  private MysqlPasswordEncoder encoder;

  @Autowired
  private G5MemberDao g5MemberDao;

  public ResponseEntity<String> imosLogin( HttpServletRequest request, String username, String password, String uuid, String otp )
      throws HttpRequestMethodNotSupportedException, IOException {

    SecurityUser user = securityUserService.findUser(username);
    if( user == null ){
      throw new CommonException(BaseCode.ERR_NOT_EXISTS_USER.code(), "미등록 혹은 권한이 없는 계정입니다.");
    }
    else if( !encoder.matches(password, user.getMbPassword()) ){
      throw new CommonException(BaseCode.ERR_INCORRECT_PASSWORD.code(), BaseCode.ERR_INCORRECT_PASSWORD.message());
    }

    G5MemberDto member = g5MemberDao.findByMbId(user.getUsername());
    if( member == null ){
      throw new CommonException(BaseCode.ERR_NOT_EXISTS_USER.code(), BaseCode.ERR_NOT_EXISTS_USER.message());
    }
    else if (MemberLevel.get( user.getMbLevel()) != MemberLevel.SUPER_ADMIN
        && MemberLevel.get( user.getMbLevel()) != MemberLevel.FIELD_MANAGER
        && MemberLevel.get( user.getMbLevel()) != MemberLevel.COOPERATIVE_COMPANY
        && MemberLevel.get( user.getMbLevel()) != MemberLevel.CONSTRUNCTION_COMPANY_MANAGER
        && MemberLevel.get( user.getMbLevel()) != MemberLevel.OFFICE_MANAGER
    ){
      throw new CommonException(BaseCode.ERR_GRADE_EXCEPTION.code(), "로그인 권한이 없는 계정입니다.");
    }

    if(otpService.isSupportOtp()){
      if( StringUtils.isBlank(uuid)){
        List<MemberOtpPhoneDto> list = new ArrayList<>();
        if( StringUtils.isNotBlank(member.getTelephone())
            && PhoneNoUtils.isValidPhoneNo(member.getTelephone())
        ){
          list.add(MemberOtpPhoneDto.builder()
              .phoneNo(MaskUtils.maskPhoneNo(member.getTelephone()))
              .uuid("ORIGIN")
              .build());
        }
        List<MemberOtpPhoneDto> mopList = member.getOtpPhoneList();
        if( mopList != null && mopList.size() != 0 ) {
          mopList.stream().forEach(otpPhone -> {
            if (PhoneNoUtils.isValidPhoneNo(otpPhone.getPhoneNo())) {
              list.add(MemberOtpPhoneDto.builder()
                  .phoneNo(MaskUtils.maskPhoneNo(otpPhone.getPhoneNo()))
                  .uuid(otpPhone.getUuid())
                  .build());
            }
          });
        }

        if( list.size() == 0 ){
          throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "올바르게 등록된 전화번호가 없습니다. 올바른 전화번호를 등록 후 로그인 시도하여 주세요");
        }

        DefaultHttpRes res = new DefaultHttpRes(BaseCode.ERR_OTP_SELECT_PHONE, list );
        return new ResponseEntity(res, HttpStatus.ACCEPTED);
      }

      String smsDstAddr = null;
      if( StringUtils.equals(uuid, "ORIGIN")) {
        smsDstAddr = member.getTelephone();
      }
      else {
        List<MemberOtpPhoneDto> mopList = member.getOtpPhoneList();
        if( mopList != null && mopList.size() != 0 ){
          for(MemberOtpPhoneDto mop : mopList ){
            if( StringUtils.equals(mop.getUuid(), uuid)){
              smsDstAddr = mop.getPhoneNo();
              break;
            }
          }
        }
      }

      if( StringUtils.isBlank(smsDstAddr) ){
        throw new CommonException(BaseCode.ERR_ARG_IS_WRONG.code(), "등록되지 않은 전화번호입니다.");
      }

      if(StringUtils.isNotBlank(otp)){
        otpService.validateOtp(user.getMbId(), NumberUtils.toInt(otp));
      }
      else {
        MemberOtp memberOtp = otpService.saveOtp(user.getMbId());
        if(StringUtils.isNotBlank(smsDstAddr)){
          SmsDto otpSms = SmsDto.builder()
              .dstAddr(smsDstAddr)
              .subject(I18nMessageSource.getMessage("OTP.LOGIN.TITLE"))
              .content(String.format(I18nMessageSource.getMessage("OTP.LOGIN.SUBJECT"), memberOtp.getOtpNum()))
              .build();

          smsService.sendSms(otpSms);
        }
        DefaultHttpRes res = new DefaultHttpRes(BaseCode.ERR_OTP_FIRED.code(), BaseCode.ERR_OTP_FIRED.message());
        return new ResponseEntity(res, HttpStatus.ACCEPTED);
      }
    }
    ResponseEntity<String> result = oauthProxyService.localLogin(request.getHeader("Authorization"), username, password);
    return new ResponseEntity(result.getBody(), result.getStatusCode());
  }

  public ResponseEntity<String> hiccLogin( HttpServletRequest request, String username, String password )
      throws HttpRequestMethodNotSupportedException, IOException {

    SecurityUser user = securityUserService.loadUserByUsername(username);
    if( user == null ){
      throw new CommonException(BaseCode.ERR_NOT_EXISTS_USER.code(), BaseCode.ERR_NOT_EXISTS_USER.message());
    }
    else if( !encoder.matches(password, user.getMbPassword()) ){
      throw new CommonException(BaseCode.ERR_INCORRECT_PASSWORD.code(), BaseCode.ERR_INCORRECT_PASSWORD.message());
    }

    G5MemberDto member = g5MemberDao.findByMbId(user.getUsername());
    if( member == null ){
      throw new CommonException(BaseCode.ERR_NOT_EXISTS_USER.code(), BaseCode.ERR_NOT_EXISTS_USER.message());
    }
    else if (MemberLevel.get( user.getMbLevel()) != MemberLevel.SUPER_ADMIN
        && MemberLevel.get( user.getMbLevel()) != MemberLevel.CONSTRUNCTION_COMPANY_MANAGER
        && MemberLevel.get( user.getMbLevel()) != MemberLevel.WP_GROUP_MANAGER
        && MemberLevel.get( user.getMbLevel()) != MemberLevel.OFFICE_MANAGER
    ){
      throw new CommonException(BaseCode.ERR_GRADE_EXCEPTION.code(), BaseCode.ERR_GRADE_EXCEPTION.message());
    }

    ResponseEntity<String> result = oauthProxyService.localLogin(request.getHeader("Authorization"), username, password);
    return new ResponseEntity(result.getBody(), result.getStatusCode());
  }

}
