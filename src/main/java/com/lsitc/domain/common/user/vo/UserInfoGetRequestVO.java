package com.lsitc.domain.common.user.vo;

import javax.validation.constraints.PositiveOrZero;
import org.apache.commons.lang3.builder.ToStringBuilder;
import com.lsitc.domain.common.user.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserInfoGetRequestVO {

  @PositiveOrZero(message = "음수의 ID는 사용할 수 없습니다.")
  private final Long id;
  private final String userId;

  public UserEntity toEntity() {
    return UserEntity.builder().id(id).userId(userId).build();
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }
}
