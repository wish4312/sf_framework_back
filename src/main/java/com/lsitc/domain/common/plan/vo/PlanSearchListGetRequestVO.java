package com.lsitc.domain.common.plan.vo;

import java.time.LocalDate;
import org.apache.commons.lang3.builder.ToStringBuilder;
import com.lsitc.domain.common.plan.entity.PlanEntity;
import com.lsitc.global.util.LocalDateUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PlanSearchListGetRequestVO {
  private final String yyyymm;

  public PlanEntity toEntity() {
    return PlanEntity.builder()
        .startDate(LocalDateUtils.getFirstDayOfMonth(getDate()))
        .endDate(LocalDateUtils.getLastDayOfMonth(getDate()))
        .build();
  }
  
  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }

  private LocalDate getDate() {
    return LocalDateUtils.parseYyyymmdd(yyyymm + "01");
  }
}
