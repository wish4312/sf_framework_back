package com.lsitc.domain.common.user.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.lsitc.domain.common.user.entity.UserEntity;

@Mapper
public interface UserDAO {

  UserEntity selectUserById(UserEntity userEntity);

  List<UserEntity> selectAll();

  int insertUser(UserEntity userEntity);

  int updateUserById(UserEntity userEntity);

  int insertUserWithId(UserEntity userEntity);

  int updateUserIsDeletedById(UserEntity userEntity);
  
  UserEntity selectUserByUserId(String userId);
}
