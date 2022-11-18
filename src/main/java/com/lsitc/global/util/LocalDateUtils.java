package com.lsitc.global.util;

import java.time.LocalDate;

public class LocalDateUtils {
  
  public static LocalDate getFirstDayOfMonth(LocalDate date) {
    return LocalDate.of(date.getYear(), date.getMonth(), 1);
  }
  
  public static LocalDate getLastDayOfMonth(LocalDate date) {
    return LocalDate.of(date.getYear(), date.getMonth(), date.lengthOfMonth());
  }
}
