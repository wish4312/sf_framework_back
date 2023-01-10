package com.lsitc.domain.common.menu.vo;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.lsitc.domain.common.menu.entity.MenuEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MainMenuListGetRequestVO {
  
  private final String locale;

  public MenuEntity toEntity() {
    return MenuEntity.builder()
        .isUsed(true)
        .build();
  }
  
  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
  }
}
