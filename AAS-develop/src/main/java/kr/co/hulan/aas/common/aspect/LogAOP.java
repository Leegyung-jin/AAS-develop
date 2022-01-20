package kr.co.hulan.aas.common.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LogAOP {

  private Logger logger = LoggerFactory.getLogger(this.getClass());

  @Before("execution(* kr.co..*Controller.*(..)) || execution(* kr.co..*Service.*(..))")
  public void before(JoinPoint j) {
    StringBuilder beforeLog = new StringBuilder();
    beforeLog.append("LogAOP").append("|");

    final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication != null && authentication.isAuthenticated()) {
      beforeLog.append(authentication.getName()).append("|");
    } else {
      beforeLog.append("Anonymous").append("|");
    }

    String type = j.getSignature().getDeclaringTypeName();
    if (type.contains("Controller")) {
      beforeLog.append("Controller").append("|");
    } else if (type.contains("Service")) {
      beforeLog.append("Service").append("|");
    }

    beforeLog.append(j.getTarget()).append("|");
    Object[] args = j.getArgs();
    if (args != null) {
      beforeLog.append(j.getSignature().getName()).append("|\n");
      StringBuilder argsBuilder = new StringBuilder();
      for (int i = 0; i < args.length; i++) {
        argsBuilder.append(String.format("%s번째 Request : %s", i + 1, args[i])).append("\n");
      }
      beforeLog.append(argsBuilder);
    }
    else {
      beforeLog.append(" ").append("|").append(" ");
    }
    logger.info(beforeLog.toString());
  }
}
