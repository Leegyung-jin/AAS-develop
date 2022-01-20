package kr.co.hulan.aas.common.aspect.historyLog;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.lang.reflect.Method;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.exception.CommonException;
import kr.co.hulan.aas.common.utils.Oauth2JwtUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class HistoryLogInsertAspect {

  private Logger logger = LoggerFactory.getLogger(this.getClass());

  @Autowired
  private Gson gson;

  @Pointcut("@annotation(HistoryLog)")
  public void historyLogPointcut() {
  }

  @Around("historyLogPointcut()")
  public Object historyLogInsert(ProceedingJoinPoint joinPoint) throws Throwable {
    HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
    HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse();

    String authorization = request.getHeader("Authorization");
    MethodSignature signature = (MethodSignature) joinPoint.getSignature();
    Method method = signature.getMethod();
    HistoryLog historyLog = method.getAnnotation(HistoryLog.class);

    Object[] args = joinPoint.getArgs();

    // Annotation category
    String category = historyLog.category();
    // Request param
    String req = null;
    // Member_NO
    Long memberNo = null;
    // Response parma
    String res = null;
    // Response Status
    int resStatus = response.getStatus();

    if (category.equals("로그인")) {
      for (int i = 0; i < args.length; i++) {
        if (args[i] instanceof OAuth2Authentication) {
          JsonObject jsonObject = gson.fromJson(gson.toJson(args[i]), JsonObject.class);
          req = jsonObject.get("userAuthentication").getAsJsonObject().get("principal").toString();
        }
      }
    } else {
      if (authorization != null) {
        memberNo = memberNoByAuthorization(authorization);
        req = gson.toJson(args);
      } else {
        throw new CommonException(BaseCode.ERR_TOKEN_EXCEPTION.code(), BaseCode.ERR_TOKEN_EXCEPTION.message());
      }
    }

    Object obj = joinPoint.proceed();
    res = gson.toJson(obj);

    System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@ HistoryLog 결과 @@@@@@@@@@@@@@@@@@@@@@@@@");
    System.out.println("URI : " + request.getRequestURI());
    System.out.println("category : " + category);
    System.out.println("req : " + req);
    System.out.println("memberNo : " + memberNo);
    System.out.println("resStatus : " + resStatus);
    System.out.println("res : " + res);
    System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");

    /*
     * 1. try catch final 을 사용할 경우
     *  - ExceptionHandlerController 로 이동하지 않는다...(resStatus = 200, Response Body 가 없음...)
     * 2. @After or @AfterThrowing 을 사용할 경우
     *  - Exception 을 가져 올 수는 있지만 해당 시점에서 resStatus = 200 이고, 각 Annotation 실행 이후
     *    ExceptionHandlerController 가 실행 된다...(실제 Response 와 각 Annotation Response 가 상이함..)
     *
     * 결론(?) filter or interceptor 로 해결해야 하지 않을까...기준은 URI...(?)
    */

    return obj;
  }

  private Long memberNoByAuthorization(String authorization) {
    Oauth2JwtUtils oauth2JwtUtils = new Oauth2JwtUtils();
    String accessToken = authorization.replace("bearer", "").replace("Bearer", "").trim();
    String decodePLD = oauth2JwtUtils.decodePLD(accessToken);
    JsonObject jsonObject = gson.fromJson(decodePLD, JsonObject.class);
    return jsonObject.get("member_info").getAsJsonObject().get("mbNo").getAsLong();
  }
}
