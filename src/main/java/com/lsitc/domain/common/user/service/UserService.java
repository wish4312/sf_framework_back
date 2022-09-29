package com.lsitc.domain.common.user.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.lsitc.domain.common.user.dao.UserDAO;
import com.lsitc.domain.common.user.entity.UserEntity;
import com.lsitc.domain.common.user.exception.UserException;
import com.lsitc.domain.common.user.vo.UserAddRequestVO;
import com.lsitc.domain.common.user.vo.UserAddResponseVO;
import com.lsitc.domain.common.user.vo.UserInfoGetRequestVO;
import com.lsitc.domain.common.user.vo.UserInfoGetResponseVO;
import com.lsitc.domain.common.user.vo.UserListGetResponseVO;
import com.lsitc.domain.common.user.vo.UserModifyRequestVO;
import com.lsitc.domain.common.user.vo.UserModifyResponseVO;
import com.lsitc.domain.common.user.vo.UserRemoveRequestVO;
import com.lsitc.domain.common.user.vo.UserRemoveResponseVO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class UserService {

  private final UserDAO userDAO;

  public UserInfoGetResponseVO getUserInfo(final UserInfoGetRequestVO userInfoGetRequestVO) {
    UserEntity userEntity = userInfoGetRequestVO.toEntity();
    log.info(userEntity.toString());
    UserEntity userInfo = userDAO.selectUserById(userEntity);
    return UserInfoGetResponseVO.of(userInfo);
  }

  public List<UserListGetResponseVO> getUserList() {
    List<UserEntity> userEntityList = userDAO.selectAll();
    return userEntityList.stream().map(UserListGetResponseVO::of).collect(Collectors.toList());
  }

  public UserAddResponseVO addUser(final UserAddRequestVO userAddRequestVO) {
    UserEntity userEntity = userAddRequestVO.toEntity();
    log.info(userEntity.toString());
    int addRows = userDAO.insertUser(userEntity); // userId가 UNIQUE KEY로 잡혀있어 중복되는 userId의 경우 에러 반환
    return UserAddResponseVO.of(addRows);
  }

  public UserModifyResponseVO modifyUser(final UserModifyRequestVO userModifyRequestVO) {
    UserEntity userEntity = userModifyRequestVO.toEntity();
    int upsertRows = upsertUser(userEntity);
    log.info(userEntity.toString());
    return UserModifyResponseVO.of(upsertRows);
  }

  private int upsertUser(UserEntity targetEntity) {
    UserEntity userEntity = userDAO.selectUserById(targetEntity);
    return userEntity != null ? userDAO.updateUserById(targetEntity)
        : userDAO.insertUserWithId(targetEntity);
  }

  // Delete시 flag 변경
  public UserRemoveResponseVO removeUser(final UserRemoveRequestVO userRemoveRequestVO)
      throws UserException {
    UserEntity userEntity = userDAO.selectUserById(userRemoveRequestVO.toEntity());
    if (userEntity == null) {
      throw new UserException();
    }
    userEntity.delete();
    log.info(userEntity.toString());
    int deleteRows = userDAO.updateUserIsDeletedById(userEntity);
    return UserRemoveResponseVO.of(deleteRows);
  }
}
