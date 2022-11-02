package com.lsitc.global.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import com.lsitc.global.error.exception.BusinessException;
import com.lsitc.global.error.exception.ErrorCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class TreeAbstractVO implements Cloneable {
  private List<TreeAbstractVO> children;
  private Integer level;

  public abstract Long getId();

  public abstract Long getParentsId();

  private void addChild(TreeAbstractVO child) {
    child.addLevel(this.level);
    this.children.add(child);
  }

  private void addLevel(int parentsLevel) {
    this.level = parentsLevel + 1;
    this.children.forEach(vo -> {
      vo.addLevel(this.level);
    });
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
    TreeAbstractVO cloneVo = (TreeAbstractVO) super.clone();
    cloneVo.setLevel(1);
    cloneVo.setChildren(new ArrayList<TreeAbstractVO>());
    return cloneVo;
  }

}
