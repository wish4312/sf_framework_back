package com.lsitc.domain.common.role.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.lsitc.domain.common.role.dao.RoleDAO;
import com.lsitc.domain.common.role.entity.RoleEntity;
import com.lsitc.domain.common.role.exception.RoleException;
import com.lsitc.domain.common.role.vo.RoleAddRequestVO;
import com.lsitc.domain.common.role.vo.RoleAddResponseVO;
import com.lsitc.domain.common.role.vo.RoleInfoGetRequestVO;
import com.lsitc.domain.common.role.vo.RoleInfoGetResponseVO;
import com.lsitc.domain.common.role.vo.RoleListGetResponseVO;
import com.lsitc.domain.common.role.vo.RoleModifyRequestVO;
import com.lsitc.domain.common.role.vo.RoleModifyResponseVO;
import com.lsitc.domain.common.role.vo.RoleRemoveRequestVO;
import com.lsitc.domain.common.role.vo.RoleRemoveResponseVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class RoleService {

  private final RoleDAO roleDAO;

  public RoleInfoGetResponseVO getRoleInfo(final RoleInfoGetRequestVO roleInfoGetRequestVO) {
    RoleEntity roleEntity = roleInfoGetRequestVO.toEntity();
    log.info(roleEntity.toString());
    RoleEntity roleInfo = roleDAO.selectRoleById(roleEntity);
    return RoleInfoGetResponseVO.of(roleInfo);
  }

  public List<RoleListGetResponseVO> getRoleList() {
    List<RoleEntity> roleEntityList = roleDAO.selectAll();
    return roleEntityList.stream().map(RoleListGetResponseVO::of).collect(Collectors.toList());
  }

  public RoleAddResponseVO addRole(final RoleAddRequestVO roleAddRequestVO) {
    RoleEntity roleEntity = roleAddRequestVO.toEntity();
    log.info(roleEntity.toString());
    int addRows = roleDAO.insertRole(roleEntity);
    return RoleAddResponseVO.of(addRows);
  }

  public RoleModifyResponseVO modifyRole(final RoleModifyRequestVO roleModifyRequestVO) {
    RoleEntity roleEntity = roleModifyRequestVO.toEntity();
    int upsertRows = upsertRole(roleEntity);
    log.info(roleEntity.toString());
    return RoleModifyResponseVO.of(upsertRows);
  }

  private int upsertRole(RoleEntity targetEntity) {
    RoleEntity roleEntity = roleDAO.selectRoleById(targetEntity);
    return roleEntity != null ? roleDAO.updateRoleById(targetEntity)
        : roleDAO.insertRoleWithId(targetEntity);
  }

  public RoleRemoveResponseVO removeRole(final RoleRemoveRequestVO roleRemoveRequestVO)
      throws RoleException {
    RoleEntity roleEntity = roleDAO.selectRoleById(roleRemoveRequestVO.toEntity());
    if (roleEntity == null) {
      throw new RoleException("roleEntity is null");
    }
    roleEntity.delete();
    log.info(roleEntity.toString());
    int deleteRows = roleDAO.updateRoleIsDeletedById(roleEntity);
    return RoleRemoveResponseVO.of(deleteRows);
  }
}
