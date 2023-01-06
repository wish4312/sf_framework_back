package com.lsitc.domain.common.menu.vo;

import java.util.List;
import java.util.stream.Collectors;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.lsitc.domain.common.menu.entity.MenuEntity;
import com.lsitc.global.common.TreeAbstractVO;
import com.lsitc.global.util.TreeUtils;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MainMenuListGetResponseVO {

  private final List<MenuTreeGetResponseVO> menuList;
  private final List<TreeAbstractVO> treeList;

  @Builder
  private MainMenuListGetResponseVO(List<MenuTreeGetResponseVO> menuList,
      List<TreeAbstractVO> treeList) {
    this.menuList = menuList;
    this.treeList = treeList;
  }

  public static MainMenuListGetResponseVO of(List<MenuEntity> menuEntityList, String locale) {
    List<MenuTreeGetResponseVO> menuList = menuEntityList.stream()
        .map(entity -> MenuTreeGetResponseVO.of(entity, locale)).collect(Collectors.toList());
    List<TreeAbstractVO> treeList = TreeUtils.getTree(menuList);
    return builder()
        .menuList(menuList)
        .treeList(treeList)
        .build();
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
  }
}
