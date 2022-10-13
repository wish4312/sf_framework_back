package com.lsitc.domain.common.bloc.vo;

import com.lsitc.domain.common.bloc.entity.BlocEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
public class BlocInfoGetResponseVO {

  private final Long id;
  private final String name;
  private final boolean isDeleted;
  private final String remark;

  @Builder
  private BlocInfoGetResponseVO(long id, String name, boolean isDeleted, String remark) {
    this.id = id;
    this.name = name;
    this.isDeleted = isDeleted;
    this.remark = remark;
  }

  public static BlocInfoGetResponseVO of(BlocEntity blocInfo) {
    return builder().id(blocInfo.getId())
        .name(blocInfo.getName())
        .isDeleted(blocInfo.isDeleted())
        .remark(blocInfo.getRemark())
        .build();
  }

  @Override
  public String toString() {
    return "BlocInfoGetResponseVO{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", isDeleted=" + isDeleted +
        ", remark='" + remark + '\'' +
        '}';
  }
}
