package com.lsitc.domain.common.bloc.vo;

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
    return "BlocListGetResponseVO{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", isDeleted=" + isDeleted +
        ", remark='" + remark + '\'' +
        '}';
  }
}
