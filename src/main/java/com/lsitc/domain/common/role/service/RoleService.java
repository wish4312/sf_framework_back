package com.lsitc.domain.common.role.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.lsitc.domain.common.role.dao.RoleDAO;
import com.lsitc.domain.common.role.entity.RoleEntity;
import com.lsitc.domain.common.role.exception.RoleException;
import com.lsitc.domain.common.role.vo.RoleListAddRequestVO;
import com.lsitc.domain.common.role.vo.RoleListAddResponseVO;
import com.lsitc.domain.common.role.vo.RoleListGetResponseVO;
import com.lsitc.domain.common.role.vo.RoleListModifyRequestVO;
import com.lsitc.domain.common.role.vo.RoleListModifyResponseVO;
import com.lsitc.domain.common.role.vo.RoleListRemoveRequestVO;
import com.lsitc.domain.common.role.vo.RoleListRemoveResponseVO;
import com.lsitc.domain.common.role.vo.RoleSearchListGetRequestVO;
import com.lsitc.domain.common.role.vo.RoleSearchListGetResponseVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class RoleService {

  private final RoleDAO roleDAO;

  public List<RoleListGetResponseVO> getRoleList() {
    List<RoleEntity> roleEntityList = roleDAO.selectAll();
    return roleEntityList.stream().map(RoleListGetResponseVO::of).collect(Collectors.toList());
  }

  public List<RoleSearchListGetResponseVO> searchRoleList(
      final RoleSearchListGetRequestVO deptInfoGetRequestVO) {
    RoleEntity roleEntity = deptInfoGetRequestVO.toEntity();
    log.info(roleEntity.toString());
    List<RoleEntity> roleEntityList = roleDAO.selectRoleByConditions(roleEntity);
    return roleEntityList.stream().map(RoleSearchListGetResponseVO::of)
        .collect(Collectors.toList());
  }

  @Transactional
  public RoleListAddResponseVO addRoleList(final List<RoleListAddRequestVO> roleListAddRequestVO) {
    List<RoleEntity> roleEntityList = roleListAddRequestVO.stream()
        .map(RoleListAddRequestVO::toEntity).collect(Collectors.toList());
    log.info(roleEntityList.toString());
    int addRows = roleDAO.insertRoleList(roleEntityList);
    return RoleListAddResponseVO.of(roleEntityList.size(), addRows);
  }

  @Transactional
  public RoleListModifyResponseVO modifyRoleList(
      final List<RoleListModifyRequestVO> roleListModifyRequestVO) {
    List<RoleEntity> roleEntityList = roleListModifyRequestVO.stream()
        .map(RoleListModifyRequestVO::toEntity).collect(Collectors.toList());

    List<RoleEntity> updateList =
        roleEntityList.stream().filter(vo -> isUpdate(vo)).collect(Collectors.toList());
    List<RoleEntity> insertList =
        roleEntityList.stream().filter(vo -> !isUpdate(vo)).collect(Collectors.toList());

    int upsertRows = (updateList.size() > 0 ? roleDAO.updateRoleById(updateList) : 0)
        + (insertList.size() > 0 ? roleDAO.insertRoleWithId(insertList) : 0);

    log.info(roleEntityList.toString());
    return RoleListModifyResponseVO.of(upsertRows);
  }

  private boolean isUpdate(RoleEntity targetEntity) {
    RoleEntity roleEntity = roleDAO.selectRoleById(targetEntity);
    return roleEntity != null;
  }

  @Transactional
  public RoleListRemoveResponseVO removeRoleList(
      final List<RoleListRemoveRequestVO> roleListRemoveRequestVO) throws RoleException {
    List<RoleEntity> roleEntityList =
        roleListRemoveRequestVO.stream().map(RoleListRemoveRequestVO::toEntity)
            .map(vo -> setDelete(vo)).collect(Collectors.toList());

    log.info(roleEntityList.toString());
    int deleteRows = roleDAO.updateRoleIsDeletedById(roleEntityList);
    return RoleListRemoveResponseVO.of(deleteRows);
  }

  private RoleEntity setDelete(RoleEntity targetEntity) {
    RoleEntity roleEntity = roleDAO.selectRoleById(targetEntity);
    if (roleEntity == null) {
      throw new RoleException("roleEntity is null");
    }
    roleEntity.delete();
    return roleEntity;
  }
}
