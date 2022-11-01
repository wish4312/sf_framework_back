package com.lsitc.domain.common.menu.vo;

import org.apache.commons.lang3.builder.ToStringBuilder;
import com.lsitc.domain.common.menu.entity.MenuEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MenuAddRequestVO {

  private final String menuNm;
  private final String upMenuId;
  private final String url;
  private final String useFg;
  private final String sortSeq;

  public MenuEntity toEntity() {
    return MenuEntity.builder()
        .name(menuNm)
        .parentsId(upMenuId != null ? Long.valueOf(upMenuId) : null)
        .url(url)
        .isUsed("1".equals(useFg))
        .sortSequence(Integer.valueOf(sortSeq))
        .build();
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }
}