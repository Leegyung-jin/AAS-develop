package kr.co.hulan.aas.config.security;

import kr.co.hulan.aas.common.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorsFilter implements Filter {

  private Logger logger = LoggerFactory.getLogger(this.getClass());
  private DateUtils dateUtils = DateUtils.INSTANCE;
  private AtomicLong id = new AtomicLong(1L);

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
  }

  @Override
  public void destroy() {
  }

  @Override
  public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain) throws IOException, ServletException {
    HttpServletRequest request = (HttpServletRequest) req;
    HttpServletResponse response = (HttpServletResponse) res;
    long requestId = this.id.incrementAndGet();

    response.setHeader("Access-Control-Allow-Origin", "*");
    response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT");
    response.setHeader("Access-Control-Max-Age", "3600");
    response.setHeader("Access-Control-Allow-Headers", request.getHeader("Access-Control-Request-Headers"));

    if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
      response.setStatus(HttpServletResponse.SC_OK);
    } else {
      filterChain.doFilter(req, res);
    }
    // LOG PRINT (어떤 로그를 찍을까...)
    StringBuilder str = new StringBuilder();
    str.append(requestLog(request, requestId));
    str.append('\n');
    str.append(responseLog(response, requestId));
    logger.info("\n {}", str);
  }

  private String requestLog(HttpServletRequest request, long requestId) {
    StringBuilder stb = new StringBuilder();
    stb.append("=================================\tHTTP REQUEST START\t=================================").append('\n');
    stb.append(String.format("Time : %s", dateUtils.nowAsStringTime())).append('\n');
    stb.append(String.format("ID : %d", requestId)).append('\n');
    stb.append(String.format("Request URI : %s", request.getRequestURI())).append('\n');
    stb.append(String.format("Request URL : %s", request.getRequestURL())).append('\n');
    stb.append(String.format("Request METHOD : %s", request.getMethod())).append('\n');
    stb.append("=================================\tHTTP REQUEST END\t=================================");
    return stb.toString();
  }

  private String responseLog(HttpServletResponse response, long requestId) {
    StringBuilder stb = new StringBuilder();
    stb.append("=================================\tHTTP RESPONSE START\t=================================").append('\n');
    stb.append(String.format("Time : %s", dateUtils.nowAsStringTime())).append('\n');
    stb.append(String.format("ID : %d", requestId)).append('\n');
    stb.append(String.format("Response Status : %s", response.getStatus())).append('\n');
    stb.append("=================================\tHTTP RESPONSE END\t=================================");
    return stb.toString();

  }
}
