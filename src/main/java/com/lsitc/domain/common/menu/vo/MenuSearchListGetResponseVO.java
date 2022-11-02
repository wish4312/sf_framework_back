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
public class MenuSearchListGetResponseVO {

  private final List<MenuTreeGetResponseVO> menuList;
  private final List<TreeAbstractVO> treeList;

  @Builder
  private MenuSearchListGetResponseVO(List<MenuTreeGetResponseVO> menuList,
      List<TreeAbstractVO> treeList) {
    this.menuList = menuList;
    this.treeList = treeList;
  }

  public static MenuSearchListGetResponseVO of(List<MenuEntity> menuEntityList) {
    List<MenuTreeGetResponseVO> menuList =
        menuEntityList.stream().map(MenuTreeGetResponseVO::of).collect(Collectors.toList());
    List<TreeAbstractVO> treeList = TreeUtils.getTree(menuList);
    return builder().menuList(menuList).treeList(treeList).build();
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
  }
}
