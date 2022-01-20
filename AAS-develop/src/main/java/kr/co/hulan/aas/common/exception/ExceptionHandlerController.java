package kr.co.hulan.aas.common.exception;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.UnexpectedTypeException;
import kr.co.hulan.aas.common.code.BaseCode;
import kr.co.hulan.aas.common.model.res.DefaultHttpRes;
import kr.co.hulan.aas.common.model.res.ValidationResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionHandlerController {

  private Logger logger = LoggerFactory.getLogger(this.getClass());

  private void log(Exception ex) {
    String message = getStackStringFromException(ex);
    logger.error(message);
  }

  private String getStackStringFromException(Exception ex) {
    StringWriter errors = new StringWriter();
    ex.printStackTrace(new PrintWriter(errors));
    return errors.toString();
  }

  // SPRING @Valid로 발생되는 Exception
  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  public DefaultHttpRes methodArgumentNotValid(MethodArgumentNotValidException ex) {
    log(ex);
    final List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
    List<ValidationResult> failResult = fieldErrors.stream()
        .map(e -> new ValidationResult(e.getField(), e.getDefaultMessage(), e.getDefaultMessage()))
        .collect(Collectors.toList());
    if(failResult != null && failResult.size() > 0 ){
      return new DefaultHttpRes<>(BaseCode.ERR_ARG_IS_WRONG.code(), failResult.get(0).getMessage(), failResult);
    }
    return new DefaultHttpRes<>(BaseCode.ERR_ARG_IS_WRONG, failResult);
  }

  // SPRING @Valid로 발생되는 Exception
  @ExceptionHandler(UnexpectedTypeException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  public DefaultHttpRes uexpectedTypeException(UnexpectedTypeException ex) {
    log(ex);
    return new DefaultHttpRes<>(BaseCode.ERR_ARG_IS_WRONG, ex.getMessage());
  }

  // SPRING @Valid로 발생되는 Exception
  @ExceptionHandler(ConstraintViolationException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  public DefaultHttpRes uexpectedTypeException(ConstraintViolationException ex) {
    log(ex);
    Set<ConstraintViolation<?>> list =  ex.getConstraintViolations();
    if( list != null && list.size() > 0 ){
      ConstraintViolation first = list.stream().findFirst().get();
      return new DefaultHttpRes<>(BaseCode.ERR_ARG_IS_WRONG.code(), first.getMessage());
    }
    else {
      return new DefaultHttpRes<>(BaseCode.ERR_ARG_IS_WRONG, ex.getMessage());
    }
  }

  // 기타 Exception
  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  public DefaultHttpRes Exception(Exception ex) {
    log(ex);
    return new DefaultHttpRes<>(BaseCode.ERR_ETC_EXCEPTION, ex.getMessage());
  }

  // SQL Exception
  @ExceptionHandler(DataAccessException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  public DefaultHttpRes sqlException(DataAccessException ex) {
    log(ex);
    return new DefaultHttpRes<>(BaseCode.ERR_SQL_EXCEPTION, ex.getMessage());
  }

  // BaseCode Exception
  @ExceptionHandler(CommonException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  public DefaultHttpRes commonException(CommonException ex) {
    log(ex);
    return new DefaultHttpRes<>(ex.getReturnCode(), ex.getReturnMessage());
  }

  // Api Exception
  @ExceptionHandler(ApiException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  public DefaultHttpRes apiException(ApiException ex) {
    log(ex);
    return new DefaultHttpRes<>(BaseCode.ERR_API_EXCEPTION, "ApiException");
  }


}
