package com.lsitc.domain.common.calendar.vo;

import org.apache.commons.lang3.builder.ToStringBuilder;
import com.lsitc.domain.common.calendar.entity.CalendarEntity;
import com.lsitc.domain.model.BooleanState;
import com.lsitc.global.util.LocalDateUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CalendarModifyRequestVO {
  
  private String dt;
  private String hldyFg;
  private String hldyNm;
  private String rmrk;
  
  public CalendarEntity toEntity() {
    return CalendarEntity.builder()
        .date(LocalDateUtils.parse(dt,"yyyy-MM-dd"))
        .isHoliday(converthldyFg())
        .holidayName(hldyNm)
        .remark(rmrk)
        .build();
  }

  private Boolean converthldyFg() {
    return BooleanState.of(this.hldyFg).getBooleanValue();
  }
  
  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }
}