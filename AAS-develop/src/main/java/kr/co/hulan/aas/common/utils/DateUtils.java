package kr.co.hulan.aas.common.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public enum DateUtils {
  INSTANCE;

  private DateTimeFormatterFactory dateTimeFormatterFactory = DateTimeFormatterFactory.INSTANCE;

  private DateTimeFormatter defaultDatePattern = dateTimeFormatterFactory.datePattern();
  private DateTimeFormatter defaultTimePattern = dateTimeFormatterFactory.timePattern();

  public String nowAsStringDate() {
    LocalDateTime now = LocalDateTime.now();
    return now.format(defaultDatePattern);
  }

  public String nowAddDayAsStringDate(int day) {
    LocalDateTime now = LocalDateTime.now();
    return now.plusDays(day).format(defaultDatePattern);
  }

  public String nowAsStringTime() {
    LocalDateTime now = LocalDateTime.now();
    return now.format(defaultTimePattern);
  }

  public String nowAsString(String pattern) {
    LocalDateTime now = LocalDateTime.now();
    return now.format(DateTimeFormatter.ofPattern(pattern));
  }
}
