package com.lsitc.domain.common.user.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.lsitc.domain.common.user.entity.UserEntity;

@Mapper
public interface UserDAO {

  UserEntity selectUserById(UserEntity userEntity);

  UserEntity selectUserByUserId(UserEntity userEntity);

  List<UserEntity> selectAll();

  List<UserEntity> selectUserByConditions(UserEntity userEntity);

  int insertUserList(List<UserEntity> userEntity);

  int updateUserById(List<UserEntity> userEntity);

  int insertUserWithId(List<UserEntity> userEntity);

  int updateUserIsDeletedById(List<UserEntity> userEntity);

}
