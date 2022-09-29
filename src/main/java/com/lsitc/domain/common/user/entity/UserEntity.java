package com.lsitc.domain.common.user.entity;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserEntity {

  private Long id;
  private String userId;
  private String password;
  private String name;
  private String email;
  private String phoneNumber;
  private Long createdBy;
  private LocalDateTime createdDate;
  private Long lastModifiedBy;
  private LocalDateTime lastModifiedDate;
  private boolean isDeleted;
  private Long deletedBy;
  private LocalDateTime deletedDate;

  @Builder
  public UserEntity(Long id, String userId, String password, String name, String email,
      String phoneNumber, Long createdBy, LocalDateTime createdDate, Long lastModifiedBy,
      LocalDateTime lastModifiedDate, boolean isDeleted, Long deletedBy,
      LocalDateTime deletedDate) {
    this.id = id;
    this.userId = userId;
    this.password = password;
    this.name = name;
    this.email = email;
    this.phoneNumber = phoneNumber;
    this.createdBy = createdBy;
    this.createdDate = createdDate;
    this.lastModifiedBy = lastModifiedBy;
    this.lastModifiedDate = lastModifiedDate;
    this.isDeleted = isDeleted;
    this.deletedBy = deletedBy;
    this.deletedDate = deletedDate;
  }

  public void delete() {
    this.isDeleted = true;
  }
}
