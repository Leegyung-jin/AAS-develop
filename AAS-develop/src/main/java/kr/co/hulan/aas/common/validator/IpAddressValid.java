package kr.co.hulan.aas.common.validator;

import static java.lang.annotation.ElementType.FIELD;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = IpAddressValidator.class)
@Documented
@Target(FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface IpAddressValid {
  String message() default "IpAddress is invalid";
  Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default {};
}
