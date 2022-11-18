package com.lsitc.domain.common.calendar.vo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import com.lsitc.domain.common.calendar.entity.CalendarEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CalendarSearchListGetRequestVO {
  private final String yyyymm;

  public CalendarEntity toEntity() {
    return CalendarEntity.builder()
        .startDate(LocalDate.parse(yyyymm + "01", DateTimeFormatter.ofPattern("yyyyMMdd")))
        .endDate(LocalDate.parse(yyyymm + "31", DateTimeFormatter.ofPattern("yyyyMMdd")))
        .build();
  }
  
  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }
}
