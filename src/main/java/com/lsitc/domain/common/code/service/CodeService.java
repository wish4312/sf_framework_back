package com.lsitc.domain.common.code.service;

import com.lsitc.domain.common.code.dao.GroupCodeDAO;
import com.lsitc.domain.common.code.entity.GroupCodeEntity;
import com.lsitc.domain.common.code.vo.GroupCodeListGetResponseVO;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class CodeService {

  private final GroupCodeDAO groupCodeDAO;

  public List<GroupCodeListGetResponseVO> getGroupCodeList() {
    List<GroupCodeEntity> groupCodeEntityList = groupCodeDAO.selectAll();
    return groupCodeEntityList.stream().map(GroupCodeListGetResponseVO::of)
        .collect(Collectors.toList());
  }
}
