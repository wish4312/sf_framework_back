package com.lsitc.domain.common.plan.vo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.lsitc.domain.common.plan.entity.PlanEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PlanInfoGetResponseVO {
  
  private Long planSeq;
  private LocalDate strtDt;
  private String strtHh;
  private String strtMm;
  private LocalDate endDt;
  private String endHh;
  private String endMm;
  private String planTitle;
  private String planCntn;
  private String planColor;
  private Long regUserNo;
  private LocalDateTime regDttm;
  private Long procUserNo;
  private LocalDateTime procDttm;
  
  @Builder
  private PlanInfoGetResponseVO(Long planSeq, LocalDate strtDt, String strtHh, String strtMm,
      LocalDate endDt, String endHh, String endMm, String planTitle, String planCntn,
      String planColor, Long regUserNo, LocalDateTime regDttm, Long procUserNo,
      LocalDateTime procDttm) {
    this.planSeq = planSeq;
    this.strtDt = strtDt;
    this.strtHh = strtHh;
    this.strtMm = strtMm;
    this.endDt = endDt;
    this.endHh = endHh;
    this.endMm = endMm;
    this.planTitle = planTitle;
    this.planCntn = planCntn;
    this.planColor = planColor;
    this.regUserNo = regUserNo;
    this.regDttm = regDttm;
    this.procUserNo = procUserNo;
    this.procDttm = procDttm;
  }

  public static PlanInfoGetResponseVO of(PlanEntity planEntity) {
    return PlanInfoGetResponseVO.builder()
        .planSeq(planEntity.getId())
        .strtDt(planEntity.getStartDate())
        .strtHh(planEntity.getStartHour())
        .strtMm(planEntity.getStartMinute())
        .endDt(planEntity.getEndDate())
        .endHh(planEntity.getEndHour())
        .endMm(planEntity.getEndMinute())
        .planTitle(planEntity.getPlanTitle())
        .planCntn(planEntity.getPlanContents())
        .planColor(planEntity.getPlanColor())
        .regUserNo(planEntity.getCreatedBy())
        .regDttm(planEntity.getCreatedDate())
        .procUserNo(planEntity.getLastModifiedBy())
        .procDttm(planEntity.getLastModifiedDate())
        .build();
  }
  
  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
  }

}
