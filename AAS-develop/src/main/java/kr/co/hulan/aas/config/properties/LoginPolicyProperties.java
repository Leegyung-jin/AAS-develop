package kr.co.hulan.aas.config.properties;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import kr.co.hulan.aas.mvc.model.domain.G5Member;
import kr.co.hulan.aas.mvc.model.dto.G5MemberDto;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "policy.login")
public class LoginPolicyProperties {

  private Integer lockCount;
  private Integer pwdChangeNotiAfter;
  private Integer pwdPeriod;

  public boolean isLocked(G5MemberDto member){
    if (member == null || member.getAttemptLoginCount() == null ) {
      return true;
    }
    return lockCount == null ? false : member.getAttemptLoginCount() >= lockCount ;
  }

  public boolean isPasswordExpired(G5MemberDto member){
    if ( pwdPeriod == null ){
      return false;
    }
    else if (member == null || member.getPwdChangeDate() == null ) {
      return true;
    }

    LocalDate today = LocalDate.now();
    LocalDate pwdChangeLocalDate = new java.sql.Date(member.getPwdChangeDate().getTime()).toLocalDate();
    long passedDays = ChronoUnit.DAYS.between(pwdChangeLocalDate, today);
    return passedDays > pwdPeriod;
  }
}
