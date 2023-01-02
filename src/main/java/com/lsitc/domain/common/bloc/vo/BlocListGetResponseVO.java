package com.lsitc.domain.common.bloc.vo;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.lsitc.domain.common.bloc.entity.BlocEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
public class BlocListGetResponseVO {

  private final Long id;
  private final String name;
  private final boolean isDeleted;
  private final String remark;

  @Builder
  private BlocListGetResponseVO(long id, String name, boolean isDeleted, String remark) {
    this.id = id;
    this.name = name;
    this.isDeleted = isDeleted;
    this.remark = remark;
  }

  public static BlocListGetResponseVO of(BlocEntity blocInfo) {
    return builder().id(blocInfo.getId())
        .name(blocInfo.getName())
        .isDeleted(blocInfo.isDeleted())
        .remark(blocInfo.getRemark())
        .build();
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
  }
}
