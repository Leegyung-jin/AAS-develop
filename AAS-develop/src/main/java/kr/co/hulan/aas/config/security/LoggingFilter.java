package kr.co.hulan.aas.config.security;

import kr.co.hulan.aas.common.utils.IpUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

@Component
@Order(4)
public class LoggingFilter implements Filter {
  private Logger logger = LoggerFactory.getLogger(this.getClass());

  @Override
  public void init(FilterConfig filterConfig) {
  }

  @Override
  public void destroy() {
  }

  @Override
  public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
    HttpServletResponse response = (HttpServletResponse) res;
    HttpServletRequest request = (HttpServletRequest) req;

    long startTime = System.currentTimeMillis();
    Exception ex = null;

    try {
      chain.doFilter(req, res);
    } catch (Exception e) {
      ex = e;
      throw e;
    } finally {
      printAccessLog(request, response, ex, System.currentTimeMillis() - startTime);
    }
  }

  private void printAccessLog(HttpServletRequest req, HttpServletResponse res, Exception exception, long elapsedTime) {
    StringBuilder sb = new StringBuilder();
    sb.append("ONCE_LOG|");
    final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication != null && authentication.isAuthenticated()) {
      sb.append(authentication.getName());
    } else {
      sb.append("Anonymous");
    }

    sb.append("|");
    sb.append(IpUtil.getClientIP(req)).append("|");
    sb.append(req.getMethod()).append("|");
    sb.append(req.getRequestURI()).append("|");

    StringBuilder params = new StringBuilder();
    Enumeration<String> e = req.getParameterNames();
    while (e.hasMoreElements()) {
      String name = e.nextElement();
      params.append(name).append("=").append(req.getParameter(name)).append(",");
    }
    sb.append(StringUtils.chomp(StringUtils.defaultIfEmpty(params.toString(), " "), ",")).append("|");
    sb.append(res.getStatus()).append("|");
    sb.append(elapsedTime).append("|");
    sb.append(exception != null ? exception.getMessage() : " ");
    logger.info(sb.toString());
  }

}
