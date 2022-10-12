package com.lsitc.domain.common.role.vo;

import com.lsitc.domain.common.role.entity.RoleEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
public class RoleListGetResponseVO {

  private final long id;
  private final String name;
  private final boolean isDeleted;
  private final String remark;

  @Builder
  private RoleListGetResponseVO(long id, String name, boolean isDeleted, String remark) {
    this.id = id;
    this.name = name;
    this.isDeleted = isDeleted;
    this.remark = remark;
  }

  public static RoleListGetResponseVO of(RoleEntity roleInfo) {
    return builder().id(roleInfo.getId())
        .name(roleInfo.getName())
        .isDeleted(roleInfo.isDeleted())
        .remark(roleInfo.getRemark())
        .build();
  }

  @Override
  public String toString() {
    return "RoleListGetResponseVO{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", isDeleted=" + isDeleted +
        ", remark='" + remark + '\'' +
        '}';
  }
}
