package com.lsitc.domain.common.plan.vo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import com.lsitc.domain.common.plan.entity.PlanEntity;
import com.lsitc.global.util.LocalDateUtils;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PlanAddRequestVO {
  
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
        .startDate(LocalDateUtils.parse(strtDt, "yyyy-MM-dd"))
        .startHour(strtHh)
        .startMinute(strtMm)
        .endDate(LocalDateUtils.parse(endDt, "yyyy-MM-dd"))
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
