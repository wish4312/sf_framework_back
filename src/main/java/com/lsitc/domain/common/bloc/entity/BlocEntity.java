package com.lsitc.domain.common.bloc.entity;

import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BlocEntity {

  private long id;
  private String name;
  private boolean isDeleted;
  private String deletedBy;
  private LocalDateTime deletedDate;
  private String remark;
  private String createdBy;
  private LocalDateTime createdDate;
  private String lastModifiedBy;
  private LocalDateTime lastModifiedDate;

  @Builder
  private BlocEntity(long id, String name, String remark) {
    this.id = id;
    this.name = name;
    this.remark = remark;
  }
}
