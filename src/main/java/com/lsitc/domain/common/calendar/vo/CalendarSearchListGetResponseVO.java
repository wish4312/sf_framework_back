package com.lsitc.domain.common.calendar.vo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
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
  private Long regUserNo;
  private LocalDateTime regDttm;
  private Long procUserNo;
  private LocalDateTime procDttm;

  @Builder
  private CalendarSearchListGetResponseVO(LocalDate dt, String hldyFg, String hldyNm, String rmrk,
      Long regUserNo, LocalDateTime regDttm, Long procUserNo, LocalDateTime procDttm) {
    this.dt = dt;
    this.hldyFg = hldyFg;
    this.hldyNm = hldyNm;
    this.rmrk = rmrk;
    this.regUserNo = regUserNo;
    this.regDttm = regDttm;
    this.procUserNo = procUserNo;
    this.procDttm = procDttm;
  }
  
  public static CalendarSearchListGetResponseVO of(CalendarEntity calendarEntity) {
    return builder().dt(calendarEntity.getDate())
        .hldyFg(convertBoolean(calendarEntity.isHoliday()))
        .hldyNm(calendarEntity.getHolidayName())
        .rmrk(calendarEntity.getRemark())
        .regUserNo(calendarEntity.getCreatedBy())
        .regDttm(calendarEntity.getCreatedDate())
        .procUserNo(calendarEntity.getLastModifiedBy())
        .procDttm(calendarEntity.getLastModifiedDate())
        .regUserNo(calendarEntity.getCreatedBy())
        .regDttm(calendarEntity.getCreatedDate())
        .procUserNo(calendarEntity.getLastModifiedBy())
        .procDttm(calendarEntity.getLastModifiedDate())
        .build();
  }
  
  private static String convertBoolean(Boolean booleanValue) {
    return BooleanState.of(booleanValue).getStringValue();
  }
  
  
  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
  }

}
