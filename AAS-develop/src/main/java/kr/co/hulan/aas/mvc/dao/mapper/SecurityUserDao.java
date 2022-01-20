package kr.co.hulan.aas.mvc.dao.mapper;

import kr.co.hulan.aas.config.security.oauth.model.SecurityUser;
import kr.co.hulan.aas.config.security.oauth.model.SecurityMemberResponse;
import org.springframework.stereotype.Repository;

@Repository
public interface SecurityUserDao {

  SecurityUser loadUserByUsername(String id);

  SecurityMemberResponse getDetailSecurityUser(String id);
}
