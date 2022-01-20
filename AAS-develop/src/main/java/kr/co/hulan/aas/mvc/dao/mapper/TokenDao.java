package kr.co.hulan.aas.mvc.dao.mapper;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TokenDao {

  List<String> getExpiredToken();

  void deleteAccessToken(List<String> list);

  void deleteRefreshToken(List<String> list);
}
