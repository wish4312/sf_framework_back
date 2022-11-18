package com.lsitc.domain.common.calendar.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import com.lsitc.global.auditing.Auditable;
import com.lsitc.global.common.BaseAbstractEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CalendarEntity extends BaseAbstractEntity implements Auditable<Long, LocalDateTime> {

  private LocalDate date;
  private boolean isHoliday;
  private String holidayName;
  private String remark;

  private LocalDate startDate;
  private LocalDate endDate;

  @Builder
  private CalendarEntity(LocalDate date, boolean isHoliday, String holidayName, String remark,
      LocalDate startDate, LocalDate endDate) {
    this.date = date;
    this.isHoliday = isHoliday;
    this.holidayName = holidayName;
    this.remark = remark;
    this.startDate = startDate;
    this.endDate = endDate;
  }
}
