package com.lsitc.domain.common.role.vo;

import com.lsitc.domain.common.role.entity.RoleEntity;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RoleInfoGetRequestVO {

  @NotNull
  @PositiveOrZero(message = "권한 ID에는 음수가 들어올 수 없습니다.")
  private final long id;

  public RoleEntity toEntity() {
    return RoleEntity.builder()
        .id(id)
        .build();
  }

  @Override
  public String toString() {
    return "RoleInfoGetRequestVO{" +
        "id=" + id +
        '}';
  }
}
