package com.lsitc.domain.common.plan.vo;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.lsitc.domain.common.plan.entity.PlanEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PlanSearchListGetResponseVO {
  
  private LocalDate dt;
  private Long planSeq;
  private LocalDate strtDt;
  private LocalDate endDt;
  private String planTitle;
  private String planColor;

  @Builder
  private PlanSearchListGetResponseVO(LocalDate dt, Long planSeq, LocalDate strtDt, LocalDate endDt,
      String planTitle, String planColor) {
    this.dt = dt;
    this.planSeq = planSeq;
    this.strtDt = strtDt;
    this.endDt = endDt;
    this.planTitle = planTitle;
    this.planColor = planColor;
  }

  public static List<PlanSearchListGetResponseVO> of(List<PlanEntity> planEntityList) {
    List<PlanSearchListGetResponseVO> resultList = new ArrayList<PlanSearchListGetResponseVO>();
    
    planEntityList.forEach(entity -> {
      
      for ( LocalDate date = entity.getStartDate();
            date.isBefore(entity.getEndDate().plusDays(1));
            date = date.plusDays(1) ) {
        PlanSearchListGetResponseVO vo = PlanSearchListGetResponseVO.builder()
        .dt(date)
        .planSeq(entity.getId())
        .strtDt(entity.getStartDate())
        .endDt(entity.getEndDate())
        .planTitle(entity.getPlanTitle())
        .planColor(entity.getPlanColor())
        .build();
        
        resultList.add(vo);
      }
      
    });
    
    return resultList;
  }
  
  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
  }

}
