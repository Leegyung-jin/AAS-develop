package kr.co.hulan.aas.config.security.oauth.service;

import com.google.gson.Gson;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import kr.co.hulan.aas.config.security.oauth.model.MemberAuthority;
import kr.co.hulan.aas.mvc.dao.mapper.G5MemberDao;
import kr.co.hulan.aas.mvc.model.dto.G5MemberDto;
import kr.co.hulan.aas.mvc.model.dto.MemberLevelDto;
import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Transactional;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.exception.oauth.CustomOauthException;
import kr.co.hulan.aas.common.utils.JsonUtil;
import kr.co.hulan.aas.config.properties.LoginPolicyProperties;
import kr.co.hulan.aas.mvc.dao.mapper.SecurityUserDao;
import kr.co.hulan.aas.config.security.oauth.model.SecurityUser;
import kr.co.hulan.aas.mvc.dao.repository.G5MemberRepository;
import kr.co.hulan.aas.mvc.model.domain.G5Member;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SecurityUserService implements UserDetailsService {

  @Autowired
  private SecurityUserDao securityUserDao;

  @Autowired
  private G5MemberDao g5MemberDao;

  @Autowired
  private LoginPolicyProperties loginPolicyProperties;

  @Autowired
  private ModelMapper modelMapper;

  @Override
  public SecurityUser loadUserByUsername(String id) {
    SecurityUser user = securityUserDao.loadUserByUsername(id);
    if( user == null ){
      throw new UsernameNotFoundException("cann't find user ["+id+"]");
    }
    modelMapper.map(loginPolicyProperties, user);
    String userRoleCode = "ROLE_" + user.getMbLevel();
    user.addAuthority(new MemberAuthority(userRoleCode, StringUtils.defaultIfBlank(user.getMbLevelName(), userRoleCode)));
    return user;
  }

  public SecurityUser findUser(String id) {
    SecurityUser user = securityUserDao.loadUserByUsername(id);
    if( user != null ){
      modelMapper.map(loginPolicyProperties, user);
      String userRoleCode = "ROLE_" + user.getMbLevel();
      user.addAuthority(new MemberAuthority(userRoleCode, StringUtils.defaultIfBlank(user.getMbLevelName(), userRoleCode)));
    }
    return user;
  }

  @Transactional("mybatisTransactionManager")
  public void updateLoginSuccess(SecurityUser user, String ip){
    if( user != null ){
      G5MemberDto member =  g5MemberDao.findByMbId(user.getMbId());
      if( member != null ){
        member.setLatestLogin(new Date());
        member.setMbLoginIp(ip);
        member.setAttemptLoginCount(0);
        g5MemberDao.updateLoginSuccess(member);
      }
    }
  }

  @Transactional("mybatisTransactionManager")
  public void updateLoginFail(SecurityUser user){
    if( user != null ){
      G5MemberDto member =  g5MemberDao.findByMbId(user.getMbId());
      if( member != null  ){
        member.setAttemptLoginCount( member.getAttemptLoginCount() + 1 );
        g5MemberDao.updateLoginFail(member);
        user.setAttemptLoginCount(member.getAttemptLoginCount());
      }
    }
  }

}
