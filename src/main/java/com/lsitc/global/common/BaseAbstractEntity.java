package com.lsitc.global.common;

import com.lsitc.global.auditing.Auditable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class BaseAbstractEntity implements Auditable<Long, LocalDateTime> {

  private Long createdBy;
  private LocalDateTime createdDate;
  private Long lastModifiedBy;
  private LocalDateTime lastModifiedDate;
}
