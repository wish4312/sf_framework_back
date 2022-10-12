package com.lsitc.domain.common.role.entity;

import java.time.LocalDateTime;
import com.lsitc.global.auditing.Auditable;
import com.lsitc.global.auditing.SoftDeletable;
import com.lsitc.global.common.BaseAbstractEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoleEntity extends BaseAbstractEntity
    implements Auditable<Long, LocalDateTime>, SoftDeletable<Long, LocalDateTime> {

  private long id;
  private String name;
  private String remark;

  @Builder
  private RoleEntity(long id, String name, String remark) {
    this.id = id;
    this.name = name;
    this.remark = remark;
  }

}
