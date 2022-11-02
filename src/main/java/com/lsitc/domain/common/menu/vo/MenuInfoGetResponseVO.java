package com.lsitc.domain.common.menu.vo;

import java.time.LocalDateTime;
import org.apache.commons.lang3.builder.ToStringBuilder;
import com.lsitc.domain.common.menu.entity.MenuEntity;
import com.lsitc.global.common.TreeAbstractVO;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MenuInfoGetResponseVO extends TreeAbstractVO{

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
  private MenuInfoGetResponseVO(Long menuId, String menuNm, Long upMenuId, String url,
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

  public static MenuInfoGetResponseVO of(MenuEntity menuEntity) {
    return builder()
        .menuId(menuEntity.getId())
        .menuNm(menuEntity.getName())
        .upMenuId(menuEntity.getParentsId())
        .url(menuEntity.getUrl())
        .useFg(menuEntity.isUsed() ? "1" : "0")
        .sortSeq(menuEntity.getSortSequence())
        .regUserNo(menuEntity.getCreatedBy())
        .regDttm(menuEntity.getCreatedDate())
        .procUserNo(menuEntity.getLastModifiedBy())
        .procDttm(menuEntity.getLastModifiedDate())
        .build();
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }

  @Override
  public Long getId() {
    return Long.valueOf(this.menuId);
  }

  @Override
  public Long getParentsId() {
    return this.upMenuId != null ? Long.valueOf(this.upMenuId) : null;
  }
}
