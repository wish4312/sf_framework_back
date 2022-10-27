package com.lsitc.domain.common.role.vo;

import javax.validation.constraints.PositiveOrZero;
import org.apache.commons.lang3.builder.ToStringBuilder;
import com.lsitc.domain.common.role.entity.RoleEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RoleSearchListGetRequestVO {

  @PositiveOrZero(message = "권한 ID에는 음수가 들어올 수 없습니다.")
  private final Long roleId;
  private final String roleNm;
  
  public RoleEntity toEntity() {
    return RoleEntity.builder()
        .id(roleId)
        .name(roleNm)
        .build();
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }
}
