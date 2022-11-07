package com.lsitc.domain.common.menu.vo;

import org.apache.commons.lang3.builder.ToStringBuilder;
import com.lsitc.domain.common.menu.entity.MenuEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MenuSearchListGetRequestVO {
  
  private String useFg;

  public MenuEntity toEntity() {
    return MenuEntity.builder()
        .isUsed(useFg != null ? (useFg.equals("1")) : null)
        .build();
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }
}
