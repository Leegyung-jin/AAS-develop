package kr.co.hulan.aas.mvc.service.token;

import kr.co.hulan.aas.mvc.dao.mapper.TokenDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeleteAccessTokenService {

  @Autowired
  private TokenDao tokenDao;

  public void deleteExpiredAccessToken(List<String> list) {
    tokenDao.deleteAccessToken(list);
  }
}
