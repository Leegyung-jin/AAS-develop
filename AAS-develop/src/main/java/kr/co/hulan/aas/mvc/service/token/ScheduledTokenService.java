package kr.co.hulan.aas.mvc.service.token;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScheduledTokenService {

  @Autowired
  private TokenService tokenService;

  public void deleteExpiredToken() {
    List<String> tokenIdList = tokenService.getListExpiredToken();
    if (!tokenIdList.isEmpty()) {
      tokenService.deleteExpiredAccessToken(tokenIdList);
      tokenService.deleteExpiredRefreshToken(tokenIdList);
    }
  }
}
