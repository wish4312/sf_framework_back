package com.lsitc.domain.common.menu.vo;

import java.util.List;
import org.apache.commons.lang3.builder.ToStringBuilder;
import com.lsitc.global.common.TreeAbstractVO;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MenuSearchListGetResponseVO {

  private final List<MenuInfoGetResponseVO> menuList;
  private final List<TreeAbstractVO> treeList;

  @Builder
  private MenuSearchListGetResponseVO(List<MenuInfoGetResponseVO> menuList,
      List<TreeAbstractVO> treeList) {
    this.menuList = menuList;
    this.treeList = treeList;
  }

  public static MenuSearchListGetResponseVO of(List<MenuInfoGetResponseVO> menuList,
      List<TreeAbstractVO> treeList) {
    return builder().menuList(menuList).treeList(treeList).build();
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }
}
