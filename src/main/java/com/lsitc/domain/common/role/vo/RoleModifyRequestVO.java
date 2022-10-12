package com.lsitc.domain.common.role.vo;

import com.lsitc.domain.common.role.entity.RoleEntity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RoleModifyRequestVO {

  @NotNull
  @PositiveOrZero(message = "음수의 ID는 사용할 수 없습니다.")
  private final long id;

  @NotBlank(message = "권한명이 필요합니다.")
  private final String name;

  private final String remark;

  public RoleEntity toEntity() {
    return RoleEntity.builder()
        .id(id)
        .name(name)
        .remark(remark)
        .build();
  }

  @Override
  public String toString() {
    return "RoleModifyRequestVO{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", remark='" + remark + '\'' +
        '}';
  }
}
