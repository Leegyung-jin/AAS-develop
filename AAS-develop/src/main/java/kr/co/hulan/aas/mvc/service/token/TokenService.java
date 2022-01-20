package kr.co.hulan.aas.mvc.service.token;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TokenService {

  @Autowired
  private ListExpiredTokenService listExpiredTokenService;
  @Autowired
  private DeleteAccessTokenService deleteAccessTokenService;
  @Autowired
  private DeleteRefreshTokenService deleteRefreshTokenService;
  @Autowired
  private ScheduledTokenService scheduledTokenService;

  public List<String> getListExpiredToken() {
    return listExpiredTokenService.getListExpiredToken();
  }

  public void deleteExpiredAccessToken(List<String> list) {
    deleteAccessTokenService.deleteExpiredAccessToken(list);
  }

  public void deleteExpiredRefreshToken(List<String> list) {
    deleteRefreshTokenService.deleteExpiredRefreshToken(list);
  }

  public void deleteExpiredToken() {
    scheduledTokenService.deleteExpiredToken();
  }
}
