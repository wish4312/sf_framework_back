package com.lsitc.domain.common.dept.vo;

import org.apache.commons.lang3.builder.ToStringBuilder;
import com.lsitc.domain.common.dept.entity.DeptEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
public class DeptSearchListGetResponseVO {

  private final Long id;
  private final String name;
  private final Long parentsId;
  private final String remark;

  @Builder
  private DeptSearchListGetResponseVO(Long id, String name, Long parentsId, String remark) {
    this.id = id;
    this.name = name;
    this.parentsId = parentsId;
    this.remark = remark;
  }

  public static DeptSearchListGetResponseVO of(DeptEntity deptInfo) {
    return builder().id(deptInfo.getId())
        .name(deptInfo.getName())
        .parentsId(deptInfo.getParentsId())
        .remark(deptInfo.getRemark())
        .build();
  }
  
  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }

}
