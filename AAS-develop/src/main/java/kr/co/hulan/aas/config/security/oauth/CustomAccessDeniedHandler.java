package kr.co.hulan.aas.config.security.oauth;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.model.res.DefaultHttpRes;
import kr.co.hulan.aas.common.utils.JsonUtil;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {

  @Override
  public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
      AccessDeniedException e) throws IOException, ServletException {
    try{
      httpServletResponse.setContentType("application/json;charset=UTF-8");
      httpServletResponse.setStatus(HttpStatus.FORBIDDEN.value());
      httpServletResponse.getWriter().write(JsonUtil
          .toStringJson(new DefaultHttpRes<Object>(BaseCode.ERR_NOT_AUTHORIZED.code(), BaseCode.ERR_NOT_AUTHORIZED.message(), e.getMessage())));
    }
    catch(Exception e2){
      e2.printStackTrace();
    }
  }
}
