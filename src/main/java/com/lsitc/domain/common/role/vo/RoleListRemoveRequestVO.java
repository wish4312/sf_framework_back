package com.lsitc.domain.common.role.vo;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import org.apache.commons.lang3.builder.ToStringBuilder;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.lsitc.domain.common.role.entity.RoleEntity;
import lombok.Getter;

@Getter
public class RoleListRemoveRequestVO {

  @NotNull
  @PositiveOrZero(message = "음수의 ID는 사용할 수 없습니다.")
  private final Long roleId;

  @JsonCreator
  public RoleListRemoveRequestVO(Long roleId) {
    this.roleId = roleId;
  }

  public RoleEntity toEntity() {
    return RoleEntity.builder().id(roleId).build();
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }
}
