package com.lsitc.global.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import com.lsitc.global.common.TreeAbstractVO;
import com.lsitc.global.error.exception.BusinessException;
import com.lsitc.global.error.exception.ErrorCode;

public class TreeUtils {

  public static <T extends TreeAbstractVO> List<TreeAbstractVO> getTree(List<T> list)
      throws BusinessException {
    List<TreeAbstractVO> cloneList = new ArrayList<TreeAbstractVO>();
    list.forEach(vo -> {
      try {
        cloneList.add(vo.cloneVO());
      } catch (CloneNotSupportedException e) {
        throw new BusinessException(e.getMessage(), ErrorCode.INTERNAL_SERVER_ERROR);
      }
    });

    List<TreeAbstractVO> reulstList = new ArrayList<TreeAbstractVO>();
    Map<Long, TreeAbstractVO> idMap =
        cloneList.stream().collect(Collectors.toMap(TreeAbstractVO::id, Function.identity()));
    cloneList.forEach(vo -> {
      if (vo.parentsId() == null) {
        reulstList.add(vo);
      } else {
        idMap.get(vo.parentsId()).addChild(vo);
      }
    });

    return reulstList;
  }

}
