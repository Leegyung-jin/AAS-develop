package kr.co.hulan.aas.config.security.oauth.model;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberAuthority implements GrantedAuthority, Serializable {

  private String authCd;
  private String authName;

  public String getAuthority(){
    return authCd;
  }
}
