package com.lsitc.domain.common.menu.vo;

import java.time.LocalDateTime;
import com.lsitc.domain.common.menu.entity.MenuEntity;
import com.lsitc.domain.model.BooleanState;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MenuListVO {
  
  private final Long menuId;
  private final String menuNm;
  private final String menuEngNm;
  private final Long upMenuId;
  private final String url;
  private final String useFg;
  private final int sortSeq;
  private final Long regUserNo;
  private final LocalDateTime regDttm;
  private final Long procUserNo;
  private final LocalDateTime procDttm;

  @Builder
  private MenuListVO(Long menuId, String menuNm, String menuEngNm, Long upMenuId, String url,
      String useFg, int sortSeq, Long regUserNo, LocalDateTime regDttm, Long procUserNo,
      LocalDateTime procDttm) {
    this.menuId = menuId;
    this.menuNm = menuNm;
    this.menuEngNm = menuEngNm;
    this.upMenuId = upMenuId;
    this.url = url;
    this.useFg = useFg;
    this.sortSeq = sortSeq;
    this.regUserNo = regUserNo;
    this.regDttm = regDttm;
    this.procUserNo = procUserNo;
    this.procDttm = procDttm;
  }

  public static MenuListVO of(MenuEntity menuEntity) {
    return of(menuEntity, null);
  }

  public static MenuListVO of(MenuEntity menuEntity, String locale) {
    return builder()
        .menuId(menuEntity.getId())
        .menuNm(getNameByLocale(menuEntity, locale))
        .menuEngNm(menuEntity.getEnglishName())
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
  
  private static String getNameByLocale(MenuEntity menuEntity, String locale) {
    return "ko-KR".equals(locale) ? menuEntity.getName()
        : "en-US".equals(locale) ? menuEntity.getEnglishName() : menuEntity.getName();
  }
}