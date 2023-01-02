package com.lsitc.domain.common.menu.vo;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.lsitc.domain.common.menu.entity.MenuEntity;
import com.lsitc.domain.model.BooleanState;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MenuSearchListGetRequestVO {
  
  private String useFg;

  public MenuEntity toEntity() {
    return MenuEntity.builder()
        .isUsed(convertUseFg())
        .build();
  }

  private Boolean convertUseFg() {
    return BooleanState.of(this.useFg).getBooleanValue();
  }
  
  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
  }
}
