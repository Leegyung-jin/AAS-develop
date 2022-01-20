package kr.co.hulan.aas.common.utils;

import java.time.format.DateTimeFormatter;

public enum DateTimeFormatterFactory {
  INSTANCE;

  private DateTimeFormatter datePattern = DateTimeFormatter.ofPattern("yyyy-MM-dd");
  private DateTimeFormatter timePattern = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

  public DateTimeFormatter datePattern() {
    return datePattern;
  }

  public DateTimeFormatter timePattern() {
    return timePattern;
  }
}
