package com.lsitc.domain.common.plan.vo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import com.lsitc.domain.common.plan.entity.PlanEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PlanModifyRequestVO {
  
  private String planSeq;
  private String strtDt;
  private String strtHh;
  private String strtMm;
  private String endDt;
  private String endHh;
  private String endMm;
  private String planTitle;
  private String planCntn;
  private String planColor;
  
  public PlanEntity toEntity() {
    return PlanEntity.builder()
        .id(Long.valueOf(planSeq))
        .startDate(LocalDate.parse(strtDt, DateTimeFormatter.ofPattern("yyyy-MM-dd")))
        .startHour(strtHh)
        .startMinute(strtMm)
        .endDate(LocalDate.parse(endDt, DateTimeFormatter.ofPattern("yyyy-MM-dd")))
        .endHour(endHh)
        .endMinute(endMm)
        .planTitle(planTitle)
        .planContents(planCntn)
        .planColor(planColor)
        .build();
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }
}