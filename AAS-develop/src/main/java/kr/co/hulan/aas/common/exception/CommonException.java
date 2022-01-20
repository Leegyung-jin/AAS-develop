package kr.co.hulan.aas.common.exception;

import lombok.*;

@Data
@Setter(AccessLevel.PRIVATE)
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class CommonException extends RuntimeException {

  private static final long serialVersionUID = -6656753000886750444L;
  private final int returnCode;
  private final String returnMessage;
}
