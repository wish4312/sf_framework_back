package com.lsitc.domain.common.menu.vo;

import java.time.LocalDateTime;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.lsitc.domain.common.menu.entity.MenuEntity;
import com.lsitc.domain.model.BooleanState;
import com.lsitc.global.common.TreeAbstractVO;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MenuTreeGetResponseVO extends TreeAbstractVO {

  private final Long menuId;
  private final String menuNm;
  private final Long upMenuId;
  private final String url;
  private final String useFg;
  private final int sortSeq;
  private final Long regUserNo;
  private final LocalDateTime regDttm;
  private final Long procUserNo;
  private final LocalDateTime procDttm;

  @Builder
  private MenuTreeGetResponseVO(Long menuId, String menuNm, Long upMenuId, String url,
      String useFg, int sortSeq, Long regUserNo, LocalDateTime regDttm, Long procUserNo,
      LocalDateTime procDttm) {
    this.menuId = menuId;
    this.menuNm = menuNm;
    this.upMenuId = upMenuId;
    this.url = url;
    this.useFg = useFg;
    this.sortSeq = sortSeq;
    this.regUserNo = regUserNo;
    this.regDttm = regDttm;
    this.procUserNo = procUserNo;
    this.procDttm = procDttm;
  }

  public static MenuTreeGetResponseVO of(MenuEntity menuEntity) {
    return builder()
        .menuId(menuEntity.getId())
        .menuNm(menuEntity.getName())
        .upMenuId(menuEntity.getParentsId())
        .url(menuEntity.getUrl())
        .useFg(convertBoolean(menuEntity.getIsUsed()))
        .sortSeq(menuEntity.getSortSequence())
        .regUserNo(menuEntity.getCreatedBy())
        .regDttm(menuEntity.getCreatedDate())
        .procUserNo(menuEntity.getLastModifiedBy())
        .procDttm(menuEntity.getLastModifiedDate())
        .build();
  }

  private static String convertBoolean(Boolean booleanValue) {
    return BooleanState.of(booleanValue).getStringValue();
  }
  
  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
  }

  @Override
  public Long id() {
    return Long.valueOf(this.menuId);
  }

  @Override
  public Long parentsId() {
    return this.upMenuId != null ? Long.valueOf(this.upMenuId) : null;
  }
}
