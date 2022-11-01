package com.lsitc.domain.common.menu.vo;

import org.apache.commons.lang3.builder.ToStringBuilder;
import com.lsitc.domain.common.menu.entity.MenuEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MenuListGetResponseVO {

  private final String menuId;
  private final String menuNm;
  private final String upMenuId;
  private final String url;
  private final String useFg;
  private final String sortSeq;
  private final String regUserNo;
  private final String regDttm;
  private final String procUserNo;
  private final String procDttm;

  @Builder
  private MenuListGetResponseVO(String menuId, String menuNm, String upMenuId, String url,
      String useFg, String sortSeq, String regUserNo, String regDttm, String procUserNo,
      String procDttm) {
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

  public static MenuListGetResponseVO of(MenuEntity menuEntity) {
    return builder()
        .menuId(String.valueOf(menuEntity.getId()))
        .menuNm(String.valueOf(menuEntity.getName()))
        .upMenuId(String.valueOf(menuEntity.getParentsId()))
        .url(String.valueOf(menuEntity.getUrl()))
        .useFg(menuEntity.isUsed() ? "1" : "0")
        .sortSeq(String.valueOf(menuEntity.getSortSequence()))
        .regUserNo(String.valueOf(menuEntity.getCreatedBy()))
        .regDttm(String.valueOf(menuEntity.getCreatedDate()))
        .procUserNo(String.valueOf(menuEntity.getLastModifiedBy()))
        .procDttm(String.valueOf(menuEntity.getLastModifiedDate()))
        .build();
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }
}
