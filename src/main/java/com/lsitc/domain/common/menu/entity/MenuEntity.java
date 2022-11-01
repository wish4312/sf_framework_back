package com.lsitc.domain.common.menu.entity;

import java.time.LocalDateTime;
import com.lsitc.global.auditing.Auditable;
import com.lsitc.global.common.BaseAbstractEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MenuEntity extends BaseAbstractEntity implements Auditable<Long, LocalDateTime> {

  private Long id;
  private String name;
  private Long parentsId;
  private String url;
  private boolean isUsed;
  private int sortSequence;

  @Builder
  private MenuEntity(Long id, String name, Long parentsId, String url, boolean isUsed,
      int sortSequence) {
    this.id = id;
    this.name = name;
    this.parentsId = parentsId;
    this.url = url;
    this.isUsed = isUsed;
    this.sortSequence = sortSequence;
  }

}
