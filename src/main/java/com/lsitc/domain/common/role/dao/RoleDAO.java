package com.lsitc.domain.common.role.dao;

import com.lsitc.domain.common.role.entity.RoleEntity;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface RoleDAO {

  RoleEntity selectRoleById(RoleEntity roleEntity);

  List<RoleEntity> selectAll();

  int insertRole(RoleEntity roleEntity);

  int updateRoleById(RoleEntity roleEntity);

  int insertRoleWithId(RoleEntity roleEntity);

  int updateRoleIsDeletedById(RoleEntity roleEntity);
}
