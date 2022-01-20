package kr.co.hulan.aas.common.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PortValidator implements ConstraintValidator<PortValid, Integer> {

  @Override
  public void initialize(PortValid constraintAnnotation) {
  }

  @Override
  public boolean isValid(Integer value, ConstraintValidatorContext cvContext) {
    return value > 0 && value < 65536;
  }
}
