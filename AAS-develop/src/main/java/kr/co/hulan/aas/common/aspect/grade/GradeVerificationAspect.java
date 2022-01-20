package kr.co.hulan.aas.common.aspect.grade;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.lang.reflect.Method;
import javax.servlet.http.HttpServletRequest;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.exception.CommonException;
import kr.co.hulan.aas.common.utils.Oauth2JwtUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class GradeVerificationAspect {

  @Autowired
  private Gson gson;

  @Around("@annotation(kr.co.hulan.aas.common.aspect.grade.ValidGrade)")
  public Object gradeVerification(ProceedingJoinPoint joinPoint) throws Throwable {
    HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
    String authorization = request.getHeader("Authorization");
    if (authorization != null) {
      int tokenGrade = gradeByAuthorization(authorization);
      MethodSignature signature = (MethodSignature) joinPoint.getSignature();
      Method method = signature.getMethod();
      ValidGrade validGrade = method.getAnnotation(ValidGrade.class);
      int[] grades = validGrade.grades();
      for (int grade : grades) {
        if (tokenGrade == grade) {
          return joinPoint.proceed();
        }
      }
      throw new CommonException(BaseCode.ERR_GRADE_EXCEPTION.code(), BaseCode.ERR_GRADE_EXCEPTION.message());
    } else {
      throw new CommonException(BaseCode.ERR_TOKEN_EXCEPTION.code(), BaseCode.ERR_TOKEN_EXCEPTION.message());
    }
  }

  private int gradeByAuthorization(String authorization) {
    Oauth2JwtUtils oauth2JwtUtils = new Oauth2JwtUtils();
    String accessToken = authorization.replace("bearer", "").replace("Bearer", "").trim();
    String decodePLD = oauth2JwtUtils.decodePLD(accessToken);
    JsonObject jsonObject = gson.fromJson(decodePLD, JsonObject.class);
    return jsonObject.get("member_info").getAsJsonObject().get("mbLevel").getAsInt();
  }
}
