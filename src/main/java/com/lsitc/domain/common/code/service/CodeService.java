package com.lsitc.domain.common.code.service;

import com.lsitc.domain.common.code.dao.GroupCodeDAO;
import com.lsitc.domain.common.code.entity.GroupCodeEntity;
import com.lsitc.domain.common.code.exception.CodeException;
import com.lsitc.domain.common.code.vo.GroupCodeAddRequestVO;
import com.lsitc.domain.common.code.vo.GroupCodeAddResponseVO;
import com.lsitc.domain.common.code.vo.GroupCodeInfoGetRequestVO;
import com.lsitc.domain.common.code.vo.GroupCodeInfoGetResponseVO;
import com.lsitc.domain.common.code.vo.GroupCodeListAddRequestVO;
import com.lsitc.domain.common.code.vo.GroupCodeListAddResponseVO;
import com.lsitc.domain.common.code.vo.GroupCodeListGetResponseVO;
import com.lsitc.domain.common.code.vo.GroupCodeListModifyRequestVO;
import com.lsitc.domain.common.code.vo.GroupCodeListModifyResponseVO;
import com.lsitc.domain.common.code.vo.GroupCodeListRemoveRequestVO;
import com.lsitc.domain.common.code.vo.GroupCodeListRemoveResponseVO;
import com.lsitc.domain.common.code.vo.GroupCodeModifyRequestVO;
import com.lsitc.domain.common.code.vo.GroupCodeModifyResponseVO;
import com.lsitc.domain.common.code.vo.GroupCodeRemoveRequestVO;
import com.lsitc.domain.common.code.vo.GroupCodeRemoveResponseVO;
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

  public GroupCodeInfoGetResponseVO getGroupCodeInfo(
      final GroupCodeInfoGetRequestVO groupCodeInfoGetRequestVO) {
    GroupCodeEntity groupCodeEntity = groupCodeInfoGetRequestVO.toEntity();
    log.info(groupCodeEntity.toString());
    GroupCodeEntity groupCodeInfo = groupCodeDAO.selectGroupCodeById(groupCodeEntity);
    return GroupCodeInfoGetResponseVO.of(groupCodeInfo);
  }

  public List<GroupCodeListGetResponseVO> getGroupCodeList() {
    List<GroupCodeEntity> groupCodeEntityList = groupCodeDAO.selectAll();
    return groupCodeEntityList.stream().map(GroupCodeListGetResponseVO::of)
        .collect(Collectors.toList());
  }

  @Transactional
  public GroupCodeAddResponseVO addGroupCode(final GroupCodeAddRequestVO groupCodeAddRequestVO) {
    GroupCodeEntity groupCodeEntity = groupCodeAddRequestVO.toEntity();
    log.info(groupCodeEntity.toString());
    int addRows = groupCodeDAO.insertGroupCode(groupCodeEntity);
    return GroupCodeAddResponseVO.of(addRows);
  }

  @Transactional
  public GroupCodeListAddResponseVO addGroupCodeList(
      final List<GroupCodeListAddRequestVO> groupCodeListAddRequestVOList) {
    List<GroupCodeEntity> groupCodeEntityList =
        groupCodeListAddRequestVOList.stream().map(GroupCodeListAddRequestVO::toEntity)
            .collect(Collectors.toList());
    int addRows = groupCodeDAO.insertGroupCodeList(groupCodeEntityList);
    return GroupCodeListAddResponseVO.of(addRows);
  }

  @Transactional
  public GroupCodeModifyResponseVO modifyGroupCode(
      final GroupCodeModifyRequestVO groupCodeModifyRequestVO) {
    GroupCodeEntity groupCodeEntity = groupCodeModifyRequestVO.toEntity();
    int upsertRows = upsertGroupCode(groupCodeEntity);
    return GroupCodeModifyResponseVO.of(upsertRows);
  }

  @Transactional
  public GroupCodeListModifyResponseVO modifyGroupCodeList(
      final List<GroupCodeListModifyRequestVO> groupCodeListModifyRequestVOList) {
    List<GroupCodeEntity> groupCodeEntityList =
        groupCodeListModifyRequestVOList.stream().map(GroupCodeListModifyRequestVO::toEntity)
            .collect(Collectors.toList());
    int upsertRows = groupCodeEntityList.stream().map(this::upsertGroupCode).mapToInt(i -> i).sum();
    if (upsertRows != groupCodeEntityList.size()) {
      throw new CodeException("Upsert 오류");
    }
    return GroupCodeListModifyResponseVO.of(upsertRows);
  }

  private int upsertGroupCode(GroupCodeEntity targetEntity) {
    GroupCodeEntity groupCodeEntity = groupCodeDAO.selectGroupCodeById(targetEntity);
    return groupCodeEntity != null ? groupCodeDAO.updateGroupCodeById(targetEntity)
        : groupCodeDAO.insertGroupCodeWithId(targetEntity);
  }

  @Transactional
  public GroupCodeRemoveResponseVO removeGroupCode(
      final GroupCodeRemoveRequestVO groupCodeRemoveRequestVO) throws CodeException {
    GroupCodeEntity groupCodeEntity = groupCodeRemoveRequestVO.toEntity();
    log.info(groupCodeEntity.toString());
    int deleteRows = groupCodeDAO.deleteGroupCodeById(groupCodeEntity);
    return GroupCodeRemoveResponseVO.of(deleteRows);
  }

  @Transactional
  public GroupCodeListRemoveResponseVO removeGroupCodeList(
      final List<GroupCodeListRemoveRequestVO> groupCodeListRemoveRequestVOList) {
    List<GroupCodeEntity> groupCodeEntityList =
        groupCodeListRemoveRequestVOList.stream().map(GroupCodeListRemoveRequestVO::toEntity)
            .collect(Collectors.toList());
    if (groupCodeEntityList.size() < 1) {
      throw new CodeException("Parameter is empty");
    }
    int deleteRows = groupCodeDAO.deleteGroupCodeListById(groupCodeEntityList);
    return GroupCodeListRemoveResponseVO.of(deleteRows);
  }
}
