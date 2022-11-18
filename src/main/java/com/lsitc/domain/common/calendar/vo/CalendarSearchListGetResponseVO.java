package com.lsitc.domain.common.calendar.vo;

import java.time.LocalDate;
import org.apache.commons.lang3.builder.ToStringBuilder;
import com.lsitc.domain.common.calendar.entity.CalendarEntity;
import com.lsitc.domain.model.BooleanState;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CalendarSearchListGetResponseVO {
  
  private LocalDate dt;
  private String hldyFg;
  private String hldyNm;
  private String rmrk;
  
  @Builder
  private CalendarSearchListGetResponseVO(LocalDate dt, String hldyFg, String hldyNm, String rmrk) {
    this.dt = dt;
    this.hldyFg = hldyFg;
    this.hldyNm = hldyNm;
    this.rmrk = rmrk;
  }
  
  public static CalendarSearchListGetResponseVO of(CalendarEntity calendarEntity) {
    return CalendarSearchListGetResponseVO.builder()
        .dt(calendarEntity.getDate())
        .hldyFg(convertBoolean(calendarEntity.isHoliday()))
        .hldyNm(calendarEntity.getHolidayName())
        .rmrk(calendarEntity.getRemark())
        .build();
  }
  
  private static String convertBoolean(Boolean booleanValue) {
    return BooleanState.of(booleanValue).getStringValue();
  }
  
  
  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }

}
