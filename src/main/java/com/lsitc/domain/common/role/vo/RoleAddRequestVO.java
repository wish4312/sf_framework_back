package com.lsitc.domain.common.role.vo;

import com.lsitc.domain.common.role.entity.RoleEntity;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RoleAddRequestVO {

  @NotBlank(message = "권한명이 필요합니다.")
  private final String name;

  private final String remark;

  public RoleEntity toEntity() {
    return RoleEntity.builder()
        .name(name)
        .remark(remark)
        .build();
  }

  @Override
  public String toString() {
    return "RoleAddRequestVO{" +
        "name='" + name + '\'' +
        ", remark='" + remark + '\'' +
        '}';
  }
}
