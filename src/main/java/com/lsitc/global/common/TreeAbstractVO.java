package com.lsitc.global.common;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public abstract class TreeAbstractVO {
  private List<TreeAbstractVO> children = new ArrayList<TreeAbstractVO>();
  private Integer level = 1;

  public abstract Long id();

  public abstract Long parentsId();

  public void addChild(TreeAbstractVO child) {
    child.addLevel(this.level);
    this.children.add(child);
  }

  private void addLevel(int parentsLevel) {
    this.level = parentsLevel + 1;
    this.children.forEach(vo -> {
      vo.addLevel(this.level);
    });
  }

}
