package com.lsitc.domain.common.user.vo;

import java.time.LocalDateTime;
import org.apache.commons.lang3.builder.ToStringBuilder;
import com.lsitc.domain.common.user.entity.UserEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserInfoGetResponseVO {

  private final long id;
  private final String userId;
  private final String name;
  private final String email;
  private final String phoneNumber;
  private final long createdBy;
  private final LocalDateTime createdDate;
  private final long lastModifiedBy;
  private final LocalDateTime lastModifiedDate;

  @Builder
  public UserInfoGetResponseVO(long id, String userId, String name, String email,
      String phoneNumber, long createdBy, LocalDateTime createdDate, long lastModifiedBy,
      LocalDateTime lastModifiedDate) {
    this.id = id;
    this.userId = userId;
    this.name = name;
    this.email = email;
    this.phoneNumber = phoneNumber;
    this.createdBy = createdBy;
    this.createdDate = createdDate;
    this.lastModifiedBy = lastModifiedBy;
    this.lastModifiedDate = lastModifiedDate;
  }

  public static UserInfoGetResponseVO of(UserEntity resultEntity) {
    return builder().id(resultEntity.getId()).userId(resultEntity.getUserId())
        .name(resultEntity.getName()).email(resultEntity.getEmail())
        .phoneNumber(resultEntity.getPhoneNumber()).createdBy(resultEntity.getCreatedBy())
        .createdDate(resultEntity.getCreatedDate()).lastModifiedBy(resultEntity.getLastModifiedBy())
        .lastModifiedDate(resultEntity.getLastModifiedDate()).build();
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }
}
