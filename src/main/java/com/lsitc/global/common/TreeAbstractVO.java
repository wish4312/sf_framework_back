package com.lsitc.global.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import com.lsitc.global.error.exception.BusinessException;
import com.lsitc.global.error.exception.ErrorCode;
import lombok.Getter;

@Getter
public abstract class TreeAbstractVO implements Cloneable {
  private List<TreeAbstractVO> children;

  public abstract Long getId();
  public abstract Long getParentsId();

  private void addChild(TreeAbstractVO child) {
    if (this.children == null)
      this.children = new ArrayList<TreeAbstractVO>();
    this.children.add(child);
  }

  public static <T extends TreeAbstractVO> List<TreeAbstractVO> getTree(List<T> list)
      throws BusinessException {
    List<TreeAbstractVO> cloneList = new ArrayList<TreeAbstractVO>();
    list.forEach(vo -> {
      try {
        cloneList.add((TreeAbstractVO) vo.clone());
      } catch (CloneNotSupportedException e) {
        throw new BusinessException(e.getMessage(), ErrorCode.INTERNAL_SERVER_ERROR);
      }
    });

    List<TreeAbstractVO> reulstList = new ArrayList<TreeAbstractVO>();
    Map<Long, TreeAbstractVO> idMap =
        cloneList.stream().collect(Collectors.toMap(TreeAbstractVO::getId, Function.identity()));
    cloneList.forEach(vo -> {
      if (vo.getParentsId() == null) {
        reulstList.add(vo);
      } else {
        idMap.get(vo.getParentsId()).addChild(vo);
      }
    });

    return reulstList;
  }

  @Override
  protected Object clone() throws CloneNotSupportedException {
    return super.clone();
  }

}
