package com.lsitc.global.common;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;

@Getter
public abstract class TreeAbstractVO implements Cloneable {
  private List<TreeAbstractVO> children;
  private Integer level;

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

  @Override
  protected Object clone() throws CloneNotSupportedException {
    return super.clone();
  }

  public TreeAbstractVO cloneVO() throws CloneNotSupportedException {
    TreeAbstractVO cloneVo = (TreeAbstractVO) this.clone();
    cloneVo.init();
    return cloneVo;
  }

  private void init() {
    this.level = 1;
    this.children = new ArrayList<TreeAbstractVO>();
  }

}
