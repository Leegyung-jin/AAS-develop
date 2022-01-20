package kr.co.hulan.aas.common.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.apache.commons.validator.routines.DomainValidator;
import org.apache.commons.validator.routines.InetAddressValidator;

public class IpAddressValidator implements ConstraintValidator<IpAddressValid, String> {

  @Override
  public void initialize(IpAddressValid constraintAnnotation) {
  }

  @Override
  public boolean isValid(String value, ConstraintValidatorContext cvContext) {
    return InetAddressValidator.getInstance().isValid(value)
       || DomainValidator.getInstance().isValid(value);
  }
}
