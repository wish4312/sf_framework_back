package com.lsitc.domain.common.code.service;

import com.lsitc.domain.common.code.dao.GroupCodeDAO;
import com.lsitc.domain.common.code.entity.GroupCodeEntity;
import com.lsitc.domain.common.code.exception.CodeException;
import com.lsitc.domain.common.code.vo.GroupCodeAddRequestVO;
import com.lsitc.domain.common.code.vo.GroupCodeAddResponseVO;
import com.lsitc.domain.common.code.vo.GroupCodeModifyRequestVO;
import com.lsitc.domain.common.code.vo.GroupCodeModifyResponseVO;
import com.lsitc.domain.common.code.vo.GroupCodeRemoveRequestVO;
import com.lsitc.domain.common.code.vo.GroupCodeRemoveResponseVO;
import com.lsitc.domain.common.code.vo.GroupCodeListSearchRequestVO;
import com.lsitc.domain.common.code.vo.GroupCodeListSearchResponseVO;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@AllArgsConstructor
public class CodeService {

  private final GroupCodeDAO groupCodeDAO;

  public List<GroupCodeListSearchResponseVO> searchGroupCodeList(
      final GroupCodeListSearchRequestVO groupCodeListSearchRequestVO) {
    GroupCodeEntity groupCodeEntity = groupCodeListSearchRequestVO.toEntity();
    log.info(groupCodeEntity.toString());
    List<GroupCodeEntity> groupCodeEntityList = groupCodeDAO.selectGroupCodeByConditions(
        groupCodeEntity);
    return groupCodeEntityList.stream().map(GroupCodeListSearchResponseVO::of)
        .collect(Collectors.toList());
  }

  @Transactional
  public GroupCodeAddResponseVO addGroupCode(
      final List<GroupCodeAddRequestVO> groupCodeAddRequestVOList) {
    List<GroupCodeEntity> groupCodeEntityList =
        groupCodeAddRequestVOList.stream()
            .map(GroupCodeAddRequestVO::toEntity)
            .collect(Collectors.toList());
    int addRows = groupCodeDAO.insertGroupCode(groupCodeEntityList);
    return GroupCodeAddResponseVO.of(addRows);
  }

  @Transactional
  public GroupCodeModifyResponseVO modifyGroupCode(
      final List<GroupCodeModifyRequestVO> groupCodeModifyRequestVOList) {
    List<GroupCodeEntity> groupCodeEntityList =
        groupCodeModifyRequestVOList.stream().map(GroupCodeModifyRequestVO::toEntity)
            .collect(Collectors.toList());
    int upsertRows = groupCodeEntityList.stream().map(this::upsertGroupCode).mapToInt(i -> i).sum();
    if (upsertRows != groupCodeEntityList.size()) {
      throw new CodeException("Upsert 오류");
    }
    return GroupCodeModifyResponseVO.of(upsertRows);
  }

  private int upsertGroupCode(GroupCodeEntity targetEntity) {
    GroupCodeEntity groupCodeEntity = groupCodeDAO.selectGroupCodeById(targetEntity);
    return groupCodeEntity != null ? groupCodeDAO.updateGroupCodeById(targetEntity)
        : groupCodeDAO.insertGroupCodeWithId(targetEntity);
  }

  @Transactional
  public GroupCodeRemoveResponseVO removeGroupCode(
      final List<GroupCodeRemoveRequestVO> groupCodeRemoveRequestVOList) {
    List<GroupCodeEntity> groupCodeEntityList =
        groupCodeRemoveRequestVOList.stream().map(GroupCodeRemoveRequestVO::toEntity)
            .collect(Collectors.toList());
    if (groupCodeEntityList.size() < 1) {
      throw new CodeException("Parameter is empty");
    }
    int deleteRows = groupCodeDAO.deleteGroupCodeById(groupCodeEntityList);
    return GroupCodeRemoveResponseVO.of(deleteRows);
  }
}
