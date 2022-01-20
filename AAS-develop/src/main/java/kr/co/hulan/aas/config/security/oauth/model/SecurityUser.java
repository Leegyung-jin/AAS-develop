package kr.co.hulan.aas.config.security.oauth.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import io.swagger.annotations.ApiModelProperty;
import java.util.Date;
import kr.co.hulan.aas.common.code.MemberLevel;
import kr.co.hulan.aas.config.properties.LoginPolicyProperties;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Data
public class SecurityUser implements UserDetails {

  private Long mbNo;
  private String mbId;
  private String mbPassword;
  private int mbLevel;
  private String wpId;
  private Date pwdChangeDate;
  private Integer attemptLoginCount;

  private Integer lockCount;
  private Integer pwdPeriod;
  private String loginCcId;
  private String loginCcName;
  private Long loginOfficeNo;
  private String loginOfficeName;
  private String coopMbId;
  private String coopMbName;

  private Collection<MemberAuthority> authorities = new ArrayList<>();

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  @Override
  public String getUsername() {
    return mbId;
  }

  @Override
  public String getPassword() {
    return mbPassword;
  }

  @Override
  public boolean isAccountNonExpired() {
    return isEnabled();
  }

  @Override
  public boolean isAccountNonLocked() {
    return lockCount == null ? true : attemptLoginCount < lockCount ;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    if ( pwdPeriod == null ){
      return true;
    }
    else if (pwdChangeDate == null) {
      return false;
    }

    LocalDate today = LocalDate.now();
    LocalDate pwdChangeLocalDate = new java.sql.Date(pwdChangeDate.getTime()).toLocalDate();
    long passedDays = ChronoUnit.DAYS.between(pwdChangeLocalDate, today);
    return passedDays <= pwdPeriod;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  public String getMbLevelName(){
    MemberLevel mlev = MemberLevel.get(getMbLevel());
    return mlev != null ? mlev.getName() : "";
  }

  public void addAuthority(MemberAuthority auth) {
    authorities.add(auth);
  }

}
