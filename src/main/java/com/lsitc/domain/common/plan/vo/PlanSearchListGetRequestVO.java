package com.lsitc.domain.common.plan.vo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import com.lsitc.domain.common.plan.entity.PlanEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PlanSearchListGetRequestVO {
  private final String yyyymm;

  public PlanEntity toEntity() {
    return PlanEntity.builder()
        .startDate(LocalDate.parse(yyyymm + "01", DateTimeFormatter.ofPattern("yyyyMMdd")))
        .endDate(LocalDate.parse(yyyymm + "31", DateTimeFormatter.ofPattern("yyyyMMdd")))
        .build();
  }
  
  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }


}
