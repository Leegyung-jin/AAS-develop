package kr.co.hulan.aas.common.validator;

import static java.lang.annotation.ElementType.FIELD;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = EnumCodeValidator.class)
@Documented
@Target(FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface EnumCodeValid {

  String message() default "invalid parameter!!";
  Class<?>[] groups() default {};
  Class<? extends CodeForEnum> target();
  Class<? extends Payload>[] payload() default{};
}
