package com.lsitc.global.auditing;

import org.springframework.security.core.context.SecurityContextHolder;
import com.lsitc.domain.common.user.entity.UserEntity;

public enum CurrentUserInfoProvider implements UserProvider<UserEntity, Long> {
  INSTANCE;

  @Override
  public UserEntity getUser() {
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    if (principal instanceof UserEntity) {
      return (UserEntity) principal;
    } else {
      return UserEntity.AnonymousUser();
    }
  }

  @Override
  public Long getId() {
    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    if (principal instanceof UserEntity) {
      UserEntity userEntity = (UserEntity) principal;
      return userEntity.getId();
    } else {
      return UserEntity.AnonymousUser().getId();
    }
  }
}
