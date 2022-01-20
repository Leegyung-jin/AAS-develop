package kr.co.hulan.aas.common.validator;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EnumCodeValidator implements ConstraintValidator<EnumCodeValid, Integer> {
  private List<Integer> types;

  @Override
  public void initialize(EnumCodeValid constraintAnnotation) {
    types = Arrays.stream(constraintAnnotation.target().getEnumConstants())
        .map(constant -> constant.getCode())
        .collect(Collectors.toList());
  }

  @Override
  public boolean isValid(Integer value, ConstraintValidatorContext context) {
    return types.contains(value);
  }
}
