package com.lsitc.domain.common.menu.entity;

import java.time.LocalDateTime;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
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
  private String englishName;
  private Long parentsId;
  private String url;
  private Boolean isUsed;
  private int sortSequence;

  @Builder
  private MenuEntity(Long id, String name, String englishName, Long parentsId, String url,
      Boolean isUsed, int sortSequence) {
    this.id = id;
    this.name = name;
    this.englishName = englishName;
    this.parentsId = parentsId;
    this.url = url;
    this.isUsed = isUsed;
    this.sortSequence = sortSequence;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
  }

}
